package com.cmri.bpt.common.entity;

import java.io.Serializable;


/**
 * @author 范晓文
 * @parameter 时间，成功次数，失败次数 
 */
public class CallLogPO  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int time;
	private int success_num=0;
	private int fail_num=0;
	private float delay_time=0;
	private int speed=0;
	private int time1;
	@Override  
    public String toString() {  
        return getTime1()+"";  
    }  
	public int getTime1() {
		return time1;
	}
	public void setTime1(int time1) {
		this.time1 = time1;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getSuccess_num() {
		return success_num;
	}
	public void setSuccess_num(int success_num) {
		this.success_num = success_num;
	}
	public int getFail_num() {
		return fail_num;
	}
	public void setFail_num(int fail_num) {
		this.fail_num = fail_num;
	}
	public float getDelay_time() {
		return delay_time;
	}
	public void setDelay_time(float delay_time) {
		this.delay_time = delay_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	
	

}
