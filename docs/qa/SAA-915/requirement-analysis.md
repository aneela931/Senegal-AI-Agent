# Requirement analysis — SAA-915

Jira: SAA-915
Summary: (Sprint 1) Activity History - Dashboard
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
| Attachments | `SAA-915 Activity History - Dashboard.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

Historic Activity Section for all profiles, mobile. Preconditions row says "selected notifications from top icons"; Normal Course says "activity historic from top icons". Postcondition: list of transaction historic. Admin portal: Yes.

Rules: historic from top icons; not all transaction types per credentials; all transactions of the day; real-time update. Types: Customer registration, Airtime & Bundle sales, Customer Services. Temporal order; current day only midnight to midnight; filter by type.

List fields differ by type (registration / airtime / reload sent). Details page: volume at top, customer MSISDN except registration, type, date/time, Transaction ID, colour-coded certification, Need Help. Rejected files show reason and Upgrade customer file. Upgrade only for rejected files; number pre-populated.

Commissions: show per transaction and total at bottom if admin set a scheme; hidden if disabled from BO.

Performance card: beginning-of-day balance, generated revenue (sum of transaction costs, commissions excluded), end-of-day balance. KPI arrows vs yesterday or target with green/red/orange rules.

## Comments that change scope

- 2026-09-17 (Aatis Sajid): "comission amount is removed."

Delivery comments (2026-09-30) for a 15-case workbook add no behaviour.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-915 | Transaction historic opens from top icons | Normal Course | description | high | Preconditions say notifications | yes | Normal Course used |
| REQ-002 | SAA-915 | Credentials limit which transaction types are visible | Type of transactions | description | high | none | yes | |
| REQ-003 | SAA-915 | All transactions realised during the current day | view all transaction realised during the day | description | high | none | yes | |
| REQ-004 | SAA-915 | Historic updates in real time | updated real-time | description | low | No refresh rule | yes | Presence only |
| REQ-005 | SAA-915 | Types: Customer registration, Airtime & Bundle sales, Customer Services | Type of transactions | description | medium | Reload sent also has list fields | yes | |
| REQ-006 | SAA-915 | Temporal order | Order & Filter | description | medium | Direction not stated | yes | |
| REQ-007 | SAA-915 | Historic for the current day only, midnight to midnight | current day only | description | high | none | yes | |
| REQ-008 | SAA-915 | Filter by transaction type | filter through the type | description | medium | none | yes | |
| REQ-009 | SAA-915 | Client registration list fields | Listing — client registration | description | medium | none | yes | |
| REQ-010 | SAA-915 | Airtime & bundle list fields | Listing — Airtime | description | medium | Commission contradicted by comment | yes | |
| REQ-011 | SAA-915 | Reload sent list fields | Listing — Reload sent | description | medium | Reload is not in the type list | yes | |
| REQ-012 | SAA-915 | Details page fields including Need Help | Details of transaction | description | medium | none | yes | |
| REQ-013 | SAA-915 | Rejected status shows reason and Upgrade button | Reject | description | high | none | yes | |
| REQ-014 | SAA-915 | Upgrade only for rejected files; number pre-filled | Call for Actions | description | high | none | yes | |
| REQ-015 | SAA-915 | Commission display vs BO, superseded by comment that amount is removed | Commissions + comment | description, comment | medium | Description and comment disagree | yes | Assert hidden |
| REQ-016 | SAA-915 | Performance card at the top of historic | Performance card | description | medium | none | yes | |
| REQ-017 | SAA-915 | Revenue is sum of transaction costs, commissions excluded | Generated revenue | description | medium | none | yes | |
| REQ-018 | SAA-915 | KPI comparison baselines | Performance Success | description | medium | none | yes | |
| REQ-019 | SAA-915 | Arrow colours: up green, down red, horizontal orange | comparaison rules | description | medium | none | yes | |

## Techniques selected

Happy path, permission, boundary (day window), negative (upgrade only if rejected), decision table (arrows). SAA-909 already covers a shorter Activity History; these cases use this ticket's extra fields rather than duplicating SAA-909 filters.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001 | none — SAA-909 search/history icons are dashboard-level | create open historic from top icons |
| REQ-016 | none | create performance-card presence after login |
| others | none | manual |
