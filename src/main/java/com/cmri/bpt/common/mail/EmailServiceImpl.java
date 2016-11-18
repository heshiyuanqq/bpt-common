package com.cmri.bpt.common.mail;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EmailServiceImpl implements EmailService {

	private static final Log logger = LogFactory.getLog(EmailServiceImpl.class);

	private JavaMailSenderImpl mailSender;

	private Configuration freemarkerConfig;

	public boolean sendEmail(SimpleMailMessage message, String templateName, Map<String, Object> model, File file) {
		Assert.notNull(mailSender, "mailSender must not be null!");

		if (message.getFrom() == null || message.getTo() == null) {
			return true;
		}

		boolean isSendSuccess = true;

		MimeMessage mm = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mm, true);
			String from = message.getFrom();
			helper.setFrom(from == null ? mailSender.getUsername() : from);

			helper.setTo(message.getTo());
			helper.setSubject(message.getSubject());

			String main = message.getText();
			if (templateName != null) {
				Template template = freemarkerConfig.getTemplate(templateName);
				main = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			}

			helper.setText(main);

			if (file != null) {
				FileSystemResource fileResource = new FileSystemResource(file);
				helper.addAttachment(fileResource.getFilename(), fileResource);
			}
			mailSender.send(mm);
		} catch (MailException e) {
			isSendSuccess = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			isSendSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSendSuccess = false;
			e.printStackTrace();
		} catch (TemplateException e) {
			isSendSuccess = false;
			e.printStackTrace();
		}

		logger.info("send email to " + message.getTo() + " success!");
		return isSendSuccess;
	}

	public boolean sendEmail(SimpleMailMessage message) {
		return sendEmail(message, null, null, null);
	}

	public boolean sendEmail(SimpleMailMessage message, File file) {
		return sendEmail(message, null, null, file);
	}

	public boolean sendEmail(SimpleMailMessage message, Map<String, Object> model) {
		return sendEmail(message, null, model, null);
	}

	public boolean sendEmail(SimpleMailMessage message, String templateName, Map<String, Object> model) {
		return sendEmail(message, templateName, model, null);
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setFreemarkerConfig(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

}
