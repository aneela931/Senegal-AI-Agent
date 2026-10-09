import com.senegalagent.pages.CustomerServicesPage
import com.senegalagent.pages.DashboardPage
import com.senegalagent.pages.LoginData
import com.senegalagent.pages.LoginJourney
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

// Jira: SAA-912 | Requirement: REQ-003 | Test Case: TC-912-003
new LoginJourney().completeLogin()
new DashboardPage().openCustomerServices()
new CustomerServicesPage().enterMsisdn(LoginData.invalidMsisdnFormat())
new CustomerServicesPage().submit()
new CustomerServicesPage().assertInvalidMsisdnMessage()
Mobile.closeApplication()
