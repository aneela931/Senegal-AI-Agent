# Requirement analysis — SAA-921

Jira: SAA-921
Summary: (Sprint 1) Re-identification Change Request
Retrieved: 2026-10-08
Source: Atlassian Jira (`getJiraIssue`, view `full`; description returned as HTML)

## Ticket facts

| Field | Value |
| --- | --- |
| Priority | Medium |
| Status | Ready for QA |
| Labels | none |
| Components | none |
| Sprint | SNAA Sprint 1 (active) |
| Linked issues | none |
| Subtasks | none |
| Attachments | `SAA-921 Re-identification Change Request.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

APIs: Check Number Status (`status_dossier`); Get Subscriber Info when Re-identification is selected, then OTP for active MSISDN; Verify Number Ownership; Send/Verify OTP; SIM Limit; Re-identification; Send Attachments.

Allowed profiles: POA, Dealer Shop, Team Leader, Staff. Access via Back Office.

Main flow: select Re-identification; enter MSISDN; check status; Get Subscriber Info; active → OTP, inactive → habit questionnaire; scan ID; OCR; validate KYC; SIM threshold; PIN; receipt.

OTP: 60s timer, 5 min expiry, resend after timer, max 5, then 10 min wait. Invalid OTP quoted message; flow ends.

Inactive questionnaire: most frequent contact (one or two MSISDNs) and latest airtime/bundle reload; both must be correct. Failure quoted message; only Cancel.

ID types, OCR retry, KYC mandatory vs editable fields, unauthorised document quoted message, SIM threshold 3 with quoted message and Home redirect, receipt On Going / Sim Activated / SMS text / Transaction ID, history, performance not counted by default (configurable in Admin Portal). Admin manages feature flag and OTP timers.

Key Business Rules table matches the above.

## Comments that change scope

- 2026-08-13 (Afaq Khan): GetSubscriberInfo failed with SSLHandshakeException / PKIX on `192.168.41.150`. Environment, not an AC.
- 2026-09-30: 18-case workbook delivery comments. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-921 | Re-identification is opened from Customer Services | Main Flow | description | high | none | yes | |
| REQ-002 | SAA-921 | Certified customer: re-identification not authorised | Key Business Rules | description | high | none | yes | |
| REQ-003 | SAA-921 | Uncertified eligible customer is allowed | Key Business Rules | description | high | none | yes | |
| REQ-004 | SAA-921 | Active MSISDN OTP rules (60s, 5 min, resend, max 5, 10 min wait) | Active MSISDN – OTP Flow | description | high | none | yes | |
| REQ-005 | SAA-921 | Invalid OTP quoted message; flow ends | Invalid OTP Error | description | high | none | yes | |
| REQ-006 | SAA-921 | Inactive MSISDN habit questionnaire; both answers required | Inactive MSISDN | description | high | Correct-answer source not stated | yes | |
| REQ-007 | SAA-921 | Failed questionnaire quoted message; only Cancel | Failure Error | description | high | none | yes | |
| REQ-008 | SAA-921 | Supported IDs; OCR retry | ID Scanning and OCR | description | medium | none | yes | |
| REQ-009 | SAA-921 | Mandatory KYC fields block continue | Mandatory Fields | description | high | none | yes | |
| REQ-010 | SAA-921 | Country and Address are editable | Editable Fields | description | medium | none | yes | |
| REQ-011 | SAA-921 | Unauthorised document quoted message | Unauthorised Document | description | high | none | yes | |
| REQ-012 | SAA-921 | SIM threshold 3 quoted message and Home redirect | SIM Threshold Rules | description | high | none | yes | |
| REQ-013 | SAA-921 | PIN submits the request | Transaction Completion | description | high | none | yes | |
| REQ-014 | SAA-921 | Receipt table and download/share | Receipt | description | high | none | yes | |
| REQ-015 | SAA-921 | History fields | History | description | medium | none | yes | |
| REQ-016 | SAA-921 | Not counted in performance by default | Performance | description | medium | none | yes | |
| REQ-020 | SAA-921 | Admin Portal manages feature flag and OTP timers | Admin Portal Configuration | description | low | No portal steps | no | |
| REQ-021 | SAA-921 | GetSubscriberInfo SSL failure documented in comments | comment | comment | high | Whether still failing on staging | yes | ENVIRONMENT if reproduced |

## Techniques selected

Happy path, negative (certified, invalid OTP, questionnaire fail, threshold, unauthorised document), boundary (OTP timer/resend), permission, validation.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001 | none | create: open Re-identification |
| REQ-005 | none | manual until an active customer MSISDN exists; OTP value stays in EnvConfig |
| others | none | manual |
