const fs = require("fs");
const path = require("path");

const deviceBlock = process.argv[2] || "DEVICE_STATUS unknown";
const appState = process.argv[3] || "APP_STATE not recorded";
const kreNote =
  process.argv[4] ||
  "No Katalon Studio or Runtime Engine run from this session. Results left Not Run.";

const SCRIPT = {
  "TC-909-001": ["Scripts/Dashboard/TC_909_001/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-002": ["Scripts/Dashboard/TC_909_002/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-003": ["Scripts/Dashboard/TC_909_003/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-005": ["Scripts/Dashboard/TC_909_005/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-011": ["Scripts/Dashboard/TC_909_011/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-012": ["Scripts/Dashboard/TC_909_012/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-013": ["Scripts/Dashboard/TC_909_013/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-014": ["Scripts/Dashboard/TC_909_014/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-019": ["Scripts/Dashboard/TC_909_019/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-909-023": ["Scripts/Dashboard/TC_909_023/Script.groovy", "LoginJourney, MobileText, Permissions", "DashboardPage", "created"],
  "TC-910-001": ["Scripts/CustomerRegistration/TC_910_001/Script.groovy", "LoginJourney, DashboardPage", "RegistrationPage", "created"],
  "TC-911-003": ["Scripts/Session/TC_911_003/Script.groovy", "LoginJourney, DashboardPage", "SessionPage", "created"],
  "TC-912-001": ["Scripts/CustomerServices/TC_912_001/Script.groovy", "LoginJourney, DashboardPage", "CustomerServicesPage", "created"],
  "TC-912-003": ["Scripts/CustomerServices/TC_912_003/Script.groovy", "LoginJourney, DashboardPage, LoginData", "CustomerServicesPage", "created"],
  "TC-913-001": ["Scripts/Performance/TC_913_001/Script.groovy", "LoginJourney, DashboardPage", "PerformanceDashboardPage", "created"],
  "TC-913-002": ["Scripts/Performance/TC_913_002/Script.groovy", "LoginJourney, DashboardPage", "PerformanceDashboardPage", "created"],
  "TC-913-007": ["Scripts/Performance/TC_913_007/Script.groovy", "LoginJourney, DashboardPage", "PerformanceDashboardPage", "created"],
  "TC-914-001": ["Scripts/Notifications/TC_914_001/Script.groovy", "LoginJourney, DashboardPage", "NotificationsPage", "created"],
  "TC-914-002": ["Scripts/Notifications/TC_914_002/Script.groovy", "LoginJourney, DashboardPage", "NotificationsPage", "created"],
  "TC-914-005": ["Scripts/Notifications/TC_914_005/Script.groovy", "LoginJourney, DashboardPage", "NotificationsPage", "created"],
  "TC-914-009": ["Scripts/Notifications/TC_914_009/Script.groovy", "LoginJourney, DashboardPage", "NotificationsPage", "created"],
  "TC-915-001": ["Scripts/ActivityHistory/TC_915_001/Script.groovy", "LoginJourney, DashboardPage", "ActivityHistoryPage", "created"],
  "TC-915-012": ["Scripts/ActivityHistory/TC_915_012/Script.groovy", "LoginJourney, DashboardPage", "ActivityHistoryPage", "created"],
  "TC-917-001": ["Scripts/CustomerRegistration/TC_917_001/Script.groovy", "LoginJourney, DashboardPage", "RegistrationPage", "created"],
  "TC-918-001": ["Scripts/CustomerServices/TC_918_001/Script.groovy", "LoginJourney, DashboardPage", "—", "created"],
  "TC-919-001": ["Scripts/CustomerServices/TC_919_001/Script.groovy", "LoginJourney, DashboardPage", "—", "created"],
  "TC-921-001": ["Scripts/CustomerServices/TC_921_001/Script.groovy", "LoginJourney, DashboardPage", "—", "created"],
};

const SUITE = {
  "SAA-909": "Test Suites/TS_Dashboard",
  "SAA-910": "Test Suites/TS_CustomerRegistration",
  "SAA-911": "Test Suites/TS_Session",
  "SAA-912": "Test Suites/TS_CustomerServices",
  "SAA-913": "Test Suites/TS_Performance",
  "SAA-914": "Test Suites/TS_Notifications",
  "SAA-915": "Test Suites/TS_ActivityHistory",
  "SAA-917": "Test Suites/TS_CustomerRegistration",
  "SAA-918": "Test Suites/TS_CustomerServices",
  "SAA-919": "Test Suites/TS_CustomerServices",
  "SAA-920": "—",
  "SAA-921": "Test Suites/TS_CustomerServices",
  "SAA-945": "—",
};

const KEYS = [
  "SAA-909",
  "SAA-910",
  "SAA-911",
  "SAA-912",
  "SAA-913",
  "SAA-914",
  "SAA-915",
  "SAA-917",
  "SAA-918",
  "SAA-919",
  "SAA-920",
  "SAA-921",
  "SAA-945",
];

for (const key of KEYS) {
  const cases = JSON.parse(fs.readFileSync(path.join(__dirname, key, "cases.json"), "utf8")).cases;
  const autoRows = [];
  const execRows = [];
  let passed = 0,
    failed = 0,
    blocked = 0,
    notRun = 0;
  for (const row of cases) {
    const scriptInfo = SCRIPT[row.id];
    const automated = row.automation === "Automated" ? "yes" : "no";
    const script = scriptInfo ? "`" + scriptInfo[0] + "`" : "`—`";
    const reused = scriptInfo ? scriptInfo[1] : "—";
    const created = scriptInfo ? scriptInfo[2] : "—";
    let autoStatus = "skipped";
    if (scriptInfo) autoStatus = "created";
    else if (row.status === "BLOCKED_REQUIREMENT") autoStatus = "blocked";
    else if (row.automation === "Manual") autoStatus = "skipped";
    autoRows.push(`| ${row.id} | ${automated} | ${script} | ${reused} | ${created} | ${autoStatus} |`);

    let result = "Not Run";
    let ftype = "ENVIRONMENT";
    let notes = kreNote;
    if (row.status === "BLOCKED_REQUIREMENT") {
      result = "Blocked";
      ftype = "REQUIREMENT_GAP";
      notes = "See open-questions.md. No script added.";
      blocked++;
    } else {
      notRun++;
    }
    execRows.push(`| ${row.id} | ${result} | ${ftype} | none | ${notes} |`);
  }

  const autoMd = `# Automation report — ${key}

| Test Case | Automated | Script | Reused Components | New Components | Status |
| --- | --- | --- | --- | --- | --- |
${autoRows.join("\n")}

Status \`mapped\` means an existing script already covers the case. Do not add a second script.
No existing Katalon scripts covered these post-login tickets (only \`TC_908_*\` / \`TS_Login\` existed). Candidate rows were created as thin scripts. Manual and blocked rows were not automated.
`;

  const execMd = `# Execution report — ${key}

Device reported before the run:

\`\`\`text
${deviceBlock}
\`\`\`

App prepare:

\`\`\`text
${appState}
\`\`\`

Profile: \`local\`
Suite: \`${SUITE[key]}\`

${kreNote}

| Test Case | Result | Failure Type | Evidence | Notes |
| --- | --- | --- | --- | --- |
${execRows.join("\n")}

## Totals

| Result | Count |
| --- | --- |
| Passed | ${passed} |
| Failed | ${failed} |
| Blocked | ${blocked} |
| Not Run | ${notRun} |
`;

  fs.writeFileSync(path.join(__dirname, key, "automation-report.md"), autoMd, "utf8");
  fs.writeFileSync(path.join(__dirname, key, "execution-report.md"), execMd, "utf8");
  console.log(key, "cases", cases.length, "blocked", blocked, "not_run", notRun);
}
