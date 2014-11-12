package com.scheduler;

import java.util.Collection;

import com.common.Message;

public interface IPriorMessageToSend {

	Message getPriorMessageToSend(Collection<Message> messages);
}
