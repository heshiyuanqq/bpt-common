package com.cmri.bpt.common.http;

import org.apache.http.HttpRequest;

public interface HttpRequestInterceptor {
	void beforeRequest(HttpRequest request);
}
