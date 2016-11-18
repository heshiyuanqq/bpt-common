package com.cmri.bpt.common.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

public class FtpPicturePO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String case_name;   //实例名
	private String ftp_type;    //ftp的类型，上传或者下载
	private String start_time;  //该case中所有ue的ftp任务最小时间
	private int x_data;       //相对时间
	private float y_data;       //速率和
	private String y_Stringdata;//速率和的String
	public String getY_Stringdata() {
		return y_Stringdata;
	}
	public void setY_Stringdata(String y_Stringdata) {
		this.y_Stringdata = y_Stringdata;
	}
	private int fail_num;    //失败次数
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public String getFtp_type() {
		return ftp_type;
	}
	public void setFtp_type(String ftp_type) {
		this.ftp_type = ftp_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public int getX_data() {
		return x_data;
	}
	public void setX_data(int x_data) {
		this.x_data = x_data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getFail_num() {
		return fail_num;
	}
	public void setFail_num(int fail_num) {
		this.fail_num = fail_num;
	}
	public float getY_data() {
		return y_data;
	}
	public void setY_data(float y_data) {
		this.y_data = y_data;
		DecimalFormat df = new DecimalFormat("0.000");
		this.y_Stringdata=df.format(y_data);
		
	}
	
	
 
}
