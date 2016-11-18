package com.cmri.bpt.common.user;

import javax.servlet.http.HttpServletRequest;

public interface UserContextHandler {
	void handleHttpSessionUserContext(HttpServletRequest request);

	void handleTokenSessionUserContext(HttpServletRequest request);
}
