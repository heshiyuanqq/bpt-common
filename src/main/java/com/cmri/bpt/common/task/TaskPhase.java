package com.cmri.bpt.common.task;

/**
 * 任务所处的阶段
 * 
 * @author koqiui
 * 
 */
public enum TaskPhase {
	start("启动"), going("进行中"), end("结束");

	private String text;

	TaskPhase(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
