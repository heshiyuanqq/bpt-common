package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class NetWorkFlowPicturePO  implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String uint;//流量单位
	private int x_data;//时间
	private float y_updata;//上传流量值
	private float y_downdata;//下载流量值
	private String start_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUint() {
		return uint;
	}
	public void setUint(String uint) {
		this.uint = uint;
	}
	public int getX_data() {
		return x_data;
	}
	public void setX_data(int x_data) {
		this.x_data = x_data;
	}
	public float getY_updata() {
		return y_updata;
	}
	public void setY_updata(float y_updata) {
		this.y_updata = y_updata;
	}
	public float getY_downdata() {
		return y_downdata;
	}
	public void setY_downdata(float y_downdata) {
		this.y_downdata = y_downdata;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	} 
	
	
}
