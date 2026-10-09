# Test data and i18n

## Inventory / testData

`Data Files/Environments/staging.json` holds UI inventory:

- `users` — role, username, MSISDN placeholders
- `msisdns` — placeholder numbers
- `flags` — UI-visible toggles such as `loginEnabled`
- `defaultLanguage` — `en`

Fill placeholders with non-secret staging values when they are known. Never commit passwords, PIN/OTP, or tokens.

Scripts read via `EnvConfig.inventory()` or `EnvConfig.get('inventory.users')`. Language: `EnvConfig.defaultLanguage()` or `I18n.load()`.

## i18n — English only

Approved language: **en**. File: `Data Files/i18n/en.json`. Do not add `fr.json`, `wo.json`, or any other locale unless the user names it.

`I18n.groovy`:

- Loads `Data Files/i18n/<lang>.json`
- Resolves nested keys (`login.button.submit`)
- `assertEquals(key, actual)` for visible text
- Fails clearly if the file or key is missing

Language resolution: `GlobalVariable.LANGUAGE` if present later, else `EnvConfig.defaultLanguage()` (`en` on staging).

Login stubs currently include `login.title`, labels, buttons, empty-state, and error keys. Replace sample strings with exact UI copy when automating Login. All label / error / button / empty-state checks must go through keys. Do not hardcode locale strings in scripts.

## How to add another language

1. Wait until the user names a new code.
2. Create `Data Files/i18n/<code>.json` with the same keys as `en.json`.
3. Only change `defaultLanguage` if they want a different default.
4. Update `Current_Progress.md` and `CHANGELOG.md`.

## How to add a string key

1. Add the key to `en.json` (and every other named language file, if any).
2. Assert with `I18n.get('login.button.submit')` or `I18n.assertEquals(...)`.
3. Do not add locale fallbacks in Groovy.
