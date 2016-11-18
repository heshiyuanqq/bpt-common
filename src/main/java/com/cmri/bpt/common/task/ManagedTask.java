package com.cmri.bpt.common.task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.cmri.bpt.common.util.StrUtil;

public abstract class ManagedTask extends TaskInfo {
	@JsonIgnore
	protected final Log logger = LogFactory.getLog(this.getClass());
	//
	@JsonIgnore
	private TaskManager taskManager = null;
	@JsonIgnore
	private List<ManagedTaskObserver> taskObservers = new CopyOnWriteArrayList<ManagedTaskObserver>();

	//
	public final void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
		//
		logger.debug("任务[" + this.getGroup() + ":" + this.getName() + ":" + this.getId() + "] 已指定管理者 ");
	}

	public final TaskManager getTaskManager() {
		return this.taskManager;
	}

	public final boolean addTaskObserver(ManagedTaskObserver taskObserver) {
		if (taskObserver != null) {
			if (taskObservers.contains(taskObserver)) {
				//
				logger.warn("任务[" + this.getGroup() + ":" + this.getName() + ":" + this.getId() + "] 中已经存在指定的观察者 ");
				//
				return false;
			} else {
				taskObservers.add(taskObserver);
				//
				logger.debug("往任务[" + this.getGroup() + ":" + this.getName() + ":" + this.getId() + "] 中加入了一个观察者 ");
				//
				return true;
			}
		}
		return false;
	}

	public final boolean removeTaskObserver(ManagedTaskObserver taskObserver) {
		if (taskObserver != null) {
			if (taskObservers.contains(taskObserver)) {
				taskObservers.remove(taskObserver);
				//
				logger.debug("从任务[" + this.getGroup() + ":" + this.getName() + ":" + this.getId() + "] 中去掉了一个观察者 ");
				//
				return true;
			} else {
				//
				logger.warn("任务[" + this.getGroup() + ":" + this.getName() + ":" + this.getId() + "] 中并不存在指定的观察者 ");
				//
				return false;
			}
		}
		return false;
	}

	protected void notifyTaskObservers() {
		for (ManagedTaskObserver taskObserver : this.taskObservers) {
			if (taskObserver != null) {
				taskObserver.onChange(this);
			}
		}
	}

	@JsonIgnore
	protected boolean isStarted = false;

	public abstract void start();

	public final void stop(String message) {
		if (!TaskPhase.end.equals(this.getStatus().getPhase())) {
			// 通知任务中止（信息）
			this.getStatus().setPhase(TaskPhase.end);
			if (StrUtil.hasText(message)) {
				this.getStatus().setMessage(message);
			}
			this.getStatus().refreshTs();
			//
			this.notifyTaskObservers();
			//
			logger.warn("任务[" + this.getGroup() + ":" + this.getName() + ":" + this.getId() + "] 已停止 ");
		}
		//
		this.taskObservers.clear();
		//
		this.taskManager.removeAliveTask(this);
	}

	public final void stop() {
		this.stop(null);
	}
}
