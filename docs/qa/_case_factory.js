const BUILD = "1.0 / 92";
const ENV = "staging";

function C(jira, n, feature, summary, preconditions, inp, expected, reqs, ac, automation, technique, a, b) {
  let extra = {};
  if (a && typeof a === "object") extra = a;
  else if (typeof a === "string") {
    extra.priority = a;
    if (b && typeof b === "object") extra = Object.assign({ priority: a }, b);
  }
  const blocked = !!extra.blocked;
  const status = blocked ? "BLOCKED_REQUIREMENT" : extra.status || "Not Executed";
  const num = jira.split("-")[1];
  const cid = "TC-" + num + "-" + String(n).padStart(3, "0");
  return {
    id: cid,
    feature,
    summary,
    preconditions,
    input: inp,
    expected,
    actual: "",
    testType: extra.test_type || "Functional",
    status,
    priority: extra.priority || "High",
    environment: ENV,
    build: BUILD,
    assignee: "",
    traceability: jira + "; " + reqs.join(", ") + "; " + ac,
    automation,
    defectId: "",
    executionDate: "",
    _reqs: reqs,
    _technique: technique,
    _blocked: blocked,
    _candidate: automation === "Candidate" || automation === "Automated",
    _automated: automation === "Automated",
  };
}

function coveragePayload(requirements, cases) {
  return {
    requirements: requirements.map((r) => ({
      id: r.id,
      testable: r.testable,
      blocked: !!r.blocked,
    })),
    cases: cases.map((c) => ({
      id: c.id,
      reqs: c._reqs,
      technique: c._technique,
      automationCandidate: c._candidate,
      automated: c._automated,
      blockedRequirement: c._blocked,
      execution: c._blocked ? "blocked" : "not_run",
    })),
  };
}

function stripInternal(cases) {
  return cases.map((c) => {
    const row = {};
    Object.keys(c).forEach((k) => {
      if (!k.startsWith("_")) row[k] = c[k];
    });
    return row;
  });
}

module.exports = { C, coveragePayload, stripInternal };
