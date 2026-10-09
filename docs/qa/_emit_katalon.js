const fs = require("fs");
const path = require("path");
const crypto = require("crypto");

const proj = path.resolve(__dirname, "../..");

function uuid() {
  return crypto.randomUUID();
}

const AUTOMATED = [
  "TC-909-001",
  "TC-909-002",
  "TC-909-003",
  "TC-909-005",
  "TC-909-011",
  "TC-909-012",
  "TC-909-013",
  "TC-909-014",
  "TC-909-019",
  "TC-909-023",
  "TC-910-001",
  "TC-911-003",
  "TC-912-001",
  "TC-912-003",
  "TC-913-001",
  "TC-913-002",
  "TC-913-007",
  "TC-914-001",
  "TC-914-002",
  "TC-914-005",
  "TC-914-009",
  "TC-915-001",
  "TC-915-012",
  "TC-917-001",
  "TC-918-001",
  "TC-919-001",
  "TC-921-001",
];

const tests = [
  {
    feature: "Dashboard",
    name: "TC_909_001",
    jira: "SAA-909",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertFitsOneScreen()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_002",
    jira: "SAA-909",
    req: "REQ-002",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertSectionsPresent()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_003",
    jira: "SAA-909",
    req: "REQ-003",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertAgentInformation()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_005",
    jira: "SAA-909",
    req: "REQ-004",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openSearch()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_011",
    jira: "SAA-909",
    req: "REQ-010",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertBalancePresent()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_012",
    jira: "SAA-909",
    req: "REQ-011",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().revealBalanceWithEye()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_013",
    jira: "SAA-909",
    req: "REQ-012",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertQrVisible()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_014",
    jira: "SAA-909",
    req: "REQ-013",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().expandQr()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_019",
    jira: "SAA-909",
    req: "REQ-017",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertPerformanceAtMostTwoCards()`,
  },
  {
    feature: "Dashboard",
    name: "TC_909_023",
    jira: "SAA-909",
    req: "REQ-021",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().assertFooter()`,
  },
  {
    feature: "CustomerRegistration",
    name: "TC_910_001",
    jira: "SAA-910",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney", "RegistrationPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openClientRegistration()
new RegistrationPage().assertOpen()`,
  },
  {
    feature: "Session",
    name: "TC_911_003",
    jira: "SAA-911",
    req: "REQ-005",
    imports: ["DashboardPage", "LoginJourney", "SessionPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().waitUntilReady()
new SessionPage().backgroundFewSeconds()
new SessionPage().assertStillLoggedIn()`,
  },
  {
    feature: "CustomerServices",
    name: "TC_912_001",
    jira: "SAA-912",
    req: "REQ-001",
    imports: ["CustomerServicesPage", "DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openCustomerServices()
new CustomerServicesPage().assertMsisdnEntry()`,
  },
  {
    feature: "CustomerServices",
    name: "TC_912_003",
    jira: "SAA-912",
    req: "REQ-003",
    imports: ["CustomerServicesPage", "DashboardPage", "LoginData", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openCustomerServices()
new CustomerServicesPage().enterMsisdn(LoginData.invalidMsisdnFormat())
new CustomerServicesPage().submit()
new CustomerServicesPage().assertInvalidMsisdnMessage()`,
  },
  {
    feature: "Performance",
    name: "TC_913_001",
    jira: "SAA-913",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney", "PerformanceDashboardPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openPerformance()
new PerformanceDashboardPage().assertOpen()`,
  },
  {
    feature: "Performance",
    name: "TC_913_002",
    jira: "SAA-913",
    req: "REQ-001",
    imports: ["LoginJourney", "PerformanceDashboardPage"],
    body: `new LoginJourney().completeLogin()
new PerformanceDashboardPage().openFromProfile()`,
  },
  {
    feature: "Performance",
    name: "TC_913_007",
    jira: "SAA-913",
    req: "REQ-007",
    imports: ["DashboardPage", "LoginJourney", "PerformanceDashboardPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openPerformance()
new PerformanceDashboardPage().filterTodayWeekMonth()`,
  },
  {
    feature: "Notifications",
    name: "TC_914_001",
    jira: "SAA-914",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney", "NotificationsPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openNotifications()
new NotificationsPage().assertList()`,
  },
  {
    feature: "Notifications",
    name: "TC_914_002",
    jira: "SAA-914",
    req: "REQ-001",
    imports: ["LoginJourney", "NotificationsPage"],
    body: `new LoginJourney().completeLogin()
new NotificationsPage().openFromTopIcon()
new NotificationsPage().assertList()`,
  },
  {
    feature: "Notifications",
    name: "TC_914_005",
    jira: "SAA-914",
    req: "REQ-004",
    imports: ["DashboardPage", "LoginJourney", "NotificationsPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openNotifications()
new NotificationsPage().assertTemporalListFormat()`,
  },
  {
    feature: "Notifications",
    name: "TC_914_009",
    jira: "SAA-914",
    req: "REQ-007",
    imports: ["DashboardPage", "LoginJourney", "NotificationsPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openNotifications()
new NotificationsPage().assertTemporalListFormat()`,
  },
  {
    feature: "ActivityHistory",
    name: "TC_915_001",
    jira: "SAA-915",
    req: "REQ-001",
    imports: ["ActivityHistoryPage", "DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openActivityHistory()
new ActivityHistoryPage().assertOpen()`,
  },
  {
    feature: "ActivityHistory",
    name: "TC_915_012",
    jira: "SAA-915",
    req: "REQ-016",
    imports: ["ActivityHistoryPage", "DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openActivityHistory()
new ActivityHistoryPage().assertPerformanceCard()`,
  },
  {
    feature: "CustomerRegistration",
    name: "TC_917_001",
    jira: "SAA-917",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney", "RegistrationPage"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openClientRegistration()
new RegistrationPage().assertKycFormReachable()`,
  },
  {
    feature: "CustomerServices",
    name: "TC_918_001",
    jira: "SAA-918",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openSimSwap()`,
  },
  {
    feature: "CustomerServices",
    name: "TC_919_001",
    jira: "SAA-919",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openUpgradeCustomerFile()`,
  },
  {
    feature: "CustomerServices",
    name: "TC_921_001",
    jira: "SAA-921",
    req: "REQ-001",
    imports: ["DashboardPage", "LoginJourney"],
    body: `new LoginJourney().completeLogin()
new DashboardPage().openReidentification()`,
  },
];

const suites = {};

for (const t of tests) {
  const excelId = t.name.replace(/_/g, "-");
  const trace = `Jira: ${t.jira} | Requirement: ${t.req} | Test Case: ${excelId}`;
  const importLines = t.imports
    .map((p) => `import com.senegalagent.pages.${p}`)
    .concat(["import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile"])
    .join("\n");
  const groovy = `${importLines}

// ${trace}
${t.body}
Mobile.closeApplication()
`;
  const scriptDir = path.join(proj, "Scripts", t.feature, t.name);
  fs.mkdirSync(scriptDir, { recursive: true });
  fs.writeFileSync(path.join(scriptDir, "Script.groovy"), groovy, "utf8");

  const tcDir = path.join(proj, "Test Cases", t.feature);
  fs.mkdirSync(tcDir, { recursive: true });
  const tcXml = `<?xml version="1.0" encoding="UTF-8"?>
<TestCaseEntity>
   <description>${trace}</description>
   <name>${t.name}</name>
   <tag>${t.jira},Functional</tag>
   <comment>${trace}. Text locators because the protected Flutter APK has no UI Test IDs.</comment>
   <testCaseGuid>${uuid()}</testCaseGuid>
</TestCaseEntity>
`;
  fs.writeFileSync(path.join(tcDir, t.name + ".tc"), tcXml, "utf8");

  if (!suites[t.feature]) suites[t.feature] = [];
  suites[t.feature].push(t.name);
}

const featureMeta = {
  Dashboard: ["dashboard", "SAA-909"],
  CustomerRegistration: ["registration", "SAA-910,SAA-917"],
  Session: ["session", "SAA-911"],
  CustomerServices: ["customer-services", "SAA-912,SAA-918,SAA-919,SAA-921"],
  Performance: ["performance", "SAA-913"],
  Notifications: ["notifications", "SAA-914"],
  ActivityHistory: ["activity-history", "SAA-915"],
};

for (const [feature, names] of Object.entries(suites)) {
  const [tag, jira] = featureMeta[feature];
  const links = names
    .map(
      (n) => `   <testCaseLink>
      <guid>${uuid()}</guid>
      <isReuseDriver>false</isReuseDriver>
      <isRun>true</isRun>
      <testCaseId>Test Cases/${feature}/${n}</testCaseId>
      <usingDataBindingAtTestSuiteLevel>false</usingDataBindingAtTestSuiteLevel>
   </testCaseLink>`
    )
    .join("\n");
  const xml = `<?xml version="1.0" encoding="UTF-8"?>
<TestSuiteEntity>
   <description>${feature} suite. Text locators: protected Flutter APK has no UI Test IDs. Never named TS_&lt;JIRA-KEY&gt;.</description>
   <name>TS_${feature}</name>
   <tag>${tag},${jira}</tag>
   <isRerun>false</isRerun>
   <testSuiteGuid>${uuid()}</testSuiteGuid>
${links}
</TestSuiteEntity>
`;
  fs.writeFileSync(path.join(proj, "Test Suites", `TS_${feature}.ts`), xml, "utf8");
  console.log("TS_" + feature, names.length);
}

const autoSet = new Set(AUTOMATED);
for (const key of [
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
]) {
  const casesPath = path.join(__dirname, key, "cases.json");
  const covPath = path.join(__dirname, key, "coverage.json");
  const casesDoc = JSON.parse(fs.readFileSync(casesPath, "utf8"));
  for (const row of casesDoc.cases) {
    if (autoSet.has(row.id)) row.automation = "Automated";
  }
  fs.writeFileSync(casesPath, JSON.stringify(casesDoc, null, 2) + "\n", "utf8");

  const cov = JSON.parse(fs.readFileSync(covPath, "utf8"));
  for (const row of cov.cases) {
    if (autoSet.has(row.id)) row.automated = true;
  }
  fs.writeFileSync(covPath, JSON.stringify(cov, null, 2) + "\n", "utf8");
}

console.log("tests", tests.length);
console.log("automated", AUTOMATED.length);
