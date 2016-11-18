package com.cmri.bpt.common.token;

public interface TokenSessionStore {
	public static enum TokenSessionEvent {
		Created("创建"), //
		Updated("更新"), //
		Expired("过期"), //
		Removed("删除");

		private String text;

		TokenSessionEvent(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
	
	void InitSessions();

	/**
	 * 新建 token
	 *
	 * @param sysUserId
	 * @param sysId
	 * @param clientId
	 * @return
	 */
	TokenSession createTokenSession(Integer sysUserId, Integer appId, String xppId);

	/**
	 * 获取token
	 *
	 * @param token
	 * @return
	 */
	TokenSession getTokenSession(String token);

	/**
	 * 获取token
	 *
	 * @param sysUserId
	 * @param userId
	 * @param sysId
	 * @param clientId
	 * 			@return
	 */
	Object getTokenSession(Integer userId, Integer sysId, String xppId);

	/**
	 * 更新Token
	 * 
	 * @param session
	 * @return
	 */
	TokenSession updateTokenSession(TokenSession session);
	
	
	/**
	 * 清除token
	 *
	 * @param token
	 * @return
	 */
	TokenSession removeTokenSession(String token);

	/**
	 * @param token
	 * @return
	 */
	boolean touchTokenSession(String token);
	
	
	boolean syncTokenSession(String token);

	/**
	 * 添加token监听器
	 *
	 * @param tokenSessionEventListener
	 */
	void addTokenSessionEventListener(TokenSessionEventListener tokenSessionEventListener);

	/**
	 * 设置token生成器
	 *
	 * @param tokenGenerator
	 */
	void setTokenGenerator(TokenGenerator tokenGenerator);

	/**
	 * 设置token过期策略
	 *
	 * @param tokenSessionExpireStrategy
	 */
	void setTokenSessionExpireStrategy(TokenSessionExpireStrategy tokenSessionExpireStrategy);
}
