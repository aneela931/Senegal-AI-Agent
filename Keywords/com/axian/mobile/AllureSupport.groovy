package com.axian.mobile

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonOutput
import internal.GlobalVariable

/**
 * Writes Allure 2 result files under allure-results/ and optionally generates allure-report/.
 * Suite-local only. Does not use the reporter agent tree.
 */
class AllureSupport {

	private static long suiteStart
	private static String suiteName = 'unknown'
	private static final Map<String, Long> caseStart = [:]

	static File resultsDir() {
		return new File(RunConfiguration.getProjectDir(), 'allure-results')
	}

	static File reportDir() {
		return new File(RunConfiguration.getProjectDir(), 'allure-report')
	}

	static void beforeSuite(TestSuiteContext ctx) {
		suiteName = ctx?.getTestSuiteId() ?: 'unknown'
		suiteStart = System.currentTimeMillis()
		File dir = resultsDir()
		dir.mkdirs()
		writeEnvironment()
		writeCategories()
		KeywordUtil.logInfo("Allure results directory: ${dir.absolutePath}")
	}

	static void beforeCase(TestCaseContext ctx) {
		resultsDir().mkdirs()
		if (ctx?.getTestCaseId()) {
			caseStart[ctx.getTestCaseId()] = System.currentTimeMillis()
		}
	}

	static void afterCase(TestCaseContext ctx) {
		if (ctx == null) {
			return
		}
		String id = ctx.getTestCaseId() ?: 'unknown'
		long start = caseStart.remove(id) ?: System.currentTimeMillis()
		long stop = System.currentTimeMillis()
		String uuid = UUID.randomUUID().toString()
		Map result = [
			uuid      : uuid,
			historyId : id,
			name      : id.tokenize('/').last(),
			fullName  : id,
			status    : mapStatus(ctx.getTestCaseStatus()),
			stage     : 'finished',
			start     : start,
			stop      : stop,
			labels    : [
				[name: 'suite', value: suiteName],
				[name: 'framework', value: 'katalon'],
				[name: 'language', value: 'groovy'],
				[name: 'package', value: 'sn.free.agent.app']
			]
		]
		String message = ctx.getTestCaseStatus()
		if (message && mapStatus(ctx.getTestCaseStatus()) != 'passed') {
			result.statusDetails = [message: message.toString()]
		}
		File out = new File(resultsDir(), "${uuid}-result.json")
		out.text = JsonOutput.prettyPrint(JsonOutput.toJson(result))
	}

	static void afterSuite(TestSuiteContext ctx) {
		writeExecutor()
		generateHtml()
	}

	private static void writeEnvironment() {
		List<String> lines = []
		lines << "environment=${safeGlobal('ENVIRONMENT')}"
		lines << "runTarget=${safeGlobal('RUN_TARGET')}"
		lines << "appPackage=${safeGlobal('APP_PACKAGE')}"
		lines << "expectedVersion=${safeGlobal('EXPECTED_VERSION')}"
		lines << "expectedBuild=${safeGlobal('EXPECTED_BUILD')}"
		lines << "localApkPath=${safeGlobal('LOCAL_APK_PATH')}"
		lines << "deviceName=${safeGlobal('DEVICE_NAME')}"
		lines << "osVersion=${safeGlobal('OS_VERSION')}"
		new File(resultsDir(), 'environment.properties').text = lines.join('\n') + '\n'
	}

	private static void writeCategories() {
		List cats = [
			[
				name: 'Product defects',
				matchedStatuses: ['failed']
			],
			[
				name: 'Script or environment failures',
				matchedStatuses: ['broken']
			]
		]
		new File(resultsDir(), 'categories.json').text = JsonOutput.prettyPrint(JsonOutput.toJson(cats))
	}

	private static void writeExecutor() {
		Map executor = [
			name    : 'Katalon Studio',
			type    : 'katalon',
			buildName: suiteName,
			reportName: 'Senegal Agent App'
		]
		new File(resultsDir(), 'executor.json').text = JsonOutput.prettyPrint(JsonOutput.toJson(executor))
	}

	private static void generateHtml() {
		File results = resultsDir()
		File report = reportDir()
		try {
			ProcessBuilder pb = new ProcessBuilder('allure', 'generate', results.absolutePath, '-o', report.absolutePath, '--clean')
			pb.directory(new File(RunConfiguration.getProjectDir()))
			pb.redirectErrorStream(true)
			Process p = pb.start()
			String output = p.inputStream.getText('UTF-8')
			int code = p.waitFor()
			if (code == 0) {
				KeywordUtil.logInfo("Allure HTML written to ${report.absolutePath}")
			} else {
				KeywordUtil.markWarning("Allure generate exited ${code}. Results are in allure-results/. Output:\n${output}")
			}
		} catch (Exception e) {
			KeywordUtil.markWarning(
				"Allure CLI could not generate HTML (${e.message}). Raw results are in allure-results/. " +
				'Install Allure CLI and Java, then run: allure generate allure-results -o allure-report --clean'
			)
		}
	}

	private static String mapStatus(String katalonStatus) {
		String s = (katalonStatus ?: '').toUpperCase()
		if (s.contains('PASS')) {
			return 'passed'
		}
		if (s.contains('FAIL')) {
			return 'failed'
		}
		if (s.contains('SKIP') || s.contains('NOT_RUN')) {
			return 'skipped'
		}
		return 'broken'
	}

	private static String safeGlobal(String name) {
		try {
			Object value = GlobalVariable."${name}"
			return value == null ? '' : value.toString()
		} catch (MissingPropertyException ignored) {
			return ''
		}
	}
}
