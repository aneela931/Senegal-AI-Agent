package com.senegalagent.pages

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling

/**
 * Identify screen language chip (top-right dropdown). Default is Français; English is Anglais in the list.
 * Phone field is on the same screen — do not treat MSISDN as "past splash".
 */
class SplashPage {

	// Do not put the accented French label in Groovy: Katalon compiles it as FranAais and Appium never matches.
	static final List FRENCH_LABELS = ['French', 'Francais']
	static final List ENGLISH_LABELS = ['Anglais', 'English']
	static final List SPLASH_COPY = ['Identify', 'Identifiez']
	static final String LANG_CHIP = "//*[contains(@content-desc,'Fran') or contains(@content-desc,'Anglais') or contains(@content-desc,'English') or contains(@content-desc,'French')]"

	void waitForLanguageChip() {
		Permissions.allowIfShown()
		for (int i = 0; i < 20; i++) {
			if (frenchVisible() || englishVisible() || splashCopyVisible()) {
				return
			}
			Permissions.allowIfShown()
			Mobile.delay(1)
		}
	}

	boolean splashCopyVisible() {
		return MobileText.isVisible(MobileText.byAnyText(SPLASH_COPY), 1)
	}

	boolean frenchVisible() {
		return MobileText.isVisible(MobileText.byAnyText(FRENCH_LABELS), 2)
	}

	boolean englishVisible() {
		return MobileText.isVisible(MobileText.byAnyText(ENGLISH_LABELS), 2)
	}

	void assertLanguageSwitcher() {
		waitForLanguageChip()
		if (frenchVisible() || englishVisible()) {
			return
		}
		Mobile.verifyElementVisible(MobileText.byAnyText(FRENCH_LABELS), MobileText.timeout())
	}

	void selectEnglish() {
		waitForLanguageChip()
		if (englishVisible() && !frenchVisible()) {
			return
		}
		def chip = MobileText.xpathObject('lang-chip', LANG_CHIP)
		if (MobileText.isVisible(chip, 5)) {
			Mobile.tap(chip, MobileText.timeout())
			Mobile.delay(1)
		} else {
			Mobile.tap(MobileText.repo('Object Repository/Login/Splash/lbl_language_french'), MobileText.timeout())
			Mobile.delay(1)
		}
		def anglaisRow = MobileText.xpathObject('anglais-row', "//*[@content-desc='Anglais' or @content-desc='English' or @resource-id='language_bottom_sheet_button_2']")
		if (MobileText.isVisible(anglaisRow, 8)) {
			Mobile.tap(anglaisRow, MobileText.timeout())
			Mobile.delay(2)
		}
	}

	void assertEnglishUiAfterSwitch() {
		Mobile.verifyElementVisible(MobileText.byAnyText(ENGLISH_LABELS), MobileText.timeout(), FailureHandling.OPTIONAL)
	}
}
