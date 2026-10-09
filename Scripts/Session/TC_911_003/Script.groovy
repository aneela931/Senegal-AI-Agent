import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.SessionPage
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-911 | Requirement: REQ-005 | Test Case: TC-911-003
new LoginJourney().completeLogin()
new DashboardPage().waitUntilReady()
new SessionPage().backgroundFewSeconds()
new SessionPage().assertStillLoggedIn()
Mobile.closeApplication()
