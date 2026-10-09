# Device check, run, and failure class

## Before any run

Run `scripts/check-android-device.ps1` from the project root.

Report every device line before execution. If the script exits with code 2, do not start a run. Write `Not Run` in the execution report and name the missing device as the reason.

The phone attached at framework setup was an Infinix X6836 (`1112025423005673`). That is historical. Trust only a fresh `adb` listing.

USB execution uses profile `local` and Studio **Run > Android** on `Test Suites/TS_<Feature>`. The plain Run action does not select a device. Cloud execution uses profile `cloud` and **Run > Test Execution - Cloud > Mobile Native Apps**. This workflow's default execution target is the connected USB device.

Do not change application source to make a test pass. Do not change `DEVICE_NAME` in the profile to match the USB phone.

## Loop

For each automated case:

1. Generate the thin script and its page/object changes.
2. Validate pairings: `.tc` name, `Script.groovy` path, suite `testCaseId`, no duplicate object.
3. Run on the reported device.
4. Collect the Studio or Allure result. No result file means `Not Run`, not `Passed`.
5. Classify the failure.
6. Edit automation only for `AUTOMATION_DEFECT`.
7. Rerun that case once after an automation fix. A second identical failure stays failed.

## Classes

| Class | Meaning | Automatic edit |
| --- | --- | --- |
| `PRODUCT_DEFECT` | App behaviour disagrees with a stated requirement | No. Do not weaken the assertion |
| `AUTOMATION_DEFECT` | Wrong locator, wrong wait, script not matching the page API | Yes, in pages, objects, or the thin script |
| `TEST_DATA` | Account, OTP, PIN, or backend state is missing or wrong | No. Ask for data. Never invent an OTP |
| `ENVIRONMENT` | Install, network, Studio, or profile failure | No |
| `REQUIREMENT_GAP` | Expected result was not in the ticket | No. Mark `BLOCKED_REQUIREMENT` |
| `DEVICE` | No device, unauthorized device, or the app is not on that device | No |
| `UNKNOWN` | Evidence does not fit the classes above | No |

Do not file a Jira defect from this skill. Defect filing stays with the log-defects agent after an explicit yes.

Cluster identical locator or exception messages so one product defect is not counted as many failures. The execution report still has one row per test case.
