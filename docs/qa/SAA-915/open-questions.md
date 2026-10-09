# Open questions — SAA-915

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | contradiction | Preconditions say open notifications; Normal Course says activity historic | REQ-001 | none — Normal Course used |
| Q-002 | contradiction | Commission per row and total vs comment "comission amount is removed" | REQ-015 | none — comment wins; assert commission is not shown |
| Q-003 | contradiction | Reload sent has list fields but is not in the Type of transactions list | REQ-005, REQ-011 | none |
| Q-004 | ambiguity | Real-time update interval | REQ-004 | none |
| Q-005 | test data | Same-day registration, airtime, reload, rejected file | REQ-009..014 | manual rows |

## Not invented

- No commission amount was treated as still required after the 2026-09-17 comment.
- No second entry point from Notifications was assumed.
