package com.cmri.bpt.common.entity;

import java.io.Serializable;
/**
 * 
 * @author 范晓文
 * @功能 小区切换记录
 * @日期 2015年11月26日
 */
public class CellChangePicturePO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String case_name;
	private String start_time; //起始时间
	private int x_data;//x时间坐标，每2秒记录一次
	private int y1_data;//小区切换次数
	private float y2_data;//最大切换时间
	private float y3_data;//最小切换时间
	private float y4_data;//平均切换时间
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
	}
	public float getY4_data() {
		return y4_data;
	}
	public void setY4_data(float y4_data) {
		this.y4_data = y4_data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
  
}
