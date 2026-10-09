import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-909 | Requirement: REQ-003 | Test Case: TC-909-003
new LoginJourney().completeLogin()
new DashboardPage().assertAgentInformation()
Mobile.closeApplication()
