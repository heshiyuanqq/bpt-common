package com.cmri.bpt.common.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.cmri.bpt.common.token.TokenSessionStore;

public class UserContextHolder {
	
	private static final Logger userLogger = Logger.getLogger("user."+UserContextHolder.class);
	
	//
	public static final String SESSION_KEY_USER_CONTEXT = "global_user_context";
	
	// ---------- UserContextHandler
	private static UserContextHandler userContextHandler;

	public static void setUserContextHandler(UserContextHandler userContextHandler) {
		if (UserContextHolder.userContextHandler == null && userContextHandler != null) {
			UserContextHolder.userContextHandler = userContextHandler;
			//
			userLogger.warn("设定 UserContextHandler : " + UserContextHolder.userContextHandler.getClass().getName());
		}
	}

	public static UserContextHandler getUserContextHandler() {
		return userContextHandler;
	}

	// ---------- TokenSessionStore
	private static TokenSessionStore tokenSessionStore;

	public static void setTokenSessionStore(TokenSessionStore tokenSessionStore) {
		if (UserContextHolder.tokenSessionStore == null && tokenSessionStore != null) {
			UserContextHolder.tokenSessionStore = tokenSessionStore;
			//
			userLogger.trace("设定 TokenSessionStore : " + UserContextHolder.tokenSessionStore.getClass().getName());
		}
	}

	public static TokenSessionStore getTokenSessionStore() {
		return tokenSessionStore;
	}

	// ---------- UserInfoSetter
	private static UserInfoSetter userInfoSetter;

	public static void setUserInfoSetter(UserInfoSetter userInfoSetter) {
		if (UserContextHolder.userInfoSetter == null && userInfoSetter != null) {
			UserContextHolder.userInfoSetter = userInfoSetter;
			//
			userLogger.trace("设定 UserInfoSetter : " + UserContextHolder.userInfoSetter.getClass().getName());
		}
	}

	private static InheritableThreadLocal<UserContext> threadUserContext = new InheritableThreadLocal<UserContext>() {
		@Override
		protected UserContext childValue(UserContext parentValue) {
			// logger.warn("创建子线程 UserContext, " +
			// Thread.currentThread().getName());
			//
			return parentValue;
		}

		@Override
		protected UserContext initialValue() {
			// logger.warn("初始化 UserContext");
			//
			return new UserContext();
		}
	};

	public static UserContext getUserContext() {
		
		userLogger.debug("get userId:" + threadUserContext.get().getUserId());
		return threadUserContext.get();
	}

	public static void setUserContext(UserContext userContext) {
		// logger.warn("重新设置 UserContext");
		//
		userLogger.debug("set userId:" + userContext.getUserId());
		threadUserContext.set(userContext);
	}

	public static void clearUserContext() {
		// logger.warn("清除 UserContext");
		//
		UserContext userContext = threadUserContext.get();
		userContext.clear();
		threadUserContext.set(userContext);
	}

	public static UserContext loadUserInfoIntoContext() {
		userInfoSetter.setUserInfo(threadUserContext.get());
		return threadUserContext.get();
	}
}
