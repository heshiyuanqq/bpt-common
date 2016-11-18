package com.cmri.bpt.common.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cmri.bpt.common.http.HttpMethod;

import edu.emory.mathcs.backport.java.util.Collections;

public class RestorInfo implements Comparable<RestorInfo> {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getOwner() {
		return owner;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public RestMapping getMappingInfo() {
		return mappingInfo;
	}

	public void setMappingInfo(RestMapping mappingInfo) {
		this.mappingInfo = mappingInfo;
		//
		String[] patterns = this.mappingInfo.value();
		Arrays.sort(patterns);
		// 倒排续取最长
		this.mappingPath = patterns[patterns.length - 1];
	}

	public List<RestMethodInfo> getHttpGetMethods() {
		return httpGetMethods;
	}

	public void setHttpGetMethods(List<RestMethodInfo> httpGetMethods) {
		this.httpGetMethods.clear();
		//
		if (httpGetMethods != null) {
			this.httpGetMethods = httpGetMethods;
			Collections.sort(this.httpGetMethods);
			//
			for (RestMethodInfo restMethodInfo : this.httpGetMethods) {
				restMethodInfo.setOwner(this.owner);
			}
		}
	}

	public List<RestMethodInfo> getHttpPostMethods() {
		return httpPostMethods;
	}

	public void setHttpPostMethods(List<RestMethodInfo> httpPostMethods) {
		this.httpPostMethods.clear();
		//
		if (httpPostMethods != null) {
			this.httpPostMethods = httpPostMethods;
			Collections.sort(this.httpPostMethods);
			//
			for (RestMethodInfo restMethodInfo : this.httpPostMethods) {
				restMethodInfo.setOwner(this.owner);
			}
		}
	}

	public List<RestMethodInfo> getHttpPutMethods() {
		return httpPutMethods;
	}

	public void setHttpPutMethods(List<RestMethodInfo> httpPutMethods) {
		this.httpPutMethods.clear();
		//
		if (httpPutMethods != null) {
			this.httpPutMethods = httpPutMethods;
			Collections.sort(this.httpPutMethods);
			//
			for (RestMethodInfo restMethodInfo : this.httpPutMethods) {
				restMethodInfo.setOwner(this.owner);
			}
		}
	}

	public boolean mayHandle(String path) {
		return path.startsWith(this.mappingPath);
	}

	public RestMethodInfo getHandlerMethodInfo(HttpMethod method, String path) {
		if (!this.mayHandle(path)) {
			return null;
		}
		String methodPath = path.substring(this.mappingPath.length());
		List<RestMethodInfo> methodInfoList = null;
		if (HttpMethod.GET.equals(method)) {
			methodInfoList = this.httpGetMethods;
		} else if (HttpMethod.POST.equals(method)) {
			methodInfoList = this.httpPostMethods;
		} else if (HttpMethod.PUT.equals(method)) {
			methodInfoList = this.httpPutMethods;
		} else {
			throw new RuntimeException("不支持指定的 http请求方法 [" + method + "]");
		}
		//
		for (RestMethodInfo restMethodInfo : methodInfoList) {
			if (restMethodInfo.matches(method, methodPath)) {
				return restMethodInfo;
			}
		}
		//
		return null;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	private String name;
	private String category;
	private String desc;
	//
	private Object owner;
	private RestMapping mappingInfo;
	//
	private List<RestMethodInfo> httpGetMethods = new ArrayList<RestMethodInfo>();
	private List<RestMethodInfo> httpPostMethods = new ArrayList<RestMethodInfo>();
	private List<RestMethodInfo> httpPutMethods = new ArrayList<RestMethodInfo>();

	//
	private String mappingPath;

	@Override
	public int compareTo(RestorInfo another) {
		// 倒序支持
		return another.mappingPath.compareTo(this.mappingPath);
	}
}
