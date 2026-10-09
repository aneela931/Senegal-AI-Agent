# Execution report — SAA-908

Device reported before the run:

```text
List of devices attached
1112025423005673       device product:X6836-OP model:Infinix_X6836 device:Infinix-X6836 transport_id:2
DEVICE_STATUS ready count=1
```

Profile: `local`
Suite: `Test Suites/TS_Login`

The phone was ready. A console run was attempted with Katalon Studio 11.5.0 `katalonc.exe` (`-executionProfile=local -browserType=Android -deviceId=1112025423005673`). It stopped before the first test case with KRE exit code 2:

```text
Activation failed: No Offline License or you have forgot to put in your -apiKey command for online activation.
```

No test case started, and no report file was produced. No API key was written into the project.

## Live device check (2026-10-07, outside Katalon)

Because the suite could not run headless, the login flow was walked on the same phone with `adb` (`uiautomator dump`, `input tap`, `input text`) against the installed APK to compare the real UI tree with the project locators. Findings:

| # | Screen | Observed on device | Project locator before | Class | Action taken |
| --- | --- | --- | --- | --- | --- |
| 1 | Splash language chip | `android.view.View`, `content-desc="Français"`, clickable. No `resource-id` anywhere in the tree. | `SplashPage.selectEnglish()` tapped `//*[@resource-id='onboarding_screen_button_0']` (never matches) | AUTOMATION_DEFECT | `selectEnglish()` now taps `Object Repository/Login/Splash/lbl_language_french` (resource-id or `content-desc='Français'`). Bottom-sheet rows `Français` / `Anglais` match the existing `anglaisRow` locator. |
| 2 | MSISDN submit | `android.widget.ImageView`, `content-desc="Next"`. There is no `android.widget.Button` on the screen. | `Login/Msisdn/btn_submit` = `(//android.widget.Button)[1]` (never matches) | AUTOMATION_DEFECT | Locator changed to `//*[@content-desc='Next' or @text='Next'] \| (//android.widget.Button)[1]`. |
| 3 | PIN field | Label `View content-desc="Enter your Yas PIN"` sits above the `EditText`. In an XPath union the label comes first in document order, so `setText` targeted the label. | `Login/Pin/input_pin` included `@content-desc='Enter your Yas PIN'` | AUTOMATION_DEFECT | Removed the label alternative; locator is `//*[@resource-id='login_screen_text_field'] \| (//android.widget.EditText)[1]`. |
| 4 | PIN submit | `ImageView content-desc="Login"`, disabled until 4 digits are entered. | `Login/Pin/btn_submit` = `//*[@content-desc='Login' or ...]` | OK | No change. |
| 5 | Flow after MSISDN | App went directly from MSISDN to the PIN screen (`Welcome POINT DE VENTE`). OTP and Terms screens were skipped because this phone is already linked to the agent account. | n/a | ENVIRONMENT | OTP/Terms cases (TC-908-006, 007, 009–013, 033) need an unlinked device or a reset of the device link. `LoginJourney.completeLogin()` already tolerates the skipped screens; `toOtpWithAgentMsisdn()` does not. |
| 6 | Incorrect PIN test data | The PIN entry has 4 cells. `testData.login.incorrectPin` in `Data Files/Environments/staging.json` is 5 characters, so it cannot be entered and Login stays disabled. | n/a | TEST_DATA | Not changed. Needs a 4-digit incorrect value in `staging.json` (any 4 digits other than the valid PIN). No failed-attempt was recorded on the account during this check. |
| 7 | OTP submit | Not observed (screen skipped, item 5). `Login/Otp/btn_submit` is also `(//android.widget.Button)[1]`; it probably has the same problem as item 2 but there is no evidence yet. | `(//android.widget.Button)[1]` | UNKNOWN | Not changed. Verify on an unlinked device before editing. |

Items 1–3 explain why the earlier Studio runs failed at the language step and at MSISDN submit.

## Studio run 20261007_231120 (TS_Login, profile local, Infinix X6836) — stopped by the user at TC_908_007

Source: `Reports/20261007_231120/TS_Login/20261007_231125/{console0.log,appium.log,*.png}`.

| Case | Result | Failure Type | Root cause (from logs / device) | Fix applied |
| --- | --- | --- | --- | --- |
| TC_908_001 | Passed | - | - | - |
| TC_908_002 | Passed | - | - | - |
| TC_908_003 | Failed | AUTOMATION_DEFECT | Failure screenshot shows the phone launcher, not the app. `appium.log`: `'sn.free.agent.app' is already running and noReset is enabled. Set forceAppLaunch capability to true`. Appium skipped the launch, the app stayed in the background and the splash wait ran 7 min against the home screen. | `LoginJourney.launchFresh()` sets `forceAppLaunch=true` before `CloudApp.launch()`. |
| TC_908_004 | Failed | ENVIRONMENT | `UiAutomator2 server ... instrumentation process is not running (probably crashed)`. | None. Re-run. |
| TC_908_005, 006, 007 | Failed | AUTOMATION_DEFECT | `Login/Msisdn/btn_submit not found`. Device check: the `Next` control exists only while the MSISDN field has focus; `MsisdnPage.submit()` hid the keyboard first, which removes `Next` from the UI tree (user screenshot 23:28 shows exactly this state). | `submit()` no longer hides the keyboard; it re-focuses the field if `Next` is missing, then taps it. Verified by hand: focus → `Next` appears → tap → PIN screen. |
| all | slow | - | `Permissions.allowIfShown()` made one lookup per label (8 × ~1.2 s) and is called twice per splash-wait iteration; a missing app turned into a 7-minute hang. | One union lookup per pass. |

Open after this run:

- PIN entry. With `adb` (`input tap`, `input text`, key events) the PIN field cannot be focused on this phone: the `EditText` and the clickable cell `View` stay `focused=false`, no keyboard opens, cells stay empty, `Login` stays disabled. Tested from a fresh app start and with the Infinix Palm Store floating bubble (`com.transsnet.store`, drawn over the PIN screen) disabled; no difference. The MSISDN field on the previous screen accepts the same input. Not classified yet: it may be an automation limitation (Appium `setText` uses the accessibility set-text action, not taps, and may still work) or a product issue. Needs one Katalon run of `TS_Login_Success` and a manual check that the keyboard opens when a person taps the PIN cells. Earlier failure logs from `C:\katalon-run\...` were from an old copy of the code (`ENGLISH_LABELS` without `Anglais`) and are stale.

| Test Case | Result | Failure Type | Evidence | Notes |
| --- | --- | --- | --- | --- |
| TC-908-001 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-002 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-003 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-004 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-005 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-006 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-007 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-009 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-010 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-011 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-012 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-013 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-014 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-015 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-016 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-017 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-018 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-019 | Blocked | REQUIREMENT_GAP | none | See open-questions.md. The existing script was left unchanged. |
| TC-908-020 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-021 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-022 | Blocked | REQUIREMENT_GAP | none | See open-questions.md. The existing script was left unchanged. |
| TC-908-023 | Blocked | REQUIREMENT_GAP | none | See open-questions.md. The existing script was left unchanged. |
| TC-908-024 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-025 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-026 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-027 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-028 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-031 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-033 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |
| TC-908-034 | Not Run | ENVIRONMENT | none | Katalon Runtime Engine activation failed before execution. |

## Totals

| Result | Count |
| --- | --- |
| Passed | 0 |
| Failed | 0 |
| Blocked | 3 |
| Not Run | 27 |
