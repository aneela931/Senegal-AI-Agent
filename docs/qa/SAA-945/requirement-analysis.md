# Requirement analysis — SAA-945

Jira: SAA-945
Summary: (Sprint 1) Create New Touchpoint (New POA)
Retrieved: 2026-10-08
Source: Atlassian Jira (`getJiraIssue`, view `full`; description returned as HTML)

## Ticket facts

| Field | Value |
| --- | --- |
| Priority | Medium |
| Status | To Do |
| Labels | none |
| Components | none |
| Sprint | SNAA Sprint 1 (active) |
| Linked issues | none |
| Subtasks | none |
| Attachments | `image-20251008-100814.png` (Figma capture; bytes not accessible); `SAA-945 Create New Touchpoint (New POA).xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

Create Dealer API. Figma link present. Profiles: Dealer, Supervisor, Team Leader, Reseller. Preconditions: authorised account. Post: request submitted, ticket to SN System, RSS notified, no stock/commission/performance impact.

Flow: New POS from dashboard; touchpoint type; owner information; location; submit; SN System and SFA; RSS of district notified.

Types: POA, POS Airtime, POS Mixte, Dealer Shop. Mandatory. All profiles can report any type.

Owner: First Name, Last Name, MSISDN, all mandatory.

Location: Enter Address (Address/Landmark, District, City with suggestions) or Locate on Map (move anywhere, search suggestions, validate or close). No zoning/territory/distance limits.

Validate enabled when mandatory fields (address) or a map location are complete. Create Dealer API on submit.

## Comments that change scope

- 2026-09-18: asked for inventory.
- 2026-09-20 (Moumadjad Ahmed Abdou): listed staging MSISDNs for testing. Values stay in EnvConfig, not in Excel.
- 2026-09-21: asked for parent MSISDN for those POA numbers. No reply in the ticket.
- 2026-09-30: 22-case workbook delivery comments.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-945 | Authorised profile opens New POS from the dashboard | Main Flow / Applicability | description | high | Staging PDV is not in the profile list | yes | |
| REQ-002 | SAA-945 | Touchpoint type is mandatory | Touchpoint Types | description | high | none | yes | |
| REQ-003 | SAA-945 | Types POA, POS Airtime, POS Mixte, Dealer Shop; no profile restriction | Rules | description | medium | none | yes | |
| REQ-004 | SAA-945 | Owner first name, last name, MSISDN are mandatory | Owner Information | description | high | Parent MSISDN unanswered | yes | |
| REQ-005 | SAA-945 | Address flow requires Address/Landmark, District, City | Option 1 | description | high | none | yes | |
| REQ-006 | SAA-945 | District and city typing shows existing suggestions | District & City Search | description | medium | none | yes | |
| REQ-007 | SAA-945 | Map opens and any location can be selected | Option 2 | description | medium | none | yes | |
| REQ-008 | SAA-945 | Map search suggests matching locations | Search Rules | description | medium | none | yes | |
| REQ-009 | SAA-945 | Map can be cancelled with the top-right close icon | Map Rules | description | low | none | yes | |
| REQ-010 | SAA-945 | No zoning/territory/distance block | Zoning Rules | description | high | none | yes | |
| REQ-011 | SAA-945 | Address flow enables Validate when mandatory fields are complete | Address Flow | description | high | none | yes | |
| REQ-012 | SAA-945 | Map flow enables Validate once a location is selected | Map Flow | description | high | none | yes | |
| REQ-013 | SAA-945 | Submit uses Create Dealer API | Submission | description | medium | Observable by success | yes | |
| REQ-014 | SAA-945 | SN System ticket, SFA share, RSS of selected district notified | Notifications | description | medium | RSS identity not in the app | yes | |
| REQ-015 | SAA-945 | No impact on stock, commission, or performance | Post Conditions | description | low | none | yes | |
| REQ-016 | SAA-945 | Comment inventory numbers exist for owner MSISDN testing | comment 2026-09-20 | comment | medium | Parent MSISDN missing | yes | EnvConfig only |
| REQ-017 | SAA-945 | All listed profiles can report any touchpoint type | No profile-based restrictions | description | medium | none | yes | Combined with REQ-003 |
| REQ-018 | SAA-945 | Figma screenshot content | attachment | attachment | low | Bytes not accessible | no | |

## Techniques selected

Happy path (address and map), negative (missing type/owner), permission (authorised profiles), mobile-specific (map/search), validation.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| all | none | no Candidate for PDV; New POS is listed for Dealer / Supervisor / TL / Reseller |
