package com.scheduler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.common.Message;
import com.gateway.IGateway;
import com.gateway.IGatewayListener;

public class Scheduler implements IGatewayListener, Runnable {
	static final Logger LOG= Logger.getLogger(Scheduler.class);
	private Semaphore semaphore;
	private int nbResources;
	private IGateway gateway;
	private Queue<Message> queueMessages = new LinkedList<Message>();
	private PriorMessageToSend priorMessageToSend = new PriorMessageToSend();
	private Set<String> setGroupCancelled = new HashSet<String>();
	private Set<String> setGroupEnded = new HashSet<String>();
	private Thread thread;
	public Scheduler(int nbResources, IGateway gateway) {
		this.nbResources = nbResources;
		this.gateway = gateway;
		semaphore = new Semaphore(this.nbResources);
		gateway.subscribe(this);
		thread = new Thread(this);
		gateway.startGateway();
		gateway.setGatewayName("Gateway");
		thread.start();
	}

	synchronized public void sendMessage(Message message) throws ErrorMessage{
		LOG.info("Send message :"+message);
		if(setGroupEnded.contains(message.getGroupID())){
			LOG.error("Received ended group message : "+message.getGroupID());
			throw new ErrorMessage("Received ended group message : "+message.getGroupID());
		}
		if(message.isEndMessage()){
			setGroupEnded.add(message.getGroupID());
		}
		if(setGroupCancelled.contains(message.getGroupID())){
			LOG.error("Received cancelled group message : "+message.getGroupID());
			throw new ErrorMessage("Received cancelled group message : "+message.getGroupID());
		}
		boolean acquire = semaphore.tryAcquire();
		if (acquire && queueMessages.size() == 0) {
			LOG.info("Send Gateway message :"+message);
			gateway.send(message);
		} else {LOG.info("Queue message :"+message);
			queueMessages.add(message);
			notifyAll();
		}
	}

	@Override
	synchronized public void completed() {
		semaphore.release();
		notifyAll();
	}

	synchronized public void cancelGroup(String groupID){
		Iterator<Message> ite = queueMessages.iterator();
		while(ite.hasNext()){
			if(groupID.equals(ite.next().getGroupID())){
				ite.remove();
			}
		}
		setGroupCancelled.add(groupID);
	}
	
	synchronized public void changePriorAlgo(IPriorMessageToSend priorMessageToSendHelper){
		priorMessageToSend.setPriorMessageToSend(priorMessageToSendHelper);
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					if(queueMessages.size() > 0){
						boolean acquire = semaphore.tryAcquire();
						if (acquire) {
							Message nextMessage = priorMessageToSend.getPriorMessageToSend(queueMessages);
							if (nextMessage != null) {
								LOG.info("Send from Queue to Gateway message :"+nextMessage);
								gateway.send(nextMessage);
							}
						} else {
							wait();
						}
					} else {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
