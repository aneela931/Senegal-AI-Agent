import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-921 | Requirement: REQ-001 | Test Case: TC-921-001
new LoginJourney().completeLogin()
new DashboardPage().openReidentification()
Mobile.closeApplication()
