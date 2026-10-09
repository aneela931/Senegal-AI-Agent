# Coverage - SAA-908

Counts were produced by scripts/compute-coverage.ps1 from the mapping JSON. They were not estimated.

## Totals

| Metric | Count |
| --- | --- |
| Total requirements | 30 |
| Testable requirements | 27 |
| Requirements covered | 27 |
| Requirements blocked | 3 |
| Requirement coverage | 100% (27 / 27 testable) |
| Total test cases | 30 |
| Positive cases | 16 |
| Negative cases | 8 |
| Boundary cases | 2 |
| Other techniques | 4 |
| Automation candidates | 27 |
| Automated | 27 |
| Pending automation | 0 |
| Execution passed | 0 |
| Execution failed | 0 |
| Execution blocked | 3 |
| Execution not run | 27 |

## Matrix

| Requirement ID | Test Cases | Coverage | Automation | Execution |
| --- | --- | --- | --- | --- |
| REQ-001 | TC-908-001 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-002 | TC-908-001 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-003 | TC-908-002 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-004 | TC-908-003, TC-908-028 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-005 | TC-908-003, TC-908-027 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-006 | TC-908-003 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-007 | TC-908-004 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-008 | TC-908-005 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-009 | TC-908-006 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-010 | TC-908-006, TC-908-028 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-011 | TC-908-007, TC-908-031 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-012 | TC-908-009, TC-908-028 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-013 | TC-908-009, TC-908-011, TC-908-033 | Covered | Automated 3 / Candidates 3 | Passed 0, Failed 0, Blocked 0, Not run 3 |
| REQ-014 | TC-908-010, TC-908-031 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-015 | TC-908-012 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-016 | TC-908-013, TC-908-028 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-017 | TC-908-018, TC-908-028 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-018 | TC-908-014, TC-908-015 | Covered | Automated 2 / Candidates 2 | Passed 0, Failed 0, Blocked 0, Not run 2 |
| REQ-019 | TC-908-016 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-020 | TC-908-014, TC-908-015, TC-908-016 | Covered | Automated 3 / Candidates 3 | Passed 0, Failed 0, Blocked 0, Not run 3 |
| REQ-021 | TC-908-017 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-022 | TC-908-034 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-023 | TC-908-019 | Blocked | Not a candidate | Passed 0, Failed 0, Blocked 1, Not run 0 |
| REQ-024 | TC-908-020 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-025 | TC-908-021 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-026 | TC-908-022 | Blocked | Not a candidate | Passed 0, Failed 0, Blocked 1, Not run 0 |
| REQ-027 | TC-908-023 | Blocked | Not a candidate | Passed 0, Failed 0, Blocked 1, Not run 0 |
| REQ-028 | TC-908-018, TC-908-024, TC-908-028 | Covered | Automated 3 / Candidates 3 | Passed 0, Failed 0, Blocked 0, Not run 3 |
| REQ-029 | TC-908-025 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |
| REQ-030 | TC-908-026 | Covered | Automated 1 / Candidates 1 | Passed 0, Failed 0, Blocked 0, Not run 1 |

## Techniques used

Happy path, negative, boundary (MSISDN length and prefix, lock-time increase), validation, permissions (Agent or Staff), session (single device), and localization. Decision-table and interruption cases were not added because the story does not state those combinations.
