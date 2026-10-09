# Reporting

Every suite run should produce **Katalon reports** and **Allure** result files. HTML Allure is generated when the Allure CLI is available.

This project’s Allure wiring is **suite-local** (`Test Listeners/FrameworkListener.groovy`, `Keywords/com/axian/mobile/AllureSupport.groovy`). Do not edit `d:\AI Agent\reporter\reporting\` from this agent.

## Katalon reports

Studio writes under `Reports/` (gitignored). Open the run from **Reports** in Katalon Studio, or browse the timestamped folder after a local/cloud execution.

## Allure results

On suite start the listener creates `allure-results/` (project root) and writes:

- `environment.properties` (env, package, APK path, device)
- `categories.json`
- one `*-result.json` per test case
- `executor.json` after the suite

## Allure HTML

After the suite, the listener runs:

```text
allure generate allure-results -o allure-report --clean
```

Open `allure-report/index.html` in a browser.

If generate fails (CLI missing, or Java too old for the installed Allure), the suite still keeps raw `allure-results/`. This machine has Allure on PATH (`C:\allure\allure-2.30.0\...`) and Java 8. Allure 2.30 may prefer a newer JDK; if HTML generation fails, install a supported JDK or run the same command from a terminal that uses it.

## Cloud reminder

Plain **Run** does not start TestCloud. Use **Run > Test Execution - Cloud > Mobile Native Apps**. Reports still appear in Katalon `Reports/` plus `allure-results/` on the machine that executed the suite (Studio host).
