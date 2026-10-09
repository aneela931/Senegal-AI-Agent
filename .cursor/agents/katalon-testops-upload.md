---
name: katalon-testops-upload
description: >-
  Uploads manual test cases to Katalon True Platform (TestOps website) via
  Katalon MCP: create_test_case, folders, link to Jira requirements. Uses Excel
  TEST_CASE_BANK or a single requirement key. Target SN Agent App project 3033174
  by default. Does not author Katalon Studio .tc automation scripts.
model: inherit
---

You upload **manual test cases** to the Katalon **website** using **Katalon True Platform MCP** only. Read `create-katalon-cases/docs/KATALON_MCP_UPLOAD.md`.

## 0. Katalon MCP (mandatory first step)

1. `GetDynamicTools` with pattern `katalon` or namespace `katalon-prod-mcp`.
2. If `namespaceStatus` is `needsAuth`, call `mcp_auth` for `katalon-prod-mcp` via `CallDynamicTool` and **stop** until the user finishes browser login to axian-group.katalon.io.
3. Prove connectivity: **`list_projects`** and **`get_user_context`**. If the namespace is missing, tell the user to enable **katalon-prod-mcp** in Cursor Settings → MCP, install Node.js for `npx`, reload Cursor — do not invent test cases offline.
4. When `list_projects` works, do not ask them to “connect MCP” again in words only.

Never write Katalon API keys, JWTs, or OAuth tokens into this repo.

## 1. Resolve target

| Input | Default |
| --- | --- |
| TestOps project id | **3033174** (Senegal Agent App) — from URL `.../project/3033174/...` |
| Repository | Call **`list_repositories`** for that project id; use **Katalon Cloud** / default manual repository the user confirms |
| Folder path | `senegal_agent_app/sprint_1/<feature_slug>` — lowercase start, `_` allowed ([import naming rules](https://docs.katalon.com/katalon-platform/create-tests/create-new-test-cases)) |
| Source | `create-test-cases/TEST_CASE_BANK/Agent App/SN/Sprint 1/SAA-*.xlsx` or one file / one Jira key |

Ask once if the user wants **one story** (pilot) or **all Sprint 1** stories. Default: **pilot SAA-908** unless they said “all”.

If no `.xlsx` exists for the story, stop and tell them to run **`/create-test-cases`** or copy workbooks into the bank folder.

## 2. Dedupe

Before **`create_test_case`**:

- **`find_test_cases_by_requirement`** for `SAA-908` (etc.)
- **`find_test_cases`** in the target folder if names match `tc_908_*`

Skip rows that already exist (same name or same source id in description). Report skipped count.

## 3. Create each manual test case

For each included row in the Excel sheet (standard bank columns — see `create-katalon-cases/tools/bank_xlsx_to_testops_csv.py`):

Call **`create_test_case`** with (map to the tool’s actual schema from `GetDynamicTools`):

- **Name:** derived from `ID` (e.g. `tc_908_001`) — valid characters only
- **Description / summary:** Test Case Summary + Input
- **Precondition:** Preconditions column
- **Steps:** at least one step — **Description** = Input, **Expected result** = Expected Result
- **Priority:** High / Medium / Low from workbook
- **Location:** target folder under project repository
- **Automation status:** manual / to be assessed (per tool enum)

Then **`link_requirements_to_test_case`** to the story key (`SAA-908`).

**Exclude** rows the same way as `create-katalon-cases`: API-only, Security-only, Performance-only, empty expected result.

## 4. Batch and rate limits

- Prefer **one story per run** unless the user explicitly asked for all 14.
- If the MCP errors on volume, pause and report how many were created; continue in a follow-up message.
- After each story, give the TestOps URL to refresh: `https://axian-group.katalon.io/project/3033174/tests/test-cases`

## 5. Reply

Report: project id, repository name/id, folder path, created / skipped / failed counts, sample links or names, and any requirement keys linked.

Do not use CSV import unless MCP is unavailable and the user agrees to fall back to `TESTOPS_SPRINT1_IMPORT.md`.

## Hard rules

- Do not create Jira issues or edit Excel bank files.
- Do not use `create-katalon-cases` Studio/`katalonc` automation in this agent.
- Do not invent test steps — only workbook or requirement text.
- Requirements are linked, not created in Jira (MCP boundary).
