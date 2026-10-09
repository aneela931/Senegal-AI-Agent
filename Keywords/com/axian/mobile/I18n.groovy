package com.axian.mobile

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import internal.GlobalVariable

/**
 * Loads Data Files/i18n/<lang>.json and resolves strings by key.
 * Approved language: en. Language comes from GlobalVariable.LANGUAGE or EnvConfig.defaultLanguage().
 */
class I18n {

	private static Map cached
	private static String cachedLang

	static Map load() {
		return load(resolveLanguage())
	}

	static Map load(String lang) {
		if (!lang) {
			throw missingLanguage()
		}
		if (cached != null && cachedLang == lang) {
			return cached
		}
		File file = jsonFile(lang)
		if (!file.isFile()) {
			throw new IllegalStateException(
				"i18n file not found: Data Files/i18n/${lang}.json. " +
				'Approved language is en (Data Files/i18n/en.json). Do not add other locales unless named.'
			)
		}
		Object parsed = new JsonSlurper().parse(file)
		if (!(parsed instanceof Map)) {
			throw new IllegalStateException("i18n JSON must be an object: ${file.name}")
		}
		cached = (Map) parsed
		cachedLang = lang
		KeywordUtil.logInfo("I18n loaded ${file.name}")
		return cached
	}

	static String get(String dottedKey) {
		Map root = load()
		Object current = root
		for (String part : dottedKey.split('\\.')) {
			if (!(current instanceof Map) || !((Map) current).containsKey(part)) {
				throw new IllegalStateException("i18n key not found in ${cachedLang}.json: ${dottedKey}")
			}
			current = ((Map) current).get(part)
		}
		if (current == null) {
			throw new IllegalStateException("i18n key has no value in ${cachedLang}.json: ${dottedKey}")
		}
		return current.toString()
	}

	static void assertEquals(String dottedKey, String actual) {
		String expected = get(dottedKey)
		if (expected != actual) {
			throw new AssertionError("i18n mismatch for ${dottedKey}: expected '${expected}' but was '${actual}'")
		}
	}

	static String resolveLanguage() {
		String fromProfile = readOptionalGlobal('LANGUAGE')
		if (fromProfile) {
			return fromProfile
		}
		try {
			String fromEnv = EnvConfig.defaultLanguage()
			if (fromEnv) {
				return fromEnv
			}
		} catch (Exception ignored) {
		}
		throw missingLanguage()
	}

	private static IllegalStateException missingLanguage() {
		return new IllegalStateException(
			'No UI language is selected. Set staging.json defaultLanguage to en, or GlobalVariable.LANGUAGE to en. ' +
			'Do not add other locale files unless the user names them.'
		)
	}

	private static String readOptionalGlobal(String name) {
		try {
			Object value = GlobalVariable."${name}"
			if (value == null) {
				return null
			}
			String text = value.toString().trim()
			return text ? text : null
		} catch (MissingPropertyException ignored) {
			return null
		}
	}

	private static File jsonFile(String lang) {
		return new File(RunConfiguration.getProjectDir(), "Data Files/i18n/${lang}.json")
	}
}
