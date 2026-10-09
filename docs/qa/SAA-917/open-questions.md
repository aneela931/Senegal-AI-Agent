# Open questions — SAA-917

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | contradiction | This annex says customer picture is No; SAA-910 requires Customer Photo | REQ-007 | none — cases assert this annex only |
| Q-002 | validation | Exact character class for "8 digits and character" and "11 digits and character" | REQ-004, REQ-006 | none — length is asserted |
| Q-003 | ambiguity | Whether "except national" means Senegalese passports use the other row (NIN) rather than being forbidden | REQ-004, REQ-005 | none |

## Not invented

- No extra KYC fields from SAA-910 were copied here.
- No OCR engine accuracy threshold was assumed.
