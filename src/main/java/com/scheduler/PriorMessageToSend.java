package com.scheduler;

import java.util.Collection;

import com.common.Message;

public class PriorMessageToSend implements IPriorMessageToSend {

	private IPriorMessageToSend priorMessageToSend;
	
	public PriorMessageToSend() {
		this.priorMessageToSend = new PriorGroupToSend();
	}
	
	public PriorMessageToSend(IPriorMessageToSend priorMessageToSend) {
		this.priorMessageToSend = priorMessageToSend;
	}

	@Override
	public Message getPriorMessageToSend(Collection<Message> messages) {
		return priorMessageToSend.getPriorMessageToSend(messages);
	}
	
	public void setPriorMessageToSend(IPriorMessageToSend priorMessageToSend) {
		this.priorMessageToSend = priorMessageToSend;
	}
	
	
}
