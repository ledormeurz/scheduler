package com.common;

public class Message {

	private String message;
	
	private String groupID;
	
	private boolean endMessage;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	public boolean isEndMessage() {
		return endMessage;
	}
	
	public void setEndMessage(boolean endMessage) {
		this.endMessage = endMessage;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + ", groupID=" + groupID
				+ ", endMessage=" + endMessage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (endMessage ? 1231 : 1237);
		result = prime * result + ((groupID == null) ? 0 : groupID.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (endMessage != other.endMessage)
			return false;
		if (groupID == null) {
			if (other.groupID != null)
				return false;
		} else if (!groupID.equals(other.groupID))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	
	
	
	
}
