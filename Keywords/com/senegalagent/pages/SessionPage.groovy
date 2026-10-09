package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import java.time.Duration

/**
 * Inactivity / background. SAA-911: re-enter before timeout without login.
 */
class SessionPage {

	void backgroundFewSeconds() {
		def driver = MobileDriverFactory.getDriver()
		try {
			driver.runAppInBackground(Duration.ofSeconds(5))
		} catch (Throwable ignored) {
			driver.runAppInBackground(5)
		}
	}

	void assertStillLoggedIn() {
		new DashboardPage().waitUntilReady()
		Mobile.verifyElementNotExist(MobileText.byAnyText(['Enter your Yas PIN', 'Identify', 'Identifiez-vous']), 5)
	}
}
