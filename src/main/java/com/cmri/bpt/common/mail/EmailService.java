package com.cmri.bpt.common.mail;

import java.io.File;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

/**
 * Email发送服务
 * 
 * @author zhangjunjun
 * 
 */
public interface EmailService {

	/**
	 * 发送邮件
	 * 
	 * @param message
	 *            基本消息（发件人，收件人，主题等）
	 * @param templateName
	 *            模板名
	 * @param model
	 *            模板模型
	 * @param file
	 *            附件
	 * @return 是否发送成功
	 */
	public boolean sendEmail(SimpleMailMessage message, String templateName, Map<String, Object> model, File file);

	/**
	 * 发送邮件
	 * 
	 * @param message
	 *            基本消息（发件人，收件人，主题,内容等）
	 * @return 是否发送成功
	 */
	public boolean sendEmail(SimpleMailMessage message);

	/**
	 * 发送邮件
	 * 
	 * @param message
	 *            基本消息（发件人，收件人，主题,内容等）
	 * @param file
	 *            附件
	 * @return 是否发送成功
	 */
	public boolean sendEmail(SimpleMailMessage message, File file);

	/**
	 * 发送邮件
	 * 
	 * @param message
	 *            基本消息（发件人，收件人，主题等）
	 * @param model
	 *            模板模型（采用配置中的默认模板）
	 * @return
	 */
	public boolean sendEmail(SimpleMailMessage message, Map<String, Object> model);

	/**
	 * 发送邮件
	 * 
	 * @param message
	 *            基本消息（发件人，收件人，主题等）
	 * @param templateName
	 *            模板名
	 * @param model
	 *            模板模型
	 * @return
	 */
	public boolean sendEmail(SimpleMailMessage message, String templateName, Map<String, Object> model);

}
