package com.cmri.bpt.common.csv;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cmri.bpt.common.util.CSVUtils;
/*import com.reserch.entity.po.FileNamesPO;
import com.reserch.entity.po.LogAnylizeInfoPO;
import com.reserch.entity.po.LogBusiCallPO;
import com.reserch.entity.po.LogBusiFtpPO;
import com.reserch.entity.po.LogBusiImPO;
import com.reserch.entity.po.LogBusiSmsPO;
import com.reserch.entity.po.WeiXinPicturePO;
import com.reserch.entity.vo.LogBusiFtpVO;
import com.reserch.mapper.LogCaseTestMapper;*/


/**
 * 读取csv文件log信息到数据库中
 * @author haha
 *
 */
public class CSVAbstractRead {
	/*@Autowired
	private LogCaseTestMapper logCaseTestMapper;
	
	*//**
	 * 读取文件中的数据显示在页面表格中（主表）
	 * @param path
	 * @param caselogname
	 * @return
	 * @throws RuntimeException
	 *//*
	@Transactional
	public LogAnylizeInfoPO abstractCSVListToTable(String path,String caselogname) throws RuntimeException{
		
		LogAnylizeInfoPO logAnylizeInfoPO = new LogAnylizeInfoPO();
		logAnylizeInfoPO.setCaselogname(caselogname);
		try {
			List<List<String>> csvlists = CSVUtils.importCsv(new File(path + "testcase/" + caselogname + "/abstract.csv"));
			for(int i=0;i<csvlists.size();i++){
				if("参测终端总数".equals(csvlists.get(i).get(0))){
					logAnylizeInfoPO.setTestuetotalnum(Integer.valueOf(csvlists.get(i).get(1)));
				}else if("LTE小区总数".equals(csvlists.get(i).get(0))){
					logAnylizeInfoPO.setTestltenum(Integer.valueOf(csvlists.get(i).get(1)));
				}else if("GSM小区总数".equals(csvlists.get(i).get(0))){
					logAnylizeInfoPO.setTestgsmnum(Integer.valueOf(csvlists.get(i).get(1)));
				}else if("TDS小区总数".equals(csvlists.get(i).get(0))){
					logAnylizeInfoPO.setTesttdsnum(Integer.valueOf(csvlists.get(i).get(1)));
				}
			}
			
			int count = GetFileName.getFileCount(path + "testcase/" +caselogname + "/");
			if(count >= 3){
				count = count - 3;
			}
			logAnylizeInfoPO.setTestuecompletenum(count);
			
			//小区重选/切换发生次数
			int countforcallid = 0;
			//TODO 重选/切换用户平均时长(需求待定)
			float avgduration = 0.0f;
			//TODO 最大时长(需求待定)
			float maxduration = 0.0f;
			//TODO 最小时长(需求待定)
			float minduration = Float.MAX_VALUE;
			//每次切换的时延list
			List<Float> durations = new ArrayList<Float>();
			//TODO 方差(需求待定)
			float var = 0.0f;
			GetFileName getfilename=new GetFileName();
			List<FileNamesPO> fileNamesPOs = getfilename.getFolderName(path + "testcase/" + caselogname);
			for(FileNamesPO po : fileNamesPOs){
				if(GetFileName.IsExistfile(path + "testcase/" + caselogname + "/" +po.getFilename(),"networklog.csv")){
					 //上一小区的callid
					 String lastCallid = "";
					 //上一小区的结束时间戳
					 long endtime = 0L;
					 csvlists = CSVUtils.importCsv(new File(path + "testcase/" + caselogname + "/" + po.getFilename() + "/networklog.csv"));
					 
					 if(csvlists != null && csvlists.size() != 0){
						 lastCallid = csvlists.get(1).get(4);
						 endtime = Long.valueOf(csvlists.get(1).get(0));
					 }
					 
					 for(int i=1;i<csvlists.size();i++){
						 if(!lastCallid.equals(csvlists.get(i).get(4))){
							 countforcallid ++;
							 lastCallid = csvlists.get(i).get(4);
							 float duration = (Long.valueOf(csvlists.get(i).get(0)) - endtime)/1000;
							 durations.add(duration);
							 avgduration += duration;
							 
							 if(maxduration < duration){
								 maxduration = duration;
							 }
							 
							 if(minduration > duration){
								 minduration = duration;
							 }
						 }
						 endtime = Long.valueOf(csvlists.get(i).get(0));
					 }
				}
			}	
			
			
			for(float duration : durations){
				var += (avgduration/countforcallid - duration) * (avgduration/countforcallid - duration) / countforcallid;
			}
			
			logAnylizeInfoPO.setNbhresettabnum(countforcallid);
			if(countforcallid==0){
				logAnylizeInfoPO.setResettabavetime(0);
			}
			else{
				logAnylizeInfoPO.setResettabavetime(avgduration/countforcallid);
			}
			if(minduration==Float.MAX_VALUE){
				logAnylizeInfoPO.setResettabmintime(0);
			}
			else{
				logAnylizeInfoPO.setResettabmintime(minduration);
			}
			logAnylizeInfoPO.setResettabmaxtime(maxduration);
			logAnylizeInfoPO.setResettabvartime(var);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{	
			int result = logCaseTestMapper.saveLogAnylizeInfo(logAnylizeInfoPO);
			if(result == 0){
				throw new SQLException();
			}
			logAnylizeInfoPO = logCaseTestMapper.selectLogAnylizeInfo(caselogname);
			return logAnylizeInfoPO;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
	*//**
	 * 读取文件中的数据显示在页面表格中（语音表）
	 * @param path
	 * @param caselogname
	 * @return
	 * @throws RuntimeException
	 *//*
	@Transactional
	public List<LogBusiCallPO> sendCallLogCSVListToTable(String path,String caselogname) throws RuntimeException{
		// 接通成功次数
		int uetotalcount = 0;
		// 接通失败次数
		int uefaildcount = 0;
		// dialing标志位
		long dialingstart = 0L;
		// 接通个数
		int successcount = 0;
		// 接通时延
		float successduration = 0.0f;
		// 标志位
		long activestart = 0L;
		// 接通保持时长
		float keeptime = 0.0f;
		// 1s抽样map
		Map<Long,Integer> onesecondmap = new HashMap<Long, Integer>();
		// minactive
		long minactive = Long.MAX_VALUE;
		// maxactive
		long maxactive = 0L;
		
		LogBusiCallPO logBusiCallPO = new LogBusiCallPO();
		logBusiCallPO.setCaselogname(caselogname);
		try {
			GetFileName getfilename=new GetFileName();
			List<FileNamesPO> fileNamesPOs = getfilename.getFolderName(path + "testcase/" + caselogname);
			for(FileNamesPO po : fileNamesPOs){
				if(GetFileName.IsExistfile(path + "testcase/" + caselogname + "/" +po.getFilename(),"sendCallLog.csv")){
					List<List<String>> csvlists = CSVUtils.importCsv(new File(path + "testcase/" + caselogname + "/" + po.getFilename() + "/sendCallLog.csv"));

					for(int i=0;i<csvlists.size();i++){
						if("IDLE".equals(csvlists.get(i).get(2))){
							uetotalcount ++;
							if("false".equals(csvlists.get(i).get(6))){
								uefaildcount ++;
							}
							
							dialingstart = 0L;
							if(activestart != 0){
								keeptime += (Long.valueOf(csvlists.get(i).get(0)) - activestart);
							}
							activestart = 0L;
						}
						
						if("DIALING".equals(csvlists.get(i).get(2))){
							dialingstart = Long.valueOf(csvlists.get(i).get(0));
						}
						
						if("ACTIVE".equals(csvlists.get(i).get(2))){
							activestart = Long.valueOf(csvlists.get(i).get(0));
							successduration += (activestart - dialingstart);
							successcount ++;
							
							long activeturnto = activestart/1000;
							Integer count = onesecondmap.get(activeturnto);
							if(count != null){
								onesecondmap.put(activeturnto, count++);
							}else{
								onesecondmap.put(activeturnto, 1);
							}
							
							if(activeturnto > maxactive){
								maxactive = activeturnto;
							}
							if(activeturnto < minactive){
								minactive = activeturnto;
							}
						}
					}

				}
			}	
			
			int maxcount = 0;
			for(long i=minactive;i<maxactive;i++){
				if((onesecondmap.get(i) != null) && (maxcount < onesecondmap.get(i))){
					maxcount = onesecondmap.get(i);
				}
			}
			
			logBusiCallPO.setCallnum(uetotalcount);
			logBusiCallPO.setFailnum(uefaildcount);
			if(successduration == 0.0f){
				logBusiCallPO.setAveduration(successduration);
			}else{
				logBusiCallPO.setAveduration(successduration/successcount/1000);
			}
			if(keeptime == 0.0f){
				logBusiCallPO.setAvekeeptime(keeptime);
			}else{
				logBusiCallPO.setAvekeeptime(keeptime/successcount/1000);
			}
			
			logBusiCallPO.setSametimepeak(maxcount);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			logBusiCallPO.setBusiname("主叫");
			if(logCaseTestMapper.saveLogBusiCall(logBusiCallPO) == 0){
				throw new SQLException();
			}
			
			logBusiCallPO.setBusiname("被叫");
			if(logCaseTestMapper.saveLogBusiCall(logBusiCallPO) == 0){
				throw new SQLException();
			}
			
			List<LogBusiCallPO> logBusiCallPOs = logCaseTestMapper.selectLogBusiCall(caselogname);
			return logBusiCallPOs;
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	*//**
	 * 读取文件中的数据显示在页面表格中（IM/ping表）
	 * @param path
	 * @param caselogname
	 * @return
	 * @throws RuntimeException
	 *//*
	@Transactional
	public List<LogBusiImPO> sendIMLogCSVListToTable(String path , String caselogname) throws RuntimeException{
		
		// 执行业务的终端数
		int uetotalcount = 0;
		// 实际发送次数
		int sendcount = 0;
		// 实际失败次数
		int faildcount = 0;
		// 发生失败的终端数
		int uefaildcount = 0;
		// 成功率
		float successrate = 0.0f;
		// 平均延时
		float avgduration = 0.0f;
		 
		int flag=0;
		LogBusiImPO logBusiImPO = new LogBusiImPO();
		logBusiImPO.setCaselogname(caselogname);
		System.out.println("读写ping开始");
		try {
			GetFileName getfilename=new GetFileName();
			List<FileNamesPO> fileNamesPOs = getfilename.getFolderName(path + "testcase/" + caselogname);
			for(FileNamesPO po : fileNamesPOs){
				if(GetFileName.IsExistfile(path + "testcase/" + caselogname + "/" +po.getFilename(),"ping.csv")){
					flag=1;
					uetotalcount ++;
					List<String> pings = ReadTxtByPath.readLogFile(path + "testcase/" + caselogname + "/" +po.getFilename() + "/ping.csv");
					sendcount += pings.size() - 1;
					for(String s:pings){
						if("timeout".equals(s.split(",")[6]) || "error".equals(s.split(",")[6])){
							faildcount ++;
						}
						if("success".equals(s.split(",")[6])){
							avgduration += Float.valueOf(s.split(",")[5]);
						}
					}
					
					for(String s:pings){
						if("timeout".equals(s.split(",")[6]) || "error".equals(s.split(",")[6])){
							uefaildcount ++;
							break;
						}
					}
				}
				
			}
			if(flag==0){
			      return null;
			}
			else{
				successrate = 1 - (float)faildcount / sendcount;
				if(sendcount==faildcount){
					avgduration=0;
				}
				else{
					avgduration = avgduration / (sendcount - faildcount);
				}
				
				
				logBusiImPO.setBusiuenum(uetotalcount);
				logBusiImPO.setActualsendnum(sendcount);
				logBusiImPO.setActualfailnum(faildcount);
				logBusiImPO.setFailuenum(uefaildcount);
				logBusiImPO.setSuccessrate(successrate);
				logBusiImPO.setAveduration(avgduration);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("读写ping结束");
		try{
			logBusiImPO.setBusiname("Ping");
			if(logCaseTestMapper.saveLogBusiIm(logBusiImPO) == 0){
				throw new SQLException();
			}
			
			List<LogBusiImPO> logBusiImPOs = logCaseTestMapper.selectLogBusiIm(caselogname);
			return logBusiImPOs;
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	*//**
	 * 
	 * @功能 读取文件中的数据显示在页面表格中(ftp上传下载表)
	 * @日期 2016年3月2日
	 * @返回值类型 LogBusiFtpPO
	 * @author 范晓文
	 * @package名 com.reserch.csv
	 * @文件名 CSVAbstractRead.java
	 *//*
	
	public LogBusiFtpPO sendFtpLogCSVListToTable(String path , String caselogname,String filetype) throws RuntimeException{
		LogBusiFtpPO retftp=new LogBusiFtpPO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("读写ftplog开始"+formatter.format(new Date(System.currentTimeMillis())));
		int busiuenum=0;//执行终端数
		int actualupdownnum=0;//实际执行次数
	    int failuenum=0;//发生失败的终端数
		int actualfailnum=0;//实际失败次数
		float allbusirate=0.0f;//总业务速率
		int allupdownduration=0;//总业务时长
		int long_time=0;
		int success_num=0;
		String failtag=null;
		String aborttag=null;
		String finishtag=null;
		String starttag=null;
		try{
			if(filetype=="FTPDownLog.csv")
			{
			  	failtag="DownloadFailed";
				aborttag="DownloadAborted";
				finishtag="DownloadFinished";
				starttag="DownloadStarted";
			}else
			{
				failtag="UploadFailed";
				aborttag="UploadAborted";
				finishtag="UploadFinished";
				starttag="UploadStarted";
			}
			GetFileName getfilename=new GetFileName();
			List<FileNamesPO> fileNamesPOs = getfilename.getFolderName(path + "testcase/" + caselogname);
			for(FileNamesPO po : fileNamesPOs){
				if(GetFileName.IsExistfile(path + "testcase/" + caselogname + "/" +po.getFilename(),filetype)){
					busiuenum++;
					if(Strisexist(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+filetype,starttag)==1){
						actualupdownnum++;
						if(Strisexist(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+filetype,failtag)==1 || Strisexist(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+filetype,aborttag)==1){
							failuenum++;
							actualfailnum++;
						}else if(Strisexist(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+filetype,finishtag)==1){
							success_num++;
							long_time=getlongtime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+filetype,filetype);
							allupdownduration=allupdownduration+long_time;
							if(long_time==0){
								allbusirate=0;
							}else{
							allbusirate+=getallbusirate(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+filetype,filetype)/long_time*1000;
							}
						}
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("读写ftplog结束"+formatter.format(new Date(System.currentTimeMillis())));
		try{
			retftp.setCaselogname(caselogname);
			retftp.setBusiname(filetype);
			retftp.setBusiuenum(busiuenum);
			retftp.setActualupdownnum(actualupdownnum);
			retftp.setActualfailnum(actualfailnum);
			retftp.setFailuenum(failuenum);
			if(actualupdownnum==0){
				retftp.setAvebusirate(0);
				retftp.setUpdownsuccessrate(0);
			}else{
				
				DecimalFormat df=new DecimalFormat("0.0");
				retftp.setAvebusirate(Float.parseFloat(df.format(allbusirate/actualupdownnum)));
				retftp.setUpdownsuccessrate(Float.parseFloat(df.format((float)success_num/actualupdownnum)));
			}
			
			if(success_num==0){
				retftp.setAveupdownduration(0);
			}else{
			retftp.setAveupdownduration(allupdownduration/success_num);
			}
			if(logCaseTestMapper.saveLogBusiFtp(retftp) == 0){
				throw new SQLException();
			}
			return retftp;
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
		
	}
	*/
	
	/**
	 * 
	 * @功能 判断csv中是否包含某个字段
	 * @日期 2016年2月29日
	 * @返回值类型 boolean
	 * @author 范晓文
	 * @package名 com.reserch.csv
	 * @文件名 CSVAbstractRead.java
	 */
	public static int Strisexist(String path,String str){
        int flag=-1;
		try{
			File file = new File(path);
			if(!file.exists()){
				flag= 0;
			}
			else{
				List<List<String>> datalist=new ArrayList<List<String>>();
				datalist=CSVUtils.importCsv(file);
				if(datalist!=null && datalist.size()>0){
					for(int i=0;i<datalist.size();i++){
						if(datalist.get(i).get(4).equals(str)){
							flag= 1;
							break;
						}
					}
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
        return flag;
	}
/**
 * 	
 * @功能 获取文件中最大时间与最小时间的差值
 * @日期 2016年3月2日
 * @返回值类型 int
 * @author 范晓文
 * @package名 com.reserch.csv
 * @文件名 CSVAbstractRead.java
 */
	
	public static int getlongtime(String path,String str){
		int long_time=0;
		long start_time=0;
		long end_time=0;
		String failtag=null;
		String aborttag=null;
		String finishtag=null;
		String starttag=null;
		
		try{
			if(str.equals("FTPDownLog.csv"))
			{
			  	failtag="DownloadFailed";
				aborttag="DownloadAborted";
				finishtag="DownloadFinished";
				starttag="DownloadStarted";
			}else
			{
				failtag="UploadFailed";
				aborttag="UploadAborted";
				finishtag="UploadFinished";
				starttag="UploadStarted";
			}
			File file = new File(path);
			if(!file.exists()){
				
			}
			else{
				List<List<String>> datalist=new ArrayList<List<String>>();
				datalist=CSVUtils.importCsv(file);
				if(datalist!=null && datalist.size()>0){
					for(int i=0;i<datalist.size();i++){
						if(datalist.get(i).get(4).equals(starttag)){
							start_time=Long.parseLong(datalist.get(i).get(0));
						   continue;
						}else if(datalist.get(i).get(4).equals(failtag)||datalist.get(i).get(4).equals(aborttag)){
							break;
						}else if(datalist.get(i).get(4).equals(finishtag)){
							end_time=Long.parseLong(datalist.get(i).get(0));
							long_time=(int) (end_time-start_time);
						}
						
					}
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
        return long_time;
		
	}
	/**
	 * 
	 * @功能 获取单文件的上传或者下载的总量
	 * @日期 2016年3月2日
	 * @返回值类型 float
	 * @author 范晓文
	 * @package名 com.reserch.csv
	 * @文件名 CSVAbstractRead.java
	 */
	
	public static float getallbusirate(String path,String str){

        float allbusirate=0;
		String failtag=null;
		String aborttag=null;
		String finishtag=null;
		String starttag=null;
		
		try{
			if(str.equals("FTPDownLog.csv"))
			{
			  	failtag="DownloadFailed";
				aborttag="DownloadAborted";
				finishtag="DownloadFinished";
				starttag="DownloadStarted";
			}else
			{
				failtag="UploadFailed";
				aborttag="UploadAborted";
				finishtag="UploadFinished";
				starttag="UploadStarted";
			}
			File file = new File(path);
			if(!file.exists()){
				
			}
			else{
				List<List<String>> datalist=new ArrayList<List<String>>();
				datalist=CSVUtils.importCsv(file);
				if(datalist!=null && datalist.size()>0){
					for(int i=0;i<datalist.size();i++){
						if(datalist.get(i).get(4).equals(starttag)){
							
						}else if(datalist.get(i).get(4).equals(failtag)||datalist.get(i).get(4).equals(aborttag)){
							allbusirate=0;
							break;
						}else if(!datalist.get(i).get(4).equals(finishtag)){
							allbusirate+=Float.parseFloat(datalist.get(i).get(4));
						}
						
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(str.equals("FTPDownLog.csv")){  
			allbusirate=allbusirate/8;
			
		}
        return allbusirate;
		
	
	}
	
/*	
	public  LogBusiSmsPO sendSmsLogCSVListToTable(String path , String caselogname) throws RuntimeException{
		int receivesysnum=0;
		int wholetimespan=0;
		long min_time=0;
		long max_time=0;
		LogBusiSmsPO retpo=new LogBusiSmsPO();
		int flag=1;
		try {
			GetFileName getfilename=new GetFileName();
			List<FileNamesPO> fileNamesPOs = getfilename.getFolderName(path + "testcase/" + caselogname);
			for(FileNamesPO po : fileNamesPOs){
				if(GetFileName.IsExistfile(path + "testcase/" + caselogname + "/" +po.getFilename(),"smslog.csv")){
					if(flag==1){
						min_time=gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","min");
						max_time=gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","max");
						receivesysnum+=(int)gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","num");
						flag=0;
					}else{
						receivesysnum+=(int)gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","num");
						if(min_time>gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","min")){
							min_time=gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","min");
						}
						if(max_time<gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","max")){
							max_time=gettime(path + "testcase/" + caselogname + "/" +po.getFilename()+"/"+"smslog.csv","max");
						}
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		try{
			wholetimespan=(int) (max_time-min_time);
			retpo.setCaselogname(caselogname);
			retpo.setReceivesysnum(receivesysnum);
			retpo.setWholetimespan(wholetimespan);
			if(logCaseTestMapper.saveLogBusiSms(retpo)==0){
				throw new SQLException();
			}
	        return retpo;
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}*/
	
	
	public static long gettime(String path,String str){
		long receivesysnum=0;
		long min_time=0;
		long max_time=0;
		long ret=0;
		try{
		
			File file = new File(path);
			if(!file.exists()){
				
			}
			else{
				List<List<String>> datalist=new ArrayList<List<String>>();
				datalist=CSVUtils.importCsv(file);
				if(datalist!=null && datalist.size()>0){
					for(int i=1;i<datalist.size();i++){
					  if(i==1){
						  min_time=Long.parseLong(datalist.get(i).get(0));
						  max_time=Long.parseLong(datalist.get(i).get(0));
						  receivesysnum++;
					  }else{
						  if(max_time<Long.parseLong(datalist.get(i).get(0))){
							  max_time=Long.parseLong(datalist.get(i).get(0));
							  receivesysnum++;
						  }
					  }
						
					}
				}
			}
			if(str.equals("min")){
				ret=min_time;
			}else if(str.equals("max")){
				ret=max_time;
			}else{
				ret=receivesysnum;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
		
	}
}
