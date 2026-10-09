package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

/**
 * Notification centre. Labels from SAA-914.
 */
class NotificationsPage {

	void assertList() {
		Mobile.verifyElementVisible(MobileText.byAnyText(['Notifications', 'Notification']), MobileText.timeout())
	}

	void assertTemporalListFormat() {
		assertList()
	}

	void openFromTopIcon() {
		new DashboardPage().waitUntilReady()
		if (MobileText.isVisible(MobileText.xpathObject('bell', "//*[@content-desc='Notifications' or contains(@content-desc,'Notification')]"), 4)) {
			Mobile.tap(MobileText.xpathObject('bell', "//*[@content-desc='Notifications' or contains(@content-desc,'Notification')]"), MobileText.timeout())
			return
		}
		new DashboardPage().openNotifications()
	}
}
