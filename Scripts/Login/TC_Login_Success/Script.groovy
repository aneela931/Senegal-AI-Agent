import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.senegalagent.pages.LoginJourney
import com.senegalagent.pages.PinPage

// One login only. Do not restart or retry with a fallback account.
// After PIN: first-login "Enable now" (biometric) if shown, then dashboard. App stays open.

RunConfiguration.setMobileDriverPreferencesProperty('dontStopAppOnReset', true)

KeywordUtil.logInfo('Login: primary account from staging.json (single attempt)')
new LoginJourney().completeLogin()
new PinPage().assertLoginSucceeded()
KeywordUtil.logInfo('Login succeeded.')
