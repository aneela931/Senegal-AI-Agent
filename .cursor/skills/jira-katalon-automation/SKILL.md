---
name: jira-katalon-automation
description: >-
  Runs the Senegal Agent App workflow from a Jira key through requirement
  analysis, attachment review, gap analysis, the project Excel test-case
  workbook, step-by-step Katalon mobile scripts (QA semantics locators), a
  connected Android device check, and coverage plus execution reports. Use when
  the user shares a Jira key (SAA-908, SAA-1025), says Automate SAA-908,
  Generate test cases for a Jira key, Read Jira and create QA cases, Create
  Katalon tests for a story, Update tests for this Jira story, or Run SAA-908
  automation.
---

# Jira to Katalon — Senegal Agent App

Project root: the `Senegal_Agent_App` Katalon project. This skill analyses one Jira ticket, reads every attachment, writes the Excel workbook in the existing column layout, and automates only the candidate cases that are not already covered. It does not create a new Katalon project and it does not convert this repo to another framework.

Read these before writing files:

- [references/requirement-model.md](references/requirement-model.md)
- [references/excel-template.md](references/excel-template.md)
- [references/qa-semantics.md](references/qa-semantics.md)
- [references/katalon-architecture.md](references/katalon-architecture.md)
- [references/execution-and-failures.md](references/execution-and-failures.md)
- [references/attribution.md](references/attribution.md)

## Trigger

A Jira key alone is enough (`SAA-908`, `SAA-1025`). Start the full checklist. Do not wait for a second prompt. Do not start from the summary alone. Do not publish cases to Jira and do not file defects.

## Checklist

```text
- [ ] 1. Fetch the Jira issue, comments, links, and every attachment
- [ ] 2. Write the requirement model and open questions (attachments are sources)
- [ ] 3. Design manual cases tied to REQ ids — numbered steps, one action each
- [ ] 4. Export Excel with scripts/export-test-cases.ps1
- [ ] 5. Search existing Katalon assets and map overlaps
- [ ] 6. Add thin, step-by-step scripts only for unmapped automation candidates
- [ ] 7. Check the USB device, reset the app to cold start, run profile local
- [ ] 8. Classify failures; fix only AUTOMATION_DEFECT
- [ ] 9. Write coverage, automation, and execution reports from actual mappings
```

## 1. Fetch Jira

Use the connected Atlassian plugin. Do not open a second Jira connection and do not read tokens from `.cursor/mcp.json`.

1. `getAccessibleAtlassianResources` once. Pass that `cloudId` on every later call.
2. `getJiraIssue` with `view: full` and `responseContentFormat: markdown`.
3. Comment bodies are not in that payload. Call `listJiraIssueComments` when it is registered. If it is not, `discover` the comment-list operation. A comment count without bodies is not evidence.
4. Linked issues come back on the evidence/full view. Load each linked issue that changes behaviour.
5. Subtasks: `searchJiraIssuesUsingJql` with `parent = <KEY>`.
6. **Attachments are mandatory evidence.** Listing names is not enough.
   - Collect every attachment from the issue payload, description embeds, and comments.
   - `discover` then `executeRead` the Jira attachment download / get-content operation. Save readable files under `docs/qa/<JIRA-KEY>/attachments/`.
   - Read each file: images with the Read tool, workbooks / PDF / markdown as text or tables.
   - Treat attachment content as a source (`attachment:<name>`). Do not invent what a file contains.
   - If bytes cannot be downloaded, write `not accessible` for that file, add an open question, and ask the user to share the file. Continue with other evidence. Do not invent screens or tables from the file name.

Capture summary, description, acceptance criteria, comments, **attachment contents**, links, dependencies, labels, components, priority, sprint, and relevant subtasks.

## 2. Requirement model

Write `docs/qa/<JIRA-KEY>/requirement-analysis.md` from [templates/requirement-analysis.md](templates/requirement-analysis.md).

Assign `REQ-001` upward. Fields and the gap list are in [references/requirement-model.md](references/requirement-model.md). Write `docs/qa/<JIRA-KEY>/open-questions.md` from [templates/open-questions.md](templates/open-questions.md).

Quote or paraphrase what each readable attachment shows in the requirement-analysis **Attachments (evidence)** section. Unreadable files stay `not accessible`.

Open questions do not stop generation of cases that already have an expected result. Cases that need a missing expected result use status `BLOCKED_REQUIREMENT`.

## 3. Manual cases

Design from the requirement rows, using only techniques that the requirement supports:

happy path, negative, boundary value analysis, equivalence partitioning, validation, state transition, decision table, permissions, interruption/retry, session, network/error, mobile-specific, regression.

Skip duplicate cases that hit the same partition and the same expected result. Every case cites at least one `REQ-` id.

Primary technique for the coverage file is one of `positive`, `negative`, `boundary`, `other`.

### Step-by-step Input (required)

The Excel `Input` column is the script. Write **numbered atomic user actions**, one action per number. Each step is one of: wait for a named screen, tap one semantic widget, type one field, observe one result.

```text
1. Wait for splash_screen.
2. Open the language sheet (onboarding_screen_button_0).
3. Wait for language_bottom_sheet.
4. Tap English (language_bottom_sheet_button_2).
5. Type EnvConfig.validMsisdn into onboarding_screen_text_field.
6. Tap continue (onboarding_screen_button_1).
```

Do not write a paragraph. Do not merge two taps into one step. Name the semantic id from [references/qa-semantics.md](references/qa-semantics.md) when the widget is known. If Spy has not shown an id, write the user action and `SEMANTIC_ID_MISSING` — do not invent one.

Expected Result is one observable outcome, copied from evidence.

## 4. Excel

Clone `SAA-908 Login - usecase (1).xlsx` through the exporter. Never overwrite that file or the Sprint 1 bank workbook.

```powershell
powershell -File .cursor/skills/jira-katalon-automation/scripts/export-test-cases.ps1 `
  -TemplatePath "SAA-908 Login - usecase (1).xlsx" `
  -OutputPath "docs/qa/<JIRA-KEY>/<JIRA-KEY> - <Summary> - Test Cases.xlsx" `
  -InputJson "docs/qa/<JIRA-KEY>/cases.json"
```

`cases.json` matches [templates/cases.example.json](templates/cases.example.json). Column mapping, IDs (`TC-908-001`, `TC-1025-001`), and the path rule are in [references/excel-template.md](references/excel-template.md).

Do not put passwords, OTP values, or PIN values in the workbook. Name the `EnvConfig` field instead.

Continue numbering after the highest existing `TC_<n>_*` for that ticket. Do not reuse an id that already has a `.tc` file.

## 5. Reuse before creating

Search, in order: existing test scripts, `Keywords/com/senegalagent/pages/`, `Keywords/com/axian/mobile/`, `Object Repository/`, `Data Files/`, `Profiles/`.

If a script already covers the requirement, map it in the automation report. Do not add a twin.

Login reuse list and file shapes: [references/katalon-architecture.md](references/katalon-architecture.md).

## 6. Thin Katalon scripts — step by step

Automate rows whose Automation value is `Candidate` and whose status is not `BLOCKED_REQUIREMENT`.

Follow [templates/thin-test.groovy](templates/thin-test.groovy). The test calls `CloudApp.launch()`, then **one page or journey method per Excel Input step**, then an assertion that matches Expected Result, then `Mobile.closeApplication()` (unless the case is the keep-open success path).

Taps, text entry, XPath, and waits stay in page objects. The script must still make the sequence readable:

```groovy
// Jira: SAA-908 | Requirement: REQ-004 | Test Case: TC-908-014
CloudApp.launch()
// 1. Wait for splash / language
new SplashPage().waitForLanguageChip()
// 2. Switch to English
new SplashPage().selectEnglish()
// 3. Type EnvConfig agent MSISDN
new MsisdnPage().enterMsisdn(LoginData.agentMsisdn())
// 4. Tap continue
new MsisdnPage().submit()
// 5. Assert Expected Result
new PinPage().assertScreen()
Mobile.closeApplication()
```

A script that collapses a six-step case into one undocumented helper is too coarse. A documented journey (`LoginJourney.completeLogin`) is allowed only when the Excel case is the full login happy path and those screens are already covered.

Traceability line in the script, and the same facts in the `.tc` description, tag, and comment:

`Jira: SAA-908 | Requirement: REQ-004 | Test Case: TC-908-014`

Prefer a Katalon Studio MCP create/update tool when one is registered in the session. The TestOps server `katalon-prod-mcp` does not write local `.tc` files. When no Studio writer is available, clone the XML of the existing files named in the architecture note and mint a new UUID only for that file's own guid. Do not add XML elements those files do not have.

**Locators:** follow [references/qa-semantics.md](references/qa-semantics.md). New objects use the semantic accessibility id (`content-desc` / `resource-id` in the form `screen_kind` or `screen_kind_N`). Do not use visible text as the primary locator. Do not invent an id that was not seen on the device or in an existing object. If Spy shows no semantic id, report `SEMANTIC_ID_MISSING` and keep a fallback that was seen.

Reuse the existing page objects for login screens. Several controls on this build behave in non-obvious ways that are already handled in `Keywords/com/senegalagent/pages/` (the segmented 4-circle PIN field needs a tap on the first circle, not the field centre; the MSISDN continue control only exists while the field has focus; `forceAppLaunch` is required; first-login biometric is success — do not restart login). See `references/katalon-architecture.md` → "Device-UI quirks" before touching `PinPage`, `MsisdnPage`, `BiometricPage`, or `LoginJourney`, and do not regenerate them in a way that drops those behaviours.

New suites are `Test Suites/TS_<Feature>`, never `TS_<JIRA-KEY>`. Leave `TS_Smoke` and `TC_Launch_App` unchanged.

## 7. Device and execution

```powershell
powershell -File .cursor/skills/jira-katalon-automation/scripts/check-android-device.ps1
```

Report the device block before running. Exit code 2 means do not run; mark cases `Not Run`.

### App state before every device run (mandatory)

The first test case expects the splash / language screen. If the app is already installed and sitting on the login or PIN screen from a previous run, `TC_908_001` fails for a reason that is not a product defect. So, before every run on a USB device, and before every re-run after a failure:

- App **installed** → force-stop it and clear its data and cache (`adb shell pm clear <APP_PACKAGE>`).
- App **not installed** → install `LOCAL_APK_PATH` from `Profiles/local.glbl` (`adb install -r -g`).

Use the helper; it reads `APP_PACKAGE` and `LOCAL_APK_PATH` from the profile and does both:

```powershell
powershell -File .cursor/skills/jira-katalon-automation/scripts/prepare-app-on-device.ps1
```

Expected output is `APP_STATE cleared ...` or `APP_STATE installed ...`. Exit 2 = no device, 3 = APK missing at `LOCAL_APK_PATH`, 4 = adb error, 5 = `KEYWORDS_STALE`.

`KEYWORDS_STALE` means a `Keywords/**/*.groovy` file is newer than its class in `bin/keyword/`. Studio compiles keywords only after it notices the change, and it does not notice edits made outside Studio until the project is refreshed. A run in that state executes the old code and the log looks like the fix was never made (run 20261008_000213). After any keyword edit: in Studio select the project, press F5, wait for the build, then re-run the helper and confirm it no longer prints `KEYWORDS_STALE`. `Scripts/**/Script.groovy` and `Object Repository/**/*.rs` are read at run time and do not need this.

Every local suite and every local test case must start from a cleared app. `CloudApp.launch()` calls `DeviceReset.clearLocalAppData()` (`pm clear` + re-grant permissions). Do not remove that. A leftover logged-in session (run 20261008_003830) has no language chip and fails on MSISDN. The helper script is extra; Katalon itself clears before each launch. Do not start the suite until this prints a success line, and record that line in the execution report. Do not uninstall the app to reset it, and do not change `CloudApp.launch()` for this.

A failure of the first case that happens because the app was not reset is classed `ENVIRONMENT`, not `PRODUCT_DEFECT`; reset and re-run before classifying.

USB runs use profile `local`. Do not change `DEVICE_NAME` (TestCloud phone) to the USB model. Do not edit the application under test.

If no Studio or Runtime Engine run is available from this session, say so, leave results `Not Run`, and still write the execution report with the device listing. Do not mark `Passed` without a result file.

## 8. Failures

Classes and the rerun rule: [references/execution-and-failures.md](references/execution-and-failures.md).

Change automation only for `AUTOMATION_DEFECT`. Do not relax an assertion to force a pass. Do not hide a product failure.

## 9. Reports

| File | Role |
| --- | --- |
| `docs/qa/<JIRA-KEY>/requirement-analysis.md` | Requirement model |
| `docs/qa/<JIRA-KEY>/open-questions.md` | Gaps |
| `docs/qa/<JIRA-KEY>/attachments/` | Downloaded Jira files (when bytes were readable) |
| `docs/qa/<JIRA-KEY>/<JIRA-KEY> - <Summary> - Test Cases.xlsx` | Cases |
| `docs/qa/<JIRA-KEY>/coverage.md` | Script output |
| `docs/qa/<JIRA-KEY>/automation-report.md` | What was reused or added |
| `docs/qa/<JIRA-KEY>/execution-report.md` | Device and results |

Coverage JSON matches [templates/coverage.example.json](templates/coverage.example.json). Generate the markdown with:

```powershell
powershell -File .cursor/skills/jira-katalon-automation/scripts/compute-coverage.ps1 `
  -InputJson "docs/qa/<JIRA-KEY>/coverage.json" `
  -OutputPath "docs/qa/<JIRA-KEY>/coverage.md" `
  -JiraKey "<JIRA-KEY>"
```

The script refuses unknown requirement ids and missing technique or execution values. Do not replace its counts with estimates.

Automation and execution tables: [templates/automation-report.md](templates/automation-report.md), [templates/execution-report.md](templates/execution-report.md).

Katalon scripts, objects, and suites stay in the Katalon folders. `docs/qa` holds the analysis, attachments, and the workbook only.

## Safety

- Do not invent acceptance criteria, credentials, OTP values, backend state, selectors, or attachment contents.
- Do not invent a semantic widget id. Report `SEMANTIC_ID_MISSING` instead.
- Do not delete working Katalon assets or edit unrelated tests.
- Do not change production application code.
- Do not weaken assertions to obtain a pass.
- Do not commit secrets. Do not copy values out of `staging.json` into Excel or markdown.
- Do not rewrite `CloudApp`, profiles' required variable set, or `TS_Smoke` as part of a story automation.
