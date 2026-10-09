# Requirement analysis — SAA-914

Jira: SAA-914
Summary: (Sprint 1) Notification Section
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
| Attachments | `SAA-914 Notification Section.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

Profiles: All. Version: Mobile. Preconditions: user logged in and selected notifications from top icons or footer. Postcondition: user views the list of notifications. Admin portal management: Yes.

Normal course: select notifications from footer; view list; select a notification and view details; for some notifications an action button is available.

Business rules. Access from top icons or footer. Types according to credentials and admin settings: Performance alerts; Stock request; Internal communication (banners, text, images, notifications; real-time; tracked in historic). Order is temporal. Historic maintained 7 days. Filter by type; filters only for reseller, reseller supervisor and dealer. Template will be shared later; for now add random description in French and English. Use cases listed: Registration, Upgrade Account, Upgrade KYC, SIM Swap, ReIdentification, Airtime, Bundles, Reload.

Listing format: name, date, description. Details: Performance alerts have no details; internal communication is document, text or other as defined by admin.

## Acceptance criteria (evidence)

none stated as a separate section.

## Comments

Three delivery comments (2026-09-30) for a 12-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-914 | Notifications open from footer and from top icons | Access Notification section | description | high | none | yes | |
| REQ-002 | SAA-914 | Notification types depend on credentials and admin settings | Type of Notifications | description | medium | none | yes | |
| REQ-003 | SAA-914 | Internal communication is received in real time and kept in historic | Internal communication | description | medium | none | yes | Needs admin send |
| REQ-004 | SAA-914 | Notifications are ordered temporally | Order & Filter | description | medium | Newest vs oldest first not stated | yes | |
| REQ-005 | SAA-914 | Historic is maintained for 7 days | 7 days | description | medium | none | yes | |
| REQ-006 | SAA-914 | Type filters exist only for reseller, reseller supervisor and dealer | Filters | description | medium | none | yes | |
| REQ-007 | SAA-914 | List rows show name, date and description | Listing of notifications | description | medium | none | yes | |
| REQ-008 | SAA-914 | Performance alerts have no details | Details — Performance alerts | description | low | none | yes | |
| REQ-009 | SAA-914 | Internal communication details are admin-defined content | Details — Internal communication | description | medium | none | yes | |
| REQ-010 | SAA-914 | Some notifications have an action button | Normal Course step 4 | description | low | Which types get a button is not listed | yes | |
| REQ-011 | SAA-914 | Use-case list and "template later / random FR+EN description" | Use cases / Template | description | low | No expected UI mapping | no | |
| REQ-014 | SAA-914 | Types are managed in the admin portal | Admin portal management | description | low | No portal steps | no | |

## Techniques selected

Happy path, permission (filter by profile), boundary (7-day historic), negative (filter hidden), localization is mentioned ("random description in french and english") but no expected strings were given so no localization case was added.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001, REQ-004, REQ-007 | none | create open-from-footer/top-icon and list format if any notification exists |
| others | none | manual |
