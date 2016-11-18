package com.cmri.bpt.common.http;

public interface HttpErrorStatusHandler {
	public void onErrorStatus(int statusCode, String reasonPhrase);
}
