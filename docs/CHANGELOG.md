# Changelog — framework

## 2026-10-05 — TS_Login (SAA-908)

- Added Login page keywords, Object Repository text/class locators, `Test Cases/Login/TC_908_*`, and `Test Suites/TS_Login.ts`.
- Replaced i18n stubs with SAA-908 quoted EN strings in `en.json` only (no `fr.json`).
- Locators are text/content-desc/class because the protected Flutter APK has no login UI Test IDs.
- Did not edit `CloudApp.groovy`, `Profiles/cloud.glbl`, or `TS_Smoke`.

## 2026-10-05 — i18n English

- Added `Data Files/i18n/en.json` with Login stub keys (labels, buttons, empty-state, errors).
- Set `defaultLanguage` to `en` on `Data Files/Environments/staging.json`.
- `EnvConfig.defaultLanguage()` feeds `I18n` when `LANGUAGE` is not on the profile.
- No `fr` or `wo` files. CloudApp and launch profiles unchanged. Excel Login cases not mapped.

## 2026-10-05 — Framework scaffold

- Added `Data Files/Environments/staging.json` (inventory/testData stubs, no secrets).
- Added `Data Files/i18n/` ready, **no locale files** (languages not named).
- Added `ApkResolver`, `EnvConfig`, `I18n`, `AllureSupport` under `Keywords/com/axian/mobile/`.
- Left `CloudApp.groovy` and cloud/local launch GlobalVariables as create-katalon-project defined them.
- Added page-object package `com.senegalagent.pages`.
- Added `Test Listeners/FrameworkListener.groovy` (APK resolve, env load, Allure results + optional HTML).
- Documented APK rule: always latest under `App/` (no `APK_VERSION` pin).
- Added `docs/` set and root `README.md`. No `Conventions.md`.
- Did not map Excel Login cases; did not add feature suites beyond existing `TS_Smoke`.
