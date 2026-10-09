# Senegal Agent App — Katalon framework

How to open this project, choose environment and APK, run locally or on cloud, and find reports.

## Open in Katalon Studio

1. Open `Senegal_Agent_App.prj` in Katalon Studio.
2. Sign in to the platform host `https://axian-group.katalon.io` (TestOps project **3033174**). Do not store an API key in this repo.
3. Let Studio regenerate `.classpath`, `.project`, and `Libs/` if they are missing (those files are gitignored).

## Profiles (do not replace the launch contract)

| Profile | `RUN_TARGET` | What happens |
| --- | --- | --- |
| `cloud` (default) | `cloud` | TestCloud installs `CLOUD_APP_ID`, then `CloudApp` calls `startExistingApplication` on `APP_PACKAGE` |
| `local` | `local` | `CloudApp` calls `startApplication` with `LOCAL_APK_PATH` on the attached device |

`APP_PACKAGE` is `sn.free.agent.app`. Device defaults: Samsung Galaxy S25 Ultra, Android 15.

**Cloud:** open `Test Suites/TS_Smoke`, select profile `cloud`, then **Run > Test Execution - Cloud > Mobile Native Apps**. Choose Android, OS 15, Samsung Galaxy S25 Ultra, and the Applications row whose App ID is `458d726a-57a1-4297-96cf-dcb34f22d3b8`. The plain **Run** button does not start TestCloud.

**Local:** attach a phone with USB debugging, select profile `local`, then **Run > Android**. `CloudApp` installs the APK under `App/<version>/`.

## Environment

Only **staging** is defined. JSON lives at `Data Files/Environments/staging.json`. The active profile’s `ENVIRONMENT` value (`staging`) selects the file. See [Environments.md](Environments.md).

## Languages

Approved: **en** only (`Data Files/i18n/en.json`). Staging sets `defaultLanguage` to `en`. Do not add French, Wolof, or other locale files unless named. Scripts assert through `I18n` keys. See [TestData_and_i18n.md](TestData_and_i18n.md).

## APK

Always the **latest** semantic version folder under `App/` unless someone later pins `APK_VERSION` on a profile or `apkVersion` in env JSON. Nothing is pinned today. Current file: `App/1.0/Senegal_AgentApp_1.0-92-30-Sept-2026-protected.apk`. See [APK_and_Versions.md](APK_and_Versions.md).

## Naming (short)

- Test suites: `TS_<FeatureName>` (for example `TS_Login`). Never a Jira key.
- Test cases: `TC_<Action>_<Condition>` under `Test Cases/<Feature>/`.
- Object Repository: `Object Repository/<Feature>/<Screen>/`.
- Page objects: `Keywords/com/senegalagent/pages/`.
- Leave `TS_Smoke` and `TC_Launch_App` as the launch smoke path.

Full conventions: [Architecture.md](Architecture.md).

## Reports

Katalon writes under `Reports/`. The framework listener writes Allure files under `allure-results/` and tries to generate `allure-report/`. See [Reporting.md](Reporting.md).

## Scope

UI / frontend only. Do not automate Test Types that are only API, Security, or Performance. See [Automation_Scope.md](Automation_Scope.md).

## Adding a feature later

Follow [Agent_Playbook.md](Agent_Playbook.md). Next Login source: `create-katalon-cases` using `c:\Users\Aneela-Parveen\Downloads\SAA-908 Login - Change Request.xlsx` (feature suite `TS_Login`, `I18n` keys in `en.json`).
