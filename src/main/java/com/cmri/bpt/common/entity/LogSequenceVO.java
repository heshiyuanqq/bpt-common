package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class LogSequenceVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String case_file_name;//用例文件名
	private Integer sequence_no;//序列号
	private String finish_flag;//结束标志位
	private String location;//UE位置
	private int flag;
	
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCase_file_name() {
		return case_file_name;
	}
	public void setCase_file_name(String case_file_name) {
		this.case_file_name = case_file_name;
	}
	public Integer getSequence_no() {
		return sequence_no;
	}
	public void setSequence_no(Integer sequence_no) {
		this.sequence_no = sequence_no;
	}
	public String getFinish_flag() {
		return finish_flag;
	}
	public void setFinish_flag(String finish_flag) {
		this.finish_flag = finish_flag;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
