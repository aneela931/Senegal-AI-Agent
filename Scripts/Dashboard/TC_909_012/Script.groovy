import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-909 | Requirement: REQ-011 | Test Case: TC-909-012
new LoginJourney().completeLogin()
new DashboardPage().revealBalanceWithEye()
Mobile.closeApplication()
