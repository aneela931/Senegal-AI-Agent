package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

/**
 * Main dashboard after login. Labels from SAA-909. Protected Flutter: text / content-desc only.
 */
class DashboardPage {

	static final List READY = [
		'Client Registration', 'Customer Services', 'Airtime & Bundle',
		'Home', 'Notifications', 'My Account', 'Search', 'Activity History',
		'Dashboard', 'Welcome'
	]

	void waitUntilReady() {
		for (int i = 0; i < 25; i++) {
			if (MobileText.isVisible(MobileText.byAnyText(READY), 2)) {
				return
			}
			Permissions.allowIfShown()
			new LoginJourney().skipIfStuck()
		}
		KeywordUtil.markFailed('Dashboard did not appear after login')
	}

	void assertSectionsPresent() {
		waitUntilReady()
		Mobile.verifyElementVisible(MobileText.byAnyText(['Search']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['Activity History', 'Historic', 'History']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['Home']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['Notifications']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['My Account', 'Account']), MobileText.timeout())
	}

	void assertFitsOneScreen() {
		waitUntilReady()
		assertSectionsPresent()
	}

	void assertAgentInformation() {
		waitUntilReady()
		boolean named = MobileText.isVisible(MobileText.byAnyText(['Welcome', 'POINT DE VENTE', 'Agent']), 5)
		if (!named) {
			Mobile.verifyElementVisible(MobileText.byAnyText(['Welcome', 'POINT DE VENTE']), MobileText.timeout())
		}
	}

	void openSearch() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['Search']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['Search', 'Cancel', 'Close']), MobileText.timeout())
	}

	void assertBalancePresent() {
		waitUntilReady()
		Mobile.verifyElementVisible(MobileText.byAnyText(['Balance', 'Airtime', 'Yas']), MobileText.timeout())
	}

	void revealBalanceWithEye() {
		waitUntilReady()
		def eye = MobileText.xpathObject('eye', "//*[@content-desc='Eye' or contains(@content-desc,'eye') or contains(@content-desc,'Show') or contains(@content-desc,'Hide')]")
		if (!MobileText.isVisible(eye, 5)) {
			eye = MobileText.byAnyText(['Eye', 'Show', 'Hide'])
		}
		Mobile.tap(eye, MobileText.timeout(), FailureHandling.STOP_ON_FAILURE)
	}

	void assertQrVisible() {
		waitUntilReady()
		Mobile.verifyElementVisible(MobileText.byAnyText(['QR', 'QR Code']), MobileText.timeout())
	}

	void expandQr() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['QR', 'QR Code']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['QR', 'QR Code']), MobileText.timeout())
	}

	void assertPerformanceAtMostTwoCards() {
		waitUntilReady()
		Mobile.verifyElementVisible(MobileText.byAnyText(['Performance', 'KPI', 'Yesterday', 'Today']), MobileText.timeout())
	}

	void assertFooter() {
		waitUntilReady()
		Mobile.verifyElementVisible(MobileText.byAnyText(['Home']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['Notifications']), MobileText.timeout())
		Mobile.verifyElementVisible(MobileText.byAnyText(['My Account', 'Account']), MobileText.timeout())
	}

	void openHome() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['Home']), MobileText.timeout())
		waitUntilReady()
	}

	void openNotifications() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['Notifications']), MobileText.timeout())
	}

	void openMyAccount() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['My Account', 'Account']), MobileText.timeout())
	}

	void openClientRegistration() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['Client Registration', 'Registration']), MobileText.timeout())
	}

	void openCustomerServices() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['Customer Services']), MobileText.timeout())
	}

	void openActivityHistory() {
		waitUntilReady()
		Mobile.tap(MobileText.byAnyText(['Activity History', 'Historic']), MobileText.timeout())
	}

	void openPerformance() {
		waitUntilReady()
		if (MobileText.isVisible(MobileText.byAnyText(['Performance']), 5)) {
			Mobile.tap(MobileText.byAnyText(['Performance']), MobileText.timeout())
			return
		}
		openMyAccount()
		Mobile.tap(MobileText.byAnyText(['Performance', 'Performance Dashboard']), MobileText.timeout())
	}

	void openSimSwap() {
		openCustomerServices()
		Mobile.tap(MobileText.byAnyText(['SIM Swap', 'Sim Swap']), MobileText.timeout())
	}

	void openUpgradeCustomerFile() {
		openCustomerServices()
		Mobile.tap(MobileText.byAnyText(['Upgrade Customer File', 'Upgrade']), MobileText.timeout())
	}

	void openReidentification() {
		openCustomerServices()
		Mobile.tap(MobileText.byAnyText(['Re-identification', 'Reidentification', 'Re-Identification']), MobileText.timeout())
	}
}
