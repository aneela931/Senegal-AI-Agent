# Requirement model

Build this before any test case. One row per distinct, evidenced behaviour. Do not create a requirement from the Jira summary alone.

## IDs

`REQ-001`, `REQ-002`, … scoped to the ticket folder. Never reuse an ID inside the same `docs/qa/<JIRA-KEY>/` analysis.

## Fields

| Field | Rule |
| --- | --- |
| Requirement ID | `REQ-00N` |
| Jira Key | The ticket being analysed |
| Requirement | Behaviour stated in the ticket, a comment, a linked issue, or the **contents** of an attachment that was actually read |
| Acceptance Criteria Reference | Quote or heading from the source. If there is no AC, write `none stated` |
| Source | `description`, `acceptance criteria`, `comment`, `linked:<KEY>`, `attachment:<name>`, `subtask:<KEY>`. An attachment source is valid only after the file was read. `attachment:<name> (not accessible)` is a gap, not a requirement. |
| Risk | `high`, `medium`, or `low` from impact stated in the ticket. Do not inflate risk |
| Ambiguity | `none` or a short description of what is unclear |
| Testable | `yes` only when an observable expected result is stated. Otherwise `no` |
| Notes | Dependencies, roles, data names. No secret values |

## Gap analysis

Record these in `open-questions.md` only when the ticket does not answer them:

- missing acceptance criteria
- ambiguous requirements
- contradictory statements
- unspecified error handling
- unspecified validation
- missing role or permission behaviour
- missing boundary behaviour
- missing API or backend dependencies
- unclear test data
- unclear expected results

Do not invent an answer to close a gap. An open question does not block the rest of the suite when other requirements are testable.

## Blocked cases

A case that cannot be expected-resulted without guessing is still listed, with Excel Status `BLOCKED_REQUIREMENT`, and it is tied to the open question. It is not a covered requirement.
