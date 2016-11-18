package com.cmri.bpt.common.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.cmri.bpt.common.annotation.Desc;
import com.cmri.bpt.common.annotation.Param;
import com.cmri.bpt.common.annotation.Return;
import com.cmri.bpt.common.base.IOMode;
import com.cmri.bpt.common.base.OrdinalMap;
import com.cmri.bpt.common.base.Result;
import com.cmri.bpt.common.base.Result.Type;
import com.cmri.bpt.common.exception.AuthenticationException;
import com.cmri.bpt.common.exception.AuthorizationException;
import com.cmri.bpt.common.exception.BusinessException;
import com.cmri.bpt.common.http.HttpMethod;
import com.cmri.bpt.common.json.JacksonObjectMapper;
import com.cmri.bpt.common.util.ExceptionUtil;
import com.cmri.bpt.common.util.StrUtil;
import com.cmri.bpt.common.util.TypeUtil;
import com.cmri.bpt.common.util.WebUtil;

public class RestProxy {

	private final Logger logger = Logger.getLogger("rest." + this.getClass());
	//
	public static final String DefaultRestRootPath = "/rest";

	public static final String[] DefaultRestorPackagesToScan = new String[] { "com.cmri.bpt" };
	//
	private static final RestProxy Default;

	static {
		Default = new RestProxy();
	}

	public static RestProxy getDefault() {
		return Default;
	}

	//
	private String rootPath;
	private RestorRegistry registry;

	public RestProxy() {
		this(DefaultRestRootPath, DefaultRestorPackagesToScan);
	}

	public RestProxy(String restRootPath, String... restorPackagesToScan) {
		this.rootPath = restRootPath;
		this.registry = new RestorRegistry(restorPackagesToScan);
	}

	public RestorRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(RestorRegistry registry) {
		this.registry = registry;
	}

	private String extractRestMappingPath(String fullUri) {
		// 需要考虑web contexPath存在的情况
		return fullUri.replaceFirst(".*?" + this.rootPath, "");
	}

	@SuppressWarnings("unchecked")
	private Result<Object> handleRequestInner(HttpServletRequest request, HttpMethod httpMethod,
			HttpServletResponse response) {
		// log request info
		logRequestInfo(request, httpMethod);

		Result<Object> result = Result.newOne();
		String fullUri = request.getRequestURI();
		String restUri = this.extractRestMappingPath(fullUri);

		List<RestorInfo> restorInfos = this.registry.getRestorInfos();

		for (RestorInfo restorInfo : restorInfos) {
			if (!restorInfo.mayHandle(restUri)) {
				continue;
			}
			RestMethodInfo restMethodInfo = restorInfo.getHandlerMethodInfo(httpMethod, restUri);
			if (restMethodInfo == null) {
				continue;
			}
			try {
				Map<String, Class<?>> paramTypeMap = restMethodInfo.getParamTypeMap();
				int paramCount = paramTypeMap.size();
				Object[] paramValues = new Object[paramCount];
				//
				List<String> paramNames = new ArrayList<String>();
				paramNames.addAll(paramTypeMap.keySet());

				for (int i = 0; i < paramCount; i++) {
					String paramName = paramNames.get(i);
					Class<?> paramType = paramTypeMap.get(paramName);
					Object paramValue = null;
					if (i == paramCount - 1 && !TypeUtil.isSimpleType(paramType)) {
						paramValue = WebUtil.convertToType(request, paramType);

					} else {
						String paramStr = request.getParameter(paramName);
						if (paramStr != null) {
							paramValue = TypeUtil.convertToType(paramStr, paramType);
						}
					}
					paramValues[i] = paramValue;
				}

				if (httpMethod != HttpMethod.GET) {
					if (!paramNames.contains("password")) {
						JacksonObjectMapper mapper = new JacksonObjectMapper();
						String json = mapper.writeValueAsString(paramValues);
						logger.debug(httpMethod.toString() + " | " + json);
					}
				}

				Object resultData = restMethodInfo.invoke(paramValues);

				if (resultData instanceof Result) {
					result = (Result<Object>) resultData;
				} else {
					result.data = resultData;
				}
				//
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (Exception ex) {
				//
				result.type = Type.warn;
				result.message = "服务器繁忙,请求处理失败!";

				if (ex instanceof InvocationTargetException) {
					InvocationTargetException exInvo = (InvocationTargetException) ex;
					Exception exx = (Exception) exInvo.getTargetException();

					if (exx instanceof AuthenticationException || exx instanceof AuthorizationException) {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						if (exx.getMessage() != null) {
							result.message = exx.getMessage();
						}
					} else {
						response.setStatus(HttpServletResponse.SC_OK);
					}

					if (exx instanceof BusinessException) {
						String errMsg = ExceptionUtil.extractMsg(exx);
						result.message = errMsg;
					}
				}
				// this.logger.warn(ex);
				this.logger.error(ex.getMessage(), ex);
			}

			logger.debug(result.toString());
			// logger.debug(JsonUtil.toJson(result.data));
			//
			return result;
		}
		//
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		//
		result.type = Type.warn;
		result.message = "无法处理您的请求";
		//
		return result;
	}

	public Result<Object> handleGetRequest(HttpServletRequest request, HttpServletResponse response) {
		return this.handleRequestInner(request, HttpMethod.GET, response);
	}

	public Result<Object> handlePostRequest(HttpServletRequest request, HttpServletResponse response) {
		return this.handleRequestInner(request, HttpMethod.POST, response);
	}

	public Result<Object> handlePutRequest(HttpServletRequest request, HttpServletResponse response) {
		return this.handleRequestInner(request, HttpMethod.PUT, response);
	}

	@SuppressWarnings("unchecked")
	private void logRequestInfo(HttpServletRequest request, HttpMethod m) {
		Map<String, String[]> params = request.getParameterMap();

		if (params == null) {
			params = new HashMap<String, String[]>();
		}

		StringBuilder sb = new StringBuilder();

		for (String key : params.keySet()) {
			if (key == "password")
				continue;

			String[] values = params.get(key);
			sb.append(String.format("%s=%s ", key, StringUtils.join(values)));
		}

		logger.debug(request.getRequestURI() + " | " + m.toString() + " | " + sb.toString());
	}

	// ------------------------------- for API only
	private boolean isCustomizedType(Class<?> hostType) {
		String typeName = hostType.getName();
		return typeName.startsWith("com.cmri.") ;
	}

	private String restorDetailFormat = "<a href='/nav/open/api/restor?name={restorName}'>{restorName2}</a>";

	private String customizedTypeFormat = "<a href='/nav/open/api/class?name={className}'>{className2}</a>";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, String>> getApiClassFields(Class<?> hostType) {
		List<Map<String, String>> fieldsList = new ArrayList<Map<String, String>>();
		if (hostType == Object.class) {
			return fieldsList;
		} else if (hostType.isEnum()) {
			Map<String, String> fieldInfo = new HashMap<String, String>();
			fieldInfo.put("-", hostType.getName());
			fieldsList.add(fieldInfo);
			Map<String, String> nameTexts = TypeUtil.enumAsMapList((Class<? extends Enum>) hostType);
			for (Map.Entry<String, String> nameText : nameTexts.entrySet()) {
				fieldInfo = new HashMap<String, String>();
				fieldInfo.put("name", nameText.getKey());
				fieldInfo.put("desc", nameText.getValue());
				fieldInfo.put("type", "枚举");
				fieldsList.add(fieldInfo);
			}
			return fieldsList;
		}
		Map<String, String> retMap = new OrdinalMap<String, String>();
		retMap.put("-", hostType.getName());
		fieldsList.add(retMap);
		//
		if (isCustomizedType(hostType)) {
			List<Map<String, String>> superFields = getApiClassFields(hostType.getSuperclass());
			if (superFields.size() > 0) {
				fieldsList.addAll(superFields);
			}
			Field[] fields = hostType.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				//
				Deprecated deprecated = field.getAnnotation(Deprecated.class);
				if (deprecated != null) {
					continue;
				}
				//
				JsonIgnore jsonIgnore = field.getAnnotation(JsonIgnore.class);
				if (jsonIgnore != null) {
					continue;
				}
				//
				Desc fieldDesc = field.getAnnotation(Desc.class);
				if (fieldDesc != null && fieldDesc.ignore()) {
					continue;
				}
				JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
				retMap = new OrdinalMap<String, String>();
				String fieldName = field.getName();
				if (jsonProperty != null) {
					fieldName = jsonProperty.value();
				}
				retMap.put("name", fieldName);
				if (fieldDesc != null) {
					retMap.put("desc", fieldDesc.value());
					IOMode ioMode = fieldDesc.mode();
					if (ioMode != null) {
						String ioSign = "";
						if (ioMode.equals(IOMode.In)) {
							ioSign = "---- >>";
						} else if (ioMode.equals(IOMode.Out)) {
							ioSign = "<< ----";
						} else if (ioMode.equals(IOMode.None)) {
							ioSign = "N/A";
						} else {
							ioSign = "<< - >>";
						}
						retMap.put("mode", ioSign);
					}
				} else {
					retMap.put("desc", "");
				}
				Class<?> fieldType = field.getType();
				String typeHtml = fieldType.getSimpleName();
				if (isCustomizedType(fieldType)) {
					typeHtml = customizedTypeFormat.replace("{className}", fieldType.getName().replace('.', '-'));
					typeHtml = typeHtml.replace("{className2}", fieldType.getSimpleName());
				} else {
					if (fieldDesc != null) {
						Class<?>[] xTypes = fieldDesc.xType();
						if (xTypes != null && xTypes.length > 0) {
							List<String> typeNames = new ArrayList<String>();
							for (Class<?> xType : xTypes) {
								String tmpHtml = null;
								if (isCustomizedType(xType)) {
									tmpHtml = customizedTypeFormat.replace("{className}",
											xType.getName().replace('.', '-'));
									tmpHtml = tmpHtml.replace("{className2}", xType.getSimpleName());
								} else {
									tmpHtml = xType.getSimpleName();
								}
								typeNames.add(tmpHtml);
							}
							typeHtml = typeHtml + " &lt;" + StrUtil.join(typeNames, ", ") + ">";
						}
					}
				}
				retMap.put("type", typeHtml);
				//
				fieldsList.add(retMap);
			}
		}
		return fieldsList;
	}

	public List<Map<String, String>> getApiRestorList() {
		List<RestorInfo> restorInfos = this.registry.getRestorInfos();
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		for (RestorInfo restorInfo : restorInfos) {
			Map<String, String> tempItem = new OrdinalMap<String, String>();
			tempItem.put("rootPath", this.rootPath);
			String restorHtml = restorDetailFormat.replace("{restorName}", restorInfo.getName());
			restorHtml = restorHtml.replace("{restorName2}", restorInfo.getName());
			tempItem.put("name", restorHtml);
			tempItem.put("category", restorInfo.getCategory());
			tempItem.put("desc", restorInfo.getDesc());
			tempItem.put("mappingPath", this.rootPath + restorInfo.getMappingPath());
			//
			retList.add(tempItem);
		}
		//
		return retList;
	}

	public Map<String, Object> getApiRestor(String restorName) {
		RestorInfo restorInfo = this.registry.getRestorInfo(restorName);
		Map<String, Object> retItem = new OrdinalMap<String, Object>();
		retItem.put("rootPath", this.rootPath);
		retItem.put("name", restorInfo.getName());
		retItem.put("category", restorInfo.getCategory());
		retItem.put("desc", restorInfo.getDesc());
		retItem.put("mappingPath", this.rootPath + restorInfo.getMappingPath());
		//
		List<Map<String, Object>> methodList = null;
		List<RestMethodInfo> methodInfos = null;
		// GET
		methodList = new ArrayList<Map<String, Object>>();
		retItem.put("getMethods", methodList);
		methodInfos = restorInfo.getHttpGetMethods();
		//
		for (RestMethodInfo methodInfo : methodInfos) {
			Map<String, Object> tempItem = new OrdinalMap<String, Object>();
			methodList.add(tempItem);
			//
			tempItem.put("httpMethod", methodInfo.getHttpMethod());
			tempItem.put("mappingPatterns", StrUtil.join(methodInfo.getMappingPatterns(), ", "));
			tempItem.put("desc", methodInfo.getMappingInfo().desc());
			//
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			tempItem.put("params", paramList);
			//
			List<Param> params = methodInfo.getParamsInfo();
			for (Param param : params) {
				Map<String, Object> xItem = new OrdinalMap<String, Object>();
				paramList.add(xItem);
				//
				xItem.put("name", param.name());
				Class<?> paramType = param.type();
				String typeHtml = paramType.getSimpleName();
				if (isCustomizedType(paramType)) {
					typeHtml = customizedTypeFormat.replace("{className}", paramType.getName().replace('.', '-'));
					typeHtml = typeHtml.replace("{className2}", paramType.getSimpleName());
				}
				xItem.put("type", typeHtml);
				xItem.put("desc", param.desc());
			}
			//
			Return returnInfo = methodInfo.getReturnInfo();
			Map<String, Object> returnItem = new OrdinalMap<String, Object>();
			tempItem.put("return", returnItem);
			Class<?> returnType = returnInfo.type();
			String typeHtml = returnType.getSimpleName();
			if (isCustomizedType(returnType)) {
				typeHtml = customizedTypeFormat.replace("{className}", returnType.getName().replace('.', '-'));
				typeHtml = typeHtml.replace("{className2}", returnType.getSimpleName());
			}
			returnItem.put("type", typeHtml);
			Class<?>[] xTypes = returnInfo.xType();
			if (xTypes != null && xTypes.length > 0) {
				List<String> genericTypes = new ArrayList<String>();
				returnItem.put("genericTypes", genericTypes);
				//
				for (Class<?> xType : xTypes) {
					typeHtml = xType.getSimpleName();
					if (isCustomizedType(xType)) {
						typeHtml = customizedTypeFormat.replace("{className}", xType.getName().replace('.', '-'));
						typeHtml = typeHtml.replace("{className2}", xType.getSimpleName());
					}
					genericTypes.add(typeHtml);
				}
			}
			returnItem.put("desc", returnInfo.desc());
		}
		// POST
		methodList = new ArrayList<Map<String, Object>>();
		retItem.put("postMethods", methodList);
		methodInfos = restorInfo.getHttpPostMethods();
		//
		for (RestMethodInfo methodInfo : methodInfos) {
			Map<String, Object> tempItem = new OrdinalMap<String, Object>();
			methodList.add(tempItem);
			//
			tempItem.put("httpMethod", methodInfo.getHttpMethod());
			tempItem.put("mappingPatterns", StrUtil.join(methodInfo.getMappingPatterns(), ", "));
			tempItem.put("desc", methodInfo.getMappingInfo().desc());
			//
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			tempItem.put("params", paramList);
			//
			List<Param> params = methodInfo.getParamsInfo();
			for (Param param : params) {
				Map<String, Object> xItem = new OrdinalMap<String, Object>();
				paramList.add(xItem);
				//
				Class<?> paramType = param.type();
				xItem.put("name", TypeUtil.isSimpleType(paramType) ? param.name()
						: "[<span style='color:#4377E7;font-weight:bold;font-style:italic;'>RequestBody</span>]");
				String typeHtml = paramType.getSimpleName();
				if (isCustomizedType(paramType)) {
					typeHtml = customizedTypeFormat.replace("{className}", paramType.getName().replace('.', '-'));
					typeHtml = typeHtml.replace("{className2}", paramType.getSimpleName());
				} else if (!TypeUtil.isSimpleType(paramType)) {
					Class<?>[] xTypes = param.xType();
					if (xTypes != null && xTypes.length > 0) {
						List<String> genericTypes = new ArrayList<String>();
						xItem.put("genericTypes", genericTypes);
						//
						for (Class<?> xType : xTypes) {
							String tmpTypeHtml = xType.getSimpleName();
							if (isCustomizedType(xType)) {
								tmpTypeHtml = customizedTypeFormat.replace("{className}",
										xType.getName().replace('.', '-'));
								tmpTypeHtml = tmpTypeHtml.replace("{className2}", xType.getSimpleName());
							}
							genericTypes.add(tmpTypeHtml);
						}
					}
				}
				xItem.put("type", typeHtml);
				xItem.put("desc", param.desc());
			}
			//
			Return returnInfo = methodInfo.getReturnInfo();
			Map<String, Object> returnItem = new OrdinalMap<String, Object>();
			tempItem.put("return", returnItem);
			Class<?> returnType = returnInfo.type();
			String typeHtml = returnType.getSimpleName();
			if (isCustomizedType(returnType)) {
				typeHtml = customizedTypeFormat.replace("{className}", returnType.getName().replace('.', '-'));
				typeHtml = typeHtml.replace("{className2}", returnType.getSimpleName());
			}
			returnItem.put("type", typeHtml);
			Class<?>[] xTypes = returnInfo.xType();
			if (xTypes != null && xTypes.length > 0) {
				List<String> genericTypes = new ArrayList<String>();
				returnItem.put("genericTypes", genericTypes);
				//
				for (Class<?> xType : xTypes) {
					typeHtml = xType.getSimpleName();
					if (isCustomizedType(xType)) {
						typeHtml = customizedTypeFormat.replace("{className}", xType.getName().replace('.', '-'));
						typeHtml = typeHtml.replace("{className2}", xType.getSimpleName());
					}
					genericTypes.add(typeHtml);
				}
			}
			returnItem.put("desc", returnInfo.desc());
		}
		// PUT
		methodList = new ArrayList<Map<String, Object>>();
		retItem.put("putMethods", methodList);
		methodInfos = restorInfo.getHttpPutMethods();
		//
		for (RestMethodInfo methodInfo : methodInfos) {
			Map<String, Object> tempItem = new OrdinalMap<String, Object>();
			methodList.add(tempItem);
			//
			tempItem.put("httpMethod", methodInfo.getHttpMethod());
			tempItem.put("mappingPatterns", StrUtil.join(methodInfo.getMappingPatterns(), ", "));
			tempItem.put("desc", methodInfo.getMappingInfo().desc());
			//
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			tempItem.put("params", paramList);
			//
			List<Param> params = methodInfo.getParamsInfo();
			for (Param param : params) {
				Map<String, Object> xItem = new OrdinalMap<String, Object>();
				paramList.add(xItem);
				//
				Class<?> paramType = param.type();
				xItem.put("name", TypeUtil.isSimpleType(paramType) ? param.name()
						: "[<span style='color:#4377E7;font-weight:bold;font-style:italic;'>RequestBody</span>]");
				String typeHtml = paramType.getSimpleName();
				if (isCustomizedType(paramType)) {
					typeHtml = customizedTypeFormat.replace("{className}", paramType.getName().replace('.', '-'));
					typeHtml = typeHtml.replace("{className2}", paramType.getSimpleName());
				} else if (!TypeUtil.isSimpleType(paramType)) {
					Class<?>[] xTypes = param.xType();
					if (xTypes != null && xTypes.length > 0) {
						List<String> genericTypes = new ArrayList<String>();
						xItem.put("genericTypes", genericTypes);
						//
						for (Class<?> xType : xTypes) {
							String tmpTypeHtml = xType.getSimpleName();
							if (isCustomizedType(xType)) {
								tmpTypeHtml = customizedTypeFormat.replace("{className}",
										xType.getName().replace('.', '-'));
								tmpTypeHtml = tmpTypeHtml.replace("{className2}", xType.getSimpleName());
							}
							genericTypes.add(tmpTypeHtml);
						}
					}
				}
				xItem.put("type", typeHtml);
				xItem.put("desc", param.desc());
			}
			//
			Return returnInfo = methodInfo.getReturnInfo();
			Map<String, Object> returnItem = new OrdinalMap<String, Object>();
			tempItem.put("return", returnItem);
			Class<?> returnType = returnInfo.type();
			String typeHtml = returnType.getSimpleName();
			if (isCustomizedType(returnType)) {
				typeHtml = customizedTypeFormat.replace("{className}", returnType.getName().replace('.', '-'));
				typeHtml = typeHtml.replace("{className2}", returnType.getSimpleName());
			}
			returnItem.put("type", typeHtml);
			Class<?>[] xTypes = returnInfo.xType();
			if (xTypes != null && xTypes.length > 0) {
				List<String> genericTypes = new ArrayList<String>();
				returnItem.put("genericTypes", genericTypes);
				//
				for (Class<?> xType : xTypes) {
					typeHtml = xType.getSimpleName();
					if (isCustomizedType(xType)) {
						typeHtml = customizedTypeFormat.replace("{className}", xType.getName().replace('.', '-'));
						typeHtml = typeHtml.replace("{className2}", xType.getSimpleName());
					}
					genericTypes.add(typeHtml);
				}
			}
			returnItem.put("desc", returnInfo.desc());
		}

		return retItem;
	}
}