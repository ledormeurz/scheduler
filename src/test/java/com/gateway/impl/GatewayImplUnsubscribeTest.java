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
@RunWith(MultiThreadedRunner.class)
public class GatewayImplUnsubscribeTest extends AbstractGatewayImplSendToto{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testUnSubscribe() throws InterruptedException {
		gatewayListener = new GatewayListenerImplToto();
		GatewayListenerImplToto gatewayListener = new GatewayListenerImplToto();
		System.out.println(gatewayListener);
		gateway.subscribe(gatewayListener);
		gateway.send(message);
		Thread.yield();
		wait.await(1000, TimeUnit.MILLISECONDS);
		assertTrue(gatewayListener.isReceivedCompleteMessage());
		gateway.unSubscribe(gatewayListener);
		gatewayListener.setReceivedCompleteMessage(false);
		gateway.send(message);
		wait.await(1000, TimeUnit.MILLISECONDS);
		assertFalse(gatewayListener.isReceivedCompleteMessage());
		System.out.println(gatewayListener);
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

}
