package com.cmri.bpt.common.entity;

import java.io.Serializable;

public class SingleUeLogPO implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String casename;
    private String ue_id;
    private int user_id;
    private float ftpdown_speed;
    private float ftpup_speed;
   
    private float networkdown_speed;
    private float networkup_speed;
    
    private int weixin_text_delay;
    private int weixin_text_success;
    private int weixin_text_fail;
    private float weixin_text_rate;
   
    public float getNetworkdown_speed() {
		return networkdown_speed;
	}
	public void setNetworkdown_speed(float networkdown_speed) {
		this.networkdown_speed = networkdown_speed;
	}
	public float getNetworkup_speed() {
		return networkup_speed;
	}
	public void setNetworkup_speed(float networkup_speed) {
		this.networkup_speed = networkup_speed;
	}
	private int web_delay;
    private int web_success;
    private int web_fail;
    private float web_rate;
    
    private int call_delay;
    private int call_success;
    private int call_fail;
    private float call_rate;
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	
	public String getUe_id() {
		return ue_id;
	}
	public void setUe_id(String ue_id) {
		this.ue_id = ue_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public float getFtpdown_speed() {
		return ftpdown_speed;
	}
	public void setFtpdown_speed(float ftpdown_speed) {
		this.ftpdown_speed = ftpdown_speed;
	}
	public float getFtpup_speed() {
		return ftpup_speed;
	}
	public void setFtpup_speed(float ftpup_speed) {
		this.ftpup_speed = ftpup_speed;
	}
	public int getWeixin_text_delay() {
		return weixin_text_delay;
	}
	public void setWeixin_text_delay(int weixin_text_delay) {
		this.weixin_text_delay = weixin_text_delay;
	}
	public int getWeixin_text_success() {
		return weixin_text_success;
	}
	public void setWeixin_text_success(int weixin_text_success) {
		this.weixin_text_success = weixin_text_success;
	}
	public int getWeixin_text_fail() {
		return weixin_text_fail;
	}
	public void setWeixin_text_fail(int weixin_text_fail) {
		this.weixin_text_fail = weixin_text_fail;
	}
	public float getWeixin_text_rate() {
		return weixin_text_rate;
	}
	public void setWeixin_text_rate(float weixin_text_rate) {
		this.weixin_text_rate = weixin_text_rate;
	}
	public int getWeb_delay() {
		return web_delay;
	}
	public void setWeb_delay(int web_delay) {
		this.web_delay = web_delay;
	}
	public int getWeb_success() {
		return web_success;
	}
	public void setWeb_success(int web_success) {
		this.web_success = web_success;
	}
	public int getWeb_fail() {
		return web_fail;
	}
	public void setWeb_fail(int web_fail) {
		this.web_fail = web_fail;
	}
	public float getWeb_rate() {
		return web_rate;
	}
	public void setWeb_rate(float web_rate) {
		this.web_rate = web_rate;
	}
	public int getCall_delay() {
		return call_delay;
	}
	public void setCall_delay(int call_delay) {
		this.call_delay = call_delay;
	}
	public int getCall_success() {
		return call_success;
	}
	public void setCall_success(int call_success) {
		this.call_success = call_success;
	}
	public int getCall_fail() {
		return call_fail;
	}
	public void setCall_fail(int call_fail) {
		this.call_fail = call_fail;
	}
	public float getCall_rate() {
		return call_rate;
	}
	public void setCall_rate(float call_rate) {
		this.call_rate = call_rate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
    

  
  
}
