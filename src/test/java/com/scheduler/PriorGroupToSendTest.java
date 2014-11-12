package com.scheduler;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.common.Message;

public class PriorGroupToSendTest {


	PriorGroupToSend priorGroupToSend;
	@Before
	public void setUp() throws Exception {
		priorGroupToSend = new PriorGroupToSend();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	Message getMEssage(String group){
		String splited [] = group.split(",");
		Message message = new Message();
		message.setGroupID(splited[0].length() == 0 ? null:splited[0]);
		message.setMessage(splited[1]);
		return message;
	}

	Queue<Message> getQueue(String groups[]){
		Queue<Message> messages = new LinkedList<Message>();
		for(String group : groups){
			messages.add(getMEssage(group));
		}
		return messages;
	}
	
	Queue<Message> processePriority(Queue<Message> messages, PriorGroupToSend priorGroupToSend){
		Queue<Message> orderedMessage = new LinkedList<Message>();
		while(messages.size()>0){
			orderedMessage.add(priorGroupToSend.getPriorMessageToSend(messages));
		}
		return orderedMessage;
	}
	
	@Test
	public void testGetPriorMessageToSend() {
		String[] in = {"G2,1","G1,2","G2,3","G3,4"};
		String[] out = {"G2,1","G2,3","G1,2","G3,4"};
		Queue<Message> messages = getQueue(in);
		Queue<Message> messagesOut = processePriority(messages, priorGroupToSend);
		Queue<Message> messagesExpected = getQueue(out);
		assertTrue(messagesExpected.equals(messagesOut));
	}
	
	@Test
	public void testGetPriorMessageToSend2() {
		String[] in = {",1","G1,2","G2,3","G3,4","G2,5","G1,6"};
		String[] out = {",1","G1,2","G1,6","G2,3","G2,5","G3,4"};
		Queue<Message> messages = getQueue(in);
		Queue<Message> messagesOut = processePriority(messages, priorGroupToSend);
		Queue<Message> messagesExpected = getQueue(out);
		assertTrue(messagesExpected.equals(messagesOut));
	}

}
