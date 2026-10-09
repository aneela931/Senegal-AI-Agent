# Requirement analysis — SAA-910

Jira: SAA-910
Summary: (Sprint 1) Customer Registration - Change Request
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
| Linked issues | SAA-957 relates to — Proof of testing - Customer Registration - Change Request (QA in Progress) |
| Subtasks | none |
| Attachments | `SAA-910 Customer Registration - Change Request.xlsx`; `SAA-910 Customer Registration - Change Request (0b78d199-...).xlsx`. Names visible; bytes not accessible. |

## Description (evidence)

APIs: Get Available MSISDN / Preconditioned SIM (fetch MSISDNs for registration); SIM Limit `checkMsisdnQuota` (count MSISDNs registered against the customer ID, Senegal threshold 3 SIMs, "API currently awaited"); New Registration (submit); Send Attachment (ID documents, customer photo, contract, procuration).

Registration types: Personal (standard flow). Procuration: procuration document must be uploaded and the request sends `IsProcuration = 1`.

MFS wallet: selected (`mfsWallet = 1`) → customer signature contract mandatory and contract image uploaded. Not selected → contract not required and not uploaded.

Auto certification: sent when at least one SIM is already registered under the customer and no KYC information has changed. The "Send:" line has no value.

Flow: Step 1 select an available MSISDN. Step 2 enter customer details (First Name, Last Name, Date of Birth, Address, Nationality, ID Type, ID Number, Alternate Number, "Other mandatory KYC fields"). Step 3 check SIM threshold: fewer than 3 active SIMs allowed; 3 or more blocked, show the SIM threshold exceeded message and a shortcut to the SIM Swap flow.

Mandatory completion: mandatory fields cannot be empty, required documents uploaded, required images captured, no step can be skipped, incomplete application cannot be submitted.

Attachments. Senegalese ID Card: ID Front, ID Back, Customer Photo; MFS Contract if MFS selected. Passport: Passport Image, Customer Photo; MFS Contract conditional. CEDEAO Card: Front, Back, Customer Photo; MFS Contract conditional. Refugee Card: Front, Back, Customer Photo; MFS Contract conditional. Procuration: additionally the procuration document.

Attachment naming: `1_<SubscriberMSISDN>_<AgentMSISDN>_<Timestamp>_<DocumentType>.jpg` with document types piece_identite_recto, piece_identite_verso, photo_identite, contrat, procuration, passeport, carte_cedeao_recto, carte_refugie_recto, carte_refugie_verso (table in the ticket; CEDEAO verso is listed with `piece_identite_verso`).

Successful registration: receipt shows Transaction ID and Certification Status; user can download, share, and send via email / apps supported by the device.

Admin Portal: SIM threshold per customer and country-specific rules are configurable.

MVP CR changes (pseudo code in the ticket):

- `DocumentType.id == "passport"` and country SENEGAL → mark as Senegalese Id Card and set "Numéro de la pièce d'identité" = MRZ Opt1. Else → passport, ID number = passportNumber.
- `DocumentType.id == "id"` and `isoAlpha2CountryCode == "SN"` → address mandatory and read-only, set to "Address". Country in BJ, GN, GW, GM, SL, NG, ML → CEDEAO card, address mandatory and editable. `passport` → address mandatory and editable.
- MRZ comparison. ID (SN or CEDEAO): first name vs MRZ SecondaryId, last name vs MRZ PrimaryId, Date of Birth vs MRZ DateOfBirth; any NOK → Stop. Passport (Senegal or foreign): the same three plus DocumentNumber vs MRZ DocumentNumber; any NOK → Stop.

## Acceptance criteria (evidence)

none stated as a separate section.

## Comments

- 2026-08-13 (Hamza Ashfaq): asked which fields are the "Other mandatory KYC fields" in Step 2. No reply in the ticket.
- 2026-09-30: three delivery comments for a 34-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-910 | Agent selects one MSISDN from the available MSISDNs returned for registration | "Step 1: Select MSISDN" | description | high | none | yes | Needs stock on staging |
| REQ-002 | SAA-910 | KYC form captures First Name, Last Name, Date of Birth, Address, Nationality, ID Type, ID Number, Alternate Number | "Step 2: Enter Customer Details" | description | high | "Other mandatory KYC fields" are not named (comment unanswered) | yes | Listed fields only |
| REQ-003 | SAA-910 | Fewer than 3 active SIMs on the customer ID → registration allowed | "Step 3 — Allowed" | description | high | none | yes | SIM Limit API "currently awaited" |
| REQ-004 | SAA-910 | 3 or more SIMs → SIM threshold exceeded message, registration stops, shortcut to SIM Swap offered | "Step 3 — Blocked" | description | high | Message text not quoted | yes | Assert a threshold message and the shortcut, not exact wording |
| REQ-005 | SAA-910 | Mandatory fields cannot be empty; required documents and images are required; no step can be skipped; incomplete application cannot be submitted | "Mandatory Completion Rules" | description | high | none | yes | |
| REQ-006 | SAA-910 | Senegalese ID Card requires ID Front, ID Back, Customer Photo | "Attachment Rules — Senegalese ID Card" | description | high | none | yes | |
| REQ-007 | SAA-910 | Passport requires Passport Image and Customer Photo | "Passport" | description | high | none | yes | |
| REQ-008 | SAA-910 | CEDEAO Card requires Front, Back, Customer Photo | "CEDEAO Card" | description | high | none | yes | |
| REQ-009 | SAA-910 | Refugee Card requires Front, Back, Customer Photo | "Refugee Card" | description | high | none | yes | |
| REQ-010 | SAA-910 | Procuration registration additionally requires the procuration document and sends `IsProcuration = 1` | "Procuration Registration" | description | high | none | yes | Request field is backend-observable only |
| REQ-011 | SAA-910 | MFS wallet selected → signature contract mandatory and contract image uploaded | "MFS Wallet Selected" | description | high | none | yes | |
| REQ-012 | SAA-910 | MFS wallet not selected → contract not required and not uploaded | "MFS Wallet Not Selected" | description | medium | none | yes | |
| REQ-013 | SAA-910 | Auto certification is sent when at least one SIM is already registered under the customer and no KYC changed | "Auto Certification Rules" | description | medium | What is sent is blank in the ticket | no | |
| REQ-014 | SAA-910 | Uploaded attachment file names follow `1_<SubscriberMSISDN>_<AgentMSISDN>_<Timestamp>_<DocumentType>.jpg` | "Attachment Naming Validation" | description | medium | none | yes | Observable in backend storage, not in the app UI |
| REQ-015 | SAA-910 | Receipt shows Transaction ID and Certification Status | "Receipt Screen" | description | high | Status values are not listed | yes | |
| REQ-016 | SAA-910 | Receipt can be downloaded, shared, and sent via email / device apps | "Receipt Actions" | description | medium | none | yes | |
| REQ-017 | SAA-910 | SIM threshold and country rules are configurable from the Admin Portal | "Admin Portal Configuration" | description | low | No portal steps | no | |
| REQ-018 | SAA-910 | Senegalese passport is recorded as Senegalese Id Card with ID number = MRZ Opt1 | "MVP CR changes" pseudo code | description | high | none | yes | Needs a Senegalese passport |
| REQ-019 | SAA-910 | Non-Senegalese passport is recorded as passport with ID number = passportNumber | pseudo code Else branch | description | high | none | yes | |
| REQ-020 | SAA-910 | Senegalese ID card (`SN`) → address mandatory and read-only | pseudo code `id` / `SN` | description | medium | Literal value "Address" vs. a real address is unclear | yes | |
| REQ-021 | SAA-910 | CEDEAO card (country in BJ, GN, GW, GM, SL, NG, ML) → address mandatory and editable | pseudo code allowed list | description | medium | none | yes | |
| REQ-022 | SAA-910 | Passport → address mandatory and editable | pseudo code `passport` | description | medium | none | yes | |
| REQ-023 | SAA-910 | ID card (SN or CEDEAO): first name, last name, date of birth must match MRZ SecondaryId, PrimaryId, DateOfBirth; any mismatch stops | MRZ comparison, ID branches | description | high | "Stop" has no message | yes | |
| REQ-024 | SAA-910 | Passport (Senegal or foreign): the three MRZ checks plus DocumentNumber vs MRZ DocumentNumber; any mismatch stops | MRZ comparison, passport branches | description | high | "Stop" has no message | yes | |

## Techniques selected

Happy path (personal registration per ID type), decision table (ID type × MFS × procuration attachments), equivalence partition (country code lists), boundary (SIM count 2 / 3), validation (mandatory completion, MRZ mismatch), negative (threshold exceeded, MRZ mismatch), regression (receipt actions).

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| all | none | create only for the entry point (Client Registration from the dashboard). Document scanning, OCR and MRZ cases need physical documents and stay manual |
