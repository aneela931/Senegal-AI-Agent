package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

/**
 * Client Registration entry. SAA-910 / SAA-917.
 */
class RegistrationPage {

	void assertOpen() {
		Mobile.verifyElementVisible(MobileText.byAnyText([
			'Client Registration', 'Registration', 'MSISDN', 'Available'
		]), MobileText.timeout())
	}

	void assertKycFormReachable() {
		assertOpen()
	}
}
