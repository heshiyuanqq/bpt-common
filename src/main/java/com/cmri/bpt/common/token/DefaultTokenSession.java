//package com.cmri.bpt.common.token;
//
//import java.util.Date;
//
//import com.cmri.bpt.common.json.JsonDateTimeDeserializer;
//import com.cmri.bpt.common.json.JsonDateTimeSerializer;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//
//public class DefaultTokenSession implements TokenSession {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -8704828563827792439L;
//
//	private Integer id;
//
//	private Integer appId;
//
//	private Integer userId;
//
//	private Integer sysId;
//
//	private String token;
//
//	private String lastIp;
//
//	private Double lastLng;
//
//	private Double lastLat;
//
//	private String lastAddress;
//
//	@JsonSerialize(using = JsonDateTimeSerializer.class)
//	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
//	private Date lastAccessTime = new Date();
//
//	private String xppId;
//
//	@Override
//	public Integer getAppId() {
//		return appId;
//	}
//
//	@Override
//	public void setAppId(Integer appId) {
//		this.appId = appId;
//	}
//
//	@Override
//	public Integer getUserId() {
//		return userId;
//	}
//
//	@Override
//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}
//
//	@Override
//	public String getToken() {
//		return token;
//	}
//
//	@Override
//	public void setToken(String token) {
//		this.token = token;
//	}
//
//	@Override
//	public String getLastIp() {
//		return lastIp;
//	}
//
//	@Override
//	public void setLastIp(String lastIp) {
//		this.lastIp = lastIp;
//	}
//
//	@Override
//	public Double getLastLng() {
//		return lastLng;
//	}
//
//	@Override
//	public void setLastLng(Double lastLng) {
//		this.lastLng = lastLng;
//	}
//
//	@Override
//	public Double getLastLat() {
//		return lastLat;
//	}
//
//	@Override
//	public void setLastLat(Double lastLat) {
//		this.lastLat = lastLat;
//	}
//
//	@Override
//	public String getLastAddress() {
//		return lastAddress;
//	}
//
//	@Override
//	public void setLastAddress(String lastAddress) {
//		this.lastAddress = lastAddress;
//	}
//
//	@Override
//	public Date getLastAccessTime() {
//		return lastAccessTime;
//	}
//
//	@Override
//	public void setLastAccessTime(Date lastAccessTime) {
//		this.lastAccessTime = lastAccessTime;
//	}
//
//	@Override
//	public Integer getSysId() {
//		return sysId;
//	}
//
//	@Override
//	public void setSysId(Integer sysId) {
//		this.sysId = sysId;
//	}
//
//	@Override
//	public String getXppId() {
//
//		return xppId;
//	}
//
//	@Override
//	public void setXppId(String xppId) {
//		this.xppId = xppId;
//
//	}
//
//	@Override
//	public Integer getId() {
//
//		return id;
//	}
//
//	@Override
//	public void setId(Integer id) {
//		this.id = id;
//
//	}
//
//	@Override
//	public Boolean isOnline() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setOnline(Boolean online) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
