package com.cmri.bpt.common.base;

@SuppressWarnings("unused")
public final class ResultCode {
	private ResultCode() {
		//
	}

	public static final int Unknown = -1;

	public static final int Ok = 0;

	//

	public static class Basic {
		private static final int Base = Ok;
		//
	}

	public static class App {
		private static final int Base = 1000;
		//
	}

	public static class User {
		private static final int Base = 2000;
		//
	}

	public static class Auth {
		private static final int Base = 3000;
		// 认证
		private static final int Authenticate = Base + 100;
		// 认证 \ 失败
		public static final int Authenticate_Fail = Authenticate + 1;

		// 授权
		private static final int Authorize = Base + 200;
		// 授权 \ 失败
		public static final int Authorize_Fail = Authorize + 1;

	}

	public static class VCode {
		private static final int Base = 4000;
		//
	}

	public static class Car {
		private static final int Base = 5000;
		//
	}

	public static class Goods {
		private static final int Base = 6000;
		//
	}

	public static class Promotion {
		private static final int Base = 7000;
		//
	}

	public static class Order {
		private static final int Base = 8000;
		//
	}

	public static class Trace {
		private static final int Base = 9000;
		//
	}
}
