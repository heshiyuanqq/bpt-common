package com.cmri.bpt.common.sms;

public interface SmsService {

	/**
	 * 发送普通短信（SMS = Short Messaging Service）
	 * 
	 * @param message
	 *            短信内容
	 * @return 发送返回的错误码
	 */
	public SmsErrorCode sendSMS(SmsMessage message);

	/**
	 * 查询普通短信余量
	 * 
	 * @return 剩余短信量,如果出现异常返回Integer.MIN_VALUE
	 */
	public int getSMSRemain();
}
