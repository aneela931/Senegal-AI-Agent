# Requirement analysis — SAA-909

Jira: SAA-909
Summary: (Sprint 1) Dashboard - Change Request
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
| Linked issues | SAA-956 relates to — Proof of testing - Dashboard Change Request (Done) |
| Subtasks | none (`subtasks` array empty in the full payload) |
| Attachments | `SAA-909 Dashboard - Change Request.xlsx`; `SAA-909 Dashboard - Change Request (ae6ac658-...).xlsx`. Names visible; bytes not accessible from this session. |

## Description (evidence)

APIs named: Agent Profile Mapping (fetch agent profile and authorised features; dashboard actions displayed when `authorized=true`), Balance (`get-balance`, called after successful PIN validation, returns Airtime (Yas) balance), agentPerformance API V2 (dashboard KPIs and comparisons), App Database (Activity History, no third-party API).

Dashboard layout: simple, fits on a single screen, no horizontal or vertical scrolling. Contains Agent Information, Search and Activity History icons, Balance Card, QR Code, Action Buttons, Performance Section, Banners, Footer.

Agent Information: Agent Name; if unavailable, Agent MSISDN. Source: Get User Details API.

Top icons: Search (search app sections and content). Activity History (activities of the current day 00:00–23:59, transactions performed through the app, filter by Action Type and Keyword / Number search, chronological order, total revenue at bottom, revenue recalculated after filtering). Supported history types stored in App DB: Registration, Re-identification, SIM Swap, Upgrade Account, Upgrade KYC, Airtime, Bundle.

Balance Card: airtime balance displayed, fetched via Balance API, updated in real time, hidden by default, revealed with the Eye icon.

QR Code: available for all agents, used as agent identifier, applicable for Cash Out and Reload journeys; on click QR expands to full screen with Agent MSISDN displayed above it.

Action Buttons: displayed based on Agent Profile Mapping (`authorized=true` → shown, `false` → hidden). Examples: Client Registration, Airtime & Bundle, Customer Services, POS Out of Stock, New POS.

Dashboard by user type. Freelancer 1 / Freelancer 2 / Staff: Search, Activity History, Agent Information, Airtime Balance, Performance Section, Banner (dependent on downstream API), Client Registration, Airtime & Bundle, Customer Services. Team Leader: the same plus Agent Out of Stock and Reload.

Performance Section: maximum 2 KPI cards, current value, previous-day comparison, Gross Add available at D+1. KPI comparison: Registration, Certification, Active Agents, Airtime Sales, Reload, POS Out of Stock vs Yesterday; Gross Add vs Target. Indicators: Current > Comparison → Up arrow, Green; Current < Comparison → Down, Red; Current = Comparison → Horizontal, Orange.

Team Leader Performance: cumulative team values from downstream API; KPIs Registration, Certification, Gross Add, Active Agents; display Today's value, Yesterday's value, trend indicator.

Footer: visible on all screens; Home → Main Dashboard, Notifications → Notification Centre, My Account → Profile / Self-Care.

## Acceptance criteria (evidence)

none stated as a separate section. The description headings above are the only acceptance evidence.

## Comments

Three comments (2026-09-30) record that an 18-case workbook was attached and delivered. They add no behaviour. SAA-956 is a proof-of-testing task; it was not loaded because it does not change behaviour.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-909 | Dashboard fits on one screen with no horizontal or vertical scrolling | "Dashboard Layout" | description | medium | none | yes | Device-size dependent; verify on the test phone |
| REQ-002 | SAA-909 | Dashboard contains Agent Information, Search and Activity History icons, Balance Card, QR Code, Action Buttons, Performance Section, Banners, Footer | "Dashboard Layout — Contain" | description | high | none | yes | |
| REQ-003 | SAA-909 | Agent Information shows Agent Name, or Agent MSISDN when the name is unavailable | "Agent Information" | description | medium | none | yes | MSISDN fallback needs an account without a name |
| REQ-004 | SAA-909 | Search icon lets the user search app sections and content | "Top Icons — Search" | description | low | Search result format and scope are not stated | yes | Only the entry point is testable |
| REQ-005 | SAA-909 | Activity History shows activities of the current day (00:00–23:59) performed through the app | "Activity History" | description | high | none | yes | Needs same-day transactions |
| REQ-006 | SAA-909 | Activity History filters by Action Type and by Keyword / Number search | "Filter by" | description | medium | none | yes | |
| REQ-007 | SAA-909 | Transactions are sorted in chronological order | "Transactions sorted in chronological order" | description | medium | Ascending or descending is not stated | yes | |
| REQ-008 | SAA-909 | Total revenue shown at the bottom and recalculated after filtering | "Total revenue displayed at bottom" | description | medium | none | yes | |
| REQ-009 | SAA-909 | History types: Registration, Re-identification, SIM Swap, Upgrade Account, Upgrade KYC, Airtime, Bundle | "Supported History Types" | description | medium | none | yes | Needs one transaction of each type |
| REQ-010 | SAA-909 | Airtime balance is displayed on the dashboard from the Balance API after PIN validation and updated in real time | "Airtime Balance" | description | high | "real time" has no refresh rule | yes | Assert presence, not refresh latency |
| REQ-011 | SAA-909 | Balance is hidden by default and revealed with the Eye icon | "Hidden by default", "Eye icon" | description | high | none | yes | |
| REQ-012 | SAA-909 | QR code is shown for all agents as the agent identifier | "QR Code — Available for all agents" | description | medium | none | yes | |
| REQ-013 | SAA-909 | Tapping the QR expands it to full screen with the Agent MSISDN above it | "On click" | description | medium | none | yes | |
| REQ-014 | SAA-909 | Action buttons are shown only when Agent Profile Mapping returns `authorized=true`; hidden when `false` | "Authorization Rules" | description | high | none | yes | Needs Back Office control of the mapping |
| REQ-015 | SAA-909 | Freelancer 1 / Freelancer 2 / Staff dashboard shows the listed sections and Client Registration, Airtime & Bundle, Customer Services | "Dashboard by User Type" | description | high | none | yes | Needs an account of that type |
| REQ-016 | SAA-909 | Team Leader dashboard additionally shows Agent Out of Stock and Reload | "Team Leader" | description | high | none | yes | Needs a Team Leader account |
| REQ-017 | SAA-909 | Performance section shows at most 2 KPI cards with current value and previous-day comparison; Gross Add appears at D+1 | "Performance Section — General Rules" | description | medium | none | yes | |
| REQ-018 | SAA-909 | Each KPI compares against the stated baseline (Yesterday, or Target for Gross Add) | "KPI Comparison Logic" | description | medium | none | yes | Data dependent |
| REQ-019 | SAA-909 | Indicator is Up/Green when current > comparison, Down/Red when lower, Horizontal/Orange when equal | "Performance Indicators" | description | medium | none | yes | Needs controllable KPI data |
| REQ-020 | SAA-909 | Team Leader performance shows cumulative team values (Registration, Certification, Gross Add, Active Agents) with today, yesterday and trend | "Team Leader Performance" | description | medium | none | yes | Needs a Team Leader account |
| REQ-021 | SAA-909 | Footer is visible on all screens with Home, Notifications, My Account | "Footer" | description | high | none | yes | |
| REQ-022 | SAA-909 | Banner is displayed depending on the downstream API response | "Banner" | description | low | No banner content, count or trigger stated | no | |

## Techniques selected

Happy path (dashboard composition, footer), permission (action buttons by profile, user-type dashboards), decision table (performance indicator colours), state transition (balance hidden → revealed, QR collapsed → full screen), equivalence partition (history types), validation (activity-history filters), mobile-specific (single-screen layout). No boundary analysis: the ticket states no numeric limits other than "maximum 2 KPI cards".

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| all | none — `Scripts/Login/` covers SAA-908 only; `LoginJourney.completeLogin()` is reused to reach the dashboard | create for dashboard presence, balance, QR, footer; manual for data- and profile-dependent rows |
