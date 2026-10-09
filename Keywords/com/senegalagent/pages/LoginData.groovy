package com.senegalagent.pages

import com.axian.mobile.EnvConfig

/**
 * Reads staging inventory / testData including login OTP/PIN from the user-supplied run data.
 */
class LoginData {

	static String msisdnForRole(String role) {
		List users = (List) EnvConfig.inventory().get('users')
		Object match = users.find { Map u -> u.get('role')?.toString() == role }
		if (match == null) {
			throw new IllegalStateException("No inventory user with role ${role}")
		}
		return ((Map) match).get('msisdn')?.toString()
	}

	static String agentMsisdn() {
		return msisdnForRole('agent')
	}

	static String nonAgentMsisdn() {
		return msisdnForRole('non_agent')
	}

	static String customerMsisdn() {
		return msisdnForRole('customer')
	}

	static String secondAgentMsisdn() {
		List numbers = (List) EnvConfig.inventory().get('msisdns')
		if (numbers != null && numbers.size() > 1) {
			return numbers[1].toString()
		}
		return agentMsisdn()
	}

	static String loginValue(String key) {
		Map testData = EnvConfig.testData()
		Map login = (Map) testData.get('login')
		if (login == null || !login.containsKey(key)) {
			throw new IllegalStateException("testData.login.${key} is missing")
		}
		return login.get(key).toString()
	}

	static String validMsisdnExample() {
		return loginValue('validMsisdnExample')
	}

	static String invalidMsisdnFormat() {
		return loginValue('invalidMsisdnFormat')
	}

	static String incorrectOtp() {
		return loginValue('incorrectOtp')
	}

	static String incorrectPin() {
		return loginValue('incorrectPin')
	}

	static String validOtp() {
		return loginValue('validOtp')
	}

	static String validPin() {
		return loginValue('validPin')
	}

	/** Second agent account used only when the primary login does not succeed. Both keys live in testData.login. */
	static boolean hasFallbackAccount() {
		Map login = (Map) EnvConfig.testData().get('login')
		return login != null && login.get('fallbackMsisdn') && login.get('fallbackPin')
	}

	static String fallbackMsisdn() {
		return loginValue('fallbackMsisdn')
	}

	static String fallbackPin() {
		return loginValue('fallbackPin')
	}
}
