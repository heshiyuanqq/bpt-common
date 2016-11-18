package com.cmri.bpt.common.entity;

import java.io.Serializable;
/**
 * 
 * @author 范晓文
 * 语音通话成功率统计
 * 2015年10月21日
 */
public class CallSuccessRatePO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String case_name;
	private int x_time;
	private float y_rate;
	private String start_time;
	private int successnumber;
	private int failenumber;
	
	public int getSuccessnumber() {
		return successnumber;
	}
	public void setSuccessnumber(int successnumber) {
		this.successnumber = successnumber;
	}
	public int getFailenumber() {
		return failenumber;
	}
	public void setFailenumber(int failenumber) {
		this.failenumber = failenumber;
	}
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
	public int getX_time() {
		return x_time;
	}
	public void setX_time(int x_time) {
		this.x_time = x_time;
	}
	public float getY_rate() {
		return y_rate;
	}
	public void setY_rate(float y_rate) {
		this.y_rate = y_rate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	

}
