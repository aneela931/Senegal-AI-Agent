import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-909 | Requirement: REQ-021 | Test Case: TC-909-023
new LoginJourney().completeLogin()
new DashboardPage().assertFooter()
Mobile.closeApplication()
