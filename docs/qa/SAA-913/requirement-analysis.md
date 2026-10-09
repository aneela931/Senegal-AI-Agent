# Requirement analysis — SAA-913

Jira: SAA-913
Summary: (Sprint 1) Performance Dashboard
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
| Attachments | `SAA-913 Performance Dashboard.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

API: agentPerformance API V2. All dashboard KPIs, targets, previous period data and histories come from the API. Target section in Telco Sales will be removed.

Access: Performance Dashboard from Main Dashboard and My Profile. Visibility depends on user profile/accreditation.

Entry rules. Team Leader can select Registration & Customer Services, Airtime and Bundle Sales, Reload, Team Performance. Other profiles except Team Leader and Reseller can select Registration & Customer Services and Airtime and Bundle Sales. Reseller has no selection screen and is redirected to Reload Dashboard.

MVP dashboards (also managed from downstream API, not implemented internally): Customer Registration & Certification for users accredited for registration/customer services; Airtime for airtime and bundle sales; Reload for reload; Team Performance for Team Leader / Supervisor.

Filters: Today / This Week / This Month, comparing with Yesterday / Previous Week / Previous Month. History filters: transaction category; certification status where applicable.

Registration dashboard KPIs and history (Registration, SIM Swap, Re-identification, Upgrade Customer File) with transaction ID, date/time, Certified / Ongoing / Rejected; rejected shows reason and Upgrade Customer File. Transaction details include Need Help; rejected upgrade pre-fills customer number.

Airtime KPIs include airtime and bundle volume. Reload KPIs split airtime and mobile money. Need Help on reload: hotline, quoted question, two options, claim shared with Back Office.

Team Performance: active if at least one App or USSD transaction in the selected period. Supervisor can view agent activity history and cannot view detailed transaction history. Team Leader can view a selected agent's dashboard and history but cannot open transaction details or upgrade customer file from that view.

## Acceptance criteria (evidence)

none stated as a separate section.

## Comments

Three delivery comments (2026-09-30) for a 24-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-913 | Performance Dashboard is opened from Main Dashboard and from My Profile | Access | description | high | none | yes | |
| REQ-002 | SAA-913 | Visibility depends on user profile / accreditation | Access | description | high | none | yes | |
| REQ-003 | SAA-913 | Team Leader can select the four dashboard types | Team Leader | description | high | none | yes | Needs TL account |
| REQ-004 | SAA-913 | Other profiles except Team Leader and Reseller select Registration and Airtime only | Other Profiles | description | high | none | yes | |
| REQ-005 | SAA-913 | Reseller has no selection screen and goes to Reload Dashboard | Reseller | description | high | none | yes | Needs Reseller |
| REQ-006 | SAA-913 | Each MVP dashboard is available only for the accredited feature | Available MVP Dashboards | description | high | "this logic is not implemented internally" | yes | |
| REQ-007 | SAA-913 | Period filters Today / This Week / This Month with the stated previous-period comparisons | Dashboard Period Filters | description | medium | none | yes | |
| REQ-008 | SAA-913 | History filters by transaction category and certification status where applicable | History Filters | description | medium | none | yes | |
| REQ-009 | SAA-913 | Registration dashboard shows the listed KPIs including Gross Add at D+1 | Registration KPIs | description | medium | none | yes | |
| REQ-010 | SAA-913 | Registration history includes the four types with ID, date/time, certification status | History Includes / Details | description | medium | none | yes | |
| REQ-011 | SAA-913 | Rejected history shows reason and Upgrade Customer File | If rejected | description | high | none | yes | |
| REQ-012 | SAA-913 | Opening a registration-family transaction shows the stated detail fields plus Need Help | Transaction Details | description | medium | none | yes | |
| REQ-013 | SAA-913 | Rejected upgrade pre-fills the customer number | For rejected customer files | description | high | none | yes | |
| REQ-014 | SAA-913 | Airtime dashboard KPIs treat volume as airtime plus bundle | Airtime Dashboard | description | medium | none | yes | |
| REQ-015 | SAA-913 | Airtime history rows show the listed fields | History Details | description | medium | none | yes | |
| REQ-016 | SAA-913 | Airtime / bundle transaction details include Need Help | Transaction Details – Airtime / Bundle | description | medium | none | yes | |
| REQ-017 | SAA-913 | Reload dashboard shows airtime and mobile money KPI triples | Reload Dashboard KPIs | description | medium | none | yes | |
| REQ-018 | SAA-913 | Reload history includes the four reload product types | History Includes | description | medium | none | yes | |
| REQ-019 | SAA-913 | Reload received vs sent details show the counterpart name and MSISDN | Transaction Details – Reload | description | medium | none | yes | |
| REQ-020 | SAA-913 | Need Help offers hotline, the quoted question, two options, and shares the claim with Back Office | Claims from Reload Transaction | description | medium | Configured hotline number is not stated | yes | |
| REQ-021 | SAA-913 | Team Performance KPIs and active-agent rule (App or USSD in the selected period) | Team Performance Dashboard | description | medium | none | yes | |
| REQ-022 | SAA-913 | Supervisor can view agent activity history and cannot view detailed transaction history | Supervisor / Reseller Team Dashboard | description | high | none | yes | |
| REQ-023 | SAA-913 | Team Leader registration dashboard shows the listed KPIs | Team Leader Registration Dashboard | description | medium | none | yes | |
| REQ-024 | SAA-913 | Team Leader can open a selected agent's dashboard with matching personal KPIs | View Selected Agent Dashboard | description | high | none | yes | |
| REQ-025 | SAA-913 | From that view the Team Leader cannot open transaction details or upgrade customer file | Restrictions | description | high | none | yes | |

## Techniques selected

Happy path, permission (profile entry rules), decision table (accreditation × dashboard), negative (supervisor / TL restrictions), state transition (filters), regression (Telco Sales target removed).

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-001 | none | create open-from-dashboard and open-from-profile |
| others | none | manual — need accredited profiles and KPI data |
