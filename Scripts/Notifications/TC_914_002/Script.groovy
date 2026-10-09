import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.NotificationsPage
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-914 | Requirement: REQ-001 | Test Case: TC-914-002
new LoginJourney().completeLogin()
new NotificationsPage().openFromTopIcon()
new NotificationsPage().assertList()
Mobile.closeApplication()
