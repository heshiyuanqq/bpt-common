package com.cmri.bpt.common.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cmri.bpt.common.annotation.Param;
import com.cmri.bpt.common.annotation.Return;
import com.cmri.bpt.common.base.OrdinalMap;
import com.cmri.bpt.common.base.UriMatcher;
import com.cmri.bpt.common.http.HttpMethod;

public class RestMethodInfo implements Comparable<RestMethodInfo> {
	public Object getOwner() {
		return owner;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public RestMapping getMappingInfo() {
		return mappingInfo;
	}

	public void setMappingInfo(RestMapping mappingInfo) {
		this.mappingInfo = mappingInfo;
		//
		String[] patterns = this.mappingInfo.value();
		Arrays.sort(patterns);
		// 倒排续
		int patternCount = patterns.length;
		this.mappingPatterns = new String[patternCount];
		for (int i = 0, j = patternCount - 1; i < patternCount; i++, j--) {
			this.mappingPatterns[j] = patterns[i];
		}
	}

	public void setOwnerHttpMethod(HttpMethod ownerHttpMethod) {
		this.ownerHttpMethod = ownerHttpMethod;
	}

	public Return getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(Return returnInfo) {
		this.returnInfo = returnInfo;
	}

	public List<Param> getParamsInfo() {
		return paramsInfo;
	}

	public void setParamsInfo(List<Param> paramsInfo) {
		this.paramsInfo = paramsInfo;
		// 缓存参数名称和类型
		paramTypeMap = new OrdinalMap<String, Class<?>>();
		for (Param param : paramsInfo) {
			paramTypeMap.put(param.name(), param.type());
		}
	}

	public boolean matches(HttpMethod method, String path) {
		if (!this.getHttpMethod().equals(method)) {
			return false;
		}
		//
		String[] patterns = this.mappingPatterns;
		for (String pattern : patterns) {
			if (UriMatcher.matches(pattern, path)) {
				return true;
			}
		}
		//
		return false;
	}

	public Map<String, Class<?>> getParamTypeMap() {
		return paramTypeMap;
	}

	public String[] getMappingPatterns() {
		return mappingPatterns;
	}

	public HttpMethod getHttpMethod() {
		HttpMethod thisMethod = this.mappingInfo.method();
		if (thisMethod == null) {
			thisMethod = this.ownerHttpMethod;
		}
		return thisMethod;
	}

	public Object invoke(Object... args)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return this.method.invoke(this.owner, args);
	}

	private Object owner;

	private Method method;

	//
	private RestMapping mappingInfo;

	private Return returnInfo;

	private List<Param> paramsInfo;

	//
	private Map<String, Class<?>> paramTypeMap = null;
	private String[] mappingPatterns;
	private HttpMethod ownerHttpMethod;

	@Override
	public int compareTo(RestMethodInfo another) {
		// 倒序支持
		return another.mappingPatterns[0].compareTo(this.mappingPatterns[0]);
	}
}
