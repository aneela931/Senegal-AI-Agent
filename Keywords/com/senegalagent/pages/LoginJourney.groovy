package com.senegalagent.pages

import com.axian.mobile.CloudApp
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

/**
 * Shared login journey. CloudApp.launch() only here (fresh start).
 * OTP/PIN come from staging testData.login (validOtp / validPin).
 */
class LoginJourney {

	SplashPage splash = new SplashPage()
	MsisdnPage msisdn = new MsisdnPage()
	OtpPage otp = new OtpPage()
	TermsPage terms = new TermsPage()
	PinPage pin = new PinPage()
	BiometricPage biometric = new BiometricPage()

	void launchFresh() {
		// Run 20261007_231120: Katalon starts the Appium session with noReset enabled. When the app is
		// still alive from the previous case, Appium logs "'sn.free.agent.app' is already running and
		// noReset is enabled. Set forceAppLaunch capability to true" and leaves the launcher on screen,
		// so the case fails on the splash check. forceAppLaunch brings the app to the foreground every time.
		RunConfiguration.setMobileDriverPreferencesProperty('forceAppLaunch', true)
		CloudApp.launch()
		Permissions.allowIfShown()
	}

	void toEnglishSplash() {
		launchFresh()
		splash.selectEnglish()
	}

	void toMsisdnEnglish() {
		toEnglishSplash()
		msisdn.assertScreen()
	}

	void submitMsisdn(String number) {
		msisdn.enterMsisdn(number)
		msisdn.submit()
	}

	void toOtpWithAgentMsisdn() {
		toMsisdnEnglish()
		submitMsisdn(LoginData.agentMsisdn())
		otp.assertScreen()
	}

	void submitIncorrectOtp() {
		otp.enterOtp(LoginData.incorrectOtp())
		otp.submit()
	}

	void submitIncorrectPin() {
		pin.enterPin(LoginData.incorrectPin())
		pin.submit()
	}

	void submitValidOtp() {
		otp.enterOtp(LoginData.validOtp())
		otp.submit()
	}

	void submitValidPin() {
		pin.enterPin(LoginData.validPin())
		pin.submit()
	}

	void toTermsAfterValidOtp() {
		toOtpWithAgentMsisdn()
		submitValidOtp()
		terms.assertScreen()
	}

	void toPinAfterTerms() {
		toTermsAfterValidOtp()
		terms.acceptAllConsents()
		terms.confirmAndContinue()
		pin.assertScreen()
	}

	boolean pinScreenVisible() {
		return MobileText.isVisible(MobileText.byAnyText(['Enter your Yas PIN', 'Yas PIN', 'mot de passe', 'POINT DE VENTE']), 4) ||
			MobileText.isVisible(MobileText.xpathObject('pin-field', "//*[@resource-id='login_screen_text_field']"), 2)
	}

	boolean otpScreenVisible() {
		return MobileText.isVisible(MobileText.byAnyText(['OTP', 'verification code', 'Verification code']), 3)
	}

	boolean termsScreenVisible() {
		return MobileText.isVisible(MobileText.byAnyText(['Confirm and Continue', 'Privacy terms']), 3)
	}

	void skipIfStuck() {
		Permissions.allowIfShown()
		['Skip', 'SKIP', 'Later', 'Not now', 'Maybe later', 'Cancel', 'Continue', 'OK', 'Got it'].each { String label ->
			if (MobileText.isVisible(MobileText.byAnyText([label]), 1)) {
				Mobile.tap(MobileText.byAnyText([label]), 5)
			}
		}
	}

	void completeLogin() {
		completeLogin(LoginData.agentMsisdn(), LoginData.validPin())
	}

	/** Same flow with explicit credentials. Used by TC_Login_Success for the fallback account. */
	void completeLogin(String msisdnValue, String pinValue) {
		toEnglishSplash()
		if (!pinScreenVisible()) {
			msisdn.assertScreen()
			submitMsisdn(msisdnValue)
			skipIfStuck()
		}
		if (otpScreenVisible()) {
			submitValidOtp()
			skipIfStuck()
		}
		if (termsScreenVisible()) {
			terms.acceptAllConsents()
			terms.confirmAndContinue()
			skipIfStuck()
		}
		if (!pinScreenVisible()) {
			for (int i = 0; i < 15 && !pinScreenVisible(); i++) {
				Mobile.delay(1)
			}
		}
		if (pinScreenVisible()) {
			pin.enterPin(pinValue)
			pin.submit()
		}
		skipIfStuck()
		biometric.completeEnrolmentIfPrompted()
		skipIfStuck()
	}
}
