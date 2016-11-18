package com.cmri.bpt.common.token;

import java.io.Serializable;
import java.util.Date;

public interface TokenSession extends Serializable {

	public static final String TOKEN_PARAM_NAME = "token";
	
	Integer getId();
	
	void setId(Integer id);
	
	String getXppId();
	
	void setXppId(String xppId);

	Integer getAppId();

	void setAppId(Integer appId);

	Integer getUserId();

	void setUserId(Integer userId);

	Integer getSysId();

	void setSysId(Integer sysId);

	String getToken();

	void setToken(String token);
	
	Boolean isOnline();
	
	void setOnline(Boolean online);

	String getLastIp();

	void setLastIp(String lastIp);

	Double getLastLng();

	void setLastLng(Double lastLng);

	Double getLastLat();

	void setLastLat(Double lastLat);

	String getLastAddress();

	void setLastAddress(String lastAddress);

	Date getLastAccessTime();

	void setLastAccessTime(Date lastAccessTime);
	
	String getPhoneType();
	
	void setPhoneType(String phoneType);
	
	String getPhoneNumber();
	
	void setPhoneNumber(String phoneNumber);
	
}