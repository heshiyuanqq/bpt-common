package com.cmri.bpt.common.task;

import java.util.Date;



import com.cmri.bpt.common.json.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 任务状态
 * 
 * @author koqiui
 * 
 */
public class TaskStatus {
	private TaskPhase phase;
	private Boolean finished = false;
	private String message = null;
	//
	private Integer maxValue = 100;
	private Integer curValue = 0;
	//
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private Date ts = new Date();

	//
	public TaskPhase getPhase() {
		return phase;
	}

	public void setPhase(TaskPhase phase) {
		this.phase = phase;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getCurValue() {
		return curValue;
	}

	public void setCurValue(Integer curValue) {
		this.curValue = curValue;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public void refreshTs() {
		this.setTs(new Date());
	}
}
