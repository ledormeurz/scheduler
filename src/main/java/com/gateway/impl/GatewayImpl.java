package com.gateway.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.common.Message;
import com.gateway.IGateway;
import com.gateway.IGatewayListener;

public class GatewayImpl implements Runnable, IGateway{

	static Random random = new Random(5); 
	private String name;
	static final Logger LOG= Logger.getLogger(GatewayImpl.class);
	static Thread thread;
	private Executor executor = Executors.newCachedThreadPool();
	private List<IGatewayListener> gatewayListeners = new ArrayList<IGatewayListener>();
	private BlockingQueue<Message> blockingQueue;
	
	public GatewayImpl(int nbResources) {
		blockingQueue = new LinkedBlockingQueue<Message>(nbResources);
	}
	
	public void send(Message message) {
		LOG.info(name+" - Receive message = "+message);
		blockingQueue.add(message);
	}

	public void subscribe(IGatewayListener gatewayListener) {
		LOG.info("subscribe");
		System.out.println(gatewayListener);
		gatewayListeners.add(gatewayListener);
	}
	
	private void notifyCompleted(Message message){
		LOG.info("Complete message = "+ message);
		for(IGatewayListener gatewayListener: gatewayListeners){
			gatewayListener.completed();
		}
	}
	
	public void setGatewayName(String name){
		this.name = name;
	}

	public void unSubscribe(IGatewayListener gatewayListener) {
		LOG.info("unSubscribe "+gatewayListeners.remove(gatewayListener));
	}

	public String getGatewayName() {
		return name;
	}

	public void run() {
		LOG.info("Started Gateway");
		Message message;
		while(true){
			try {
				message = blockingQueue.take();
				executor.execute(new ResourcesTask(message));
			} catch (InterruptedException e) {
				//LOG.error("Brutal stop process", e);
			}
		}
		//LOG.info("End Gateway");
	}
	
	class ResourcesTask implements Runnable{
		Message message;
		public ResourcesTask(Message message) {
			this.message = message;
		}
		@Override
		public void run() {
			LOG.info(name+" - Process message = "+message);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			LOG.info(name+"- End process message = "+message);
			notifyCompleted(message);
		}
		
	}

	public void startGateway() {
		if(thread == null){
			thread = new Thread(this,"Thread "+name);
			
			thread.start();
		}
	}

	public void stopGateway() {
		if(thread != null){
			thread.interrupt();
		}
	}

}
