package com.cmri.bpt.common.msg;

public class MsgRequest<TData> {
	public Integer code = MsgRequestCode.Unknown;
	public TData data = null;

	public Integer getCode() {
		return code;
	}

	public MsgRequest<TData> setCode(Integer code) {
		if (code == null) {
			code = MsgRequestCode.Unknown;
		}
		this.code = code;
		return this;
	}

	public TData getData() {
		return data;
	}

	public MsgRequest<TData> setData(TData data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("code = " + code);
		builder.append("data = " + data);
		return builder.toString();
	}

	public void echo() {
		System.out.println(this.toString());
	}

	public static <TData> MsgRequest<TData> newOne() {
		return new MsgRequest<TData>();
	}
}
