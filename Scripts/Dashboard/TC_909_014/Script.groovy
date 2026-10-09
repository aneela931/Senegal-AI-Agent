import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-909 | Requirement: REQ-013 | Test Case: TC-909-014
new LoginJourney().completeLogin()
new DashboardPage().expandQr()
Mobile.closeApplication()
