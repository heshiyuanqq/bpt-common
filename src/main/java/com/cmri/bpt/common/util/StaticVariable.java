package com.cmri.bpt.common.util;

public class StaticVariable {
	
		public static int sendcallLog_SuccessNum=0;//统计接通率时接通一直保持接通状态的log个数（sendcalllog.csv 为空）.
		public static int callLog_SuccessNum = 0;//统计通话用户时一直保持接通状态的log个数.
		public static Long error = 1451577600000L;//对有可能错误日志时间的一个限制时间戳（自己定义的时间为2016/1/1）
		public static int call_ConnectRate_StepSize=2;//画接通率时判断坐标时间是否在通话区间时 的循环步长.
		public static int call_UserNumber_StepSize = 1;//画通话在线用户判断坐标时间是否在通话区间时 的循环步长. 
	
}
	
