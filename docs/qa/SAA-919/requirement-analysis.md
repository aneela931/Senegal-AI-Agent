# Requirement analysis — SAA-919

Jira: SAA-919
Summary: (Sprint 1) Upgrade Customer Profile - Change Request
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
| Attachments | `SAA-919 Upgrade Customer Profile - Change Request.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

Profiles: Freelancer, POA, Cash Point, Dealer Shop, Team Leader. Preconditions: authorised agent, active MSISDN, not certified / rejected, missing or invalid documents. Post: documents to BO, status On Going, history record, no stock/commission/performance impact.

Allowed: uncertified / rejected only. Not allowed: certified. Status screen shows rejection reason and missing/invalid documents. Indicators green/red for ID and contract; Mixx checked/unchecked. Valid documents view-only; invalid replaceable. Mixx check makes contract mandatory.

Flow: select Upgrade Customer File; status; upload National ID / Passport / CEDEAO / Refugee; unsupported quoted message; status refresh; Mixx contract fields and signature; PIN; receipt On Going / Sim Activation Activated / SMS message / Transaction ID; download and share. Sentinel retains existing documents and adds new ones. History: type Upgrade Customer File.

AA-3 registration validation remains applicable.

## Acceptance criteria (evidence)

none stated as a separate section. Receipt table and status indicator table are used as AC.

## Comments

Three delivery comments (2026-09-30) for a 15-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-919 | Upgrade Customer File is opened from Customer Services | Step 1 | description | high | none | yes | |
| REQ-002 | SAA-919 | Uncertified / rejected files can be upgraded | Allowed | description | high | none | yes | |
| REQ-003 | SAA-919 | Certified files cannot be upgraded | Not Allowed | description | high | none | yes | |
| REQ-004 | SAA-919 | Rejection reason and missing/invalid documents are shown | Rejection Information | description | high | none | yes | |
| REQ-005 | SAA-919 | Status indicators for ID, Contract, Mixx | Status Indicators | description | medium | none | yes | |
| REQ-006 | SAA-919 | Valid ID/contract view-only; invalid/missing replaceable | ID Document / Contract rules | description | medium | none | yes | |
| REQ-007 | SAA-919 | Status screen lists document status, missing elements, rejection reason | Step 2 | description | high | none | yes | |
| REQ-008 | SAA-919 | Mixx originally subscribed is pre-checked; checking makes contract mandatory | Mixx Subscription | description | high | none | yes | |
| REQ-009 | SAA-919 | Supported ID types | Step 3 | description | medium | none | yes | |
| REQ-010 | SAA-919 | Unsupported document quoted message | Invalid / Unsupported Document | description | high | none | yes | |
| REQ-011 | SAA-919 | Status refresh, Cross to replace, Confirm only when complete | Step 5 | description | high | none | yes | |
| REQ-012 | SAA-919 | Mixx contract fields and signature | Step 6 | description | high | none | yes | |
| REQ-013 | SAA-919 | PIN confirmation on submit | Step 7 | description | high | none | yes | |
| REQ-014 | SAA-919 | Receipt table and download/share | Step 8 | description | high | none | yes | |
| REQ-015 | SAA-919 | Post-conditions including On Going and no stock/commission/performance impact | Post Conditions | description | medium | none | yes | |
| REQ-016 | SAA-919 | AA-3 registration validation still applies | Registration Rules | description | high | none | yes | |
| REQ-017 | SAA-919 | Existing documents retained; new documents added | Sentinel Processing | description | medium | BO-side evidence | yes | |
| REQ-018 | SAA-919 | Upgrade appears in registration history with stated fields | History Rules | description | medium | none | yes | |

## Techniques selected

Happy path, negative (certified blocked, unsupported document), permission, state transition (status refresh), validation.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001 | none | create: open Upgrade Customer File |
| others | none | manual |
