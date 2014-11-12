package com.gateway.impl;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import lux.MultiThreadedRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MultiThreadedRunner.class)
public class GatewayImplSubscibeTest extends AbstractGatewayImplSendToto{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testSubscribe() throws InterruptedException {
		gatewayListener = new GatewayListenerImplToto();
		gateway.subscribe(gatewayListener);
		gateway.send(message);
		Thread.yield();
		wait.await(1100, TimeUnit.MILLISECONDS);
		assertTrue(gatewayListener.isReceivedCompleteMessage());
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
