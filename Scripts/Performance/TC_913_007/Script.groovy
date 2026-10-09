import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.PerformanceDashboardPage
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-913 | Requirement: REQ-007 | Test Case: TC-913-007
new LoginJourney().completeLogin()
new DashboardPage().openPerformance()
new PerformanceDashboardPage().filterTodayWeekMonth()
Mobile.closeApplication()
