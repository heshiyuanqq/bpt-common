package com.cmri.bpt.common.task;

public interface ManagedTaskObserver {
	public String getGroup();

	public void setGroup(String group);

	public void onChange(TaskInfo taskInfo);
}
