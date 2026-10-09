# Requirement analysis — SAA-908

Jira: SAA-908
Summary: (Sprint 1) Login - Change Request
Retrieved: 2026-10-07
Source: Atlassian Jira (`getJiraIssue`, view `full`)

## Ticket facts

| Field | Value |
| --- | --- |
| Priority | Medium |
| Status | QA in Progress |
| Labels | none |
| Components | none |
| Sprint | SNAA Sprint 1 (active) |
| Linked issues | SAA-955 relates to — Proof of testing - Log In - Change Request |
| Subtasks | none (`parent = SAA-908` returned no issues) |
| Attachments | `SAA-908 Login - Change Request.xlsx`; `SAA-908 Login - Change Request (41bc2059-e583-4796-966c-d096c220fb66).xlsx`. Names are visible. File bytes were not downloaded. |

## Description (evidence)

APIs named in the story:

- Get User Detail — validate MSISDN and user role (Agent/Staff only). Linked solution document section "1. Get User Detail".
- Check Pin — validate user PIN. Linked solution document section "3. Check Pin".

Login flow stated in the story:

1. Splash is shown on launch. Default language is French. The user can switch between French and English.
2. The user enters an agent MSISDN. Senegal format: prefix 7, length 9 digits, example `70 000 01 01`. Country code +221 is described as handled by the backend.
3. Get User Detail is called. Grade Name Agent or Staff proceeds to OTP. Any other grade shows: "The phone number entered is not allowed to connect to the YAS Agent App. Please try again with a correct number."
4. Last used MSISDN is auto-filled on a later login and can be edited.
5. OTP screen follows a successful MSISDN validation. Resend OTP is available after OTP expiry. Invalid OTP shows: "Invalid OTP. The code you entered is incorrect. Please check the OTP and try again."
6. The user must accept Privacy terms, Camera access, and Image access before Confirm and Continue.
7. Check PIN. Attempts 1 and 2 show: "PIN entered is incorrect. Please try again." Attempt 3 shows the temporary-lock message, with the wait written as "X minute". Three attempts are allowed. Lock time increases by 1 minute after every set of 3 failures. Retry is enabled only after the lock expires.
8. The description says the user can request a PIN reset from the PIN screen.
9. When one connected device is allowed, a login of the same MSISDN on another device ends the existing session, requires OTP on the new device, and links the new device. The existing device shows: "You have been logged out of your current session because your account was accessed using your credentials on another device."
10. Connected-device count and new prefixes are managed in Back Office.
11. First successful login requires biometric setup (fingerprint or face). Senegal cannot skip it. Biometrics can later be disabled from Profile Settings.

## Comments that change scope

- 2026-08-12: country code +221 stays static on the FE for now.
- 2026-08-13: password reset is not part of the mobile app. The reporter confirmed "no its not".
- Later comments only record that a 34-case workbook was attached. They do not add behaviour.

## Linked solution document

Page `1413709826` was read. Get User Detail lists `detailGradeName` values Agent and Free Staff (grade codes GRT and FSTAF). Check Pin is `GET /sentinel/check-pin-agent` and does not state a lock duration. Section 2 of that page describes a free-user OTP API (6 digits, 5-minute expiry, and a note that expiry is not working). That section is not the agent-login acceptance criteria, so those numbers were not copied into the requirements.

SAA-955 contains proof screenshots titled around an incorrect agent number and an incorrect OTP. The image bytes were not read.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-908 | Splash is displayed when the app launches | Splash Screen, "Displayed when app is launched" | description | medium | none | yes | |
| REQ-002 | SAA-908 | Default language is French | "Default language: French" | description | medium | none | yes | |
| REQ-003 | SAA-908 | User can switch between French and English | "User can switch between French and English" | description | medium | none | yes | |
| REQ-004 | SAA-908 | User enters an agent MSISDN | MSISDN Entry | description | high | none | yes | |
| REQ-005 | SAA-908 | Senegal MSISDN is prefix 7 and 9 digits | "Prefix: 7", "Length: 9 digits", example 70 000 01 01 | description | high | none | yes | |
| REQ-006 | SAA-908 | +221 is not typed by the user; FE keeps it static | Story says backend handles +221. Comment says keep +221 static on the FE | description, comment | medium | Which layer owns +221 | yes | Do not assert the API payload |
| REQ-007 | SAA-908 | Last used MSISDN is auto-filled on a later login | "Last used MSISDN should be auto-filled" | description | medium | none | yes | |
| REQ-008 | SAA-908 | User can edit the auto-filled MSISDN | "User can edit the auto-filled MSISDN" | description | medium | none | yes | |
| REQ-009 | SAA-908 | Get User Detail is used to validate the MSISDN | "Call Get User Detail API" | description | high | none | yes | Observable by OTP or the block message |
| REQ-010 | SAA-908 | Grade Agent or Staff proceeds to OTP | "Grade Name = Agent or Staff" | description | high | Story says Staff; solution doc says Free Staff | yes | No separate Staff MSISDN in the story |
| REQ-011 | SAA-908 | Any other grade shows the quoted block message | Invalid User quote | description | high | none | yes | |
| REQ-012 | SAA-908 | OTP screen follows successful MSISDN validation | "OTP screen displayed after successful MSISDN validation" | description | high | none | yes | |
| REQ-013 | SAA-908 | Resend OTP is available after OTP expiry | "Resend OTP button available after OTP expiry" | description | medium | Expiry duration is not in the story | yes | Do not use the free-user 5-minute note as the rule |
| REQ-014 | SAA-908 | Invalid OTP shows the quoted error | Invalid OTP quote | description | high | none | yes | |
| REQ-015 | SAA-908 | Privacy, camera, and image access must be accepted | Terms list | description | high | none | yes | |
| REQ-016 | SAA-908 | Confirm and Continue is available only after those three acceptances | "Only after acceptance can user select Confirm and Continue" | description | high | none | yes | |
| REQ-017 | SAA-908 | Check PIN validates the PIN | "Call Check PIN API" | description | high | Success screen after PIN is not named | yes | |
| REQ-018 | SAA-908 | Wrong PIN attempts 1 and 2 show the quoted message | Wrong PIN 1st and 2nd attempt quote | description | high | none | yes | |
| REQ-019 | SAA-908 | The third wrong PIN shows the temporary-lock message | Wrong PIN 3rd attempt quote | description | high | The wait is written as X minute | yes | Quote kept with X |
| REQ-020 | SAA-908 | Three attempts are allowed | "3 attempts allowed" | description | high | none | yes | |
| REQ-021 | SAA-908 | Lock time increases by 1 minute after every set of 3 failures | Additional rule | description | high | Starting duration is X | yes | Relative increase is stated |
| REQ-022 | SAA-908 | Retry is enabled only after the lock expires | "Retry button enabled only after lock period expires" | description | high | none | yes | |
| REQ-023 | SAA-908 | PIN reset from the PIN screen | "User can request PIN reset from PIN screen" | description | medium | Contradicted by comments | no | Out of mobile scope per comment |
| REQ-024 | SAA-908 | A second-device login ends the first session and shows the quoted message | Single Device Login | description | high | none | yes | Needs two devices to execute |
| REQ-025 | SAA-908 | The new device requires OTP and becomes the linked device | Same section | description | high | none | yes | Needs two devices to execute |
| REQ-026 | SAA-908 | Connected-device count is managed in Back Office | Configuration | description | medium | No Back Office steps | no | |
| REQ-027 | SAA-908 | New prefixes can be added in Back Office | Configuration | description | medium | No Back Office steps | no | |
| REQ-028 | SAA-908 | First login biometric setup is mandatory and cannot be skipped in Senegal | First Login Biometric Authentication | description | high | none | yes | |
| REQ-029 | SAA-908 | User configures fingerprint or face recognition | "Fingerprint OR Face Recognition" | description | high | Screen after enrolment is not named | yes | |
| REQ-030 | SAA-908 | User can later disable biometrics from Profile Settings | "disable biometrics from Profile Settings" | description | medium | none | yes | |

## Techniques selected

Happy path, negative, boundary value analysis on MSISDN length/prefix and on the lock-time increase, validation, permission (Agent or Staff), session (single device), localization. No decision table or interruption case was added; the story does not state those combinations.

## Existing automation already covering a requirement

Thirty Katalon scripts under `Scripts/Login/` already exist, including the three blocked cases. They were mapped. No new script, page, or object was added.

## Cases from the old 34-row workbook that were not carried forward

| Old ID | Why it is not in this workbook |
| --- | --- |
| TC-908-008 | HTTP 500 / timeout behaviour is not in the story |
| TC-908-029 | Missing PIN parameter behaviour is not in the story |
| TC-908-030 | Missing MSISDN parameter behaviour is not in the story |
| TC-908-032 | Same partition and same block message as TC-908-007 |
