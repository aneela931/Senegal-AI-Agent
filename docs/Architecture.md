# Architecture

Frontend-only Katalon Android framework on the Senegal Agent App (Yas Agent) project created by `create-katalon-project`.

## Layers

| Layer | Location | Role |
| --- | --- | --- |
| Launch | `Keywords/com/axian/mobile/CloudApp.groovy` | Only launch path. Local: `startApplication(LOCAL_APK_PATH)`. Cloud: `startExistingApplication(APP_PACKAGE)` after TestCloud installs `CLOUD_APP_ID`. Do not fork this. |
| APK | `ApkResolver.groovy` | Picks latest `App/<version>/` unless pinned. Updates `LOCAL_APK_PATH` at suite/case start. |
| Environment | `EnvConfig.groovy` + `Data Files/Environments/*.json` | Staging JSON (inventory / testData). Selected by `GlobalVariable.ENVIRONMENT`. |
| i18n | `I18n.groovy` + `Data Files/i18n/en.json` | String keys for UI asserts. Approved language: **en**. |
| Pages | `Keywords/com/senegalagent/pages/` | One class per screen. Package name is **`com.senegalagent`**. |
| Locators | `Object Repository/<Feature>/<Screen>/` | Test objects, not hardcoded in scripts. |
| Tests | `Test Cases/<Feature>/` + `Scripts/<Name>/` | Call pages + `I18n` + `EnvConfig`. Call `CloudApp.launch()` only when a fresh start is needed. |
| Suites | `Test Suites/TS_<FeatureName>/` | Feature name, never a Jira key. `TS_Smoke` stays as created. |
| Allure | `Test Listeners/FrameworkListener.groovy` + `AllureSupport.groovy` | Writes `allure-results/` and tries `allure generate`. |

## App facts

- Display: Senegal Agent App / Yas Agent
- Package: `sn.free.agent.app`
- Version 1.0, build 92 (Applications metadata; also `EXPECTED_VERSION` / `EXPECTED_BUILD`)
- TestOps project: `3033174` on `https://axian-group.katalon.io`
- `CLOUD_APP_ID`: `458d726a-57a1-4297-96cf-dcb34f22d3b8`

## Profiles

`Profiles/cloud.glbl` and `Profiles/local.glbl` keep the create-katalon-project contract: `APP_PACKAGE`, `CLOUD_APP_ID`, `LOCAL_APK_PATH`, `DEVICE_*`, `OS_VERSION`, `ENVIRONMENT`, `EXPECTED_*`, `DEFAULT_TIMEOUT`, `RUN_TARGET`. Do not break that set. Optional later: `LANGUAGE`, `APK_VERSION` — neither is present now.

`Profiles/default.glbl` is the empty template. Do not run it.

## APK resolver

Scan `App/*/`. Compare folder names as semantic versions. Highest wins. If `GlobalVariable.APK_VERSION` or env `apkVersion` is set, use that folder. Local install still goes through `CloudApp` and `LOCAL_APK_PATH`. Cloud still uses TestCloud + `CLOUD_APP_ID`.

## Environment JSON

See [Environments.md](Environments.md). Only `staging.json` exists. No secrets or tokens.

## i18n

See [TestData_and_i18n.md](TestData_and_i18n.md). Language is **en** (`defaultLanguage` on staging). `I18n` never hardcodes locale strings in scripts.

## Allure

See [Reporting.md](Reporting.md). Listener is suite-local. Do not edit `reporter/reporting/`.

## Page-object rules

- Package: `com.senegalagent.pages`.
- One class per screen (for example `LoginPage`).
- Methods are user actions and visible-state checks (`tapLogin`, `assertError`).
- Locators: Object Repository, not `findTestObject` string soup in the test script.
- **SAA-908 / protected Flutter APK:** Android `R.id` names for login controls are stripped. Do not invent `resource-id` values. Login objects use **visible text / content-desc** from the Excel quotes (via `I18n` / `en.json`) and **class locators** (`EditText` / `Button`) for fields with no labelled id. Language remains **en** only.
- Text checks: `I18n.get('key')` / `I18n.assertEquals('key', actual)` against `en.json`.
- Data: `EnvConfig.inventory()` / `EnvConfig.testData()`, never passwords or API tokens in JSON.
- Launch: `CloudApp.launch()` from the test (or a shared flow keyword), not from every page method.

## Naming conventions

| Kind | Pattern | Examples |
| --- | --- | --- |
| Test suite | `TS_<FeatureName>` | `TS_Smoke`, `TS_Login` |
| Test case entity | `TC_<Action>_<Condition>` | `TC_Launch_App` |
| Test case folder | `Test Cases/<Feature>/` | `Test Cases/Login/` |
| Script | `Scripts/<TestCaseName>/Script.groovy` | already used by `TC_Launch_App` |
| Object Repository | `Object Repository/<Feature>/<Screen>/<element>` | `Object Repository/Login/LoginScreen/btn_login` |
| Page class | PascalCase screen name | `LoginPage.groovy` |
| Env JSON | `Data Files/Environments/<env>.json` | `staging.json` |
| i18n JSON | `Data Files/i18n/<lang>.json` | `en.json` (only named language) |
| APK folder | `App/<version>/` | `App/1.0/` |
| GlobalVariables | existing launch names unchanged | `RUN_TARGET`, `CLOUD_APP_ID` |

Do **not** name suites after Jira keys (`TS_SAA-908` is invalid). Jira keys belong in case description or a comment, not the suite name.

Smoke (`TS_Smoke` / `TC_Launch_App`) stays the launch check. Do not fold feature cases into it.
