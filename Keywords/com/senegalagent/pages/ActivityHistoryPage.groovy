package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

/**
 * Activity historic. Labels from SAA-909 / SAA-915.
 */
class ActivityHistoryPage {

	void assertOpen() {
		Mobile.verifyElementVisible(MobileText.byAnyText(['Activity History', 'Historic', 'History', 'Transaction']), MobileText.timeout())
	}

	void assertPerformanceCard() {
		assertOpen()
		Mobile.verifyElementVisible(MobileText.byAnyText(['Balance', 'Revenue', 'Performance']), MobileText.timeout())
	}
}
