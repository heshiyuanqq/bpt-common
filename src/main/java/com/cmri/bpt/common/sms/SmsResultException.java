package com.cmri.bpt.common.sms;

import com.cmri.bpt.common.exception.ResultException;

public class SmsResultException extends ResultException {
	private static final long serialVersionUID = 5766407036148183048L;

	public SmsResultException() {
		super();
	}

	public SmsResultException(String message) {
		super(message);
	}

	public SmsResultException(String message, Throwable cause) {
		super(message, cause);
	}

	public SmsResultException(Throwable cause) {
		super(cause);
	}
}
