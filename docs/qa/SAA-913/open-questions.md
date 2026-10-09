# Open questions — SAA-913

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | role | Staging inventory is PDV. Which Performance entry list applies to PDV | REQ-002, REQ-004 | none — TC-913-003..005 need named profiles |
| Q-002 | test data | Accredited vs non-accredited accounts; Reseller; Team Leader; Supervisor | REQ-003..006, REQ-021..025 | manual rows stay Not Executed until those accounts exist |
| Q-003 | expected result | Configured hotline number for Need Help | REQ-020 | none — TC-913-018 asserts that a hotline control dials a number, not a specific value |
| Q-004 | ambiguity | "this logic is not implemented internally" vs the accreditation table | REQ-006 | none |
| Q-005 | API dependency | How Gross Add D+1 is populated on staging | REQ-009 | none |

## Not invented

- No default profile for PDV was assumed on the selection screen.
- No hotline MSISDN was assumed.
- No KPI numeric values were assumed.
