package com.cmri.bpt.common.msg;

public interface MsgSender<TMsg> {

	String getTopic();

	void sendMessage(TMsg msg);
}
