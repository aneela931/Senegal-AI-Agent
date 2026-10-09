package com.senegalagent.pages

/**
 * Other-device logout banner. Exact quote from SAA-908.
 */
class DeviceSessionPage {

	static final String LOGOUT_MSG = 'Object Repository/Login/Device/lbl_logged_out_other_device'

	void assertLoggedOutOtherDevice() {
		MobileText.assertRepoVisible(LOGOUT_MSG)
		MobileText.assertI18nVisible('device.message.loggedOutOtherDevice')
	}
}
