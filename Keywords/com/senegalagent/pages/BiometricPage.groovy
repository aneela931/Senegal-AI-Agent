package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * First-login biometric prompt (device 2026-10-08): "Improve your security" + "Enable now".
 * Tapping Enable now continues to the dashboard. This is not a skip.
 */
class BiometricPage {

	static final String SCREEN = 'Object Repository/Login/Biometric/scr_biometric'
	static final List SCREEN_LABELS = [
		'Improve your security',
		'Enable biometric',
		'Enable now',
		'fingerprint',
		'Fingerprint'
	]
	void assertMandatoryScreen() {
		MobileText.assertRepoVisible(SCREEN)
	}

	boolean isShowing() {
		return MobileText.isVisible(MobileText.byAnyText(SCREEN_LABELS), 3) ||
			MobileText.repoVisibleQuick(SCREEN, 3)
	}

	void completeEnrolmentIfPrompted() {
		if (!isShowing()) {
			return
		}
		def enable = MobileText.xpathObject('enable-now',
			"//*[contains(@content-desc,'Enable now') or contains(@text,'Enable now') or contains(@content-desc,'Enable') or contains(@text,'Enable')]")
		if (MobileText.isVisible(enable, 8)) {
			Mobile.tap(enable, MobileText.timeout(), FailureHandling.OPTIONAL)
			Mobile.delay(3)
		}
		Permissions.allowIfShown()
	}

	void assertDashboardOrHome() {
		MobileText.assertI18nContainsVisible('home.dashboard')
	}
}
