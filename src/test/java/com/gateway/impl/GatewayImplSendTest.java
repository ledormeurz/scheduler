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
public class GatewayImplSendTest extends AbstractGatewayImplSendToto{
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	@Test
	public void testSend() throws InterruptedException {
		gatewayListener = new GatewayListenerImplToto();
		gateway.subscribe(gatewayListener);
		gateway.send(message);
		Thread.yield();
		wait.await(700, TimeUnit.MILLISECONDS);
		assertTrue(gatewayListener.isReceivedCompleteMessage());
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}


}
