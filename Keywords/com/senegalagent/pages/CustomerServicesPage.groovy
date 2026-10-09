package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * Customer Services MSISDN entry. Quoted invalid message from SAA-912.
 */
class CustomerServicesPage {

	void assertMsisdnEntry() {
		Mobile.verifyElementVisible(MobileText.byAnyText(['Customer Services', 'MSISDN', 'Enter']), MobileText.timeout())
	}

	void enterMsisdn(String number) {
		def field = MobileText.xpathObject('cs-msisdn', "(//android.widget.EditText)[1]")
		Mobile.tap(field, MobileText.timeout(), FailureHandling.OPTIONAL)
		Mobile.setText(field, number, MobileText.timeout())
	}

	void submit() {
		def next = MobileText.xpathObject('cs-next', "//*[@content-desc='Next' or @text='Next' or @content-desc='Continue' or @text='Continue']")
		if (MobileText.isVisible(next, 4)) {
			Mobile.tap(next, MobileText.timeout())
			return
		}
		Mobile.tap(MobileText.byAnyText(['Next', 'Continue', 'Validate', 'OK']), MobileText.timeout())
	}

	void assertInvalidMsisdnMessage() {
		Mobile.verifyElementVisible(MobileText.byAnyText([
			'It seems the MSISDN entered is either incorrect or inactive.'
		]), MobileText.timeout())
	}
}
