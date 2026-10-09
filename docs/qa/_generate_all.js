const fs = require("fs");
const path = require("path");
const { coveragePayload, stripInternal } = require("./_case_factory.js");
const { saa_909, saa_910, saa_911, saa_912 } = require("./_gen_909_912.js");
const { saa_913, saa_914, saa_915, saa_917 } = require("./_gen_913_921.js");
const { saa_918, saa_919, saa_920, saa_921, saa_945 } = require("./_gen_918_945.js");

const TICKETS = {
  "SAA-909": ["Dashboard - Change Request", saa_909],
  "SAA-910": ["Customer Registration - Change Request", saa_910],
  "SAA-911": ["Logout Inactivity Timeout", saa_911],
  "SAA-912": ["Telco Services - Change Request", saa_912],
  "SAA-913": ["Performance Dashboard", saa_913],
  "SAA-914": ["Notification Section", saa_914],
  "SAA-915": ["Activity History - Dashboard", saa_915],
  "SAA-917": ["KYC Form Details", saa_917],
  "SAA-918": ["Sim Swap - Change Request", saa_918],
  "SAA-919": ["Upgrade Customer Profile - Change Request", saa_919],
  "SAA-920": ["Reidentification within a SIM Swap", saa_920],
  "SAA-921": ["Re-identification Change Request", saa_921],
  "SAA-945": ["Create New Touchpoint (New POA)", saa_945],
};

const root = __dirname;
for (const [key, pair] of Object.entries(TICKETS)) {
  const fn = pair[1];
  const { reqs, cases } = fn();
  const folder = path.join(root, key);
  fs.mkdirSync(folder, { recursive: true });
  fs.writeFileSync(
    path.join(folder, "cases.json"),
    JSON.stringify({ sheetName: key, cases: stripInternal(cases) }, null, 2) + "\n",
    "utf8"
  );
  fs.writeFileSync(
    path.join(folder, "coverage.json"),
    JSON.stringify(coveragePayload(reqs, cases), null, 2) + "\n",
    "utf8"
  );
  console.log(key + " cases=" + cases.length + " reqs=" + reqs.length);
}
