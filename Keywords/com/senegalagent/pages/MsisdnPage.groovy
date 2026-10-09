package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * MSISDN screen. Field uses class locator (no resource-id in protected Flutter APK).
 */
class MsisdnPage {

	static final String FIELD = 'Object Repository/Login/Msisdn/input_msisdn'
	static final String SUBMIT = 'Object Repository/Login/Msisdn/btn_submit'
	static final String AGENT_ONLY = 'Object Repository/Login/Msisdn/lbl_agent_only_error'

	void assertScreen() {
		MobileText.assertRepoVisible(FIELD)
	}

	String currentMsisdnText() {
		return Mobile.getText(MobileText.repo(FIELD), MobileText.timeout(), FailureHandling.OPTIONAL)
	}

	void assertPrefillEditable() {
		assertScreen()
		Mobile.verifyElementAttributeValue(MobileText.repo(FIELD), 'enabled', 'true', MobileText.timeout(), FailureHandling.OPTIONAL)
	}

	void enterMsisdn(String msisdn) {
		Mobile.clearText(MobileText.repo(FIELD), MobileText.timeout(), FailureHandling.OPTIONAL)
		MobileText.setEditText(FIELD, msisdn)
	}

	void submit() {
		// Device check 2026-10-07 (Infinix X6836): the 'Next' control exists only while the MSISDN
		// field has focus (keyboard shown). Hiding the keyboard removes it from the UI tree,
		// so the keyboard is NOT hidden here. If Next is missing, focus the field first.
		def next = MobileText.repo(SUBMIT)
		if (!MobileText.isVisible(next, 3)) {
			Mobile.tap(MobileText.repo(FIELD), MobileText.timeout())
			Mobile.delay(1)
		}
		Mobile.tap(next, MobileText.timeout())
	}

	void assertAccepted() {
		new OtpPage().assertScreen()
	}

	void assertStillOnMsisdn() {
		assertScreen()
		Mobile.verifyElementNotExist(MobileText.repo(OtpPage.FIELD), 5, FailureHandling.OPTIONAL)
	}

	void assertAgentOnlyError() {
		MobileText.assertRepoVisible(AGENT_ONLY)
		MobileText.assertI18nVisible('msisdn.error.agentOnly')
	}

	void assertInlineFormatBlock() {
		assertStillOnMsisdn()
	}
}
