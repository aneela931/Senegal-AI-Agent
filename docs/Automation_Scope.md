# Automation scope

This project automates **frontend / UI** behaviour of Senegal Agent App on Android (local device or Test Execution Cloud).

## Automate

Test types that exercise the running app UI, including:

- Functional
- Negative (UI-visible errors)
- UX (navigation, empty states, enabled/disabled controls)
- Localization (`en.json` keys via `I18n`)
- Configuration that is visible in the UI

## Skip (do not automate here)

Do not implement cases whose Test Type is **only**:

- API
- Security
- Performance

Those stay in other suites or manual banks. Do not call backend APIs from these scripts to “set up” data unless the user later defines a UI-only path.

## Suites

One suite per **feature** (`TS_Login`), never per Jira key (`SAA-908`). Smoke launch remains `TS_Smoke`.
