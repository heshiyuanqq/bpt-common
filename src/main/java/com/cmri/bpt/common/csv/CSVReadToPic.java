package com.cmri.bpt.common.csv;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.StringUtils;

import com.cmri.bpt.common.entity.CallLogPO;
import com.cmri.bpt.common.entity.CallUserNumberPO;
import com.cmri.bpt.common.entity.CellChangePicturePO;
import com.cmri.bpt.common.entity.FileNamesPO;
import com.cmri.bpt.common.entity.FtpPicturePO;
import com.cmri.bpt.common.entity.LogSequenceVO;
import com.cmri.bpt.common.entity.NetWorkFlowPicturePO;
import com.cmri.bpt.common.entity.NetWorkPicturePO;
import com.cmri.bpt.common.entity.PicUESmsPO;
import com.cmri.bpt.common.entity.WebPicturePO;
import com.cmri.bpt.common.entity.WeiXinPicturePO;
import com.cmri.bpt.common.util.CSVUtils;
import com.cmri.bpt.common.util.GetFileName;
import com.cmri.bpt.common.util.MapKeyComparator;
import com.cmri.bpt.common.util.ReadTxtByPath;
import com.cmri.bpt.common.util.StaticVariable;


public class CSVReadToPic {


	/**
	 *  @author 范晓文
	 * 读取通话成功率的全部文件
	 * @param path
	 * @return
	 */
	
	//List<List<CallLogPO>>
	public static List<CallLogPO> ReadAllCallLog(String path,Long min){
		
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<List<CallLogPO>> retlist=new ArrayList<List<CallLogPO>>();
		List<CallLogPO> retlist1 = new ArrayList<CallLogPO>();		
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"sendCallLog.csv")){
						List<CallLogPO> onelist=new ArrayList<CallLogPO>();																	
						onelist= ReadOneCallLog(path+"/"+filename.getFilename()+"/sendCallLog.csv",min);
						if(onelist!=null&&onelist.size()>=1){
							//retlist.add(onelist);
							retlist1.addAll(onelist);
							}
				
						
					}
					//在线用户数统计
			/*		if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"sendCallLog.csv")){
						List<Integer> onesendlist=new ArrayList<Integer>();												
						onesendlist= ReadOneCallLog1(path+"/"+filename.getFilename()+"/sendCallLog.csv",min,"sendCallLog.csv");
						if(onesendlist!=null){
						  retlist1.add(onesendlist);
						}					
					}
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"calllog.csv")){
						List<Integer> onereceivelist=new ArrayList<Integer>();											
						 onereceivelist= ReadOneCallLog1(path+"/"+filename.getFilename()+"/calllog.csv",min,"calllog.csv");
						if( onereceivelist!=null){
						  retlist1.add( onereceivelist);
						}				
					}*/
					
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist1;
	}
	
	
	
	/**
	 *  @author 范晓文
	 * 读取通话用户数的全部文件
	 * @param path
	 * @return
	 */
	public static List<CallUserNumberPO> ReadAllCallLog1(String path,Long min){
		String callLogType=null;
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		//List<List<Integer>> retlist=new ArrayList<List<Integer>>();
		List<CallUserNumberPO> retlist= new ArrayList<CallUserNumberPO>();
		//Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					/*min=Getmintimefromlog(path,"sendCallLog.csv");
					if(Getmintimefromlog(path,"calllog.csv")<min){
						min=Getmintimefromlog(path,"calllog.csv");
					}*/
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"sendCallLog.csv")){
						List<CallUserNumberPO> onesendlist=new ArrayList<CallUserNumberPO>();												
						onesendlist= ReadOneCallLog1(path+"/"+filename.getFilename()+"/sendCallLog.csv",min,"sendCallLog.csv");
						if(onesendlist!=null){
						  retlist.addAll(onesendlist);
						}					
					}
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"calllog.csv")){
						List<CallUserNumberPO> onereceivelist=new ArrayList<CallUserNumberPO>();											
						 onereceivelist= ReadOneCallLog1(path+"/"+filename.getFilename()+"/calllog.csv",min,"calllog.csv");
						if( onereceivelist!=null){
						  retlist.addAll( onereceivelist);
						}				
					}
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
  /**
   * @功能 读取path下所有ping。csv的内容
   * @日期 2015年11月2日
   * @返回值类型 List<List<CallLogPO>>
   * @author 范晓文
   * @package名 com.reserch.csv
   * @文件名 CSVReadToPic.java
   */
	public static List<List<CallLogPO>> ReadAllPingLog(String path){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<List<CallLogPO>> retlist=new ArrayList<List<CallLogPO>>();
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,"ping.csv");
			if(min!=Long.MAX_VALUE){
				if(listfilename!=null&& listfilename.size()>0){
					for(FileNamesPO filename:listfilename){
						if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"ping.csv")){
							List<CallLogPO> onelist=new ArrayList<CallLogPO>();
							onelist= ReadOnePingLog(path+"/"+filename.getFilename()+"/ping.csv",min);
							if(onelist!=null){
								retlist.add(onelist);
								}
							}
					}
				}
			}
			else{
				retlist=null;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	/**
	 * 
	 * @功能   读取所有networklog,并解析
	 * @日期 2015年11月2日
	 * @返回值类型 List<List<CallLogPO>>
	 * @author 范晓文
	 * @package名 com.reserch.csv
	 * @文件名 CSVReadToPic.java
	 */
	
	public static List<NetWorkPicturePO> ReadAllNetWorkLog(String path,String case_name){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<NetWorkPicturePO> retlist=new ArrayList<NetWorkPicturePO>();
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,"networklog.csv");
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					//System.out.println("手机位置------------"+filename.getFilename().substring(0,5));
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"networklog.csv")){
						List<NetWorkPicturePO> onelist=new ArrayList<NetWorkPicturePO>();
						onelist= ReadOneNetWorkLog(path+"/"+filename.getFilename()+"/networklog.csv",min,case_name);
						if(onelist!=null){
							retlist.addAll(onelist);
							}
						}
				}
			}
			else{
				retlist=null;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	/**
	 * 
	 * @功能   读取所有networklog,并解析
	 * @日期 2015年11月2日
	 * @返回值类型 List<List<CallLogPO>>
	 * @author ycliu
	 * @package名 com.reserch.csv
	 * @文件名 CSVReadToPic.java
	 */
	
	public static List<NetWorkFlowPicturePO> readNetWorkFlow(String path,String case_name,String name){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<NetWorkFlowPicturePO> retlist=new ArrayList<NetWorkFlowPicturePO>();
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,"networklog.csv");
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					//System.out.println("手机位置------------"+filename.getFilename().substring(0,5));
					if (name!=null&&name.equals("calllog")) {
						if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"networklog.csv")&&GetFileName.IsExistfile(path+"/"+filename.getFilename(),"sendCallLog.csv")){
							List<NetWorkFlowPicturePO> onelist=new ArrayList<NetWorkFlowPicturePO>();
							onelist= readOneNetWorkFlowLog(path+"/"+filename.getFilename()+"/networklog.csv",min,case_name);
							if(onelist!=null){
								retlist.addAll(onelist);
							}
						}
					}else if (name!=null&&name.equals("videolog")) {
							if (GetFileName.IsExistfile(path+"/"+filename.getFilename(),"LiveLog1.csv")||GetFileName.IsExistfile(path+"/"+filename.getFilename(),"LiveLog2.csv")) {
								if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"networklog.csv")){
								List<NetWorkFlowPicturePO> onelist=new ArrayList<NetWorkFlowPicturePO>();
								onelist= readOneNetWorkFlowLog(path+"/"+filename.getFilename()+"/networklog.csv",min,case_name);
								if(onelist!=null){
									retlist.addAll(onelist);
								}
							}
						}
						
					}else {
						if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"networklog.csv")){
							List<NetWorkFlowPicturePO> onelist=new ArrayList<NetWorkFlowPicturePO>();
							onelist= readOneNetWorkFlowLog(path+"/"+filename.getFilename()+"/networklog.csv",min,case_name);
							if(onelist!=null){
								retlist.addAll(onelist);
							}
						}
					}
				}
			}
			else{
				retlist=null;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
/**
 * 
 * @功能 
 * @日期 2015年11月11日
 * @返回值类型 List<FtpPicturePO>
 * @author 范晓文
 * @package名 com.reserch.csv
 * @文件名 CSVReadToPic.java
 */
	
	public static List<FtpPicturePO> ReadAllFtpLog(String path,String case_name){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<FtpPicturePO> retlist=new ArrayList<FtpPicturePO>();
		List<List<FtpPicturePO>> uplist=new ArrayList<List<FtpPicturePO>>();
		List<List<FtpPicturePO>> downlist=new ArrayList<List<FtpPicturePO>>();
		Map<Integer,FtpPicturePO> retdownlistrate=new TreeMap<Integer, FtpPicturePO>() ;
		Map<Integer,FtpPicturePO> retuplistrate=new TreeMap<Integer, FtpPicturePO>() ;
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,"FTPDownLog.csv");
		/*	if(Getmintimefromlog(path,"FTPUpLog.csv")<min){
				min=Getmintimefromlog(path,"FTPUpLog.csv");
			}*/
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"FTPDownLog.csv")){
						List<FtpPicturePO> onelist=new ArrayList<FtpPicturePO>();
						onelist= ReadOneFtpLog(path+"/"+filename.getFilename()+"/FTPDownLog.csv",min,case_name,"FTPDownLog.csv");
						if(onelist!=null){
							downlist.add(onelist);
							}
						}
					/*if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"FTPDownLog.csv")){
						List<FtpPicturePO> onelist=new ArrayList<FtpPicturePO>();
						onelist= ReadOneFtpLog(path+"/"+filename.getFilename()+"/FTPDownLog.csv",min,case_name,"FTPUpLog.csv");
						if(onelist!=null){
							uplist.add(onelist);
							}
						}*/
					//临时注释
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"FTPUpLog.csv")){
						List<FtpPicturePO> onelist=new ArrayList<FtpPicturePO>();
						onelist= ReadOneFtpLog(path+"/"+filename.getFilename()+"/FTPUpLog.csv",min,case_name,"FTPUpLog.csv");
						if(onelist!=null){
							uplist.add(onelist);
							}
						}
					
				}
				
				
				for(int i=0;i<downlist.size();i++){
					for(int j=0;j<downlist.get(i).size();j++){
						FtpPicturePO ftplog=new FtpPicturePO();
						ftplog.setX_data(downlist.get(i).get(j).getX_data());
						ftplog.setFtp_type(downlist.get(i).get(j).getFtp_type());
						ftplog.setCase_name(downlist.get(i).get(j).getCase_name());
						ftplog.setStart_time(downlist.get(i).get(j).getStart_time());						
					    if(retdownlistrate!=null && retdownlistrate.containsKey(downlist.get(i).get(j).getX_data())){				    	
					    	ftplog.setY_data(retdownlistrate.get(downlist.get(i).get(j).getX_data()).getY_data()+downlist.get(i).get(j).getY_data());
					    	ftplog.setFail_num(retdownlistrate.get(downlist.get(i).get(j).getX_data()).getFail_num()+downlist.get(i).get(j).getFail_num());
					    	
					    }
					    else{				   
					    	ftplog.setY_data(downlist.get(i).get(j).getY_data());
					    	ftplog.setFail_num(downlist.get(i).get(j).getFail_num());
					    				    				       
					    }
					    retdownlistrate.put(downlist.get(i).get(j).getX_data(),ftplog);
					    
					}
				}
			 if(!retdownlistrate.isEmpty()){
				 retdownlistrate = sortMapftpByKey(retdownlistrate);				
				 for(Map.Entry<Integer,FtpPicturePO> entry:retdownlistrate.entrySet()){ 
					 FtpPicturePO ftpdown=new FtpPicturePO();
					 ftpdown.setX_data(entry.getValue().getX_data());
					 ftpdown.setCase_name(entry.getValue().getCase_name());
					 ftpdown.setFtp_type(entry.getValue().getFtp_type());
					 ftpdown.setStart_time(entry.getValue().getStart_time());
		
					 ftpdown.setY_data(entry.getValue().getY_data());
					 ftpdown.setFail_num(entry.getValue().getFail_num());
					 retlist.add(ftpdown);
					}   
			 }
			 
				for(int i=0;i<uplist.size();i++){
					for(int j=0;j<uplist.get(i).size();j++){
						FtpPicturePO ftplog=new FtpPicturePO();
						ftplog.setX_data(uplist.get(i).get(j).getX_data());
						ftplog.setFtp_type(uplist.get(i).get(j).getFtp_type());
						ftplog.setCase_name(uplist.get(i).get(j).getCase_name());
						ftplog.setStart_time(uplist.get(i).get(j).getStart_time());
					    if(retuplistrate!=null && retuplistrate.containsKey(uplist.get(i).get(j).getX_data())){				    	
					    	ftplog.setY_data(retuplistrate.get(uplist.get(i).get(j).getX_data()).getY_data()+uplist.get(i).get(j).getY_data());
					    	ftplog.setFail_num(retuplistrate.get(uplist.get(i).get(j).getX_data()).getFail_num()+uplist.get(i).get(j).getFail_num());
					    }
					    else{				   
					    	ftplog.setY_data(uplist.get(i).get(j).getY_data());
					    	ftplog.setFail_num(uplist.get(i).get(j).getFail_num());
					    				    				       
					    }
					    retuplistrate.put(uplist.get(i).get(j).getX_data(),ftplog);
					    
					}
				}
			 if(!retuplistrate.isEmpty()){
				 retuplistrate = sortMapftpByKey(retuplistrate);				
				 for(Map.Entry<Integer,FtpPicturePO> entry:retuplistrate.entrySet()){ 
					 FtpPicturePO ftpup=new FtpPicturePO();
					 ftpup.setX_data(entry.getValue().getX_data());
					 ftpup.setCase_name(entry.getValue().getCase_name());
					 ftpup.setFtp_type(entry.getValue().getFtp_type());
					 ftpup.setStart_time(entry.getValue().getStart_time());
					
					 ftpup.setY_data(entry.getValue().getY_data());
					 ftpup.setFail_num(entry.getValue().getFail_num());
					 retlist.add(ftpup);
					}   
			 }
			 
			 
			 
			 
				
			}
			else{
				retlist=null;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	
	/* * 
	 * @功能 获取全部weblog日志的信息，并分析
	 * @日期 2015年11月16日
	 * @返回值类型 List<WebPicturePO>
	 * @author 范晓文
	 * @package名 com.reserch.csv
	 * @文件名 CSVReadToPic.java
	 */
	public static List<List<WebPicturePO>> ReadAllWebLog(String path){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<List<WebPicturePO>> retlist=new ArrayList<List<WebPicturePO>>();
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,"WebLog.csv");
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"WebLog.csv")){
						List<WebPicturePO> onelist=new ArrayList<WebPicturePO>();
						onelist= ReadOneWebLog(path+"/"+filename.getFilename()+"/WebLog.csv",min);
						if(onelist!=null){
							retlist.add(onelist);
							}
						}
				}
			}
			else{
				retlist=null;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	
/**
 * 	
 * @功能 获取所有weixin的log的信息
 * @日期 2015年11月24日
 * @返回值类型 List<List<WeiXinPicturePO>>
 * @author 范晓文
 * @package名 com.reserch.csv
 * @文件名 CSVReadToPic.java
 */
	public static List<List<WeiXinPicturePO>> ReadAllWeiXinLog(String path,String type){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<List<WeiXinPicturePO>> retlist=new ArrayList<List<WeiXinPicturePO>>();
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,type);
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),type)){
						List<WeiXinPicturePO> onelist=new ArrayList<WeiXinPicturePO>();
						onelist= ReadOneWeiXinLog(path+"/"+filename.getFilename()+"/"+type,min,type);
						if(onelist!=null){
							retlist.add(onelist);
							}
						}
				}
			}
			else{
				retlist=null;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	/**
	 * 
	 * @功能 处理小区切换的所有数据
	 * @日期 2015年11月27日
	 * @返回值类型 List<List<CellChangePicturePO>>
	 * @author 范晓文
	 * @package名 com.reserch.csv
	 * @文件名 CSVReadToPic.java
	 */
	public static List<List<CellChangePicturePO>> ReadAllCellChangeLog(String path,String case_name){
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		List<List<CellChangePicturePO>> retlist=new ArrayList<List<CellChangePicturePO>>();
		Long min=(long) 0;
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			min=Getmintimefromlog(path,"networklog.csv");
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"networklog.csv")){
						List<CellChangePicturePO> onelist=new ArrayList<CellChangePicturePO>();
						onelist= ReadOneCellChangeLog(path+"/"+filename.getFilename()+"/networklog.csv",min);
						if(onelist!=null){
							retlist.add(onelist);
							}
						}
				}
			}
			else{
				retlist=null;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	
	/**
	 *  @author 范晓文
	 * 读取某个sendcalllog语音LOG，并解析
	 * @param path 文件位置
	 * @param min 起始统计时间
	 * @return List<CallLogPO> 各个时间点的成功 失败次数
	 */
	
	private static List<CallLogPO> ReadOneCallLog(String path,long min)
	{
		
		File file = new File(path);
		int success_num=0;
		int fail_num=0;
		int flag=0;
		int time_temp=2;
		int timeActive=0;
		List<CallLogPO> retlist=new ArrayList<CallLogPO>();
		if(!file.exists()){
			return null;
		}
		else{
			
			List<List<String>> datalist=new ArrayList<List<String>>();
			datalist=CSVUtils.importCsv(file);
			if(datalist.size()>0&&datalist!=null){
				for(int i=1;i<datalist.size();i++){
					//System.out.println("==================================>"+datalist.get(i).get(2));
					if(datalist.get(i)!=null&&Long.parseLong(datalist.get(i).get(0))<min){
						break;
					}
					if(datalist.get(i)!=null&&datalist.get(i).size()>6&&datalist.get(i).get(6).equals("1")&&Long.parseLong(datalist.get(i).get(0))>=min){
						
						/* success_num++;*/
	                    if(flag==0){
	                    	time_temp=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);
	                    	flag=1;
	                    }
	                    
	                    if((Long.parseLong(datalist.get(i).get(0))-min)/1000+1>=time_temp){
	                    	CallLogPO calllog=new CallLogPO();
	                    	fail_num=0;
	    					success_num=1;	                    		                    	
	    					timeActive=(int) ((Long.parseLong(datalist.get(i-1).get(0))-min)/1000+1);//Active时的时间
	                    	time_temp=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);//IDEL时的时间
	                    	calllog.setTime1(timeActive);
	                    	calllog.setTime(time_temp);
	                        calllog.setFail_num(fail_num);
	    					calllog.setSuccess_num(success_num);
	    					retlist.add(calllog);
	                    }
	           
						
					}
					else if(datalist.get(i)!=null&&datalist.get(i).size()>6&&datalist.get(i).get(6).equals("0")&&Long.parseLong(datalist.get(i).get(0))>=min){
						/*fail_num++;*/
						
						 if(flag==0){
		                    	time_temp=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);
		                    	flag=1;
		                    }
						if((Long.parseLong(datalist.get(i).get(0))-min)/1000+1>=time_temp){
							CallLogPO calllog=new CallLogPO();
							fail_num=1;
	    					success_num=0;	    					
	    					timeActive=(int) ((Long.parseLong(datalist.get(i-1).get(0))-min)/1000+1);//Active时的时间
	                    	time_temp=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);
	                    	calllog.setFail_num(fail_num);
	    					calllog.setSuccess_num(success_num);
	    					calllog.setTime(time_temp);
	    					calllog.setTime1(timeActive);
	    					retlist.add(calllog);	    						    					
	                    }
				
					}														
				}				
			}else{
				//CSVReadToPic.callLogSuccessNum+=1;
				StaticVariable.sendcallLog_SuccessNum+=1;
			}
			
		}		
		return retlist;		
	}
	/**
	 *  @author 范晓文
	 * 统计一个calllog.csv的数据,并获取该用户在第几秒处于通话状态
	 * @param path
	 * @param min 其实统计时间
	 * @return List<CallUserNumberPO>
	 */
	
	private static List<CallUserNumberPO> ReadOneCallLog1(String path,Long min,String callLogType)
	{
		File file = new File(path);	
		int time=0;
		int timeIdel=0;
		List<CallUserNumberPO> retlist=new ArrayList<CallUserNumberPO>();
		if(!file.exists()){
			return null;
		}
		else{
			
			List<List<String>> datalist=new ArrayList<List<String>>();
			datalist=CSVUtils.importCsv(file);
			if(datalist.size()>0&&datalist!=null){
				for(int i=1;i<datalist.size();i++){
					if(callLogType.equals("sendCallLog.csv")){
						if(datalist.get(i)!=null&&Long.parseLong(datalist.get(i).get(0))<min){
							break;
						}
						if(datalist.get(i)!=null&&datalist.get(i).size()>=3&&datalist.get(i).get(2).equals("ACTIVE")&&Long.parseLong(datalist.get(i).get(0))>=min){
							CallUserNumberPO callUserNumberPO = new CallUserNumberPO();
//		                    while(Long.parseLong(datalist.get(i).get(0))>=min+time*1000){
//		                    	time=time+1;
//		                    }
							
							time=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);
			                timeIdel=(int) ((Long.parseLong(datalist.get(i+1).get(0))-min)/1000+1);									                    
		                    callUserNumberPO.setX_data(time);
		                    callUserNumberPO.setX_data1(timeIdel);
		                    callUserNumberPO.setY_data(1);
		                    retlist.add(callUserNumberPO);       
						}	
					}
					if(callLogType.equals("calllog.csv")){
						if(datalist.get(i)!=null&&Long.parseLong(datalist.get(i).get(0))<min){
							break;
						}
						if(datalist.get(i)!=null&&datalist.get(i).size()>=3&&datalist.get(i).get(2).equals("OFFHOOK")&&Long.parseLong(datalist.get(i).get(0))>=min){
							CallUserNumberPO callUserNumberPO = new CallUserNumberPO();   
//		                    while(Long.parseLong(datalist.get(i).get(0))>=min+time*1000){
//		                    	time=time+1;
//		                    }
							if(i!=datalist.size()-1){
								time=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);	                    
			                    timeIdel=(int) ((Long.parseLong(datalist.get(i+1).get(0))-min)/1000+1);
							}		                    
		                    callUserNumberPO.setX_data(time);
		                    callUserNumberPO.setX_data1(timeIdel);
		                    callUserNumberPO.setY_data(1);
		                    retlist.add(callUserNumberPO);        
						}	
					}
						
				}
			}else{
				StaticVariable.callLog_SuccessNum+=1;
			}
			

		}
		return retlist;
		
	}
/**
 * 	
 * @功能 读取单个network文件数据
 * @日期 2015年11月2日
 * @返回值类型 void
 * @author 范晓文
 * @return 
 * @package名 com.reserch.csv
 * @文件名 CSVReadToPic.java
 */
	
	public static List<NetWorkPicturePO> ReadOneNetWorkLog(String path,Long min,String case_name)
	{
		File file = new File(path);
		List<NetWorkPicturePO> retlist=new ArrayList<NetWorkPicturePO>();
		if(!file.exists()){
			return null;
		}
		else{
			List<List<String>> datalist=new ArrayList<List<String>>();
			datalist=CSVUtils.importCsv(file);
			int time=0;
			for(int i=1;i<datalist.size();i++){
				if(((Long.parseLong(datalist.get(i).get(0))-min)/1000)%5==0){
					NetWorkPicturePO network=new NetWorkPicturePO();
					network.setCell_type(datalist.get(i).get(4));
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					String start_time = sdf.format(new Date(min));
			        network.setStart_time(start_time);
			        time=(int) ((Long.parseLong(datalist.get(i).get(0))-min)/1000+1);
			     
//			        System.out.println(time);
//			        while(Long.parseLong(datalist.get(i).get(0))>min+time*1000){
//                    	time=time+1;
//                    }
//			        System.out.println(time);
			       
					network.setX_data(time);
					network.setCase_name(case_name);
			        network.setCell_type(datalist.get(i).get(4));
					if(datalist.get(i).get(5).equals("gsm") || datalist.get(i).get(5).equals("gprs") || datalist.get(i).get(5).equals("edge") || datalist.get(i).get(5).equals("GSM") || datalist.get(i).get(5).equals("GPRS") || datalist.get(i).get(5).equals("EDGE")){
						network.setY_data(Integer.parseInt(datalist.get(i).get(9)));
					}
					else if(datalist.get(i).get(5).equals("lte") ||datalist.get(i).get(5).equals("LTE") ){
						network.setY_data(Integer.parseInt(datalist.get(i).get(11)));
					}
					else if(datalist.get(i).get(5).equals("TD-SCDMA") || datalist.get(i).get(5).equals("td-scdma")||datalist.get(i).get(5).equals("HSDPA") || datalist.get(i).get(5).equals("hsdpa")||datalist.get(i).get(5).equals("HSPA") || datalist.get(i).get(5).equals("hspa")){
						network.setY_data(Integer.parseInt(datalist.get(i).get(10)));
					}
					retlist.add(network);
				}
			}
			
		}
		return retlist;
	}
	
	/**
	 * 	
	 * @功能 读取单个network文件数据
	 * @日期 2015年11月2日
	 * @返回值类型 void
	 * @author ycliu
	 * @return 
	 * @package名 com.reserch.csv
	 * @文件名 CSVReadToPic.java
	 */
	
	public static List<NetWorkFlowPicturePO> readOneNetWorkFlowLog(String path,Long min,String case_name)
	{
		File file = new File(path);
		List<NetWorkFlowPicturePO> retlist=new ArrayList<NetWorkFlowPicturePO>();
		if(!file.exists()){
			return null;
		}else{
			List<List<String>> datalist=new ArrayList<List<String>>();
			datalist=CSVUtils.importCsv(file);
			int time=0;
			NetWorkFlowPicturePO networkFlow =null;
			for(int i=1;i<datalist.size();i++){
					networkFlow =new NetWorkFlowPicturePO();
					//以最小时间为x轴起点，以5秒（5000ms）为间隔
				    time=(int)((Long.parseLong(datalist.get(i).get(0))-min)/1000);
					networkFlow.setX_data(time);
					if (datalist.get(i).size()>=13) {
						networkFlow.setY_updata(Integer.parseInt(datalist.get(i).get(12)));
						networkFlow.setY_downdata(Integer.parseInt(datalist.get(i).get(13)));
					}
					/*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					String start_time = sdf.format(new Date(Long.parseLong(datalist.get(i).get(0))));
					networkFlow.setStart_time(start_time);*/
					networkFlow.setStart_time(datalist.get(i).get(0));
					retlist.add(networkFlow);
				}
		}
		return retlist;
	}
	
	
	
	/**
	 * 	
	 * @功能 读取单个ftplog文件数据
	 * @日期 2015年11月2日
	 * @返回值类型 void
	 * @author 范晓文
	 * @return 
	 * @package名 com.reserch.csv
	 * @文件名 CSVReadToPic.java
	 */
		
		public static List<FtpPicturePO> ReadOneFtpLog(String path,Long min,String case_name,String ftp_type)
		{
			File file = new File(path);
			List<FtpPicturePO> retlist=new ArrayList<FtpPicturePO>();
			if(!file.exists()){
				return null;
			}
			else{
				List<List<String>> datalist=new ArrayList<List<String>>();
				datalist=CSVUtils.importCsv(file);
				int time_temp=0;
				float speed=0;
				int flag=0;
				int flag1=0;
				int fail_num=0;
				int sucess_num=0;
				String failtag=null;
				String aborttag=null;
				String finishtag=null;
				String starttag=null;
				int k;
				failtag="DownloadFailed";
				aborttag="DownloadAborted";
				finishtag="DownloadFinished";
				starttag="DownloadStarted";
				if(ftp_type=="FTPDownLog.csv"){
					k=4;
				}else{
					k=5;
				}
				
				for(int i=1;i<datalist.size();i++){
					if(datalist.get(i).size()<6){
						continue ;
					}
					if(ftp_type=="FTPUpLog.csv" && datalist.get(i).get(5).equals("-1")){
						  continue ;
						 }
					if(datalist.get(i)!=null&&datalist.get(i).size()>4&&!datalist.get(i).get(4).equals(finishtag)&&
							!datalist.get(i).get(4).equals(starttag)&&Long.parseLong(datalist.get(i).get(0))>=min){
						if(!datalist.get(i).get(4).equals(aborttag)&&!datalist.get(i).get(4).equals(failtag)){						
							  speed+=Float.parseFloat(datalist.get(i).get(k)) ;							 
							  sucess_num++;

						}else{
							fail_num++;
						}
						flag1=2;
	                    if(flag==0){
	                    	time_temp=(int) (((Long.parseLong(datalist.get(i).get(0))-min)/1000+10));
	                    	flag=1;
	                    }
	                    Long t = (Long.parseLong(datalist.get(i).get(0))-min)/1000+1;
	                    if(((Long.parseLong(datalist.get(i).get(0))-min)/1000+1)>time_temp){
	                    	FtpPicturePO ftpllog=new FtpPicturePO();
	                    	ftpllog.setCase_name(case_name);
	                    	ftpllog.setFtp_type(ftp_type);
	                    	ftpllog.setX_data(time_temp);
	                    	if(!datalist.get(i).get(4).equals(aborttag)&&!datalist.get(i).get(4).equals(failtag)){
	                    				float m = ((speed-Float.parseFloat(datalist.get(i).get(k)))/1024/1024*8)/(sucess_num-1);
                                    	ftpllog.setY_data(((speed-Float.parseFloat(datalist.get(i).get(k)))/1024/1024*8)/(sucess_num-1));
                                
		                    		speed=Float.parseFloat(datalist.get(i).get(k));
		                    		sucess_num=1;
	                    		    ftpllog.setFail_num(fail_num);
		    			            fail_num=0;
	 						}else{
	 							if(sucess_num==0){
	 								ftpllog.setY_data(0);
	 							}else{
		 						
		 								
		 								ftpllog.setY_data((speed/1024/1024*8)/sucess_num);
		 						
	 							}
	 						    ftpllog.setFail_num(fail_num-1);
	 							speed=0;
	 							fail_num=1;
	 							sucess_num=0;
	 						}
	                     
	                    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    					String start_time = sdf.format(new Date(min));
	    					ftpllog.setStart_time(start_time);
	    					retlist.add(ftpllog);		 
	                    	time_temp=(int) (((Long.parseLong(datalist.get(i).get(0))-min)/1000+10));
	                    
	                    }
	           
						
					}
				}
				if(flag1==2){
					FtpPicturePO ftpllog=new FtpPicturePO();
                	ftpllog.setCase_name(case_name);
                	ftpllog.setFtp_type(ftp_type);
                	ftpllog.setX_data(time_temp);
                	if(sucess_num==0){
                		ftpllog.setY_data(0);
                	}else{
                
                	ftpllog.setY_data((speed/1024/1024*8)/sucess_num);
                
                	}
                	ftpllog.setFail_num(fail_num);
                	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					String start_time = sdf.format(new Date(min));
					ftpllog.setStart_time(start_time);
					retlist.add(ftpllog);
				}else if(flag1==0)
				{
					retlist=null;
				}
				
				
				
			}
			return retlist;
		}
/**
 * 
 * @功能 获取最小的时间;
 * @日期 2015年11月2日
 * @返回值类型 Long
 * @author 范晓文
 * @throws SQLException 
 * @package名 com.reserch.csv
 * @文件名 CSVReadToPic.java
 */

 
public static Long Getmintimefromlog(String path,String filesname) throws SQLException{
    Long retmin=Long.MAX_VALUE;   			 
	List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
	GetFileName getfilename=new GetFileName();
	listfilename=getfilename.getFolderName(path);
	if(listfilename!=null&& listfilename.size()>0){
		for(FileNamesPO filename:listfilename){			
			File file = new File(path+"/"+filename.getFilename()+"/"+filesname);			
			if(!file.exists()){
				
			}
			else{
				List<List<String>> datalist=new ArrayList<List<String>>();
				datalist=CSVUtils.importCsv(file);			
				if(datalist!=null && datalist.size()>1)
				{
					if(datalist.get(1).get(0)!=null && !datalist.get(1).get(0).equals("")&&Long.parseLong(datalist.get(1).get(0))<retmin&&
							Long.parseLong(datalist.get(1).get(0))>StaticVariable.error){
						retmin=Long.parseLong(datalist.get(1).get(0));
					}
				/*	if(datalist.get(1).get(0)!=null && !datalist.get(1).get(0).equals("")){
					    retmin=Long.parseLong(datalist.get(1).get(0));
					}
					else if(datalist.size()>2&&datalist.get(2).get(0)!=null && !datalist.get(2).get(0).equals("")&&Long.parseLong(datalist.get(2).get(0))<retmin){
						   retmin=Long.parseLong(datalist.get(2).get(0));

						}
					else if(datalist.size()>2&&datalist.get(3).get(0)!=null && !datalist.get(3).get(0).equals("")&&Long.parseLong(datalist.get(3).get(0))<retmin){
						   retmin=Long.parseLong(datalist.get(3).get(0));

						}*/
					
				}
		}
		}
		
	}
return retmin;
 
 
}
 /**
  * 
  * @功能 获取单个pinglog文件的内容
  * @日期 2015年11月23日
  * @返回值类型 List<CallLogPO>
  * @author 范晓文
  * @package名 com.reserch.csv
  * @文件名 CSVReadToPic.java
  */
 
 private static List<CallLogPO> ReadOnePingLog(String path,Long min)
	{
		int fail_num=0;
	    int success_num=0;
		int flag=0;
		int time_temp=1;
		float delay_time=0;
		List<CallLogPO> retlist=new ArrayList<CallLogPO>();			
			List<String> datalist=new ArrayList<String>();
			datalist=ReadTxtByPath.readLogFile(path);
			for(int i=1;i<datalist.size();i++){
				String oneline[] = datalist.get(i).split(","); 
				if(oneline[6].equals("success")){
					success_num++;
				
	                if(flag==0){
	                	time_temp=(int) ((Long.parseLong(oneline[0])-min)/1000+1);
	                	flag=1;
	                }
	                if(((Long.parseLong(oneline[0])-min)/1000+1)>time_temp){
	                	CallLogPO log=new CallLogPO();
						log.setTime(time_temp);
						log.setFail_num(fail_num);
						log.setSuccess_num(success_num-1);
						log.setDelay_time(delay_time);
						
						retlist.add(log);
						time_temp=(int) ((Long.parseLong(oneline[0])-min)/1000+1);
						delay_time=(Float.parseFloat(oneline[5]));
						fail_num=0;
	 					success_num=1;
	 					continue;
	                }
	            	delay_time+=(Float.parseFloat(oneline[5]));
			   }
				else if(oneline[6].equals("timeout")||oneline[6].equals("error")){
					  fail_num++;
					  if(flag==0){
		                	time_temp=(int) ((Long.parseLong(oneline[0])-min)/1000+1);
		                	flag=1;
		                }
		                if(((Long.parseLong(oneline[0])-min)/1000+1)>time_temp){
		                	CallLogPO log=new CallLogPO();
							log.setTime(time_temp);
							log.setFail_num(fail_num-1);
							log.setSuccess_num(success_num);
							log.setDelay_time(delay_time);
							retlist.add(log);
							time_temp=(int) ((Long.parseLong(oneline[0])-min)/1000+1);
							fail_num=1;
		 					success_num=0;
		 					delay_time= 0;
		                }
				}
			}
			if((fail_num!=0||success_num!=0)){
				CallLogPO log=new CallLogPO();
				log.setFail_num(fail_num);
				log.setSuccess_num(success_num);
				log.setDelay_time(delay_time);
				log.setTime(time_temp);
				retlist.add(log);
			}
		
		return retlist;
		
	}
 /**
  * 
  * @功能 获取一个weblog的信息
  * @日期 2015年11月16日
  * @返回值类型 List<CallLogPO>
  * @author 范晓文
  * @package名 com.reserch.csv
  * @文件名 CSVReadToPic.java
  */
 private static List<WebPicturePO> ReadOneWebLog(String path,Long min)
	{
		int fail_num=0;
	    int success_num=0;
		int flag=0;
		int time_temp=1;
		int delay_time=0;
		File file=new File(path);
		List<WebPicturePO> retlist=new ArrayList<WebPicturePO>();			
		List<List<String>> datalist=new ArrayList<List<String>>();
		datalist=CSVUtils.importCsv(file);
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		for(int k=1;k<datalist.size();k++){
			list1.add((Long.parseLong(datalist.get(k).get(0))-min)/1000+1);
		}
		for(int j=1;j<datalist.size();j++){
			list2.add((((Long.parseLong(datalist.get(j).get(0))-min)/1000)/5)*5+5);
		}
			for(int i=1;i<datalist.size();i++){
				
				if(datalist.get(i).get(5).equals("Success")){
					success_num++;
				
	                if(flag==0){
	                	time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
	                	flag=1;
	                }
	                Long long1=(Long.parseLong(datalist.get(i).get(0))-min)/1000+1;
	                if(((Long.parseLong(datalist.get(i).get(0))-min)/1000+1)>time_temp){
	                	WebPicturePO log=new WebPicturePO();
						log.setX_data(time_temp);
						log.setY1_data(fail_num);
						log.setSucess_num(success_num-1);
						log.setY3_data(delay_time);
						
						retlist.add(log);
						time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
						delay_time=(Integer.parseInt(datalist.get(i).get(6)));
						fail_num=0;
	 					success_num=1;
	 					continue;
	                }
	            	delay_time+=Integer.parseInt(datalist.get(i).get(6));
			   }
				else if(datalist.get(i).get(5).equals("Fail")){
					  fail_num++;
					  if(flag==0){
		                	time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
		                	flag=1;
		                }
					  Long long1=(Long.parseLong(datalist.get(i).get(0))-min)/1000+1;
		                if((Long.parseLong(datalist.get(i).get(0))-min)/1000+1>time_temp){
		                	WebPicturePO log=new WebPicturePO();
		                	log.setX_data(time_temp);
							log.setY1_data(fail_num-1);
							log.setSucess_num(success_num);
							log.setY3_data(delay_time);
							
							retlist.add(log);
							time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
							fail_num=1;
		 					success_num=0;
		 					delay_time= 0;
		                }
				}
			}
			if((fail_num!=0||success_num!=0)){
				WebPicturePO log=new WebPicturePO();
				log.setX_data(time_temp);
				log.setY1_data(fail_num);
				log.setSucess_num(success_num);
				log.setY3_data(delay_time);
				retlist.add(log);
			}
		
		return retlist;
		
	}
 
 /**
  * 
  * @功能 获取单个weixin.log的内容
  * @日期 2015年11月24日
  * @返回值类型 List<WebPicturePO>
  * @author 范晓文
  * @package名 com.reserch.csv
  * @文件名 CSVReadToPic.java
  */
 
 
 private static List<WeiXinPicturePO> ReadOneWeiXinLog(String path,Long min,String type)
	{
		int fail_num=0;
	    int success_num=0;
		int flag=0;
		int time_temp=1;
		int delay_time=0;
		File file=new File(path);
		List<WeiXinPicturePO> retlist=new ArrayList<WeiXinPicturePO>();			
		List<List<String>> datalist=new ArrayList<List<String>>();
		datalist=CSVUtils.importCsv(file);
			for(int i=1;i<datalist.size();i++){
				
				if(datalist.get(i).get(5).equals("Success")){
					success_num++;
				
	                if(flag==0){
	                	time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
	                	flag=1;
	                }
	                if(((Long.parseLong(datalist.get(i).get(0))-min)/1000+1)>time_temp){
	                	WeiXinPicturePO log=new WeiXinPicturePO();
						log.setX_data(time_temp);
						log.setY1_data(fail_num);
						log.setSucess_num(success_num-1);
						log.setY3_data(delay_time);
						log.setType(type);
						
						retlist.add(log);
						time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
						delay_time=(Integer.parseInt(datalist.get(i).get(6)));
						fail_num=0;
	 					success_num=1;
	 					continue;
	                }
	            	delay_time+=Integer.parseInt(datalist.get(i).get(6));
			   }
				else if(datalist.get(i).get(5).equals("Fail")){
					  fail_num++;
					  if(flag==0){
		                	time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
		                	flag=1;
		                }
		                if((Long.parseLong(datalist.get(i).get(0))-min)/1000+1>time_temp){
		                	WeiXinPicturePO log=new WeiXinPicturePO();
		                	log.setX_data(time_temp);
							log.setY1_data(fail_num-1);
							log.setSucess_num(success_num);
							log.setY3_data(delay_time);
							log.setType(type);
							retlist.add(log);
							time_temp=(int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)/5)*5+5);
							fail_num=1;
		 					success_num=0;
		 					delay_time= 0;
		                }
				}
			}
			if((fail_num!=0||success_num!=0)){
				WeiXinPicturePO log=new WeiXinPicturePO();
				log.setX_data(time_temp);
				log.setY1_data(fail_num);
				log.setSucess_num(success_num);
				log.setY3_data(delay_time);
				log.setType(type);
				retlist.add(log);
			}
		
		return retlist;
		
	}
 
/**
 * 
 * @功能  读取小区切换LOG并处理
 * @日期 2016年2月26日
 * @返回值类型 List<CellChangePicturePO>
 * @author 范晓文
 * @package名 com.reserch.csv
 * @文件名 CSVReadToPic.java
 */
 
 private static List<CellChangePicturePO> ReadOneCellChangeLog(String path,Long min)
	{
		int flag=0;
		int flag1=0;
		int max_time=0;
		int min_time=0;
		int total_time=0;
		long start_time=0;
		String cellid=null;
		File file=new File(path);
		List<CellChangePicturePO> retlist=new ArrayList<CellChangePicturePO>();			
		List<List<String>> datalist=new ArrayList<List<String>>();
		datalist=CSVUtils.importCsv(file);
			for(int i=1;i<datalist.size();i++){
				if((datalist.get(i).get(5).equals("gsm") || datalist.get(i).get(5).equals("gprs") || datalist.get(i).get(5).equals("edge") || datalist.get(i).get(5).equals("GSM") || datalist.get(i).get(5).equals("GPRS") || datalist.get(i).get(5).equals("EDGE") ||datalist.get(i).get(5).equals("lte") ||datalist.get(i).get(5).equals("LTE")||datalist.get(i).get(5).equals("TD-SCDMA") || datalist.get(i).get(5).equals("td-scdma")||datalist.get(i).get(5).equals("HSDPA") || datalist.get(i).get(5).equals("hsdpa")||datalist.get(i).get(5).equals("HSPA") || datalist.get(i).get(5).equals("hspa"))){
					if(flag==0){
					    cellid=datalist.get(i).get(4);
					    start_time=Long.parseLong(datalist.get(i).get(0));
	                    flag=1;
	                    continue;
					}
					if(!datalist.get(i).get(4).equals(cellid) || flag1==1){
						if(datalist.get(i).get(4).equals("unknown")||datalist.get(i).get(4).equals("null")){
							if(flag1==0){
							  CellChangePicturePO log=new CellChangePicturePO();
	                          log.setX_data((int) (((start_time-min)/1000)+1));
	                          log.setY1_data(1);
	                          log.setY2_data(0);
	                          log.setY3_data(0);
	                          log.setY4_data(0);
	                          retlist.add(log);
							}
							cellid=datalist.get(i).get(4);
							flag1=1;
							continue;
						}
						else if(flag1!=1){
							CellChangePicturePO log=new CellChangePicturePO();
	                          log.setX_data((int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)+1)));
	                          log.setY1_data(1);
	                          log.setY2_data(0);
	                          log.setY3_data(0);
	                          log.setY4_data(0);
	                          retlist.add(log);
	                          cellid=datalist.get(i).get(4);
						}
						else{
							int time=(int)((Long.parseLong(datalist.get(i).get(0))-start_time));				            
			                max_time=time;
			                min_time=time;   
			                total_time=time;
			                flag1=0;
			                CellChangePicturePO log=new CellChangePicturePO();
	                          log.setX_data((int) ((((Long.parseLong(datalist.get(i).get(0))-min)/1000)+1)));
	                          log.setY1_data(0);
	                          log.setY2_data(max_time);
	                          log.setY3_data(min_time);
	                          log.setY4_data(total_time);
	                          retlist.add(log);
	                          start_time=Long.parseLong(datalist.get(i).get(0));
	                          cellid=datalist.get(i).get(4);
						}
						
					}
					 else{
			             start_time=Long.parseLong(datalist.get(i).get(0));
			          }
			}
			}
			
		if(retlist.size()==0){
			retlist=null;
		}
		return retlist;
		
	}
 
 
 /**
  * @author 范晓文
  * 将map按照key值从小到大排序
  * @param map
  * @return
  */
 public static Map<Integer,FtpPicturePO> sortMapftpByKey(Map<Integer,FtpPicturePO> map) {
     if (map == null || map.isEmpty()) {
         return null;
     }

     Map<Integer,FtpPicturePO> sortMap = new TreeMap<Integer,FtpPicturePO>(
            new MapKeyComparator());

     sortMap.putAll(map);

     return sortMap;
 }
 
	/**
	 *  @author zzk
	 * 读取一个case下的所有手机短信csv
	 * @param path
	 * @param case_name
	 * @param totalList
	 */
	public static void readAllUESmsLogsByCaseName(String path, String case_name, List<PicUESmsPO> totalList){
		
		if(totalList == null || !StringUtils.hasText(path) || !StringUtils.hasText(case_name)){
			return;
		}
		
		List<FileNamesPO> listfilename=new ArrayList<FileNamesPO>();
		try{
			GetFileName getfilename=new GetFileName();
			listfilename=getfilename.getFolderName(path);
			if(listfilename!=null&& listfilename.size()>0){
				for(FileNamesPO filename:listfilename){
					if(GetFileName.IsExistfile(path+"/"+filename.getFilename(),"smslog.csv")){
						
						readOneSmsLog(path+"/"+filename.getFilename() + "/smslog.csv", case_name, filename.getFilename(), totalList);
						
					}
				}
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 *  @author zzk
	 * 根据logSequenceVOs读取一个case下的所有手机短信csv
	 * @param path
	 * @param case_name
	 * @param totalList
	 * @param logSequenceVOs
	 */
	public static void readAllUESmsLogsByCaseName(String path, String case_name, List<PicUESmsPO> totalList, List<LogSequenceVO> logSequenceVOs){
		
		if(totalList == null || !StringUtils.hasText(path) || !StringUtils.hasText(case_name) || logSequenceVOs == null || logSequenceVOs.size() == 0){
			return;
		}
		
		Map<String, Integer> logSequenceMap = GetFileName.getLogSequenceMap(logSequenceVOs);
		
		Iterator<Map.Entry<String, Integer>> it = logSequenceMap.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, Integer> entry = it.next();
			String fileName = entry.getKey();
			Integer no = entry.getValue();
			
			if(GetFileName.IsExistfile(path+"/"+fileName + "/" + no,"smslog.csv")){
				
				readOneSmsLog(path+"/"+fileName + "/" + no + "/smslog.csv", case_name, fileName, totalList);
				
			}
		}
	}

	/**
	 * 
	 * 从一个csv文件中提取PicUESmsPO，放入参数totalList
	 * @author zzk
	 * @param path
	 * @param case_name
	 * @param ue_location
	 * @param totalList
	 * */
	private static void readOneSmsLog(String path, String case_name,
			String ue_location, List<PicUESmsPO> totalList) {

		List<String> dataList = new ArrayList<String>();
		List<Long> timeList = new ArrayList<Long>();
		dataList = ReadTxtByPath.readLogFile(path);

		if (dataList != null && dataList.size() > 1) {

			int column = -1;// TimeStamp(ms)列
			String[] titles = dataList.get(0).split(",");

			if (titles != null && titles.length > 0) {
				for (int i = 0; i < titles.length; i++) {
					if ("TimeStamp(ms)".equals(titles[i])) {
						column = i;
					}
				}
			}

			if (column > -1) {

				for (int i = 1; i < dataList.size(); i++) {

					String[] contents = dataList.get(i).split(",");
					if (contents.length == titles.length) {

						try {
							Long time = new Long(contents[column]);

							if (!timeList.contains(time)) {// 不同时间视为有效时间
								timeList.add(time);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

				if (timeList.size() > 0) {

					Collections.sort(timeList);

					for (int i = 0; i < timeList.size(); i++) {
						PicUESmsPO picUESmsPO = new PicUESmsPO();
						picUESmsPO.setCase_name(case_name);
						picUESmsPO.setUe_location(ue_location);
						picUESmsPO.setTime_stamp(timeList.get(i));
						totalList.add(picUESmsPO);
					}
				}
			}
		}
	}
	
	
	


}
