package com.cmri.bpt.common.entity;

import java.io.Serializable;
/**
 * 
 * @author zzk
 * ue短信统计图
 * 2015年11月06日
 */
public class PicUESmsPO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String case_name;
	private int x_data;
	private int y_data;
	private String start_time;
	private String ue_location;
	private Long time_stamp;
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
	public String getUe_location() {
		return ue_location;
	}
	public void setUe_location(String ue_location) {
		this.ue_location = ue_location;
	}
	public Long getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Long time_stamp) {
		this.time_stamp = time_stamp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
