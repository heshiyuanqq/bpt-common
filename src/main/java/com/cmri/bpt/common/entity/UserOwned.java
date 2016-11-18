package com.cmri.bpt.common.entity;

/**
 * 接口：被用户所拥有
 * 
 * @author koqiui
 * 
 * @param <TUserId>
 *            用户id
 */
public interface UserOwned<TUserId> {
	void setUserId(TUserId userId);

	TUserId getUserId();

	boolean isOwnedBy(TUserId userId);
}
