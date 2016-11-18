package com.cmri.bpt.common.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

public class WeiXinPicturePO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String case_name; //case名称
	private String start_time; //起始记录时间
	private int sucess_num; //成功次数
	private int x_data;  //相对起始时间的时间间隔
	private int y1_data;  //失败次数
	private float y2_data;  //成功率
	private float y3_data;  //时延
	private String y3_Stringdata; //时延 String类型
	private String type; //微信的类型
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public int getSucess_num() {
		return sucess_num;
	}
	public void setSucess_num(int sucess_num) {
		this.sucess_num = sucess_num;
	}
	public int getX_data() {
		return x_data;
	}
	public void setX_data(int x_data) {
		this.x_data = x_data;
	}
	public int getY1_data() {
		return y1_data;
	}
	public void setY1_data(int y1_data) {
		this.y1_data = y1_data;
	}
	public float getY2_data() {
		return y2_data;
	}
	public void setY2_data(float y2_data) {
		this.y2_data = y2_data;
	
	}
	
	public float getY3_data() {
		return y3_data;
		
	}
	public void setY3_data(float y3_data) {
		this.y3_data = y3_data;
		DecimalFormat df = new DecimalFormat("0.000");
		this.y3_Stringdata=df.format(y3_data);
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getY3_Stringdata() {
		return y3_Stringdata;
	}
	public void setY3_Stringdata(String y3_Stringdata) {
		this.y3_Stringdata = y3_Stringdata;
	}
	

}
