import com.senegalagent.pages.CustomerServicesPage
import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-912 | Requirement: REQ-001 | Test Case: TC-912-001
new LoginJourney().completeLogin()
new DashboardPage().openCustomerServices()
new CustomerServicesPage().assertMsisdnEntry()
Mobile.closeApplication()
