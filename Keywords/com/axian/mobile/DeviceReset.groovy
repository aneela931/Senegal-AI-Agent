package com.axian.mobile

import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

/**
 * Cold-start the app under test on a USB device before every local case.
 * pm clear wipes login state left by a previous run (home/dashboard). Appium
 * autoGrantPermissions does not run again mid-suite, so runtime permissions
 * are re-granted here. Cloud runs are left alone.
 */
class DeviceReset {

	static final List RUNTIME_PERMS = [
		'android.permission.POST_NOTIFICATIONS',
		'android.permission.CAMERA',
		'android.permission.RECORD_AUDIO',
		'android.permission.ACCESS_FINE_LOCATION',
		'android.permission.ACCESS_COARSE_LOCATION',
		'android.permission.READ_PHONE_STATE',
		'android.permission.READ_CONTACTS',
		'android.permission.READ_EXTERNAL_STORAGE',
		'android.permission.WRITE_EXTERNAL_STORAGE',
		'android.permission.READ_MEDIA_IMAGES',
		'android.permission.READ_MEDIA_VIDEO',
		'android.permission.READ_MEDIA_AUDIO'
	]

	static void clearLocalAppData() {
		if (!isLocal()) {
			return
		}
		String pkg = packageName()
		if (!pkg) {
			KeywordUtil.logInfo('DeviceReset skipped: APP_PACKAGE empty')
			return
		}
		String adb = findAdb()
		if (!adb) {
			KeywordUtil.logInfo('DeviceReset skipped: adb not found')
			return
		}
		shell(adb, 'shell', 'am', 'force-stop', pkg)
		String clearOut = shell(adb, 'shell', 'pm', 'clear', pkg)
		RUNTIME_PERMS.each { String perm ->
			shell(adb, 'shell', 'pm', 'grant', pkg, perm)
		}
		KeywordUtil.logInfo("DeviceReset cleared package=${pkg} result=${clearOut.trim()}")
	}

	/** Device tap. Appium tap-at-centre misses the first PIN circle; adb tap does not. */
	static void tap(int x, int y) {
		String adb = findAdb()
		if (!adb) {
			return
		}
		shell(adb, 'shell', 'input', 'tap', String.valueOf(x), String.valueOf(y))
	}

	/** Types into the focused field via the IME. Appium setText on this Flutter PIN widget is a no-op. */
	static void type(String text) {
		String adb = findAdb()
		if (!adb || text == null) {
			return
		}
		shell(adb, 'shell', 'input', 'text', text)
	}

	private static boolean isLocal() {
		try {
			return (GlobalVariable.RUN_TARGET ?: '').toString() == 'local'
		} catch (MissingPropertyException ignored) {
			return true
		}
	}

	private static String packageName() {
		try {
			Object value = GlobalVariable.APP_PACKAGE
			return value == null ? '' : value.toString().trim()
		} catch (MissingPropertyException ignored) {
			return ''
		}
	}

	private static String findAdb() {
		List candidates = []
		String androidHome = System.getenv('ANDROID_HOME')
		String sdkRoot = System.getenv('ANDROID_SDK_ROOT')
		if (androidHome) {
			candidates.add(new File(androidHome, 'platform-tools/adb.exe').absolutePath)
			candidates.add(new File(androidHome, 'platform-tools/adb').absolutePath)
		}
		if (sdkRoot) {
			candidates.add(new File(sdkRoot, 'platform-tools/adb.exe').absolutePath)
		}
		candidates.add('C:\\sdk\\platform-tools\\adb.exe')
		candidates.add('adb')
		for (String path : candidates) {
			if (!path) {
				continue
			}
			if (path == 'adb' || new File(path).isFile()) {
				return path
			}
		}
		return null
	}

	private static String shell(String... args) {
		try {
			ProcessBuilder pb = new ProcessBuilder(args)
			pb.redirectErrorStream(true)
			Process p = pb.start()
			String out = p.inputStream.getText('UTF-8')
			p.waitFor()
			return out ?: ''
		} catch (Exception e) {
			KeywordUtil.logInfo("DeviceReset adb: ${e.message}")
			return ''
		}
	}
}
