# Requirement analysis — SAA-917

Jira: SAA-917
Summary: (Sprint 1) KYC Form Details
Retrieved: 2026-10-08
Source: Atlassian Jira (`getJiraIssue`, view `full`)

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
| Attachments | `SAA-917 KYC Form Details.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

Form is for Registration and Re-Registration. Annex AA-3.1.2 KYC form details — Senegal.

ID types: National ID (Yes, OCR mandatory); CEDEAO ID (CEDAO, OCR optional); Passeport (Yes except national, 8 digits and character long, OCR mandatory); Passport - Senegalese (NIN, OCR mandatory); Refugee card (Yes, 11 digits and character long, OCR optional).

Customer identification: Picture of client No / NA; Fingerprint No / NA.

KYC fields: Name mandatory OCR YES; Last name mandatory OCR YES; Gender Yes, Not Mandatory, OCR optional; Date of birth mandatory OCR YES; Country only for passport, OCR YES; Address (street, region, préfecture, commune, canton, city) mandatory OCR YES; ID number mandatory OCR YES.

## Acceptance criteria (evidence)

none stated as a separate section.

## Comments

Three delivery comments (2026-09-30) for a 16-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-917 | Form applies to registration and re-registration | header | description | medium | none | yes | |
| REQ-002 | SAA-917 | National ID is available; OCR mandatory | Type of ID | description | high | none | yes | |
| REQ-003 | SAA-917 | CEDEAO ID is available; OCR optional | CEDEAO ID | description | high | Spelling CEDAO | yes | |
| REQ-004 | SAA-917 | Foreign passport allowed except national; 8 alphanumeric; OCR mandatory | Passeport | description | high | "digits and character" | yes | |
| REQ-005 | SAA-917 | Senegalese passport uses NIN; OCR mandatory | Passport - Senegalese | description | high | none | yes | |
| REQ-006 | SAA-917 | Refugee card 11 alphanumeric; OCR optional | Refugee card | description | high | none | yes | |
| REQ-007 | SAA-917 | Customer picture is not required on this annex | Picture of client \| No | description | medium | Contradicts SAA-910 Customer Photo | yes | Do not merge the two tickets |
| REQ-008 | SAA-917 | Fingerprint is not required | Fingerprint \| No | description | medium | none | yes | |
| REQ-009 | SAA-917 | Name is mandatory with OCR YES | Name | description | high | none | yes | |
| REQ-010 | SAA-917 | Gender is present and not mandatory | Gender | description | medium | none | yes | |
| REQ-011 | SAA-917 | Date of birth is mandatory with OCR YES | Date of birth | description | high | none | yes | |
| REQ-012 | SAA-917 | Country is only for passport | Country | description | medium | none | yes | |
| REQ-013 | SAA-917 | Address elements are mandatory with OCR YES | Adress | description | high | none | yes | |
| REQ-014 | SAA-917 | ID number is mandatory with OCR YES | ID number | description | high | none | yes | |

## Techniques selected

Happy path, boundary (passport 8, refugee 11), negative (gender optional, picture/fingerprint absent), validation, equivalence partition (ID types).

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001 | none | create: open registration (KYC form reachability). Document OCR stays manual |
| others | none | manual |
