package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * OTP screen. Resend label from Excel "Resend OTP". Field is class locator.
 */
class OtpPage {

	static final String FIELD = 'Object Repository/Login/Otp/input_otp'
	static final String RESEND = 'Object Repository/Login/Otp/btn_resend'
	static final String SUBMIT = 'Object Repository/Login/Otp/btn_submit'
	static final String INVALID = 'Object Repository/Login/Otp/lbl_invalid_otp'

	void assertScreen() {
		MobileText.assertRepoVisible(FIELD)
	}

	void enterOtp(String otp) {
		MobileText.setEditText(FIELD, otp)
	}

	void submit() {
		MobileText.hideKeyboardQuietly()
		MobileText.tapRepo(SUBMIT)
	}

	void assertInvalidOtpError() {
		MobileText.assertRepoVisible(INVALID)
		MobileText.assertI18nVisible('otp.error.invalid')
	}

	void assertResendDisabled() {
		Mobile.verifyElementNotClickable(MobileText.repo(RESEND), MobileText.timeout(), FailureHandling.STOP_ON_FAILURE)
	}

	void tapResend() {
		MobileText.tapRepo(RESEND)
	}

	void assertResendEnabled() {
		Mobile.verifyElementClickable(MobileText.repo(RESEND), MobileText.timeout())
	}
}
