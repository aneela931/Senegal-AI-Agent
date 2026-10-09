# Clone the Senegal Agent App test-case workbook and replace data rows.
# Does not modify the template. Styles, widths, freeze panes, and the header stay as copied.
param(
    [Parameter(Mandatory = $true)][string]$TemplatePath,
    [Parameter(Mandatory = $true)][string]$OutputPath,
    [Parameter(Mandatory = $true)][string]$InputJson
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$expectedHeaders = @(
    'ID', 'UseCase/Feature', 'Test Case Summary', 'Preconditions', 'Input',
    'Expected Result', 'Actual Result', 'Test Type', 'Status', 'Priority',
    'Environment', 'Build version', 'Assignee', 'Source / Traceability',
    'Automation', 'Defect ID', 'Execution Date'
)

function Escape-XmlText([string]$text) {
    if ($null -eq $text) { return '' }
    return [System.Security.SecurityElement]::Escape($text)
}

function Get-ZipText($zipPath, $entryName) {
    $zip = [System.IO.Compression.ZipFile]::OpenRead($zipPath)
    try {
        $entry = $zip.Entries | Where-Object { $_.FullName -eq $entryName } | Select-Object -First 1
        if (-not $entry) { throw "Missing zip entry $entryName" }
        $reader = New-Object System.IO.StreamReader($entry.Open())
        try { return $reader.ReadToEnd() } finally { $reader.Close() }
    } finally { $zip.Dispose() }
}

function Set-ZipText($zipPath, $entryName, [string]$text) {
    $zip = [System.IO.Compression.ZipFile]::Open($zipPath, [System.IO.Compression.ZipArchiveMode]::Update)
    try {
        $entry = $zip.Entries | Where-Object { $_.FullName -eq $entryName } | Select-Object -First 1
        if ($entry) { $entry.Delete() }
        $created = $zip.CreateEntry($entryName)
        $utf8 = New-Object System.Text.UTF8Encoding $false
        $writer = New-Object System.IO.StreamWriter($created.Open(), $utf8)
        try { $writer.Write($text) } finally { $writer.Close() }
    } finally { $zip.Dispose() }
}

function Get-SharedStrings([string]$sstXml) {
    $list = New-Object System.Collections.Generic.List[string]
    $matches = [regex]::Matches($sstXml, '<si>(.*?)</si>', 'Singleline')
    foreach ($m in $matches) {
        $inner = $m.Groups[1].Value
        $texts = [regex]::Matches($inner, '<t[^>]*>(.*?)</t>', 'Singleline')
        $joined = ''
        foreach ($t in $texts) { $joined += [System.Net.WebUtility]::HtmlDecode($t.Groups[1].Value) }
        $list.Add($joined)
    }
    return $list
}

if (-not (Test-Path -LiteralPath $TemplatePath)) { throw "Template not found: $TemplatePath" }
$templateFull = (Resolve-Path -LiteralPath $TemplatePath).Path
$outputFull = [System.IO.Path]::GetFullPath($OutputPath)
if ($templateFull -eq $outputFull) { throw 'Refusing to overwrite the template workbook.' }

$payload = Get-Content -LiteralPath $InputJson -Raw -Encoding UTF8 | ConvertFrom-Json
if (-not $payload.sheetName) { throw 'Input JSON needs sheetName.' }
$rows = @($payload.cases)
if ($rows.Count -lt 1) { throw 'Input JSON needs at least one case.' }

$sheetName = [string]$payload.sheetName
if ($sheetName.Length -gt 31) { throw "Sheet name longer than 31 characters: $sheetName" }
if ($sheetName -match '[:\\/\?\*\[\]]') { throw "Sheet name has illegal characters: $sheetName" }

$outDir = Split-Path -Parent $outputFull
if ($outDir -and -not (Test-Path -LiteralPath $outDir)) {
    New-Item -ItemType Directory -Path $outDir | Out-Null
}
Copy-Item -LiteralPath $templateFull -Destination $outputFull -Force

$strings = Get-SharedStrings (Get-ZipText $outputFull 'xl/sharedStrings.xml')
for ($i = 0; $i -lt $expectedHeaders.Count; $i++) {
    if ($strings[$i] -ne $expectedHeaders[$i]) {
        throw "Template header column $($i + 1) is '$($strings[$i])', expected '$($expectedHeaders[$i])'."
    }
}

$sheetXml = Get-ZipText $outputFull 'xl/worksheets/sheet1.xml'
$headerMatch = [regex]::Match($sheetXml, '<row r="1"[^>]*>.*?</row>', 'Singleline')
if (-not $headerMatch.Success) { throw 'Header row was not found in the template sheet.' }

$keys = @(
    'id', 'feature', 'summary', 'preconditions', 'input', 'expected', 'actual',
    'testType', 'status', 'priority', 'environment', 'build', 'assignee',
    'traceability', 'automation', 'defectId', 'executionDate'
)
$letters = @('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q')

$nextIndex = $strings.Count
$newStrings = New-Object System.Collections.Generic.List[string]
$dataRows = New-Object System.Collections.Generic.List[string]
$excelRow = 2
foreach ($case in $rows) {
    if (-not $case.id) { throw 'Every case needs an id.' }
    $cells = New-Object System.Collections.Generic.List[string]
    for ($c = 0; $c -lt $keys.Count; $c++) {
        $value = [string]$case.($keys[$c])
        $ref = '{0}{1}' -f $letters[$c], $excelRow
        if ([string]::IsNullOrEmpty($value)) {
            $cells.Add(('<c r="{0}" s="2"/>' -f $ref))
        } else {
            $newStrings.Add($value)
            $cells.Add(('<c r="{0}" s="2" t="s"><v>{1}</v></c>' -f $ref, $nextIndex))
            $nextIndex++
        }
    }
    $dataRows.Add(('<row r="{0}" ht="45" customHeight="1" spans="1:17" s="2" customFormat="1" x14ac:dyDescent="0.25">{1}</row>' -f $excelRow, ($cells -join '')))
    $excelRow++
}

$lastRow = $excelRow - 1
$sheetData = '<sheetData>' + $headerMatch.Value + ($dataRows -join '') + '</sheetData>'
$sheetXml = [regex]::Replace($sheetXml, '<dimension ref="[^"]*"/>', ('<dimension ref="A1:Q{0}"/>' -f $lastRow))
$sheetXml = [regex]::Replace($sheetXml, '<autoFilter ref="[^"]*"/>', ('<autoFilter ref="A1:Q{0}"/>' -f $lastRow))
$sheetXml = [regex]::Replace($sheetXml, '<sheetData>.*?</sheetData>', $sheetData, 'Singleline')
Set-ZipText $outputFull 'xl/worksheets/sheet1.xml' $sheetXml

$sst = Get-ZipText $outputFull 'xl/sharedStrings.xml'
$insert = ''
foreach ($text in $newStrings) {
    $insert += '<si><t xml:space="preserve">{0}</t></si>' -f (Escape-XmlText $text)
}
if ($sst -notmatch '</sst>') { throw 'sharedStrings.xml has no closing sst tag.' }
$sst = $sst.Replace('</sst>', $insert + '</sst>')
$unique = $strings.Count + $newStrings.Count
$sst = [regex]::Replace($sst, 'uniqueCount="\d+"', ('uniqueCount="{0}"' -f $unique), 1)
$sst = [regex]::Replace($sst, 'count="\d+"', ('count="{0}"' -f $unique), 1)
Set-ZipText $outputFull 'xl/sharedStrings.xml' $sst

$workbook = Get-ZipText $outputFull 'xl/workbook.xml'
$escapedSheet = Escape-XmlText $sheetName
$workbook2 = [regex]::Replace($workbook, '(<sheet\b[^>]*\bname=")[^"]*(")', ('$1{0}$2' -f $escapedSheet), 1)
if ($workbook2 -eq $workbook -and $workbook -notmatch ('name="' + [regex]::Escape($sheetName) + '"')) {
    throw 'Could not rename the worksheet.'
}
if ($workbook2 -ne $workbook) { Set-ZipText $outputFull 'xl/workbook.xml' $workbook2 }

Write-Output "Wrote $outputFull rows=$($rows.Count) sheet=$sheetName"
