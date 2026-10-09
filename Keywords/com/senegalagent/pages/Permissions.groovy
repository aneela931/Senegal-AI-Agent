package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

/**
 * Android runtime permission dialogs (Allow / Autoriser). Do not wait for a human tap.
 */
class Permissions {

	static final List ALLOW_LABELS = [
		'Allow',
		'ALLOW',
		'While using the app',
		'Allow all the time',
		'Autoriser',
		'AUTORISER',
		'Pendant l\'utilisation de l\'application',
		'Lors de l\'utilisation de l\'appli'
	]

	static void allowIfShown() {
		// One lookup for all labels per pass. The previous one-call-per-label loop cost ~10 s per
		// call (run 20261007_231120) and turned a 20-iteration splash wait into a 7-minute hang.
		def anyAllow = MobileText.byAnyText(ALLOW_LABELS)
		for (int i = 0; i < 8; i++) {
			if (!MobileText.isVisible(anyAllow, 1)) {
				return
			}
			Mobile.tap(anyAllow, 5)
			Mobile.delay(1)
		}
	}
}
