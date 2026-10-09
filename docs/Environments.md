# Environments

Environment config is **JSON only** under `Data Files/Environments/`. These files are loaded by `EnvConfig.groovy` from disk. They are not Katalon InternalData Excel sources.

## Current files

| File | When used |
| --- | --- |
| `staging.json` | Profile `ENVIRONMENT` is `staging` (cloud and local profiles) |

Do not add prod / sit / uat unless the user asks. `apkVersion` is omitted (always latest APK). `defaultLanguage` is `en`.

## Schema

Required:

- `environment` — label, must match the file intent (here `staging`)
- `inventory` and/or `testData` — objects for UI data (users, MSISDNs, flags)

Optional:

- `apkVersion` — folder name under `App/` (omit = latest)
- `defaultLanguage` — a **user-named** language code that already has `Data Files/i18n/<lang>.json`

Do **not** put passwords, OTP secrets, API keys, or tokens in these files.

## How `EnvConfig` selects a file

`GlobalVariable.ENVIRONMENT` from the active profile is the file stem: `Data Files/Environments/${ENVIRONMENT}.json`. Missing file → clear failure.

Helpers: `EnvConfig.load()`, `EnvConfig.get('dotted.key')`, `EnvConfig.inventory()`, `EnvConfig.testData()`.

## How to add a new environment

1. Confirm the name with the user (do not invent).
2. Copy `staging.json` to `Data Files/Environments/<name>.json`.
3. Set `"environment": "<name>"` and replace placeholders. Still no secrets.
4. Duplicate or adjust `Profiles` only if a run must bind a different `ENVIRONMENT` label. Prefer asking before changing the launch profiles.
5. Update this file and `CHANGELOG.md`.
