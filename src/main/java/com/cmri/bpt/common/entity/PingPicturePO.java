package com.cmri.bpt.common.entity;

import java.io.Serializable;
/**
 * PING业务的三个图
 * @author 范晓文
 *
 * 2015年10月26日
 */
public class PingPicturePO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String case_name;
	private int x_data;
	private int y1_data;
	private float y2_data;
	private float y3_data;
	private String start_time;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public float getY3_data() {
		return y3_data;
	}
	public void setY3_data(float y3_data) {
		this.y3_data = y3_data;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	

	
}
