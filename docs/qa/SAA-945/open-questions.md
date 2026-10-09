# Open questions — SAA-945

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | role | Staging inventory is PDV; allowed profiles are Dealer, Supervisor, Team Leader, Reseller | REQ-001 | none — TC-945-001 uses an authorised profile, not PDV |
| Q-002 | test data | Parent MSISDN for the POA numbers listed in the 2026-09-20 comment (asked 2026-09-21, unanswered) | REQ-004, REQ-016 | none |
| Q-003 | attachment | Figma PNG bytes not accessible | REQ-018 | TC-945-011 |
| Q-004 | expected result | In-app confirmation copy after Create Dealer success | REQ-013, REQ-014 | none — success is BO/SN System evidence |

## Not invented

- No parent MSISDN was assumed.
- Comment MSISDNs were not copied into Excel.
- No extra screens were taken from the unread PNG.
