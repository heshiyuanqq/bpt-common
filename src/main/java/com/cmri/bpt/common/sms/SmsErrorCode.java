package com.cmri.bpt.common.sms;

/**
 * 短信错误码
 * 
 * @author zhangjunjun
 * 
 */
public enum SmsErrorCode {

	/**
	 * Http请求异常
	 */
	HTTP_ERROR("0"),

	/**
	 * 发送正常
	 */
	OK("1"),

	/**
	 * 没有该用户账户
	 */
	NO_ACCOUNT("-1"),

	/**
	 * 密钥不正确（不是用户密码）
	 */
	KEY_INCORRECT("-2"),

	/**
	 * 短信数量不足
	 */
	NOT_ENOUGH("-3"),

	/**
	 * 该用户被禁用
	 */
	FORBBIDDEN_ACCOUNT("-11"),

	/**
	 * 短信内容出现非法字符
	 */
	FORBBIDDEN_CONTENT("-14"),

	/**
	 * 手机号码为空
	 */
	BLANK_NUMBER("-41"),

	/**
	 * 短信内容为空
	 */
	BLANK_CONTENT("-42");

	private String code;

	public String getValue() {
		return code;
	}

	SmsErrorCode(String code) {
		this.code = code;
	}

}
