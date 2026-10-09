# Open questions — SAA-911

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | test data | The inactivity timeout value configured for the staging test profile (PDV). Name it `EnvConfig testData.session.inactivityTimeoutMinutes` when known | REQ-001, REQ-003, REQ-004 | none — TC-911-001, 002, 004, 005 are Manual and wait for the value |
| Q-002 | expected result | Which screen is shown after an automatic logout (MSISDN entry, PIN, or splash) and whether a message is displayed | REQ-001 | TC-911-006 |
| Q-003 | boundary | Whether the logout happens exactly at the configured minute or on the next user interaction after it | REQ-001 | none — TC-911-005 records the observed behaviour |
| Q-004 | interruption | Behaviour when the timeout expires during an in-progress transaction (e.g. registration) | REQ-001 | none — not designed, see below |
| Q-005 | role | Profiles available on staging and their configured values | REQ-002 | TC-911-008 is Manual |

## Not invented

- No default timeout value was assumed.
- No logout message or destination screen was assumed.
- No behaviour was assumed for a timeout during an in-progress transaction; no case was written for it.
