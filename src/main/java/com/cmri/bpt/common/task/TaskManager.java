package com.cmri.bpt.common.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmri.bpt.common.base.TargetJudger;
import com.cmri.bpt.common.util.BoolUtil;
import com.cmri.bpt.common.util.CollectionUtil;
import com.cmri.bpt.common.util.StrUtil;

public class TaskManager {
	private final Log logger = LogFactory.getLog(this.getClass());
	//
	public static final String UnNamedGroup = "group.unnamed";
	public static final int DefaultQueueSize = 10;
	//
	private String defaultGroup = UnNamedGroup;
	// 任务组-是否已初始化
	private ReentrantLock initMapLock = new ReentrantLock();
	private Map<String, Boolean> groupInitStateMap = new ConcurrentHashMap<String, Boolean>();
	// 任务组的任务-是否执行中
	private ReentrantLock stateMapLock = new ReentrantLock();
	private Map<String, Boolean> groupTasksStateMap = new ConcurrentHashMap<String, Boolean>();
	// 任务组-任务
	private ReentrantLock taskMapLock = new ReentrantLock();
	private Map<String, ArrayBlockingQueue<ManagedTask>> groupTasksMap = new ConcurrentHashMap<String, ArrayBlockingQueue<ManagedTask>>();
	// 所有（未终止的）任务id的快照
	private Map<String, List<Integer>> aliveTaskIdsMap = new ConcurrentHashMap<String, List<Integer>>();
	// 任务组-观察者
	private ReentrantLock observerMapLock = new ReentrantLock();
	private Map<String, List<ManagedTaskObserver>> groupOberversMap = new ConcurrentHashMap<String, List<ManagedTaskObserver>>();

	//
	public String getDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(String defaultGroup) {
		if (!StrUtil.hasText(defaultGroup)) {
			throw new RuntimeException("默认任务组名不可为空");
		}
		this.defaultGroup = defaultGroup;
	}

	private List<Integer> getAliveTaskIds(String group) {
		return aliveTaskIdsMap.get(group);
	}

	private ArrayBlockingQueue<ManagedTask> getGroupTasks(String group) {
		ArrayBlockingQueue<ManagedTask> taskList = groupTasksMap.get(group);
		if (taskList == null) {
			taskMapLock.lock();
			if (taskList == null) {
				taskList = new ArrayBlockingQueue<ManagedTask>(DefaultQueueSize);
				//
				groupTasksMap.put(group, taskList);
				//
				logger.debug("任务组[" + group + "]任务队列已创建");
				// 同时创建对应的未完成任务快照
				List<Integer> aliveTaskIds = new CopyOnWriteArrayList<Integer>();
				aliveTaskIdsMap.put(group, aliveTaskIds);
			}
			taskMapLock.unlock();
		}
		return taskList;
	}

	private List<ManagedTaskObserver> getGroupObservers(String group) {
		List<ManagedTaskObserver> observerList = groupOberversMap.get(group);
		if (observerList == null) {
			observerMapLock.lock();
			if (observerList == null) {
				observerList = new CopyOnWriteArrayList<ManagedTaskObserver>();
				groupOberversMap.put(group, observerList);
				//
				logger.debug("任务组[" + group + "]观察者队列已创建");
			}
			observerMapLock.unlock();
		}
		return observerList;
	}

	private class GroupTasksExecutor extends Thread {
		private String group;

		GroupTasksExecutor(String group) {
			this.group = group;
		}

		@Override
		public void run() {
			while (true) {
				if (groupTasksStateMap.get(group)) {
					try {
						ManagedTask task = getGroupTasks(group).take();
						task.start();
					} catch (InterruptedException e) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void initTasksLoop(String group) {
		Boolean initialized = groupInitStateMap.get(group);
		if (!BoolUtil.isTrue(initialized)) {
			initMapLock.lock();
			initialized = groupInitStateMap.get(group);
			if (!BoolUtil.isTrue(initialized)) {
				groupInitStateMap.put(group, true);
				// 启动任务循环
				new GroupTasksExecutor(group).start();
			}
			initMapLock.unlock();
		}
	}

	public boolean isTasksRunning() {
		return this.isTasksRunning(null);
	}

	public boolean isTasksRunning(String group) {
		if (!StrUtil.hasText(group)) {
			group = this.defaultGroup;
		}
		Boolean running = groupTasksStateMap.get(group);
		return BoolUtil.isTrue(running);
	}

	public void startTasks() {
		this.startTasks(null);
	}

	public void startTasks(String group) {
		if (!StrUtil.hasText(group)) {
			group = this.defaultGroup;
		}
		//
		stateMapLock.lock();
		//
		groupTasksStateMap.put(group, true);
		//
		stateMapLock.unlock();
		//
		initTasksLoop(group);
		//
		logger.debug("任务组[" + group + "] 被请求 启动执行");
	}

	public void stopTasks() {
		this.stopTasks(null);
	}

	public void stopTasks(String group) {
		if (!StrUtil.hasText(group)) {
			group = this.defaultGroup;
		}
		//
		stateMapLock.lock();
		//
		groupTasksStateMap.put(group, false);
		//
		stateMapLock.unlock();
		//
		logger.debug("任务组[" + group + "] 被请求 停止执行");
	}

	public void addTask(ManagedTask task) {
		this.addTask(task, null);
	}

	// 可以继续添加：true, 否则：false
	public void addTask(ManagedTask task, String group) {
		if (task != null) {
			if (!StrUtil.hasText(group)) {
				group = this.defaultGroup;
			}
			ArrayBlockingQueue<ManagedTask> taskList = this.getGroupTasks(group);
			final int taskId = task.getId();
			int index = CollectionUtil.searchIndex(taskList, new TargetJudger<ManagedTask>() {
				@Override
				public boolean isTarget(ManagedTask toBeChecked) {
					return toBeChecked.getId() == taskId;
				}
			});
			if (index == -1) {
				// 添加新任务
				task.setGroup(group);
				task.setTaskManager(this);
				//
				List<ManagedTaskObserver> observers = this.getGroupObservers(group);
				for (ManagedTaskObserver observer : observers) {
					task.addTaskObserver(observer);
				}
				try {
					taskList.put(task);
					//
					List<Integer> aliveTaskIds = this.getAliveTaskIds(group);
					aliveTaskIds.add(task.getId());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void removeAliveTask(ManagedTask task) {
		if (task != null) {
			String group = task.getGroup();
			List<Integer> aliveTaskIds = this.getAliveTaskIds(group);
			int index = aliveTaskIds.indexOf(task.getId());
			if (index != -1) {
				aliveTaskIds.remove(index);
			}
		}
	}

	public boolean isTaskAlive(ManagedTask task) {
		List<Integer> aliveTaskIds = this.getAliveTaskIds(task.getGroup());
		if (aliveTaskIds == null) {
			return false;
		}
		return aliveTaskIds.indexOf(task.getId()) != -1;
	}

	public void addTaskObserver(ManagedTaskObserver observer) {
		this.addTaskObserver(observer, null);
	}

	public void addTaskObserver(ManagedTaskObserver observer, String group) {
		if (observer != null) {
			if (!StrUtil.hasText(group)) {
				group = this.defaultGroup;
			}
			List<ManagedTaskObserver> observerList = this.getGroupObservers(group);
			if (!observerList.contains(observer)) {
				observer.setGroup(group);
				observerList.add(observer);
				//
				ArrayBlockingQueue<ManagedTask> tasks = this.getGroupTasks(group);
				for (ManagedTask task : tasks) {
					task.addTaskObserver(observer);
				}
			}
		}
	}

	public void removeTaskObserver(ManagedTaskObserver observer) {
		if (observer != null) {
			String group = observer.getGroup();
			List<ManagedTaskObserver> observerList = this.getGroupObservers(group);
			if (observerList.indexOf(observer) != -1) {
				observerList.remove(observer);
				//
				ArrayBlockingQueue<ManagedTask> tasks = this.getGroupTasks(group);
				for (ManagedTask task : tasks) {
					task.removeTaskObserver(observer);
				}
			}
		}
	}
}
