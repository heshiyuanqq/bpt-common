package com.cmri.bpt.common.msg;

public interface MsgReceiver<TMsg> {
	String getTopic();

	void receiveMessage(TMsg msg);
}
