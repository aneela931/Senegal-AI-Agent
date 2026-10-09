# Open questions — SAA-909

Questions are gaps in the ticket. They do not block cases whose expected results are already stated.

| ID | Topic | What is missing | Affected requirements | Blocks cases |
| --- | --- | --- | --- | --- |
| Q-001 | expected result | What a Search returns (sections only, or content inside screens) and how results are shown | REQ-004 | none — TC-909-004 checks only that the search entry opens |
| Q-002 | ambiguity | Whether Activity History order is newest-first or oldest-first | REQ-007 | none — TC-909-008 accepts either consistent order and records which |
| Q-003 | boundary | What "updated in real time" means for the balance (refresh interval, pull-to-refresh, on return to dashboard) | REQ-010 | none — balance presence is tested; refresh latency is not asserted |
| Q-004 | test data | An account whose Get User Details has no Agent Name, to test the MSISDN fallback | REQ-003 | TC-909-003 is Manual until the account exists |
| Q-005 | role | The staging account in `staging.json` is a Point de Vente (PDV). The ticket lists Freelancer 1 / Freelancer 2 / Staff / Team Leader only. Which dashboard list applies to PDV | REQ-015, REQ-016 | none — TC-909-015/016 need Freelancer/Staff and Team Leader accounts |
| Q-006 | role | POS Out of Stock and New POS appear as examples of action buttons but in no user-type list | REQ-014 | none |
| Q-007 | missing acceptance criteria | Banner content, count and trigger | REQ-022 | TC-909-024 |
| Q-008 | API dependency | How to drive KPI values above / below / equal to the comparison on staging for the indicator colours | REQ-019 | none — TC-909-018..020 are Manual |

## Not invented

- No colour hex values were assumed for green / red / orange.
- No refresh interval was assumed for the balance.
- No search result layout was assumed.
- No banner was assumed to exist on staging.
- No dashboard list was assumed for the PDV profile; the automated presence check asserts only the sections the ticket lists for every user type (Search, Activity History, Agent Information, Airtime Balance, Performance, footer) plus the three common action buttons.
