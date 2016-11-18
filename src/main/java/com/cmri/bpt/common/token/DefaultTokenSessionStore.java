package com.cmri.bpt.common.token;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//public class DefaultTokenSessionStore extends AbstractTokenSessionStore implements TokenSessionStore {
//	// userId <=> appId<=>TokenSession(s)
//	private Map<Integer, Map<Integer, TokenSession>> userIdTokenSessionsMap = new ConcurrentHashMap<Integer, Map<Integer, TokenSession>>();
//
//	// 删除指定的TokenSession
//	private void removeTokenSessionInner(String token) {
//		TokenSession tokenSession = getTokenSession(token);
//		if (tokenSession == null) {
//			return;
//		}
//		//
//		Integer userId = tokenSession.getUserId();
//		//
//		Map<Integer, TokenSession> userTokenSessions = userIdTokenSessionsMap.get(userId);
//		if (userTokenSessions != null) {
//			Integer appId = tokenSession.getAppId();
//			//
//			userTokenSessions.remove(appId);
//		}
//		//
//		notifyEventListeners(TokenSessionEvent.Removed, tokenSession);
//	}
//
//	// TokenSession过期检查周期
//	private static final int ExpirationCheckInterval = 60 * 1000;
//
//	// 过期执行器（清除过期的TokenSession）
//	class TokenSessionExpirationExecutor extends Thread {
//
//		@Override
//		public void run() {
//			while (true) {
//				if (tokenSessionExpireStrategy != null) {
//					for (Map.Entry<String, TokenSession> tokenSessionEntry : tokenSessionMap.entrySet()) {
//						String token = tokenSessionEntry.getKey();
//						TokenSession tokenSession = tokenSessionEntry.getValue();
//						if (tokenSessionExpireStrategy.isTokenSessionExpired(tokenSession)) {
//							//
//							notifyEventListeners(TokenSessionEvent.Expired, tokenSession);
//							//
//							removeTokenSessionInner(token);
//						}
//					}
//				}
//				//
//				try {
//					Thread.sleep(ExpirationCheckInterval);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	public DefaultTokenSessionStore() {
//		// 启动过期执行器
//		new TokenSessionExpirationExecutor().start();
//	}
//
//	@Override
//	public TokenSession getTokenSession(Integer userId, Integer sysId, Integer appId) {
//		Map<Integer, TokenSession> userTokenSessions = this.userIdTokenSessionsMap.get(userId);
//		if (userTokenSessions == null) {
//			return null;
//		}
//		for (Map.Entry<Integer, TokenSession> tokenSessionEntry : userTokenSessions.entrySet()) {
//			if (appId.equals(tokenSessionEntry.getKey())) {
//				return tokenSessionEntry.getValue();
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public TokenSession createTokenSession(Integer userId, Integer sysId, Integer appId) {
//		TokenSession tokenSession = this.getTokenSession(userId, sysId, appId);
//		if (tokenSession == null) {
//			String token = this.tokenGenerator.generateToken();
//			//
//			tokenSession = new DefaultTokenSession();
//			tokenSession.setToken(token);
//			tokenSession.setUserId(userId);
//			tokenSession.setAppId(appId);
//			tokenSession.setLastAccessTime(new Date());
//			//
//			this.tokenSessionMap.put(token, tokenSession);
//			//
//			Map<Integer, TokenSession> userTokenSessions = this.userIdTokenSessionsMap.get(userId);
//			if (userTokenSessions == null) {
//				userTokenSessions = new ConcurrentHashMap<Integer, TokenSession>();
//				//
//				this.userIdTokenSessionsMap.put(userId, userTokenSessions);
//				//
//				userTokenSessions.put(appId, tokenSession);
//			}
//			//
//			this.notifyEventListeners(TokenSessionEvent.Created, tokenSession);
//		} else {
//			this.touchTokenSession(tokenSession.getToken());
//		}
//		return tokenSession;
//	}
//
//	@Override
//	public TokenSession removeTokenSession(String token) {
//		TokenSession tokenSession = this.tokenSessionMap.get(token);
//		if (tokenSession != null) {
//			this.removeTokenSessionInner(token);
//		}
//		return tokenSession;
//	}
//}
