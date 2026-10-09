import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.RegistrationPage
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-917 | Requirement: REQ-001 | Test Case: TC-917-001
new LoginJourney().completeLogin()
new DashboardPage().openClientRegistration()
new RegistrationPage().assertKycFormReachable()
Mobile.closeApplication()
