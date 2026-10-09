// Jira: <KEY> | Requirement: REQ-00N | Test Case: TC-<n>-00N
import com.axian.mobile.CloudApp
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.senegalagent.pages.LoginData
import com.senegalagent.pages.SplashPage
import com.senegalagent.pages.MsisdnPage
import com.senegalagent.pages.PinPage

CloudApp.launch()
// 1. Wait for splash / language (splash_screen)
new SplashPage().waitForLanguageChip()
// 2. Choose English (language_bottom_sheet_button_2)
new SplashPage().selectEnglish()
// 3. Type EnvConfig agent MSISDN (onboarding_screen_text_field)
new MsisdnPage().enterMsisdn(LoginData.agentMsisdn())
// 4. Tap continue (onboarding_screen_button_1)
new MsisdnPage().submit()
// 5. Assert Expected Result
new PinPage().assertScreen()
Mobile.closeApplication()
