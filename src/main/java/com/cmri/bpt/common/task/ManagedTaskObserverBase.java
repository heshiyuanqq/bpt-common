package com.cmri.bpt.common.task;

public abstract class ManagedTaskObserverBase implements ManagedTaskObserver {
	private String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public abstract void onChange(TaskInfo taskInfo);
}
