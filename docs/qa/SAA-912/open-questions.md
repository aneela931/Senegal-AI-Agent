# Open questions — SAA-912

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | expected result | What an accredited agent sees for a POS / POA / Cash Point / Agent Account MSISDN: a message, or the status with no Telco buttons | REQ-004 | none — TC-912-005 asserts the three actions are absent |
| Q-002 | contradiction | "Upgrade KYC" appears in the Senegal table but not in the Action Buttons list; how the general matrix (3 rows) and the Senegal `Status_Dossier` table (11 rows) combine when they disagree (e.g. Certifié allows Upgrade KYC but the matrix has no such action) | REQ-010, REQ-011 | TC-912-020 |
| Q-003 | attachment | `image-20250311-182903.png` under "Accredited Users" could not be read; it may hold a screen or a table | REQ-007..009 | none |
| Q-004 | test data | Customer MSISDNs for each certification class and each `Status_Dossier`; a POS/POA/Cash Point/Agent MSISDN; a non-accredited agent account | REQ-004..010 | manual cases are Not Executed until data exists |
| Q-005 | validation | MSISDN format rules on the Customer Services input (length, prefix) are not stated here; SAA-908 rules apply to the agent login only | REQ-003 | none — TC-912-003 uses the invalid format from `testData.login.invalidMsisdnFormat` and expects the quoted message or an inline block |

## Not invented

- No format validation message was assumed for the Customer Services MSISDN field.
- No combination rule was assumed between the two eligibility tables.
- No content was assumed for the unreadable PNG.
