import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-918 | Requirement: REQ-001 | Test Case: TC-918-001
new LoginJourney().completeLogin()
new DashboardPage().openSimSwap()
Mobile.closeApplication()
