package org.notification.model;

import java.util.List;

public class PushBody {
	private String content;
	
	private String payLoadBody;
	
	private List<String> cids;
	
	private String application;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPayLoadBody() {
		return payLoadBody;
	}

	public void setPayLoadBody(String payLoadBody) {
		this.payLoadBody = payLoadBody;
	}

	public List<String> getCids() {
		return cids;
	}

	public void setCids(List<String> cids) {
		this.cids = cids;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
}
