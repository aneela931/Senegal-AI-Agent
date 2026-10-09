# Katalon architecture in this project

Project file: `Senegal_Agent_App.prj`. Do not recreate the project. Do not edit `Libs/`. Studio regenerates `Libs/internal/GlobalVariable.groovy` from profiles.

## Layers already present

| Layer | Path | Rule |
| --- | --- | --- |
| Launch | `Keywords/com/axian/mobile/CloudApp.groovy` | Only launch path. `CloudApp.launch()` then, when the case finishes, `Mobile.closeApplication()` |
| APK | `Keywords/com/axian/mobile/ApkResolver.groovy` | Do not hardcode a second install path |
| Environment | `Keywords/com/axian/mobile/EnvConfig.groovy`, `Data Files/Environments/staging.json` | Read data here. Never copy OTP, PIN, or passwords into Excel, Groovy literals, or `docs/qa` |
| i18n | `Keywords/com/axian/mobile/I18n.groovy`, `Data Files/i18n/en.json` | Language is `en` only |
| Reporting | `Keywords/com/axian/mobile/AllureSupport.groovy`, `Test Listeners/FrameworkListener.groovy` | Do not edit `reporter/reporting/` |
| Pages | `Keywords/com/senegalagent/pages/` | One class per screen. Business steps live here |
| Journey | `LoginJourney.groovy` | Multi-screen login flow. Call it from tests |
| Locators | `Object Repository/Login/<Screen>/*.rs` | No selectors inside test scripts |
| Cases | `Test Cases/<Feature>/*.tc` plus `Scripts/<Feature>/<Case>/Script.groovy` | This repo uses `Script.groovy`, not a timestamped script name |
| Suites | `Test Suites/TS_<Feature>.ts` | `TS_Login`, `TS_Smoke`. Never `TS_SAA-908` |
| Profiles | `Profiles/cloud.glbl` (default), `Profiles/local.glbl`, `Profiles/default.glbl` | Do not run `default`. USB runs use profile `local`. Do not retarget `DEVICE_NAME` to the USB phone; that field is the TestCloud device (`Samsung Galaxy S25 Ultra`, Android 15) |

`Include/scripts/groovy` is declared in the `.prj` and has no Groovy sources. `Include/config/log.properties` exists. Do not invent a second helper package there.

## Reuse these before adding a class

Pages: `SplashPage`, `MsisdnPage`, `OtpPage`, `TermsPage`, `PinPage`, `BiometricPage`, `ProfileSettingsPage`, `DeviceSessionPage`, `Permissions`, `MobileText`, `LoginData`, `Pages`.

Journey: `LoginJourney`.

Login objects already exist under `Object Repository/Login/`. Search that tree before creating an object.

Existing login scripts `TC_908_*` already cover SAA-908. On a later "Automate SAA-908", map those cases. Add a script only for a requirement they do not cover. Do not renumber them. The next new id for a ticket is one past the highest existing `TC_<n>_*` file.

## File shapes to copy

Clone `Test Cases/Login/TC_908_001.tc`. Keep the same elements. Put traceability in `<description>`, `<tag>` (Jira key and test type), and `<comment>`. Set a new `testCaseGuid`. Do not add elements that file does not have.

Clone `Object Repository/Login/Msisdn/input_msisdn.rs` for a new object. New `elementGuidId`. Locator contract is [qa-semantics.md](qa-semantics.md): spy Accessibility and use the semantic id (`onboarding_screen_text_field`, `login_screen_button_3`). Prefer, in order: that semantic `content-desc` / `resource-id`, an existing object that already uses it, the Device-UI quirk fallback below, relative XPath, absolute XPath last. Do not use visible text as the primary locator (language changes). Do not invent an id Spy has not shown (`SEMANTIC_ID_MISSING`). Do not use coordinates except the PIN first-circle offset. Do not add `sleep`.

## Device-UI quirks confirmed on the Infinix X6836 (protected build 1.0-92)

These were verified on the real device. Keep the existing page objects; do not regenerate them in a way that drops these behaviours, and reuse the same handling for any new screen that looks the same.

- **PIN entry is 4 separate circles inside one field.** The field's horizontal centre is the gap between circle 2 and circle 3, so a plain `Mobile.tap(field)` (which taps the centre) never focuses the input and no keyboard opens — the cells stay empty and the Login/Connexion button stays disabled. This is not a locator or credential problem. Tap the **first circle** instead: read the field rectangle (`getElementLeftPosition/TopPosition/Width/Height`) and `Mobile.tapAtPosition(left + width/8, top + height/2)`, then `Mobile.setText(field, pin)`. `PinPage.enterPin()` already does this; use it. This is the one place coordinates are allowed, and only as an offset derived from the element's own rectangle, never a hardcoded screen point.
- **The MSISDN submit control is an `ImageView` with `content-desc='Next'`, and it only exists while the field has focus.** Do not hide the keyboard before tapping it. `MsisdnPage.submit()` already re-focuses the field if `Next` is missing; use it. `Login/Msisdn/btn_submit.rs` carries `content-desc='Next'` with the Button class as fallback.
- **`forceAppLaunch` must be set before the first launch of a case.** With Katalon's default `noReset`, Appium leaves a backgrounded app where it was and the splash check fails. `LoginJourney.launchFresh()` sets it.
- **The device may already be linked**, so the app can jump straight from MSISDN to the PIN screen, skipping OTP and Terms. Flow helpers must tolerate missing OTP/Terms screens (`LoginJourney.completeLogin`), not assume them.
- **Keyword edits need a Studio rebuild.** A run with a `Keywords/**/*.groovy` newer than its `bin/keyword/*.class` executes the old code. `prepare-app-on-device.ps1` exits 5 (`KEYWORDS_STALE`) to catch this; refresh the project (F5) before running.
- **Clear app data before every local suite and every local case.** `CloudApp.launch()` calls `DeviceReset.clearLocalAppData()`. A previous successful login leaves the home dashboard; the next case then cannot find the language chip / Identify / MSISDN (run 20261008_003830). Do not drop this call.
- **Do not write `Français` as a raw Groovy string.** Katalon compiled it as `FranAais` and the language chip never matched. Use ASCII labels and `contains(@content-desc,'Fran')` in the object repository. Prefer the semantic ids `language_bottom_sheet_button_1` / `_2` over visible text.
- **First-login biometric is success.** After PIN, `biometric_enable_screen` may appear. Tap Enable (`biometric_enable_screen_button_2`). Do not restart `completeLogin`. `PinPage.loginSucceeded()` is true when home **or** that screen is showing.
- **PIN type uses `adb input text` after the first-circle tap.** Appium `setText` on this field can return 200 with empty cells.

Clone a `<testCaseLink>` block from `Test Suites/TS_Login.ts`. Its `<guid>` is not the test case guid. Point `<testCaseId>` at `Test Cases/<Feature>/<CaseName>` with no extension. Give the link a new guid.

Script: one traceability line, then **one page call per Excel Input step**, then close (unless the keep-open success path):

```groovy
// Jira: SAA-908 | Requirement: REQ-004 | Test Case: TC-908-014
CloudApp.launch()
// 1. Wait for splash / language
new SplashPage().waitForLanguageChip()
// 2. Choose English
new SplashPage().selectEnglish()
// 3. Type agent MSISDN
new MsisdnPage().enterMsisdn(LoginData.agentMsisdn())
// 4. Tap continue
new MsisdnPage().submit()
// 5. Assert Expected Result
new PinPage().assertScreen()
Mobile.closeApplication()
```

Page methods own taps, text entry, and waits. A generated test that calls `Mobile.tap`, `Mobile.setText`, or builds an XPath is too thick. Move that into a page or journey. A six-step Excel case must not become one undocumented helper.

## When Katalon MCP is missing

`katalon-prod-mcp` in `.cursor/mcp.json` is the TestOps endpoint `https://axian-group.katalon.io/mcp`. It is not a Studio file writer. If a Studio tool that creates `.tc` / `.rs` / `.ts` is actually registered, use it. Otherwise edit files using the shapes above. Do not guess extra XML. After writing, check that each `.tc` has exactly one `Scripts/<Feature>/<name>/Script.groovy`.

Katalon Runtime Engine is a separate licence. Do not claim a console `katalonc` run succeeded unless that executable is present and the run's report files exist.
