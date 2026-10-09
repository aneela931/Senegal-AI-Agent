import json
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from _case_factory import coverage_payload, strip_internal
from _gen_909_912 import saa_909, saa_910, saa_911, saa_912
from _gen_913_921 import saa_913, saa_914, saa_915, saa_917
from _gen_918_945 import saa_918, saa_919, saa_920, saa_921, saa_945

ROOT = os.path.dirname(os.path.abspath(__file__))

TICKETS = {
    "SAA-909": ("Dashboard - Change Request", saa_909),
    "SAA-910": ("Customer Registration - Change Request", saa_910),
    "SAA-911": ("Logout Inactivity Timeout", saa_911),
    "SAA-912": ("Telco Services - Change Request", saa_912),
    "SAA-913": ("Performance Dashboard", saa_913),
    "SAA-914": ("Notification Section", saa_914),
    "SAA-915": ("Activity History - Dashboard", saa_915),
    "SAA-917": ("KYC Form Details", saa_917),
    "SAA-918": ("Sim Swap - Change Request", saa_918),
    "SAA-919": ("Upgrade Customer Profile - Change Request", saa_919),
    "SAA-920": ("Reidentification within a SIM Swap", saa_920),
    "SAA-921": ("Re-identification Change Request", saa_921),
    "SAA-945": ("Create New Touchpoint (New POA)", saa_945),
}


def main():
    for key, (_summary, fn) in TICKETS.items():
        reqs, cases = fn()
        folder = os.path.join(ROOT, key)
        os.makedirs(folder, exist_ok=True)
        payload = {"sheetName": key, "cases": strip_internal(cases)}
        with open(os.path.join(folder, "cases.json"), "w", encoding="utf-8") as fh:
            json.dump(payload, fh, indent=2, ensure_ascii=False)
            fh.write("\n")
        cov = coverage_payload(reqs, cases)
        with open(os.path.join(folder, "coverage.json"), "w", encoding="utf-8") as fh:
            json.dump(cov, fh, indent=2, ensure_ascii=False)
            fh.write("\n")
        print("%s cases=%d reqs=%d" % (key, len(cases), len(reqs)))


if __name__ == "__main__":
    main()
