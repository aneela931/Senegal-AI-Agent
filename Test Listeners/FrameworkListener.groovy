import com.axian.mobile.AllureSupport
import com.axian.mobile.ApkResolver
import com.axian.mobile.EnvConfig
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.util.KeywordUtil

/**
 * Framework bootstrap plus Allure result files. Does not change CloudApp launch.
 */
class FrameworkListener {

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		EnvConfig.load()
		ApkResolver.applyToProfile()
		AllureSupport.beforeSuite(testSuiteContext)
	}

	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		try {
			EnvConfig.load()
			ApkResolver.applyToProfile()
		} catch (Exception e) {
			KeywordUtil.logInfo("Framework before-case skipped: ${e.message}")
		}
		AllureSupport.beforeCase(testCaseContext)
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		AllureSupport.afterCase(testCaseContext)
	}

	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		AllureSupport.afterSuite(testSuiteContext)
	}
}
