package com.senegalagent.pages

/**
 * Profile Settings. Label from Excel input "Profile Settings".
 */
class ProfileSettingsPage {

	static final String SETTINGS = 'Object Repository/Login/Profile/btn_profile_settings'

	void openSettings() {
		MobileText.tapRepo(SETTINGS)
	}

	void assertSettingsVisible() {
		MobileText.assertI18nVisible('profile.settings')
	}
}
