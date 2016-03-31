package com.github.aureliano.edocs.common.message;

public class ContextMessage {

	private SeverityLevel severityLevel;
	private String message;
	
	public ContextMessage() {}

	public SeverityLevel getSeverityLevel() {
		return severityLevel;
	}

	public ContextMessage withSeverityLevel(SeverityLevel severityLevel) {
		this.severityLevel = severityLevel;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ContextMessage withMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((severityLevel == null) ? 0 : severityLevel.hashCode());
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
		ContextMessage other = (ContextMessage) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (severityLevel != other.severityLevel)
			return false;
		return true;
	}
}