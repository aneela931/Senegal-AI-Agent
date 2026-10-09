# Open questions — SAA-918

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | expected result | Anti-Fraud Validation Expected Result is empty | REQ-021 | TC-918-020 |
| Q-002 | role | Staging agent is PDV; allowed profiles are POA, Dealer Shop, Team Leader, Staff | REQ-001 | none — entry case records if the action is hidden |
| Q-003 | expected result | Unsupported document error text | REQ-009 | none |
| Q-004 | ambiguity | "BE sends" under Procuration has no payload besides IsProcuration=1 in the rules table | REQ-005 | none |
| Q-005 | test data | Customers with MFS 1,999 and 2,000 FCFA; burnt and already-activated ICCIDs | REQ-002, REQ-003, REQ-016, REQ-017 | manual rows |

## Not invented

- No anti-fraud lock message was assumed.
- No ICCID length was assumed.
