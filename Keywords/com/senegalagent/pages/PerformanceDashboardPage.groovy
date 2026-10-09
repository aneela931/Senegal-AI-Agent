package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * Performance dashboards. Labels from SAA-913.
 */
class PerformanceDashboardPage {

	void assertOpen() {
		Mobile.verifyElementVisible(MobileText.byAnyText([
			'Performance', 'Today', 'This Week', 'This Month',
			'Registration', 'Airtime', 'Reload'
		]), MobileText.timeout())
	}

	void filterTodayWeekMonth() {
		assertOpen()
		['Today', 'This Week', 'This Month'].each { String label ->
			if (MobileText.isVisible(MobileText.byAnyText([label]), 3)) {
				Mobile.tap(MobileText.byAnyText([label]), MobileText.timeout(), FailureHandling.OPTIONAL)
			}
		}
	}

	void openFromProfile() {
		new DashboardPage().openMyAccount()
		Mobile.tap(MobileText.byAnyText(['Performance', 'Performance Dashboard']), MobileText.timeout())
		assertOpen()
	}
}
