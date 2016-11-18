package com.cmri.bpt.common.msg;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MsgBroker<TMsg> {
	protected final Log logger = LogFactory.getLog(this.getClass());
	//
	protected static int DefaultQueueSize = 100;
	private int msgQueueSize = DefaultQueueSize;
	//
	private ReentrantLock msgQueueLock = new ReentrantLock();
	protected Map<String, ArrayBlockingQueue<TMsg>> topicMsgQueues = new ConcurrentHashMap<String, ArrayBlockingQueue<TMsg>>();
	//
	private ReentrantLock msgReceiversLock = new ReentrantLock();
	protected Map<String, List<MsgReceiver<TMsg>>> topicMsgReceivers = new ConcurrentHashMap<String, List<MsgReceiver<TMsg>>>();

	public MsgBroker(int msgQueueSize) {
		this.msgQueueSize = msgQueueSize;
	}

	public MsgBroker() {
		//
	}

	protected ArrayBlockingQueue<TMsg> getMsgQueue(String topic) {
		ArrayBlockingQueue<TMsg> msgQueue = this.topicMsgQueues.get(topic);
		if (msgQueue == null) {
			msgQueueLock.lock();
			if (msgQueue == null) {
				msgQueue = new ArrayBlockingQueue<TMsg>(this.msgQueueSize);
				this.topicMsgQueues.put(topic, msgQueue);
				//
				logger.debug("主题消息队列[" + topic + "]已创建");
				//
				new MsgExecutor(topic).start();
			}
			msgQueueLock.unlock();
		}
		return msgQueue;
	}

	protected List<MsgReceiver<TMsg>> getMsgReceivers(String topic) {
		List<MsgReceiver<TMsg>> msgReceivers = this.topicMsgReceivers.get(topic);
		if (msgReceivers == null) {
			msgReceiversLock.lock();
			if (msgReceivers == null) {
				msgReceivers = new CopyOnWriteArrayList<MsgReceiver<TMsg>>();
				this.topicMsgReceivers.put(topic, msgReceivers);
			}
			msgReceiversLock.unlock();
		}
		return msgReceivers;
	}

	// 单消息发送线程
	class MsgSendingThread extends Thread {
		List<MsgReceiver<TMsg>> msgReceivers;
		TMsg msg;

		MsgSendingThread(TMsg msg, List<MsgReceiver<TMsg>> msgReceivers) {
			this.msg = msg;
			this.msgReceivers = msgReceivers;
		}

		@Override
		public void run() {
			for (MsgReceiver<TMsg> msgReceiver : msgReceivers) {
				msgReceiver.receiveMessage(msg);
			}
		}
	}

	// 主题消息执行器
	class MsgExecutor extends Thread {
		private String topic;

		public MsgExecutor(String topic) {
			this.topic = topic;
		}

		@Override
		public void run() {
			while (true) {
				try {
					ArrayBlockingQueue<TMsg> msgQueue = getMsgQueue(topic);
					TMsg msg = msgQueue.take();
					// System.out.println("取出消息："+JsonUtil.toJson(msg));
					List<MsgReceiver<TMsg>> msgReceivers = getMsgReceivers(topic);
					new MsgSendingThread(msg, msgReceivers).start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean addMsgReceiver(MsgReceiver<TMsg> msgReceiver) {
		if (msgReceiver == null) {
			return false;
		} else {
			String topic = msgReceiver.getTopic();
			List<MsgReceiver<TMsg>> msgReceivers = this.getMsgReceivers(topic);
			if (!msgReceivers.contains(msgReceiver)) {
				msgReceivers.add(msgReceiver);
				return true;
			}
			return false;
		}
	}

	public boolean sendMsgFor(MsgSender<TMsg> msgSender, TMsg msg) {
		String topic = msgSender.getTopic();
		ArrayBlockingQueue<TMsg> msgQueue = this.getMsgQueue(topic);
		try {
			msgQueue.put(msg);
			// System.out.println("放入消息："+JsonUtil.toJson(msg));
			//
			return true;
		} catch (InterruptedException e) {
			this.logger.error(e);
			//
			return false;
		}
	}

}
