# Put the app under test in a cold-start state before a local device run.
# - App installed  -> force-stop it and clear its data/cache (pm clear), so the first case starts on the splash screen.
# - App missing    -> install LOCAL_APK_PATH from Profiles/local.glbl (adb install -r -g).
# Also refuses to continue when a Keywords/*.groovy file is newer than its compiled class in bin/keyword:
# Studio only recompiles keywords after the project is refreshed (F5), and a run with stale classes
# silently executes old code (run 20261008_000213).
# Exit 2 when no device is ready, 3 when the APK file is missing, 4 when adb reports an error, 5 when keywords are stale.
param(
    [string]$ProjectDir = (Resolve-Path (Join-Path $PSScriptRoot '..\..\..\..')).Path,
    [string]$Serial = ''
)
$ErrorActionPreference = 'Stop'

$kwRoot = Join-Path $ProjectDir 'Keywords'
$binRoot = Join-Path $ProjectDir 'bin\keyword'
$stale = @()
if (Test-Path -LiteralPath $kwRoot) {
    Get-ChildItem -LiteralPath $kwRoot -Recurse -Filter *.groovy | ForEach-Object {
        $rel = $_.FullName.Substring($kwRoot.Length + 1) -replace '\.groovy$', '.class'
        $cls = Join-Path $binRoot $rel
        if (-not (Test-Path -LiteralPath $cls) -or (Get-Item -LiteralPath $cls).LastWriteTime -lt $_.LastWriteTime) { $stale += $rel }
    }
}
if ($stale.Count -gt 0) {
    Write-Output 'KEYWORDS_STALE Studio has not compiled these keyword changes yet:'
    $stale | ForEach-Object { Write-Output "  $_" }
    Write-Output 'In Katalon Studio: select the project in Tests Explorer, press F5 (Refresh), wait for the build to finish, then run this script again.'
    exit 5
}

$adb = Get-Command adb -ErrorAction SilentlyContinue
if ($adb) { $adb = $adb.Source } elseif (Test-Path -LiteralPath 'C:\sdk\platform-tools\adb.exe') { $adb = 'C:\sdk\platform-tools\adb.exe' }
if (-not $adb) { Write-Output 'DEVICE_STATUS none (adb not found)'; exit 2 }

$devices = @((& $adb devices) -split "`r?`n" | Where-Object { $_ -match '^\S+\s+device$' } | ForEach-Object { ($_ -split '\s+')[0] })
if ($devices.Count -lt 1) { Write-Output 'DEVICE_STATUS none'; exit 2 }
if (-not $Serial) { $Serial = $devices[0] }
if ($devices -notcontains $Serial) { Write-Output "DEVICE_STATUS serial $Serial not ready"; exit 2 }

[xml]$profile = Get-Content -LiteralPath (Join-Path $ProjectDir 'Profiles\local.glbl') -Raw -Encoding UTF8
function Get-Var([string]$name) {
    $node = $profile.GlobalVariableEntities.GlobalVariableEntity | Where-Object { $_.name -eq $name }
    if (-not $node) { throw "Variable $name not found in Profiles/local.glbl" }
    return $node.initValue.Trim().Trim("'").Trim('"')
}
$package = Get-Var 'APP_PACKAGE'
$apkRel = Get-Var 'LOCAL_APK_PATH'
$apk = Join-Path $ProjectDir $apkRel

$installed = (& $adb -s $Serial shell pm list packages $package 2>&1 | Out-String) -match ('package:' + [regex]::Escape($package) + '(\s|$)')

if ($installed) {
    & $adb -s $Serial shell am force-stop $package | Out-Null
    $clear = (& $adb -s $Serial shell pm clear $package 2>&1 | Out-String).Trim()
    if ($clear -ne 'Success') { Write-Output "APP_STATE error: pm clear returned '$clear'"; exit 4 }
    # pm clear also revokes runtime permissions. Re-grant the ones the APK requests so the state matches 'adb install -g'.
    $dump = & $adb -s $Serial shell dumpsys package $package 2>&1 | Out-String
    $requested = [regex]::Matches($dump, 'android\.permission\.[A-Z_]+') | ForEach-Object { $_.Value } | Select-Object -Unique
    $granted = @()
    $ErrorActionPreference = 'Continue'
    foreach ($perm in $requested) {
        $r = (& $adb -s $Serial shell pm grant $package $perm 2>&1 | Out-String).Trim()
        if (-not $r) { $granted += $perm }   # pm grant prints nothing on success; non-runtime permissions print an error and are skipped
    }
    $ErrorActionPreference = 'Stop'
    Write-Output "APP_STATE cleared package=$package serial=$Serial permissionsGranted=$($granted.Count)"
} else {
    if (-not (Test-Path -LiteralPath $apk)) { Write-Output "APP_STATE error: APK not found at $apk"; exit 3 }
    $install = (& $adb -s $Serial install -r -g $apk 2>&1 | Out-String).Trim()
    if ($install -notmatch 'Success') { Write-Output "APP_STATE error: install returned '$install'"; exit 4 }
    Write-Output "APP_STATE installed package=$package apk=$apkRel serial=$Serial"
}
exit 0
