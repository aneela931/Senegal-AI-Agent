# Open questions — SAA-910

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | ambiguity | Which fields are the "Other mandatory KYC fields" (asked in the 2026-08-13 comment, unanswered) | REQ-002 | none — TC-910-003 covers the named fields only |
| Q-002 | expected result | Exact SIM threshold exceeded message and where the SIM Swap shortcut leads | REQ-004 | none — TC-910-006 asserts a threshold message and a shortcut |
| Q-003 | API dependency | `checkMsisdnQuota` is "currently awaited"; staging behaviour when the API is unavailable | REQ-003, REQ-004 | TC-910-005, TC-910-006 may be Blocked at run time |
| Q-004 | missing acceptance criteria | What is sent for auto certification and how the agent sees it | REQ-013 | TC-910-017 |
| Q-005 | expected result | Message or screen when an MRZ comparison is NOK ("Stop") | REQ-023, REQ-024 | none — cases assert the flow does not continue |
| Q-006 | ambiguity | Whether the read-only address for a Senegalese ID card is the literal "Address" or the address read from the card | REQ-020 | none — TC-910-021 asserts mandatory + read-only only |
| Q-007 | expected result | Allowed Certification Status values on the receipt | REQ-015 | none |
| Q-008 | test data | Customer identities with 0, 2 and 3 registered SIMs; Senegalese and foreign passports; CEDEAO and Refugee cards; MSISDN stock | REQ-001, REQ-003, REQ-004, REQ-006..009, REQ-018..024 | manual cases are Not Executed until documents are available |
| Q-009 | validation | Alternate Number format and whether it is mandatory | REQ-002 | none |

## Not invented

- No message text was assumed for the SIM threshold error or for an MRZ mismatch.
- No list of "other mandatory KYC fields" was assumed.
- No certification status vocabulary was assumed on the receipt.
- `IsProcuration = 1`, `mfsWallet = 1` and file naming are request / storage facts; the cases record them as backend evidence to collect, not as UI assertions.
