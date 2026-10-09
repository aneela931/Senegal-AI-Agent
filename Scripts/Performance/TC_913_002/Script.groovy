import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.PerformanceDashboardPage
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-913 | Requirement: REQ-001 | Test Case: TC-913-002
new LoginJourney().completeLogin()
new PerformanceDashboardPage().openFromProfile()
Mobile.closeApplication()
