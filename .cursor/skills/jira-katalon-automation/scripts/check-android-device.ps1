# Print attached Android devices. Exit 2 when none are ready.
$ErrorActionPreference = 'Stop'
$adb = Get-Command adb -ErrorAction SilentlyContinue
if (-not $adb) {
    $fallback = 'C:\sdk\platform-tools\adb.exe'
    if (Test-Path -LiteralPath $fallback) { $adb = $fallback } else { $adb = $null }
} else {
    $adb = $adb.Source
}
if (-not $adb) {
    Write-Output 'DEVICE_STATUS none'
    Write-Output 'adb was not found on PATH or at C:\sdk\platform-tools\adb.exe.'
    exit 2
}

$output = & $adb devices -l 2>&1 | Out-String
Write-Output $output.TrimEnd()
$ready = @($output -split "`r?`n" | Where-Object { $_ -match '^\S+\s+device(\s|$)' })
if ($ready.Count -lt 1) {
    Write-Output 'DEVICE_STATUS none'
    exit 2
}
Write-Output ("DEVICE_STATUS ready count={0}" -f $ready.Count)
exit 0
