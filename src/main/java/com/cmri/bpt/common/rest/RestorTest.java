package com.cmri.bpt.common.rest;

import com.cmri.bpt.common.token.TokenSessionStore;
import com.cmri.bpt.common.user.UserContext;
import com.cmri.bpt.common.user.UserContextHolder;

public abstract class RestorTest {
	private RestProxy restProxy = RestProxy.getDefault();
	private RestorRegistry registry = restProxy.getRegistry();

	//

	protected <T> T getRestor(Class<T> restorType) {
		return this.registry.getRestor(restorType);
	}

	protected UserContext getUserContext() {
		return UserContextHolder.getUserContext();
	}

	protected TokenSessionStore getTokenSessionStore() {
		return UserContextHolder.getTokenSessionStore();
	}

	protected UserContext loadUserInfoIntoContext() {
		return UserContextHolder.loadUserInfoIntoContext();
	}
}
