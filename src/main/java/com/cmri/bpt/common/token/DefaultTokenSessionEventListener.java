package com.cmri.bpt.common.token;

import com.cmri.bpt.common.util.DateUtil;

public class DefaultTokenSessionEventListener implements TokenSessionEventListener {

	@Override
	public void onSessionCreated(TokenSession tokenSession) {
		System.out.println("Token 已创建 >> userId: " + tokenSession.getUserId() + ", appId: " + tokenSession.getAppId()
				+ ", token: " + tokenSession.getToken() + ", 最后访问时间："
				+ DateUtil.toStdDateTimeXStr(tokenSession.getLastAccessTime()));
	}

	@Override
	public void onSessionUpdated(TokenSession tokenSession) {
		System.out.println("Token 已更新 >> userId: " + tokenSession.getUserId() + ", appId: " + tokenSession.getAppId()
				+ ", token: " + tokenSession.getToken() + ", 最后访问时间："
				+ DateUtil.toStdDateTimeXStr(tokenSession.getLastAccessTime()));
	}

	@Override
	public void onSessionExpired(TokenSession tokenSession) {
		System.out.println("Token 已过期 >> userId: " + tokenSession.getUserId() + ", appId: " + tokenSession.getAppId()
				+ ", token: " + tokenSession.getToken() + ", 最后访问时间："
				+ DateUtil.toStdDateTimeXStr(tokenSession.getLastAccessTime()));
	}

	@Override
	public void onSessionRemoved(TokenSession tokenSession) {
		System.out.println("Token 已删除 >> userId: " + tokenSession.getUserId() + ", appId: " + tokenSession.getAppId()
				+ ", token: " + tokenSession.getToken() + ", 最后访问时间："
				+ DateUtil.toStdDateTimeXStr(tokenSession.getLastAccessTime()));
	}

}
