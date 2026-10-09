# Coverage counts from an explicit mapping. Refuses to emit a percentage without that mapping.
param(
    [Parameter(Mandatory = $true)][string]$InputJson,
    [Parameter(Mandatory = $true)][string]$OutputPath,
    [string]$JiraKey = ''
)

$ErrorActionPreference = 'Stop'
$data = Get-Content -LiteralPath $InputJson -Raw -Encoding UTF8 | ConvertFrom-Json
$requirements = @($data.requirements)
$cases = @($data.cases)
if ($requirements.Count -lt 1) { throw 'requirements must be a non-empty array.' }

$reqIds = @{}
foreach ($req in $requirements) {
    if (-not $req.id) { throw 'A requirement is missing id.' }
    if ($reqIds.ContainsKey([string]$req.id)) { throw "Duplicate requirement id $($req.id)." }
    $reqIds[[string]$req.id] = $true
}

$techniques = @('positive', 'negative', 'boundary', 'other')
$executions = @('passed', 'failed', 'blocked', 'not_run')
foreach ($case in $cases) {
    if (-not $case.id) { throw 'A case is missing id.' }
    $linked = @($case.reqs)
    if ($linked.Count -lt 1) { throw "Case $($case.id) has no requirement id." }
    foreach ($rid in $linked) {
        if (-not $reqIds.ContainsKey([string]$rid)) { throw "Case $($case.id) cites unknown requirement $rid." }
    }
    if ($techniques -notcontains [string]$case.technique) {
        throw "Case $($case.id) technique must be one of: $($techniques -join ', ')."
    }
    if ($executions -notcontains [string]$case.execution) {
        throw "Case $($case.id) execution must be one of: $($executions -join ', ')."
    }
}

function Count-Cases($predicate) {
    $n = 0
    foreach ($case in $cases) { if (& $predicate $case) { $n++ } }
    return $n
}

$testable = @($requirements | Where-Object { $_.testable -eq $true })
$covered = 0
$blockedReqs = 0
$matrix = New-Object System.Collections.Generic.List[string]

foreach ($req in $requirements) {
    $rid = [string]$req.id
    $mapped = @($cases | Where-Object { @($_.reqs) -contains $rid })
    $ids = @($mapped | ForEach-Object { $_.id }) -join ', '
    if (-not $ids) { $ids = '-' }

    $nonBlocked = @($mapped | Where-Object { $_.blockedRequirement -ne $true })
    $coverage = 'Gap'
    $onlyBlockedCases = ($mapped.Count -gt 0 -and $nonBlocked.Count -eq 0)
    if ($req.blocked -eq $true -or ($req.testable -eq $true -and $onlyBlockedCases)) {
        $coverage = 'Blocked'
        $blockedReqs++
    } elseif ($req.testable -ne $true) {
        $coverage = 'Not testable'
    } elseif ($nonBlocked.Count -gt 0) {
        $coverage = 'Covered'
        $covered++
    }

    $candidates = @($mapped | Where-Object { $_.automationCandidate -eq $true }).Count
    $automated = @($mapped | Where-Object { $_.automated -eq $true }).Count
    if ($candidates -eq 0) { $autoCell = 'Not a candidate' }
    else { $autoCell = "Automated $automated / Candidates $candidates" }

    $passed = @($mapped | Where-Object { $_.execution -eq 'passed' }).Count
    $failed = @($mapped | Where-Object { $_.execution -eq 'failed' }).Count
    $blockedEx = @($mapped | Where-Object { $_.execution -eq 'blocked' }).Count
    $notRun = @($mapped | Where-Object { $_.execution -eq 'not_run' }).Count
    $execCell = "Passed $passed, Failed $failed, Blocked $blockedEx, Not run $notRun"

    $matrix.Add("| $rid | $ids | $coverage | $autoCell | $execCell |")
}

$testableCount = $testable.Count
if ($testableCount -eq 0) {
    $percent = 'n/a (no testable requirements)'
} else {
    $pct = [math]::Round((100.0 * $covered / $testableCount), 1)
    $percent = "$pct% ($covered / $testableCount testable)"
}

$pending = @($cases | Where-Object {
    $_.automationCandidate -eq $true -and $_.automated -ne $true -and $_.blockedRequirement -ne $true
}).Count

$title = if ($JiraKey) { "# Coverage - $JiraKey" } else { '# Coverage' }
$lines = @(
    $title,
    '',
    'Counts were produced by scripts/compute-coverage.ps1 from the mapping JSON. They were not estimated.',
    '',
    '## Totals',
    '',
    '| Metric | Count |',
    '| --- | --- |',
    "| Total requirements | $($requirements.Count) |",
    "| Testable requirements | $testableCount |",
    "| Requirements covered | $covered |",
    "| Requirements blocked | $blockedReqs |",
    "| Requirement coverage | $percent |",
    "| Total test cases | $($cases.Count) |",
    "| Positive cases | $(@($cases | Where-Object { $_.technique -eq 'positive' }).Count) |",
    "| Negative cases | $(@($cases | Where-Object { $_.technique -eq 'negative' }).Count) |",
    "| Boundary cases | $(@($cases | Where-Object { $_.technique -eq 'boundary' }).Count) |",
    "| Other techniques | $(@($cases | Where-Object { $_.technique -eq 'other' }).Count) |",
    "| Automation candidates | $(@($cases | Where-Object { $_.automationCandidate -eq $true }).Count) |",
    "| Automated | $(@($cases | Where-Object { $_.automated -eq $true }).Count) |",
    "| Pending automation | $pending |",
    "| Execution passed | $(@($cases | Where-Object { $_.execution -eq 'passed' }).Count) |",
    "| Execution failed | $(@($cases | Where-Object { $_.execution -eq 'failed' }).Count) |",
    "| Execution blocked | $(@($cases | Where-Object { $_.execution -eq 'blocked' }).Count) |",
    "| Execution not run | $(@($cases | Where-Object { $_.execution -eq 'not_run' }).Count) |",
    '',
    '## Matrix',
    '',
    '| Requirement ID | Test Cases | Coverage | Automation | Execution |',
    '| --- | --- | --- | --- | --- |'
)
$lines += $matrix

$outDir = Split-Path -Parent ([System.IO.Path]::GetFullPath($OutputPath))
if ($outDir -and -not (Test-Path -LiteralPath $outDir)) {
    New-Item -ItemType Directory -Path $outDir | Out-Null
}
$utf8 = New-Object System.Text.UTF8Encoding $false
[System.IO.File]::WriteAllLines($OutputPath, $lines, $utf8)
Write-Output "Wrote $OutputPath coverage=$percent"
