package com.cmri.bpt.common.base;

public class Result<TData> {
	public enum Type {
		unknown(-1), info(0), warn(1), error(2), fatal(4);

		//
		private int value = -1;

		Type(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	//
	// public Integer requestCode = MsgRequestCode.Unknown;
	//
	public Type type = Type.info;
	public Integer code = ResultCode.Ok;
	public String codeName = null;
	public String message = null;
	public TData data = null;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("type = " + type);
		builder.append("       code = " + code);
		builder.append("       codeName = " + codeName);
		builder.append("       message = " + message);
		builder.append("       data = " + data);
		return builder.toString();
	}

	public void echo() {
		System.out.println(this.toString());
	}

	public Result<TData> info(TData data, String message) {
		this.setType(Type.info);
		this.setMessage(message);
		this.setData(data);

		return this;
	}

	public Result<TData> error(String message) {
		this.setType(Type.error);
		this.setMessage(message);
		this.setData(null);

		return this;
	}

	public static <TData> Result<TData> newOne() {
		return new Result<TData>();
	}

	public Result<TData> setType(Type type) {
		this.type = type;
		return this;
	}

	public Result<TData> setCode(Integer code) {
		if (code == null) {
			code = ResultCode.Unknown;
		}
		this.code = code;
		return this;
	}

	public Result<TData> setCodeName(String codeName) {
		this.codeName = codeName;
		return this;
	}

	public Result<TData> setMessage(String message) {
		this.message = message;
		return this;
	}

	public Result<TData> setData(TData data) {
		this.data = data;
		return this;
	}
}
