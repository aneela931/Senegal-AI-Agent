import com.senegalagent.pages.ActivityHistoryPage
import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-915 | Requirement: REQ-001 | Test Case: TC-915-001
new LoginJourney().completeLogin()
new DashboardPage().openActivityHistory()
new ActivityHistoryPage().assertOpen()
Mobile.closeApplication()
