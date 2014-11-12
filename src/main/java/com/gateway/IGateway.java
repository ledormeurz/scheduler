package com.gateway;

import com.common.Message;

public interface IGateway {

	void setGatewayName(String name);
	
	String getGatewayName();
	
	void send(Message message);
	
	void subscribe( IGatewayListener gatewayClient);
	
	void unSubscribe (IGatewayListener gatewayClient);
	
	void startGateway();
	
	void stopGateway();
}
