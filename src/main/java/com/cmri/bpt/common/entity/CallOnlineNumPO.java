package com.cmri.bpt.common.entity;

import java.io.Serializable;
/**
 * 
 * @author 范晓文
 * 语音通话在线人数统计
 * 2015年10月21日
 */
public class CallOnlineNumPO implements Serializable{	
	private static final long serialVersionUID = 1L;
	private int id;
	private String case_name;
	private int x_data;	
	private int y_data;
	private String start_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public int getX_data() {
		return x_data;
	}
	public void setX_data(int x_data) {
		this.x_data = x_data;
	}
	public int getY_data() {
		return y_data;
	}
	public void setY_data(int y_data) {
		this.y_data = y_data;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
	
	
	

}
