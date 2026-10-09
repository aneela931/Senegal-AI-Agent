package com.axian.mobile

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import internal.GlobalVariable

/**
 * Loads Data Files/Environments/<env>.json. Env name comes from GlobalVariable.ENVIRONMENT.
 * No secrets or tokens belong in these files.
 */
class EnvConfig {

	private static Map cached
	private static String cachedName

	static Map load() {
		String envName = environmentName()
		if (cached != null && cachedName == envName) {
			return cached
		}
		File file = jsonFile(envName)
		if (!file.isFile()) {
			throw new IllegalStateException("Environment JSON not found: Data Files/Environments/${envName}.json")
		}
		Object parsed = new JsonSlurper().parse(file)
		if (!(parsed instanceof Map)) {
			throw new IllegalStateException("Environment JSON must be an object: ${file.name}")
		}
		cached = (Map) parsed
		cachedName = envName
		KeywordUtil.logInfo("EnvConfig loaded ${file.name}")
		return cached
	}

	static void reload() {
		cached = null
		cachedName = null
		load()
	}

	static String environmentName() {
		try {
			Object value = GlobalVariable.ENVIRONMENT
			if (value != null && value.toString().trim()) {
				return value.toString().trim()
			}
		} catch (MissingPropertyException ignored) {
		}
		throw new IllegalStateException('GlobalVariable.ENVIRONMENT is missing. Use the cloud or local profile.')
	}

	static Object get(String dottedKey) {
		Map root = load()
		if (!dottedKey) {
			return root
		}
		Object current = root
		for (String part : dottedKey.split('\\.')) {
			if (!(current instanceof Map) || !((Map) current).containsKey(part)) {
				throw new IllegalStateException("EnvConfig key not found: ${dottedKey}")
			}
			current = ((Map) current).get(part)
		}
		return current
	}

	static Map inventory() {
		Object value = load().get('inventory')
		return value instanceof Map ? (Map) value : [:]
	}

	static Map testData() {
		Object value = load().get('testData')
		if (value instanceof Map) {
			return (Map) value
		}
		return inventory()
	}

	static String defaultLanguage() {
		Object value = load().get('defaultLanguage')
		if (value == null) {
			return null
		}
		String text = value.toString().trim()
		return text ? text : null
	}

	private static File jsonFile(String envName) {
		return new File(RunConfiguration.getProjectDir(), "Data Files/Environments/${envName}.json")
	}
}
