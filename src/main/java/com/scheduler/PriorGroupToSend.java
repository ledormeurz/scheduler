package com.scheduler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.common.Message;

public class PriorGroupToSend implements IPriorMessageToSend {

	private String lastGroupPrior = null;

	@Override
	public Message getPriorMessageToSend(Collection<Message> messages) {
		Iterator<Message> ite = messages.iterator();
		Message firstMessage = null;
		Message message;
		while(ite.hasNext()){
			message = ite.next();
			if(lastGroupPrior == null){
				if(message.getGroupID() != null){
					lastGroupPrior = message.getGroupID();
				}
				ite.remove();
				return message;
			} else {
				if(lastGroupPrior.equals(message.getGroupID())){
					ite.remove();
					return message;
				}
			}
			if(firstMessage == null){
				firstMessage = message;
			}
		}
		lastGroupPrior = null;
		if(firstMessage != null){
			messages.remove(firstMessage);
			if(firstMessage.getGroupID() != null){
				lastGroupPrior = firstMessage.getGroupID();
			}
		}
		return firstMessage;
	}

}
