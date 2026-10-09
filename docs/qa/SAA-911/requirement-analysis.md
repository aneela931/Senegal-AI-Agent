# Requirement analysis — SAA-911

Jira: SAA-911
Summary: (Sprint 1) Logout/Inactivity Timeout
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
| Linked issues | SAA-959 relates to — Proof of testing - Logout/Inactivity Logout - Change Request (Done) |
| Subtasks | none |
| Attachments | `SAA-911 Logout Inactivity Timeout.xlsx` and two renamed copies. Names visible; bytes not accessible. |

## Description (evidence)

AA-1.3.2 Inactivity Logout:

- Agent is automatically logged out after a given period of time.
- Inactivity timeout is manageable through the admin portal in minutes and per profile.
- Inactivity means the user has performed no action in the app and has not touched the screen for the set amount of time.
- Timeout applies even when the user leaves the app. If the user left the app and the time is not yet up, they can re-enter the agent app without logging in.

## Acceptance criteria (evidence)

none stated as a separate section.

## Comments

Three delivery comments (2026-09-30) for a 12-case workbook. No behaviour added.

## Requirement model

| Requirement ID | Jira Key | Requirement | Acceptance Criteria Reference | Source | Risk | Ambiguity | Testable | Notes |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| REQ-001 | SAA-911 | Agent is logged out automatically after the configured inactivity period | "automatically logged out after a given period of time" | description | high | Period value and post-logout screen are not stated | yes | Needs the configured value for the test profile |
| REQ-002 | SAA-911 | Inactivity timeout is configurable in the admin portal in minutes and per profile | "manageable through the admin portal" | description | medium | No portal steps | no | |
| REQ-003 | SAA-911 | Any action or screen touch before the period resets inactivity | "no action on the app, nor touch the screen" | description | high | none | yes | |
| REQ-004 | SAA-911 | Timeout continues to run while the app is in the background | "should apply, even when user leaves the app" | description | high | none | yes | |
| REQ-005 | SAA-911 | Returning to the app before the period ends does not require a new login | "able to reenter the agent app without login in, if the time is not yet up" | description | high | none | yes | Short background interval is sufficient |

## Techniques selected

State transition (active → inactive → logged out; foreground → background → foreground), boundary (just before / at the timeout), interruption (background), session. Permission and decision-table techniques are not supported by the text.

## Existing automation already covering a requirement

| Requirement ID | Existing Katalon case | Action |
| --- | --- | --- |
| REQ-005 | none | create `TC_911_003` (background a few seconds, relaunch, still logged in) using `LoginJourney.completeLogin()` |
| REQ-001, REQ-003, REQ-004 | none | manual until the configured timeout is known |
