# Requirement analysis — SAA-918

Jira: SAA-918
Summary: (Sprint 1) Sim Swap - Change Request
Retrieved: 2026-10-08
Source: Atlassian Jira (`getJiraIssue`, view `full`)

## Ticket facts

| Field | Value |
| --- | --- |
| Priority | Medium |
| Status | QA in Progress |
| Labels | none |
| Components | none |
| Sprint | SNAA Sprint 1 (active) |
| Linked issues | none |
| Subtasks | none |
| Attachments | `SAA-918 Sim Swap - Change Request.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

APIs: Check Number Status, Get User Details, Get Balance (MFS, curl in ticket), Verify Number Ownership, SIM Swap, Send Attachments.

Allowed profiles: POA, Dealer Shop, Team Leader, Staff. Eligible customers: Certified Telco, Certified Telco + MFS, Eligible Non-Certified.

Flow: select SIM Swap; MFS balance max 1,999 FCFA, ≥ 2,000 blocked with quoted store-redirect message; Regular default vs Procuration; ID types; scan; OCR not editable, retake on failure; KYC four mandatory fields; compare OCR to record; failed verification quoted message; re-identification buttons depend on rights, Senegal redirects to SIM Swap Uncertified Customer Flow; non-certified may need habit questionnaire; anti-fraud heading has empty expected result; ICCID scan or manual; burnt SIM and already-activated quoted errors; PIN; receipt Certified / Sim Activated / identity verified / Transaction ID; download and share.

## Acceptance criteria (evidence)

The Key Business Rules table. Anti-Fraud Validation has an empty Expected Result.

## Comments

Three delivery comments (2026-09-30) for a 23-case workbook. Worklog mentions unit testing. No extra behaviour.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-918 | SIM Swap is selected from Customer Services | Step 1 | description | high | PDV not in allowed profiles | yes | |
| REQ-002 | SAA-918 | MFS balance below 2,000 FCFA allows swap | Key Business Rules | description | high | none | yes | 1,999 boundary |
| REQ-003 | SAA-918 | Balance ≥ 2,000 FCFA blocks with the quoted message | quoted error | description | high | none | yes | |
| REQ-004 | SAA-918 | Types Regular (default) and Procuration | Step 3 | description | medium | none | yes | |
| REQ-005 | SAA-918 | Procuration captures the document and sends IsProcuration=1 | Procuration Rules | description | high | "BE sends" is incomplete | yes | |
| REQ-006 | SAA-918 | Supported ID types | Step 4 | description | medium | none | yes | |
| REQ-007 | SAA-918 | OCR extracts name, DOB, ID number | Step 5 | description | high | none | yes | |
| REQ-008 | SAA-918 | OCR failure requires retake; OCR data not editable | OCR Failure | description | high | none | yes | |
| REQ-009 | SAA-918 | Unsupported document blocks the transaction | Unsupported Document | description | high | Message not quoted | yes | |
| REQ-010 | SAA-918 | Four KYC fields are mandatory | Step 6 | description | high | none | yes | |
| REQ-011 | SAA-918 | Match on four identity fields proceeds to SIM scan | Verification Successful | description | high | none | yes | |
| REQ-012 | SAA-918 | Mismatch shows the quoted identification-failed message | Verification Failed | description | high | none | yes | |
| REQ-013 | SAA-918 | Without re-identification rights: Cancel and Try Again | User not authorised | description | high | none | yes | |
| REQ-014 | SAA-918 | With rights: Re-identify Customer; Senegal uncertified flow | User authorised | description | high | Uncertified flow not specified here | yes | |
| REQ-015 | SAA-918 | Non-certified may need habit questionnaire and extra documents | Non-Certified Customer Rules | description | medium | "may require" | yes | |
| REQ-016 | SAA-918 | Unreadable ICCID quoted error with Try Again / Cancel | Burnt SIM | description | high | none | yes | |
| REQ-017 | SAA-918 | Already activated ICCID quoted error with Try Again / Cancel | ICCID Already Activated | description | high | none | yes | |
| REQ-018 | SAA-918 | ICCID via barcode scan or manual entry | SIM Scan | description | medium | none | yes | |
| REQ-019 | SAA-918 | PIN completes activation and ICCID update | SIM Swap Completion | description | high | none | yes | |
| REQ-020 | SAA-918 | Receipt fields and download/share | Receipt | description | high | none | yes | |
| REQ-021 | SAA-918 | Same ID on 3 swaps same day different MSISDNs/identities | Anti-Fraud Validation | description | high | Expected Result empty | no | |
| REQ-022 | SAA-918 | Incomplete mandatory fields cannot continue | Key Business Rules | description | high | none | yes | |

## Techniques selected

Happy path, boundary (1,999 / 2,000 FCFA), negative (quoted errors), permission (re-identify rights), validation (OCR, KYC).

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001 | none | create: open SIM Swap from Customer Services |
| others | none | manual — documents, ICCID, MFS balances |
