# Requirement analysis — SAA-912

Jira: SAA-912
Summary: (Sprint 1) Telco Services - Change Request
Retrieved: 2026-10-08
Source: Atlassian Jira (`getJiraIssue`, view `full`; description returned as HTML)

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
| Attachments | `image-20250311-182903.png` (referenced under "Accredited Users"); `SAA-912 Telco Services - Change Request.xlsx` and two renamed copies. Names visible; bytes not accessible. The image content is unknown. |

## Description (evidence)

APIs: getNumberStatus (validate customer MSISDN, retrieve certification / status, managed by BE); verifyNumberOwnership; Eligibility Validation (BE) determines available services from customer status and agent accreditation.

Customer Services lets agents check customer certification status and perform eligible services based on agent accreditation, customer certification status and customer account status. Services: SIM Swap, Re-identification, Upgrade Customer File, Subscribe to Mixx by Yas.

Basic flow: select Customer Services → enter customer MSISDN → system validates MSISDN → system retrieves certification status → eligible actions displayed.

Invalid / inactive MSISDN: "It seems the MSISDN entered is either incorrect or inactive."

Agent account restrictions: if the MSISDN belongs to an active POS, POA, Cash Point or Agent Account, Telco services (SIM Swap, Re-identification, Upgrade Customer File) are not allowed.

Non-accredited users: may enter the MSISDN and view certification status; may not perform any of the four services; no action buttons displayed.

Accredited users: actions depend on certification status (matrix). Certified Telco + MFS: SIM Swap only. Certified Telco Only: SIM Swap and Mixx by Yas. Non-Certified: SIM Swap, Re-identification, Upgrade Customer File.

Senegal eligibility by `Status_Dossier` (SIM Swap / Re-identification, Upgrade KYC, Upgrade Customer File): 0-dossier ✅❌✅; A régulariser ✅❌✅; Ré-initié ✅✅✅; Initié ✅❌✅; Identifié ❌✅✅; Certifié ❌✅❌; Migré ✅❌✅; A certifier ❌✅❌; Identifié avec complement MFS ❌❌✅; Ré-initié après migration ✅❌✅; Ré-identifié ✅❌✅. Upgrade Customer File may call tsARTService to retrieve missing documents.

Action buttons: only authorised actions are displayed; unauthorised actions must not be displayed.

## Acceptance criteria (evidence)

none stated as a separate section.

## Comments

Three delivery comments (2026-09-30) for a 26-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-912 | Customer Services is opened from the dashboard and asks for the customer MSISDN | "Basic Flow" 1–2 | description | high | none | yes | |
| REQ-002 | SAA-912 | A valid MSISDN returns the customer certification status and the eligible actions | "Basic Flow" 3–5 | description | high | none | yes | |
| REQ-003 | SAA-912 | Invalid or inactive MSISDN shows "It seems the MSISDN entered is either incorrect or inactive." | "MSISDN Validation" | description | high | none | yes | |
| REQ-004 | SAA-912 | MSISDN of an active POS, POA, Cash Point or Agent Account → SIM Swap, Re-identification, Upgrade Customer File not allowed | "Agent Account Restrictions" | description | high | What is shown instead (message vs. hidden buttons) is not stated | yes | Assert the three actions are absent |
| REQ-005 | SAA-912 | Non-accredited agent can enter the MSISDN and see the certification status | "Non-Accredited Users — Allowed" | description | high | none | yes | Needs a non-accredited account |
| REQ-006 | SAA-912 | Non-accredited agent sees no action buttons | "UI Behaviour — No action buttons displayed" | description | high | none | yes | |
| REQ-007 | SAA-912 | Certified Telco + MFS customer → SIM Swap only | matrix row 1 | description | high | none | yes | |
| REQ-008 | SAA-912 | Certified Telco Only → SIM Swap and Mixx by Yas | matrix row 2 | description | high | none | yes | |
| REQ-009 | SAA-912 | Non-Certified → SIM Swap, Re-identification, Upgrade Customer File; no Mixx | matrix row 3 | description | high | none | yes | |
| REQ-010 | SAA-912 | Senegal `Status_Dossier` rows decide SIM Swap / Re-identification, Upgrade KYC, Upgrade Customer File availability | "Senegal Eligibility Rules" | description | high | How the general matrix and the Senegal table combine is not stated; Upgrade KYC is not in the Action Buttons list | yes | One case per status row |
| REQ-011 | SAA-912 | Only authorised actions are displayed; unauthorised actions are not displayed | "Action Buttons" | description | high | none | yes | |
| REQ-012 | SAA-912 | Upgrade Customer File may call tsARTService to retrieve missing documents | "For Upgrade Customer File" | description | low | "may" | no | |

## Techniques selected

Happy path, negative (invalid MSISDN), permission (accredited vs. non-accredited, agent-account restriction), decision table (certification matrix, Status_Dossier table), equivalence partition (MSISDN classes). No boundary analysis is supported by the text.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001, REQ-003 | none | create: open Customer Services from the dashboard; submit `testData.login.invalidMsisdnFormat` and assert the quoted message |
| others | none | manual — need customer MSISDNs per status and accredited / non-accredited agent accounts |
