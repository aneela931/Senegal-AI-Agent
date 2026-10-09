# APK and versions

## Layout

```text
App/
  1.0/
    Senegal_AgentApp_1.0-92-30-Sept-2026-protected.apk
```

Folder name is the app version. The file name is the original APK name.

## Resolution rule

`ApkResolver` scans `App/*/`, compares folder names as semantic versions, and uses the **highest** unless a pin exists.

Pins (none today):

- Profile `APK_VERSION` (not present on cloud/local)
- Env JSON `apkVersion` (not present on `staging.json`)

At suite/case start the listener sets `LOCAL_APK_PATH` to `App/<version>/<file>.apk` so **local** `CloudApp.launch()` installs that file. **Cloud** still uses TestCloud + `CLOUD_APP_ID`; the resolver does not install from disk on cloud.

## Adding a new APK

1. Create `App/<version>/`.
2. Place the `.apk` there.
3. Leave unpinned to pick it up as latest, or set a pin if a run must stay on an older build.
4. Keep `CLOUD_APP_ID` / `EXPECTED_VERSION` / `EXPECTED_BUILD` in sync with the Applications row when the cloud binary changes (create-katalon-project / TestOps). Do not write API keys into the project.

## Local install path

Profile value today: `App/1.0/Senegal_AgentApp_1.0-92-30-Sept-2026-protected.apk`. After a new version folder appears, the resolver updates `LOCAL_APK_PATH` at runtime for local runs.
