package com.gateway.impl;

import com.gateway.IGatewayListener;

public class GatewayListenerImplToto implements IGatewayListener {

	volatile private boolean receivedCompleteMessage = false;
	
	public GatewayListenerImplToto() {
		System.out.println(" GatewayListenerImplTest const");
	}
	
	public void completed() {
		System.out.println("completed");
		receivedCompleteMessage = true;
	}
	
	public boolean isReceivedCompleteMessage() {
		return receivedCompleteMessage;
	}
	
	public void setReceivedCompleteMessage(boolean receivedCompleteMessage) {
		System.out.println("setReceivedCompleteMessage");
		this.receivedCompleteMessage = receivedCompleteMessage;
	}

}
