# Excel template

Clone this file. Do not redesign the workbook and do not overwrite it.

`SAA-908 Login - usecase (1).xlsx` at the Senegal_Agent_App project root.

The Sprint 1 bank file `create-test-cases/TEST_CASE_BANK/Agent App/SN/Sprint 1/SAA-908 Login - Change Request.xlsx` uses the same columns. It is not the file to clone, and it is not a file to overwrite.

## Workbook

| Item | Value |
| --- | --- |
| Sheet | One sheet, named the Jira key (`SAA-908` in the template). Rename it to the ticket being generated |
| Dimension | `A:Q` |
| Freeze | `ySplit=1`, top-left `A2` |
| Filter | Header row through the last data row (`A1:Q<last>`) |
| Header style | Row style `s="1"`, cell style `s="1"` |
| Data style | Row height 45, row style `s="2"`, cell style `s="2"` |
| Widths | A 14, B 28, C 48, D 36, E 36, F 48, G 18, H 16, I 14, J 12, K 28, L 28, M 16, N 36, O 12, P 14, Q 16 |

Write new workbooks with `scripts/export-test-cases.ps1` so these styles stay intact.

## Columns (row 1, do not rename)

| Col | Header | What to write |
| --- | --- | --- |
| A | ID | `TC-<number>-<nnn>` from the Jira key. `SAA-908` → `TC-908-001`. `SAA-1025` → `TC-1025-001` |
| B | UseCase/Feature | Screen or feature name, not the Jira key |
| C | Test Case Summary | One condition |
| D | Preconditions | Setup only |
| E | Input | Numbered **atomic** steps plus non-secret data names. One action per number (wait, tap one semantic widget, type one field, observe). Name the semantic id from `qa-semantics.md` when it is known. This sheet has no separate steps column |
| F | Expected Result | Observable outcome copied from evidence |
| G | Actual Result | Empty until a run |
| H | Test Type | Template words such as `Functional` or `Functional / Localization`. Add the primary technique in the coverage JSON, not as a new column |
| I | Status | `Not Executed` until a run. `BLOCKED_REQUIREMENT` when an open question blocks the expected result. After a run: `Passed`, `Failed`, or `Blocked` |
| J | Priority | `High`, `Medium`, or `Low` |
| K | Environment | The environment name the user gave, otherwise the profile value `staging`. Do not copy the sample IP from template row 2 |
| L | Build version | `EXPECTED_VERSION` / `EXPECTED_BUILD` read from `Profiles/local.glbl` at generation time |
| M | Assignee | Blank unless the user names one. Do not copy the sample assignee |
| N | Source / Traceability | `<JIRA-KEY>; REQ-00N; <AC reference>` |
| O | Automation | `Manual`, `Candidate`, or `Automated` |
| P | Defect ID | Empty unless a defect already exists |
| Q | Execution Date | Empty until a run |

## Output path

`docs/qa/<JIRA-KEY>/<JIRA-KEY> - <Jira summary> - Test Cases.xlsx`

Strip characters that are illegal in Windows file names from the summary. Example: `SAA-908 - Login - Change Request - Test Cases.xlsx`.

## Katalon file name

Excel `TC-908-014` is stored as `Test Cases/<Feature>/TC_908_014.tc` and `Scripts/<Feature>/TC_908_014/Script.groovy`.
