package com.cmri.bpt.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.cmri.bpt.common.util.StrUtil;

@SuppressWarnings("deprecation")
public class SimpleHttpClient<TResult> {
	ResponseHandler<TResult> responseHandler = null;
	HttpRequestInterceptor httpRequestInterceptor;

	private HttpClient httpClient;
	private String httpBaseUrl = "";
	//
	private static final int CONNECTION_TIMEOUT_MS = 60000;
	private static final int SOCKET_TIMEOUT_MS = 60000;
	private static final int SOCKET_BUFFER_SIZE = 4096;

	@SuppressWarnings("deprecation")
	private void initHttpClient() {
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT_MS);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT_MS);
		HttpConnectionParams.setSocketBufferSize(httpParams, SOCKET_BUFFER_SIZE);
		HttpClientParams.setRedirecting(httpParams, false);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		//
		httpClient = new DefaultHttpClient(httpParams);
	}

	public SimpleHttpClient(String scheme, String host, int port, String path,
			ResponseHandler<TResult> responseHandler) {
		try {
			if (!StrUtil.hasText(scheme)) {
				scheme = "http";
			}
			if (!StrUtil.hasText(host)) {
				host = "localhost";
			}
			if (port <= 0) {
				port = 80;
			}
			if (!StrUtil.hasText(path)) {
				path = "";
			}
			@SuppressWarnings("deprecation")
			String httpBaseUrl = URIUtils.createURI(scheme, host, port, path, null, null).toString();
			System.out.println(httpBaseUrl);
			//
			this.initHttpClient();
			//
			this.httpBaseUrl = httpBaseUrl;
			this.responseHandler = responseHandler;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public SimpleHttpClient(String host, int port, String path, ResponseHandler<TResult> responseHandler) {
		this(null, host, port, path, responseHandler);
	}

	public SimpleHttpClient(String host, String path, ResponseHandler<TResult> responseHandler) {
		this(null, host, -1, path, responseHandler);
	}

	public SimpleHttpClient(String httpBaseUrl, ResponseHandler<TResult> responseHandler) {
		this.initHttpClient();
		//
		this.httpBaseUrl = httpBaseUrl;
		this.responseHandler = responseHandler;
	}

	public SimpleHttpClient(ResponseHandler<TResult> responseHandler) {
		this("", responseHandler);
	}

	public SimpleHttpClient() {
		this("", null);
	}

	// String httpPath
	public static String makeUrlString(String httpPath, String urlParamString) {
		if (httpPath.indexOf("?") == -1) {
			httpPath += "?";
		} else if (!httpPath.endsWith("?")) {
			httpPath += "&";
		}
		return httpPath + urlParamString;
	}

	@SuppressWarnings("deprecation")
	private HttpUriRequest createRequest(String httpPath, List<HttpNameValuePair> params, String httpMethod)
			throws HttpException {
		URI uri = null;
		HttpUriRequest request = null;
		if (httpPath == null) {
			httpPath = "";
		}
		if (!httpPath.startsWith("http")) {
			httpPath = this.httpBaseUrl + httpPath;
		}
		if (params == null) {
			params = new ArrayList<HttpNameValuePair>();
		}
		try {
			if (HttpGet.METHOD_NAME.equalsIgnoreCase(httpMethod)) {
				@SuppressWarnings("deprecation")
				String urlParamString = URLEncodedUtils.format(params, HTTP.UTF_8);
				String httpUrl = makeUrlString(httpPath, urlParamString);
				uri = new URI(httpUrl);
				request = new HttpGet(uri);
			} else if (HttpPost.METHOD_NAME.equalsIgnoreCase(httpMethod)) {
				uri = new URI(httpPath);
				HttpPost post = new HttpPost(uri);
				UrlEncodedFormEntity entity = null;
				try {
					
					entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				post.setEntity(entity);
				request = post;
			} else if (HttpPut.METHOD_NAME.equalsIgnoreCase(httpMethod)) {
				uri = new URI(httpPath);
				HttpPut put = new HttpPut(uri);
				UrlEncodedFormEntity entity = null;
				try {
					entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				put.setEntity(entity);
				request = put;
			} else if (HttpDelete.METHOD_NAME.equalsIgnoreCase(httpMethod)) {
				String urlParamString = URLEncodedUtils.format(params, HTTP.UTF_8);
				String httpUrl = makeUrlString(httpPath, urlParamString);
				uri = new URI(httpUrl);
				request = new HttpDelete(uri);
			} else {
				// default to GET
				String urlParamString = URLEncodedUtils.format(params, HTTP.UTF_8);
				String httpUrl = makeUrlString(httpPath, urlParamString);
				uri = new URI(httpUrl);
				request = new HttpGet(uri);
			}
		} catch (URISyntaxException e) {
			throw new HttpException("Invalid URI:" + uri);
		}
		return request;
	}

	public void setHttpRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
		this.httpRequestInterceptor = httpRequestInterceptor;
	}

	private TResult sendRequest(HttpUriRequest request) throws HttpException {
		TResult result = null;
		try {
			if (this.httpRequestInterceptor != null) {
				this.httpRequestInterceptor.beforeRequest(request);
			}
			result = httpClient.execute(request, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			request.abort();
			throw new HttpException("Execute http " + request.getMethod() + " request fail [" + e.getMessage() + "] !");
		} catch (IOException e) {
			e.printStackTrace();
			request.abort();
			throw new HttpException("Execute http " + request.getMethod() + " request fail [" + e.getMessage() + "] !");
		}
		return result;
	}

	public TResult doRequest(String httpPath, List<HttpNameValuePair> params, String httpMethod) throws HttpException {
		HttpUriRequest request = createRequest(httpPath, params, httpMethod);
		return sendRequest(request);
	}

	public TResult doGetRequest(String httpPath, List<HttpNameValuePair> params) throws HttpException {
		return doRequest(httpPath, params, HttpGet.METHOD_NAME);
	}

	public TResult doPostRequest(String httpPath, List<HttpNameValuePair> params) throws HttpException {
		return doRequest(httpPath, params, HttpPost.METHOD_NAME);
	}

	public TResult doPutRequest(String httpPath, List<HttpNameValuePair> params) throws HttpException {
		return doRequest(httpPath, params, HttpPut.METHOD_NAME);
	}

	public TResult doDeleteRequest(String httpPath, List<HttpNameValuePair> params) throws HttpException {
		return doRequest(httpPath, params, HttpDelete.METHOD_NAME);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		httpClient.getConnectionManager().shutdown();
		super.finalize();
	}
}
