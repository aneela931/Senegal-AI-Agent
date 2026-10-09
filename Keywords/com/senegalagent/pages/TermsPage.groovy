package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * Terms consents. Labels from Excel: Privacy terms, Camera access, Image access, Confirm and Continue.
 */
class TermsPage {

	static final String CONFIRM = 'Object Repository/Login/Terms/btn_confirm_and_continue'
	static final String PRIVACY = 'Object Repository/Login/Terms/chk_privacy_terms'
	static final String CAMERA = 'Object Repository/Login/Terms/chk_camera_access'
	static final String IMAGE = 'Object Repository/Login/Terms/chk_image_access'

	void assertScreen() {
		MobileText.assertRepoVisible(CONFIRM)
	}

	void tapConfirmWithoutConsents() {
		MobileText.tapRepo(CONFIRM)
	}

	void assertStillOnTerms() {
		assertScreen()
	}

	void acceptAllConsents() {
		Mobile.tap(MobileText.repo(PRIVACY), MobileText.timeout(), FailureHandling.OPTIONAL)
		Mobile.tap(MobileText.repo(CAMERA), MobileText.timeout(), FailureHandling.OPTIONAL)
		Mobile.tap(MobileText.repo(IMAGE), MobileText.timeout(), FailureHandling.OPTIONAL)
	}

	void confirmAndContinue() {
		MobileText.tapRepo(CONFIRM)
	}
}
