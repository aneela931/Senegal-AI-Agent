package com.senegalagent.pages

import com.axian.mobile.I18n
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import groovy.util.XmlSlurper
import internal.GlobalVariable

/**
 * Locator helper. Protected Flutter APK has no login resource-ids.
 * XPath TestObjects are built in memory. ObjectRepository.findTestObject is not used:
 * Katalon 11 MobileElementEntity files throw NPE (LocatorStrategy name is null).
 */
class MobileText {

	private static final Map xpathCache = [:]

	static int timeout() {
		try {
			return Integer.parseInt(GlobalVariable.DEFAULT_TIMEOUT.toString())
		} catch (Exception ignored) {
			return 20
		}
	}

	static TestObject xpathObject(String name, String xpath) {
		TestObject to = new TestObject(name)
		to.addProperty('xpath', ConditionType.EQUALS, xpath)
		return to
	}

	static TestObject repo(String path) {
		return xpathObject(path, xpathFromRepoFile(path))
	}

	static String xpathFromRepoFile(String path) {
		if (xpathCache.containsKey(path)) {
			return xpathCache.get(path)
		}
		String relative = path
		if (relative.startsWith('Object Repository/')) {
			relative = relative.substring('Object Repository/'.length())
		}
		File file = new File(RunConfiguration.getProjectDir(), 'Object Repository/' + relative + '.rs')
		String xp = new XmlSlurper().parse(file).selectorCollection.entry.value.text()
		if (!xp) {
			throw new IllegalStateException('No xpath in ' + file.getAbsolutePath())
		}
		xpathCache.put(path, xp)
		return xp
	}

	static String xpathLit(String s) {
		if (s.contains("'") && s.contains('"')) {
			return 'concat(\'' + s.replace("'", "',\"'\",'") + '\')'
		}
		if (s.contains("'")) {
			return '"' + s + '"'
		}
		return "'" + s + "'"
	}

	static TestObject byAnyText(List texts) {
		List parts = []
		texts.each { String t ->
			parts.add('@text=' + xpathLit(t))
			parts.add('@content-desc=' + xpathLit(t))
			parts.add('contains(@text,' + xpathLit(t) + ')')
			parts.add('contains(@content-desc,' + xpathLit(t) + ')')
		}
		return xpathObject('any:' + texts.join('|'), '//*[' + parts.join(' or ') + ']')
	}

	static TestObject byTextContains(String fragment) {
		return byAnyText([fragment])
	}

	static TestObject byI18n(String key) {
		String text = I18n.get(key)
		return xpathObject('i18n:' + key, '//*[(@text=' + xpathLit(text) + ' or @content-desc=' + xpathLit(text) + ')]')
	}

	static TestObject containsI18n(String key) {
		String text = I18n.get(key)
		return xpathObject('i18n-contains:' + key, '//*[contains(@text,' + xpathLit(text) + ') or contains(@content-desc,' + xpathLit(text) + ')]')
	}

	static boolean isVisible(TestObject to, int seconds) {
		try {
			Boolean ok = Mobile.verifyElementVisible(to, seconds, FailureHandling.OPTIONAL)
			return ok != null && ok.booleanValue()
		} catch (Throwable ignored) {
			return false
		}
	}

	static void tapRepo(String path) {
		Mobile.tap(repo(path), timeout())
	}

	static void assertRepoVisible(String path) {
		Mobile.verifyElementVisible(repo(path), timeout())
	}

	static boolean repoVisible(String path) {
		return isVisible(repo(path), timeout())
	}

	static void assertI18nVisible(String key) {
		TestObject to = byI18n(key)
		Mobile.verifyElementVisible(to, timeout())
		String actual = Mobile.getText(to, timeout(), FailureHandling.OPTIONAL)
		if (actual && actual.trim() && actual.trim() == I18n.get(key)) {
			I18n.assertEquals(key, actual.trim())
		}
	}

	static void assertI18nContainsVisible(String key) {
		Mobile.verifyElementVisible(containsI18n(key), timeout())
	}

	static void tapI18n(String key) {
		Mobile.tap(byI18n(key), timeout())
	}

	static boolean i18nVisible(String key) {
		return i18nVisible(key, 8)
	}

	static boolean i18nVisible(String key, int seconds) {
		return isVisible(byI18n(key), seconds)
	}

	static boolean repoVisibleQuick(String path, int seconds) {
		return isVisible(repo(path), seconds)
	}

	static void setEditText(String repoPath, String value) {
		Mobile.setText(repo(repoPath), value, timeout())
	}

	static void hideKeyboardQuietly() {
		Mobile.hideKeyboard(FailureHandling.OPTIONAL)
	}
}
