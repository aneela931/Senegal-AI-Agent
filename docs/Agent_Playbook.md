# Agent playbook — add or update a UI feature

For a later agent (`create-katalon-cases` or a follow-up `build-katalon-framework` run that maps cases). This project already has launch + framework. Do **not** recreate the project from the android-cloud template. Do **not** attach files to Jira here. Do **not** invent languages.

## Before you start

1. Project path: `d:\AI Agent\create-katalon-project\projects\Senegal_Agent_App`
2. Languages: **en** only (`Data Files/i18n/en.json`). Do not add other locales unless named. Do not hardcode UI strings — use `I18n` keys.
3. Environment: use existing `staging.json` unless the user names another env.
4. Excel bank (optional, next Login source): `c:\Users\Aneela-Parveen\Downloads\SAA-908 Login - Change Request.xlsx`
5. Keep `CloudApp`, `TS_Smoke`, `TC_Launch_App`, and cloud/local launch variables unchanged.

## Steps

1. Read the Excel (or story) and **drop** rows whose Test Type is only API, Security, or Performance.
2. Group remaining UI rows by **feature**, not by Jira key. Suite name: `TS_<FeatureName>` (Login → `TS_Login`, not `TS_SAA-908`).
3. Create `Object Repository/<Feature>/<Screen>/` objects from the running app / UI Test IDs when available.
4. Add page classes under `Keywords/com/senegalagent/pages/`.
5. Add cases under `Test Cases/<Feature>/` with scripts in `Scripts/<Name>/`. Use `EnvConfig` and `I18n`. Call `CloudApp.launch()` only when a fresh start is required.
6. Add a test suite `Test Suites/TS_<FeatureName>` linking those cases. Do not dump them into `TS_Smoke`.
7. Run on a connected Android device (local profile) or Test Execution Cloud as documented in [README.md](README.md).
8. Confirm Katalon `Reports/` plus `allure-results/` (and `allure-report/` if CLI works).
9. Update [Current_Progress.md](Current_Progress.md) and [CHANGELOG.md](CHANGELOG.md).

## Out of scope for this playbook

- `create-test-cases` Excel bank generation
- `attach-test-cases` Jira attachments
- Editing `/Users/hamzaashfaq/Documents/AXIAN/Katalon Agents/templates/android-cloud`
- Reporter engine under `reporter/reporting/`
