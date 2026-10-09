# Automation report — <JIRA-KEY>

| Test Case | Automated | Script | Reused Components | New Components | Status |
| --- | --- | --- | --- | --- | --- |
| TC-... | yes / no | `Scripts/<Feature>/<Case>/Script.groovy` or `—` | pages, journeys, objects reused | pages or objects added | mapped / created / blocked / skipped |

Status `mapped` means an existing script already covers the case. Do not add a second script.

## Semantic locators

| Screen | Widget | Semantic id | Seen on device / existing object | Notes |
| --- | --- | --- | --- | --- |
| | | `screen_kind` or `screen_kind_N` | yes / `SEMANTIC_ID_MISSING` | |

New objects use ids from `references/qa-semantics.md`. Do not invent a name.
