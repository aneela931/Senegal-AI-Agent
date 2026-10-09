# Open questions — SAA-921

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | test data | Active and inactive eligible customer MSISDNs; correct habit-questionnaire answers | REQ-004, REQ-006 | none — answers must come from EnvConfig, not invented |
| Q-002 | API dependency | Whether GetSubscriberInfo SSL failure from 2026-08-13 is still present | REQ-021 | none — classify ENVIRONMENT if seen |
| Q-003 | role | Staging PDV vs allowed profiles POA / Dealer Shop / Team Leader / Staff | REQ-001 | none |
| Q-004 | expected result | Eligible `status_dossier` values for this flow vs SAA-912 table | REQ-003 | none — SAA-912 table is not copied in |

## Not invented

- No habit-questionnaire correct answers were invented.
- No OTP value was written into Excel.
