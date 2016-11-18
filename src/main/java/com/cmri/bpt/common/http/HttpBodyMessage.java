package com.cmri.bpt.common.http;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

public class HttpBodyMessage implements HttpInputMessage {
	HttpServletRequest request;

	public HttpBodyMessage(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public HttpHeaders getHeaders() {
		return null;
	}

	@Override
	public InputStream getBody() throws IOException {
		return request.getInputStream();
	}

}
