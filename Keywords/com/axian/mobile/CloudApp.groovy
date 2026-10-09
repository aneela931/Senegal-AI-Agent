package com.axian.mobile

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import internal.GlobalVariable

/**
 * Only launch path for this project.
 * Local: clear app data, then install/open LOCAL_APK_PATH.
 * Cloud: TestCloud installs CLOUD_APP_ID, then this activates APP_PACKAGE.
 */
class CloudApp {
	static void launch() {
		String target = (GlobalVariable.RUN_TARGET ?: 'cloud').toString()
		if (target == 'local') {
			DeviceReset.clearLocalAppData()
			RunConfiguration.setMobileDriverPreferencesProperty('autoGrantPermissions', true)
			RunConfiguration.setMobileDriverPreferencesProperty('forceAppLaunch', true)
			Mobile.startApplication(GlobalVariable.LOCAL_APK_PATH.toString(), false, FailureHandling.STOP_ON_FAILURE)
			return
		}
		Mobile.startExistingApplication(GlobalVariable.APP_PACKAGE.toString(), FailureHandling.STOP_ON_FAILURE)
	}
}
