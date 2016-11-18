package com.cmri.bpt.common.token;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class AbstractTokenSessionStore implements TokenSessionStore {
	
	
	protected List<TokenSessionEventListener> tokenSessionEventListeners = new ArrayList<TokenSessionEventListener>();
	protected TokenGenerator tokenGenerator;
	protected TokenSessionExpireStrategy tokenSessionExpireStrategy;
	// token <=> TokenSession
	protected Map<String, TokenSession> tokenSessionMap = new ConcurrentHashMap<String, TokenSession>();

	public void addTokenSessionEventListener(TokenSessionEventListener tokenSessionEventListener) {
		if (tokenSessionEventListeners.indexOf(tokenSessionEventListener) == -1) {
			this.tokenSessionEventListeners.add(tokenSessionEventListener);
		}
	}

	public void setTokenGenerator(TokenGenerator tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}

	public void setTokenSessionExpireStrategy(TokenSessionExpireStrategy tokenSessionExpireStrategy) {
		this.tokenSessionExpireStrategy = tokenSessionExpireStrategy;
	}

	public TokenSession getTokenSession(String token) {
		return this.tokenSessionMap.get(token);
	}

	public boolean touchTokenSession(String token) {
		TokenSession tokenSession = getTokenSession(token);
		if (tokenSession == null) {
			return false;
		}
		tokenSession.setLastAccessTime(new Date());
		//
		this.notifyEventListeners(TokenSessionEvent.Updated, tokenSession);
		return true;
	}

	// 执行TokenSessionEvent通知
	protected void notifyEventListeners(TokenSessionEvent tokenSessionEvent, TokenSession tokenSession) {
		if (TokenSessionEvent.Updated.equals(tokenSessionEvent)) {
			for (TokenSessionEventListener TokenSessionEventListener : tokenSessionEventListeners) {
				TokenSessionEventListener.onSessionUpdated(tokenSession);
			}
		} else if (TokenSessionEvent.Created.equals(tokenSessionEvent)) {
			for (TokenSessionEventListener TokenSessionEventListener : tokenSessionEventListeners) {
				TokenSessionEventListener.onSessionCreated(tokenSession);
			}
		} else if (TokenSessionEvent.Expired.equals(tokenSessionEvent)) {
			for (TokenSessionEventListener TokenSessionEventListener : tokenSessionEventListeners) {
				TokenSessionEventListener.onSessionExpired(tokenSession);
			}
		} else if (TokenSessionEvent.Removed.equals(tokenSessionEvent)) {
			for (TokenSessionEventListener TokenSessionEventListener : tokenSessionEventListeners) {
				TokenSessionEventListener.onSessionRemoved(tokenSession);
			}
		}
	}
}
