package com.cmri.bpt.common.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.cmri.bpt.common.config.PropertyConfigurer;
import com.cmri.bpt.common.util.NetUtil;
import com.cmri.bpt.common.util.OSUtil;

/**
 * Web环境辅助类
 * 
 * @date 2014-08-23 modified by koqiui - 提供了多系统自适应支持
 */
public class WebEnvHelper {
	// properties配置文件名称列表（多个可用逗号分隔）
	private static final String confPropertyFileNames = "conf/configuration.properties";
	private static final PropertyConfigurer propertyConfigurer;
	// 消息配置
	private static final String messageFileNames = "i18n/information_zh_CN.properties,i18n/validation_zh_CN.properties";
	private static final PropertyConfigurer messageConfigurer;
	//
	// private static Boolean _isRunndingOnServer = null;
	private static Environment _env;

	static {
		propertyConfigurer = PropertyConfigurer.newInstance(confPropertyFileNames, "UTF-8");

		messageConfigurer = PropertyConfigurer.newInstance(messageFileNames, "UTF-8");

		if (NetUtil.hasHostIp(propertyConfigurer.get("yikuaixiu.com.ip"))) {
			System.out.println("正在product服务器上运行");
			_env = Environment.Product;
		} else if (NetUtil.hasHostIp(propertyConfigurer.get("test.yikuaixiu.com.ip"))) {
			System.out.println("正在test服务器上运行");
			_env = Environment.Test;

		} else if (NetUtil.hasHostIp(propertyConfigurer.get("visi.yikuaixiu.com.ip"))) {
			System.out.println("正在visi服务器上运行");
			_env = Environment.Visi;

		} else {
			System.out.println("正在dev服务器上运行");
			_env = Environment.Dev;
		}
		//
	}

	private static String key_cmsUploadBaseDir = "cms_upload_base_dir" + OSUtil.getDefaultSuffix();

	public static String getCmsUploadBaseDir() {
		return propertyConfigurer.get(key_cmsUploadBaseDir);
	}

	public static String getRootRealPath(HttpServlet servlet) {
		ServletContext context = servlet.getServletContext();
		return servlet != null ? context.getRealPath("") : null;
	}

	public static String getConfig(String key) {
		return propertyConfigurer.get(key);
	}

	// 判断是否在yikuaixiu.com上运行
	public static boolean isRunndingOnYkxServer() {
		return _env == Environment.Product || _env == Environment.Visi;
	}

	public static String getMessage(String msgKey) {
		return messageConfigurer.get(msgKey);
	}

	public static Environment getEnvironment() {
		return _env;
	}

	public enum Environment {
		Product, Test, Dev, Visi
	}
}
