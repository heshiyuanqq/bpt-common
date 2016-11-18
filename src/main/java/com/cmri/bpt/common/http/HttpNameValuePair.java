package com.cmri.bpt.common.http;

import org.apache.http.message.BasicNameValuePair;

public class HttpNameValuePair extends BasicNameValuePair {
	private static final long serialVersionUID = 3084857821712288283L;

	public HttpNameValuePair(String name, Object value) {
		super(name, value == null ? null : value.toString());
	}

	private HttpNameValuePair(String name, String value) {
		super(name, value);
	}

	public static HttpNameValuePair newOne(String name, Object value) {
		return new HttpNameValuePair(name, value);
	}
}
