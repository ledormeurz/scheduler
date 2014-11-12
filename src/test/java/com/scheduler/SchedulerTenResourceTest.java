package com.scheduler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.common.Message;
import com.gateway.IGateway;
import com.gateway.impl.GatewayImpl;

public class SchedulerTenResourceTest {

	private Scheduler scheduler;
	private IGateway gateway;
	private GatewayListenerImplToto gatewayListenerImplToto;
	@Before
	public void setUp() throws Exception {
		int nbResources = 10;
		gateway = new GatewayImpl(nbResources);
		gatewayListenerImplToto = new GatewayListenerImplToto();
		gateway.subscribe(gatewayListenerImplToto);
		scheduler = new Scheduler(nbResources, gateway);
	}

	
	
	List<Message> getQueue(List<String> groups){
		List<Message> messages = new LinkedList<Message>();
		for(String group : groups){
			messages.add(getMEssage(group));
		}
		return messages;
	}
	
	Message getMEssage(String group){
		String splited [] = group.split(",");
		Message message = new Message();
		message.setGroupID(splited[0].length() == 0 ? null:splited[0]);
		message.setMessage(splited[1]);
		return message;
	}

	List<Message> getQueue(String groups[]){
		List<Message> messages = new LinkedList<Message>();
		for(String group : groups){
			messages.add(getMEssage(group));
		}
		return messages;
	}

	@Test
	public void testScheduler() throws ErrorMessage, InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		String[] in = {"G2,1","G1,2","G2,3","G3,4"};
		List<Message> messages = getQueue(in);
		for(Message message : messages){
			scheduler.sendMessage(message);
		}
		
		countDownLatch.await(4, TimeUnit.SECONDS);
		
	}

	

	@Test(expected=ErrorMessage.class)
	public void testCancelGroup() throws ErrorMessage, InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		String[] in = {"G2,1","G1,2","G2,3","G3,4"};
		List<Message> messages = getQueue(in);
		for(Message message : messages){
			scheduler.sendMessage(message);
		}
		scheduler.cancelGroup("G2");
		scheduler.sendMessage(getMEssage("G2,4"));
		
		countDownLatch.await(4, TimeUnit.SECONDS);
	}
	
	@Test(expected=ErrorMessage.class)
	public void testTerminateGroup() throws ErrorMessage, InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		String[] in = {"G2,1","G1,2","G2,3","G3,4"};
		List<Message> messages = getQueue(in);
		for(Message message : messages){
			scheduler.sendMessage(message);
		}
		Message terminateMessage = getMEssage("G2,4");
		terminateMessage.setEndMessage(true);
		scheduler.sendMessage(terminateMessage);
		Message message = getMEssage("G2,5");
		scheduler.sendMessage(message);
		
		countDownLatch.await(4, TimeUnit.SECONDS);
	}

	
	List<String> generateBigData(int nbGroup, int nbMessageMax){
		List<String> messages = new ArrayList<String>();
		Random random = new Random();
		for(int g = 0 ; g < nbGroup ; g++){
			int max = random.nextInt(nbMessageMax+1);
			for(int m = 0; m < max ; m++){
				messages.add("G"+g+",titi"+m);
			}
		}
		return messages;
	}
	
	
	@Test
	public void testMultiMessage() throws ErrorMessage, InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		List<String> infoMessage =  generateBigData(10, 10);
		List<Message> messages = getQueue(infoMessage);
		
		for(Message message : messages){
			scheduler.sendMessage(message);
		}
		countDownLatch.await(1000, TimeUnit.SECONDS);
		
		assertEquals(messages.size(), gatewayListenerImplToto.getNbCompletedMessage());
		
	}

	

}
