package org.denevell.natch.jerseymvc.app.models;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class AddThreadOutput {
	private ThreadOutput thread;
	private String errorMessage;

	public ThreadOutput getThread() {
		return thread;
	}

	public void setThread(ThreadOutput thread) {
		this.thread = thread;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
