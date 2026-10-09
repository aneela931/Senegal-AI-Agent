"""Shared factory for Senegal Agent App Excel case JSON rows."""
BUILD = "1.0 / 92"
ENV = "staging"


def case(
    jira,
    n,
    feature,
    summary,
    preconditions,
    inp,
    expected,
    reqs,
    ac,
    automation,
    technique,
    priority="High",
    test_type="Functional",
    status="Not Executed",
    blocked=False,
):
    if blocked:
        status = "BLOCKED_REQUIREMENT"
    num = jira.split("-")[1]
    cid = "TC-%s-%03d" % (num, n)
    req_text = ", ".join(reqs)
    return {
        "id": cid,
        "feature": feature,
        "summary": summary,
        "preconditions": preconditions,
        "input": inp,
        "expected": expected,
        "actual": "",
        "testType": test_type,
        "status": status,
        "priority": priority,
        "environment": ENV,
        "build": BUILD,
        "assignee": "",
        "traceability": "%s; %s; %s" % (jira, req_text, ac),
        "automation": automation,
        "defectId": "",
        "executionDate": "",
        "_reqs": reqs,
        "_technique": technique,
        "_blocked": blocked,
        "_candidate": automation in ("Candidate", "Automated"),
        "_automated": automation == "Automated",
    }


def coverage_payload(requirements, cases):
    cov_reqs = []
    for r in requirements:
        cov_reqs.append(
            {
                "id": r["id"],
                "testable": r["testable"],
                "blocked": r.get("blocked", False),
            }
        )
    cov_cases = []
    for c in cases:
        cov_cases.append(
            {
                "id": c["id"],
                "reqs": c["_reqs"],
                "technique": c["_technique"],
                "automationCandidate": c["_candidate"],
                "automated": c["_automated"],
                "blockedRequirement": c["_blocked"],
                "execution": "blocked" if c["_blocked"] else "not_run",
            }
        )
    return {"requirements": cov_reqs, "cases": cov_cases}


def strip_internal(cases):
    out = []
    for c in cases:
        row = {k: v for k, v in c.items() if not k.startswith("_")}
        out.append(row)
    return out
