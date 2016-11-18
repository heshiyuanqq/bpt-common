package com.cmri.bpt.common.token;

public interface TokenSessionExpireStrategy {
	boolean isTokenSessionExpired(TokenSession tokenSession);
}
