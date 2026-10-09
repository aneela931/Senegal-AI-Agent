# Open questions — SAA-908

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | contradiction | The description says the user can request a PIN reset from the PIN screen. Comments on 2026-08-13 say password reset is not in the mobile app, and the reporter confirmed that. | REQ-023 | TC-908-019 |
| Q-002 | unclear expected results | Connected-device count and new prefixes are "managed from Back Office". No screen, step, or expected Back Office result is written. | REQ-026, REQ-027 | TC-908-022, TC-908-023 |
| Q-003 | unclear test data | OTP length and expiry duration are not in the story. Solution-doc section 2 states 6 digits and 5 minutes for a free-user OTP API, and says that expiry is not working. Those figures were not adopted as agent rules. | REQ-013 | none for design. A timed run of TC-908-011 and TC-908-033 must use the timer the app shows, not an assumed 5 minutes |
| Q-004 | missing boundary behaviour | The third-attempt lock message uses "X minute". Check Pin in the solution document does not give the starting lock duration. | REQ-019, REQ-021 | none. TC-908-016 keeps the quoted message, including X. TC-908-017 checks the stated +1 minute difference |
| Q-005 | unclear test data | The story allows Grade Name Staff. The solution document names Free Staff (FSTAF). No Staff MSISDN is given in the story. Staging still has a placeholder for the non-agent number. | REQ-010, REQ-011 | none. TC-908-006 can run for Agent. The Staff value is not executed until a number is supplied |
| Q-006 | ambiguous requirements | Error quotes are English. The default splash language is French. The story does not say whether those errors are translated. | REQ-011, REQ-014 | none. TC-908-031 is limited to the English UI |
| Q-007 | missing test data | Single-device login needs two devices. Only one phone (Infinix X6836) is attached, and it is already linked to the agent account, so the app skips OTP and Terms and opens the PIN screen directly. | REQ-024, REQ-025, REQ-013 | none for design. TC-908-020 and TC-908-021 were not run. OTP/Terms cases need an unlinked device or a device-link reset |
| Q-009 | test data | `testData.login.incorrectPin` in `staging.json` is 5 characters; the PIN screen accepts exactly 4 digits, so the value cannot be entered. | REQ-018, REQ-019 | TC-908-014 to TC-908-017 and TC-908-034 cannot execute until a 4-digit incorrect PIN is set in `staging.json` |
| Q-008 | unspecified error handling | The older workbook had API 500, missing PIN, and missing MSISDN cases. The story does not state those results. | none | TC-908-008, TC-908-029, TC-908-030 were not generated |

## Not invented

A post-login screen name, an OTP value, a PIN value, a Staff MSISDN, a Back Office click path, and a numeric substitute for X were left out. Expected results that previously said "dashboard" were narrowed to the biometric and PIN outcomes the story actually states.
