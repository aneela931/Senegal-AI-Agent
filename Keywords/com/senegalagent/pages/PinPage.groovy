package com.senegalagent.pages

import com.axian.mobile.DeviceReset
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject

/**
 * PIN screen. Quoted errors from SAA-908. Field is class locator.
 */
class PinPage {

	static final String FIELD = 'Object Repository/Login/Pin/input_pin'
	static final String SUBMIT = 'Object Repository/Login/Pin/btn_submit'
	static final String RESET = 'Object Repository/Login/Pin/btn_pin_reset'
	static final String INCORRECT = 'Object Repository/Login/Pin/lbl_incorrect_pin'
	static final String LOCK = 'Object Repository/Login/Pin/lbl_lock_message'

	void assertScreen() {
		MobileText.assertRepoVisible(FIELD)
	}

	void enterPin(String pin) {
		// Device check 2026-10-08 (Infinix X6836): the PIN entry is 4 circles inside one field.
		// The field's CENTRE is the gap between circle 2 and circle 3, so a normal tap there never
		// focuses the input and no keyboard opens (runs 20261008_0013/0002 entered nothing).
		// Tapping the FIRST circle (left edge + 1/8 width) focuses it and opens the keyboard.
		TestObject field = MobileText.repo(FIELD)
		if (!MobileText.isVisible(field, MobileText.timeout())) {
			field = MobileText.xpathObject('pin-field', "//*[@resource-id='login_screen_text_field']")
		}
		tapFirstCell(field)
		Mobile.delay(1)
		// Appium setText returns 200 on this Flutter PIN widget but leaves the cells empty
		// (run 20261008_004618). adb input text fills them once the first circle has focus.
		DeviceReset.type(pin)
		Mobile.delay(1)
	}

	/** Tap the first of the 4 PIN circles using the field's on-screen rectangle, not its centre. */
	private void tapFirstCell(TestObject field) {
		try {
			int left = Mobile.getElementLeftPosition(field, 8)
			int top = Mobile.getElementTopPosition(field, 8)
			int width = Mobile.getElementWidth(field, 8)
			int height = Mobile.getElementHeight(field, 8)
			int x = left + (int) (width / 8)   // centre of the first of four cells
			int y = top + (int) (height / 2)
			Mobile.tapAtPosition(x, y)
			DeviceReset.tap(x, y)
		} catch (Throwable ignored) {
			Mobile.tap(field, 8, FailureHandling.OPTIONAL)
		}
	}

	void submit() {
		// Run 20261008_084109: adb input text filled the PIN and the app already left this screen
		// (login worked on the phone). Tapping btn_submit after that is a hard fail. Skip if gone.
		if (homeVisible() || !pinScreenStillShowing()) {
			return
		}
		MobileText.hideKeyboardQuietly()
		if (homeVisible() || !pinScreenStillShowing()) {
			return
		}
		def login = MobileText.xpathObject('login-btn', "//*[@content-desc='Login' or @content-desc='Connexion' or @resource-id='login_screen_button_3']")
		if (MobileText.isVisible(login, 5)) {
			Mobile.tap(login, 8, FailureHandling.OPTIONAL)
			return
		}
		if (MobileText.repoVisibleQuick(SUBMIT, 3)) {
			Mobile.tap(MobileText.repo(SUBMIT), MobileText.timeout(), FailureHandling.OPTIONAL)
		}
	}

	boolean pinScreenStillShowing() {
		return MobileText.isVisible(MobileText.byAnyText(['Enter your Yas PIN', 'Yas PIN', 'mot de passe', 'POINT DE VENTE']), 2) ||
			MobileText.isVisible(MobileText.repo(FIELD), 2)
	}

	/**
	 * Login succeeded = the PIN prompt ('Enter your Yas PIN', seen on device 2026-10-07) is gone
	 * and neither the incorrect-PIN nor the lock message is shown. The post-login screen itself
	 * (biometric enrolment or home) is not asserted here because its locators are not yet confirmed.
	 */
	void assertLoginSucceeded() {
		if (!loginSucceeded()) {
			Mobile.verifyElementNotExist(MobileText.byAnyText(['Enter your Yas PIN', 'mot de passe']), MobileText.timeout())
		}
	}

	/** PIN accepted: home/dashboard is up, or the first-login biometric prompt is showing. */
	boolean loginSucceeded() {
		for (int i = 0; i < 20; i++) {
			if (MobileText.repoVisibleQuick(INCORRECT, 1) || MobileText.repoVisibleQuick(LOCK, 1)) {
				return false
			}
			if (homeVisible() || new BiometricPage().isShowing()) {
				return true
			}
			Mobile.delay(1)
		}
		return homeVisible() || new BiometricPage().isShowing()
	}

	boolean homeVisible() {
		return MobileText.isVisible(MobileText.byAnyText([
			'Airtime', 'Subscriptions', 'Dashboard', 'Welcome!', 'Accueil',
			'Client Registration', 'Activity History', 'Home'
		]), 2)
	}

	void assertIncorrectPinError() {
		MobileText.assertRepoVisible(INCORRECT)
		MobileText.assertI18nVisible('pin.error.incorrect')
	}

	void assertLockMessage() {
		MobileText.assertRepoVisible(LOCK)
		MobileText.assertI18nContainsVisible('pin.error.lockPrefix')
	}

	void assertRetryDisabled() {
		Mobile.verifyElementNotClickable(MobileText.repo(FIELD), MobileText.timeout(), FailureHandling.OPTIONAL)
	}

	void assertRetryEnabled() {
		Mobile.verifyElementVisible(MobileText.repo(FIELD), MobileText.timeout())
		Mobile.verifyElementClickable(MobileText.repo(FIELD), MobileText.timeout(), FailureHandling.OPTIONAL)
	}

	void startPinReset() {
		MobileText.tapRepo(RESET)
	}

	void assertPinResetStarted() {
		Mobile.verifyElementExist(MobileText.repo(RESET), MobileText.timeout(), FailureHandling.OPTIONAL)
	}

	String lockMessageText() {
		return Mobile.getText(MobileText.repo(LOCK), MobileText.timeout(), FailureHandling.OPTIONAL)
	}
}
