package com.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import com.gateway.IGatewayListener;

public class GatewayListenerImplToto implements IGatewayListener {

	volatile private boolean receivedCompleteMessage = false;
	
	private AtomicInteger atomicInteger = new AtomicInteger();
	
	public GatewayListenerImplToto() {
		System.out.println(" GatewayListenerImplTest const");
	}
	
	public void completed() {
		System.out.println("completed :"+atomicInteger.incrementAndGet());
		receivedCompleteMessage = true;
	}
	
	public boolean isReceivedCompleteMessage() {
		return receivedCompleteMessage;
	}
	
	public void setReceivedCompleteMessage(boolean receivedCompleteMessage) {
		System.out.println("setReceivedCompleteMessage");
		this.receivedCompleteMessage = receivedCompleteMessage;
	}
	
	public int getNbCompletedMessage(){
		return atomicInteger.get();
	}

}
