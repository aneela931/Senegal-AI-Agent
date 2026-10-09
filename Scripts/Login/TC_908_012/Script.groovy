import com.axian.mobile.CloudApp
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.senegalagent.pages.LoginData
import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.SplashPage
import com.senegalagent.pages.MsisdnPage
import com.senegalagent.pages.OtpPage
import com.senegalagent.pages.TermsPage
import com.senegalagent.pages.PinPage
import com.senegalagent.pages.BiometricPage
import com.senegalagent.pages.ProfileSettingsPage
import com.senegalagent.pages.DeviceSessionPage

// SAA-908 TC-908-012. Precondition: post-OTP Terms screen. OTP success is not stored.
new LoginJourney().toTermsAfterValidOtp()
new TermsPage().tapConfirmWithoutConsents()
new TermsPage().assertStillOnTerms()
Mobile.closeApplication()
