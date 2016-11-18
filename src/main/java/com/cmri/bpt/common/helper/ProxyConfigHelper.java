package com.cmri.bpt.common.helper;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mysql.jdbc.StringUtils;

/**
 * ProxyConfigSetter <br/>
 * 设置http 和 socket 代理服务器 by Hu Changwei, 2013.05.27
 */
public final class ProxyConfigHelper {
	private static final Log logger = LogFactory.getLog(ProxyConfigHelper.class);

	//
	private ProxyConfigHelper() {
	}

	private final static String defaultConfigFile = "conf/proxy.properties";

	public static boolean config() {
		return config(null);
	}

	public static boolean config(String configFile) {
		try {
			if (StringUtils.isNullOrEmpty(configFile)) {
				configFile = defaultConfigFile;
			}
			Properties proxyConfig = new Properties();
			proxyConfig.load(ProxyConfigHelper.class.getClassLoader().getResourceAsStream(configFile));
			// http proxy
			boolean proxySet = Boolean.parseBoolean(proxyConfig.getProperty("proxySet", "false"));
			if (proxySet) {
				System.getProperties().put("proxySet", true);
				System.getProperties().put("http.proxyHost", proxyConfig.getProperty("http.proxyHost"));
				System.getProperties().put("http.proxyPort",
						Integer.parseInt(proxyConfig.getProperty("http.proxyPort")));
			}
			// socket proxy
			boolean socksProxySet = Boolean.parseBoolean(proxyConfig.getProperty("socksProxySet", "false"));
			if (socksProxySet) {
				System.getProperties().put("socksProxySet", true);
				System.getProperties().put("socksProxyHost", proxyConfig.getProperty("socksProxyHost"));
				System.getProperties().put("socksProxyPort",
						Integer.parseInt(proxyConfig.getProperty("socksProxyPort")));
			}
			logger.debug("ProxyConfigHelper : config finished.");
			return true;
		} catch (IOException e) {
			logger.error("ProxyConfigHelper : " + e.getMessage());
			return false;
		}
	}
}
