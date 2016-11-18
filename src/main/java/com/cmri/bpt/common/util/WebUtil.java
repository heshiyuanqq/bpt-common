package com.cmri.bpt.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.FileCopyUtils;

import com.cmri.bpt.common.helper.FileHelper;
import com.cmri.bpt.common.http.HttpBodyMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class WebUtil {

	private static MappingJackson2HttpMessageConverter jsonConverter = null;
	private static ObjectMapper objectMapper = null;

	static {
		jsonConverter = new MappingJackson2HttpMessageConverter();
		objectMapper = new com.cmri.bpt.common.json.JacksonObjectMapper();
		// jsonConverter.setObjectMapper(objectMapper);
		// jsonConverter.setObjectMapper(objectMapper);
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
	}

	private WebUtil() {
		//
	}

	private static String DEFAULT_ENCODING = "utf-8";

	private static String CONTENT_TYPE_JSON = "application/json";
	private static String CONTENT_TYPE_HTML = "text/html";
	private static String CONTENT_TYPE_JAVASCRIPT = "text/javascript";

	public static void markAsJson(HttpServletResponse response) throws IOException {
		response.setContentType(CONTENT_TYPE_JSON);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
	}

	public static void responseAsJson(HttpServletResponse response, Object jsonObject) throws IOException {
		responseAsJson(response, jsonObject, null);
	}

	public static void responseAsJson(HttpServletResponse response, Object jsonObject, String encoding)
			throws IOException {
		if (!StrUtil.hasText(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		response.setContentType(CONTENT_TYPE_JSON);
		response.setCharacterEncoding(encoding);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		PrintWriter writer = response.getWriter();
		writer.write(JsonUtil.toJson(jsonObject));
		writer.flush();
	}

	public static void markAsHtml(HttpServletResponse response) throws IOException {
		response.setContentType(CONTENT_TYPE_HTML);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
	}

	public static void responseAsHtml(HttpServletResponse response, String htmlContent) throws IOException {
		responseAsHtml(response, htmlContent, null);
	}

	public static void responseAsHtml(HttpServletResponse response, String htmlContent, String encoding)
			throws IOException {
		if (!StrUtil.hasText(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		response.setContentType(CONTENT_TYPE_HTML);
		response.setCharacterEncoding(encoding);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		PrintWriter writer = response.getWriter();
		writer.write(htmlContent);
		writer.flush();
	}

	public static void markAsJavascript(HttpServletResponse response) throws IOException {
		response.setContentType(CONTENT_TYPE_JAVASCRIPT);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
	}

	public static void responseAsJavascript(HttpServletResponse response, String jsContent) throws IOException {
		responseAsJavascript(response, jsContent, null);
	}

	public static void responseAsJavascript(HttpServletResponse response, String jsContent, String encoding)
			throws IOException {
		if (!StrUtil.hasText(encoding)) {
			encoding = DEFAULT_ENCODING;
		}
		response.setContentType(CONTENT_TYPE_JAVASCRIPT);
		response.setCharacterEncoding(encoding);
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		PrintWriter writer = response.getWriter();
		writer.write(jsContent);
		writer.flush();
	}

	public static String getContextPrefix(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme()).append("://");
		sb.append(request.getServerName());
		int port = request.getServerPort();
		if (port != 80) {
			sb.append(":").append(port);
		}
		return sb.toString();
	}

	public static String requestBodyAsString(HttpServletRequest request) {
		try {
			BufferedReader reader = request.getReader();
			StringBuffer sb = new StringBuffer();
			String lineStr = null;
			while ((lineStr = reader.readLine()) != null) {
				sb.append(lineStr);
				sb.append(FileHelper.FILE_SEPERATOR);
			}
			int length = sb.length();
			if (length > 0) {
				sb.deleteCharAt(length - 1);
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> Object convertToType(HttpServletRequest request, Class<T> javaType) throws IOException {
		HttpBodyMessage httpBodyMessage = new HttpBodyMessage(request);
		return jsonConverter.read(javaType, httpBodyMessage);
	}

	public static <T> Object convertToGenericType(HttpServletRequest request, Class<?> genericType, Class<?> javaType)
			throws JsonParseException, JsonMappingException, IOException {
		HttpBodyMessage httpBodyMessage = new HttpBodyMessage(request);

		InputStream is = httpBodyMessage.getBody();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FileCopyUtils.copy(is, bos);
		String data = new String(bos.toByteArray());

		JavaType type = objectMapper.getTypeFactory().constructParametricType(genericType, javaType);

		return objectMapper.readValue(data, type);
	}

	//
	private static Pattern PatternStaticResource = Pattern
			.compile(".+(\\.js|\\.css|\\.png|\\.jpg|\\.gif|\\.bmp|\\.ico|\\.swf)$");

	public static boolean isStaticResourceURI(String uri) {
		return PatternStaticResource.matcher(uri).matches();
	}

	/**
	 * @Definition: 根据 sessionKey 和 session对象的类型获得 session对象
	 * @author: TangWenWu
	 * @Created date: 2015-5-5
	 * @param session
	 * @param sessionKey
	 *            sessionKey 必填，否则返回空
	 * @param type
	 *            session对象的类型 必填，否则返回空
	 * @return
	 */
	public static <T> T getSessionObject(HttpSession session, String sessionKey, Class<T> type) {
		Object obj = session.getAttribute(sessionKey);
		if (null != obj) {
			String jsonStr = obj.toString();
			// System.out.println("sessionKey==="+sessionKey+"-----Class<T>
			// type---"+type.getName()+"---value===="+jsonStr);
			return JsonUtil.fromJson(jsonStr, type);
		}
		return null;
	}

	/**
	 * @Definition: 根据 sessionKey 和 session对象,把session对象 转换为JSON对象
	 * @author: TangWenWu
	 * @Created date: 2015-5-5
	 * @param session
	 * @param sessionKey
	 *            sessionKey 必填
	 * @param obj
	 *            session对象的类型 必填
	 * @return
	 */
	public static void setSessionAsJson(HttpSession session, String sessionKey, Object obj) {
		String jsonObj = JsonUtil.toJson(obj);
		if (session != null) {
			session.setAttribute(sessionKey, jsonObj);
		}
	}
}
