package com.cmri.bpt.common.msg;

@SuppressWarnings("unused")
public final class MsgRequestCode {
	private MsgRequestCode() {
		//
	}

	public static final int Unknown = -1;

	public static final int Ping = 0;

	//

	public static class Basic {

		private static final int Base = Ping;
		//
	}

	public static class App {
		private static final int Base = 1000;
		//
	}

	public static class User {
		private static final int Base = 2000;

		/*
		 * 498 Token expired/invalid (Esri) Returned by ArcGIS for Server. 499
		 * Token required (Esri) Returned by ArcGIS for Server.
		 */

		public static final int Token_ExpiredOrInvalid = 20498;

		public static final int Token_Required = 20499;

	}

	public static class Auth {
		private static final int Base = 3000;
		//
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

	public static class Ue {
		private static final int Base = 9000;
		//
		// 司机行程跟踪
		private static final int Driver_Track = Base + 100;
		//
		// 司机行程跟踪 \ 距离计算
		private static final int Driver_Track_Calc = Driver_Track + 10;
		// 启动计算任务
		public static final int Driver_Track_Calc_StartTasks = Driver_Track_Calc + 1;
		// 停止计算任务
		public static final int Driver_Track_Calc_StopTasks = Driver_Track_Calc + 2;
		// 查询计算任务是否进行中
		public static final int Driver_Track_Calc_QueryRunning = Driver_Track_Calc + 3;
		// 查询计算进度（信息）
		public static final int Driver_Track_Calc_QueryProgress = Driver_Track_Calc + 4;
		//
		// Ue \ 位置信息
		private static final int Ue_Loc = Driver_Track + 20;
		// 自己的位置信息
		public static final int Ue_Loc_Owner = Ue_Loc + 1;
		// 所有Ue的位置信息
		public static final int Ue_Loc_All = Ue_Loc + 2;

		// 通知信息
		public static final int Driver_Track_Info = Driver_Track + 50;

	}


	


}
