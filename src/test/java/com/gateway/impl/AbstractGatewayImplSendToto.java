package com.gateway.impl;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lux.MultiThreadedRunner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.common.Message;
import com.gateway.IGateway;
import com.gateway.IGatewayListener;
//@RunWith(MultiThreadedRunner.class)
public class AbstractGatewayImplSendToto {

	protected IGateway gateway;
	protected GatewayListenerImplToto gatewayListener;
	protected Message message;
	protected CountDownLatch wait;
	protected AtomicInteger atomicInteger = new AtomicInteger();
	
	public void setUp() throws Exception {
		//gatewayListener = new GatewayListenerImplTest();
		gateway = new GatewayImpl(5);
		gateway.setGatewayName("Gateway "+atomicInteger.getAndIncrement());
		gateway.startGateway();
		Thread.yield();
		wait = new CountDownLatch(1);
		wait.await(700, TimeUnit.MILLISECONDS);
		
		message = new Message();
		message.setMessage("I'm a message");
		message.setGroupID("DDD");
	}

	public void tearDown() throws Exception {
		gateway.stopGateway();
	}

}
