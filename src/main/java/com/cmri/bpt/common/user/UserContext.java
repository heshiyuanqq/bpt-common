package com.cmri.bpt.common.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.cmri.bpt.common.json.JsonDateTimeDeserializer;
import com.cmri.bpt.common.json.JsonDateTimeSerializer;
import com.cmri.bpt.common.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 用户的上下文信息
 * 
 * @author koqiui
 * 
 */
public class UserContext implements Serializable {
	private static final long serialVersionUID = -5716285466191930366L;
	private Integer userId;
	private String userName;
	private String phoneNumber;
	private String gender;

	@JsonIgnore
	private HttpSession httpSession;
	private String clientIp;

	private Integer appId;
	private String token;
	// 衍生的token
	private String derivedToken;
	//
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	private Date ts = new Date();

	// 是否系统用户
	public boolean isSystemUser() {
		return userId != null && userId.intValue() > 0;
	}

	// 是否已认证
	public boolean isAuthenticated() {
		// 已经设置了系统用户id或第三方用户id
		return isSystemUser();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = StrUtil.filterEmojiChars(userName, ".");
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	// TODO 待废弃
	public void setHttpSession(HttpSession httpSession) {
		// this.httpSession = httpSession;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDerivedToken() {
		return derivedToken;
	}

	public void setDerivedToken(String derivedToken) {
		this.derivedToken = derivedToken;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public void clear() {
		this.userId = null;
		this.userName = null;
		this.phoneNumber = null;
		this.gender = null;

		this.httpSession = null;
		this.clientIp = null;

		this.appId = null;
		this.token = null;
		this.derivedToken = null;
		this.ts = new Date();
	}
}
