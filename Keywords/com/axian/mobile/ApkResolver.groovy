package com.axian.mobile

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

/**
 * Resolves an APK under App/<version>/. Highest semantic version unless APK_VERSION is pinned
 * on the profile or EnvConfig JSON. Does not change CloudApp launch (startApplication vs
 * startExistingApplication). Updates LOCAL_APK_PATH so local runs install the resolved file.
 */
class ApkResolver {

	static String resolveRelativePath() {
		File appRoot = new File(RunConfiguration.getProjectDir(), 'App')
		if (!appRoot.isDirectory()) {
			throw new IllegalStateException("APK root not found: ${appRoot.absolutePath}")
		}

		String pin = pinnedVersion()
		File versionDir
		if (pin) {
			versionDir = new File(appRoot, pin)
			if (!versionDir.isDirectory()) {
				throw new IllegalStateException("Pinned APK version folder not found: App/${pin}")
			}
		} else {
			File[] dirs = appRoot.listFiles({ File f -> f.isDirectory() } as FileFilter)
			if (dirs == null || dirs.length == 0) {
				throw new IllegalStateException("No version folders under App/")
			}
			versionDir = dirs.toList().max { File a, File b -> compareSemVer(a.name, b.name) }
		}

		File[] apks = versionDir.listFiles({ File f -> f.isFile() && f.name.toLowerCase().endsWith('.apk') } as FileFilter)
		if (apks == null || apks.length == 0) {
			throw new IllegalStateException("No .apk in App/${versionDir.name}/")
		}
		File apk = apks.toList().sort { it.name }.first()
		return "App/${versionDir.name}/${apk.name}"
	}

	static String applyToProfile() {
		String relative = resolveRelativePath()
		try {
			GlobalVariable.LOCAL_APK_PATH = relative
		} catch (MissingPropertyException ignored) {
			KeywordUtil.logInfo("LOCAL_APK_PATH is not a GlobalVariable; resolved APK is ${relative}")
		}
		KeywordUtil.logInfo("ApkResolver using ${relative}")
		return relative
	}

	private static String pinnedVersion() {
		String fromProfile = readOptionalGlobal('APK_VERSION')
		if (fromProfile) {
			return fromProfile
		}
		try {
			Object jsonPin = EnvConfig.get('apkVersion')
			if (jsonPin != null && jsonPin.toString().trim()) {
				return jsonPin.toString().trim()
			}
		} catch (Exception ignored) {
			// Env JSON may not be loaded yet; latest under App/ is the default.
		}
		return null
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

	static int compareSemVer(String left, String right) {
		List<Integer> a = parseSemVer(left)
		List<Integer> b = parseSemVer(right)
		int n = Math.max(a.size(), b.size())
		for (int i = 0; i < n; i++) {
			int av = i < a.size() ? a[i] : 0
			int bv = i < b.size() ? b[i] : 0
			int cmp = av <=> bv
			if (cmp != 0) {
				return cmp
			}
		}
		return 0
	}

	private static List<Integer> parseSemVer(String raw) {
		String head = (raw ?: '0').tokenize('-')[0]
		return head.tokenize('.').collect { String part ->
			part.replaceAll(/[^0-9].*/, '') ?: '0'
		}.collect { it as Integer }
	}
}
