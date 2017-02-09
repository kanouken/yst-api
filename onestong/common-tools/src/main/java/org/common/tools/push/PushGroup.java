package org.common.tools.push;

import java.util.List;

public class PushGroup {
	
    private String content;
	
	private String payLoadBody; //type#title#coupontypeId
	
	
	private String cids;
	
	private List<String> tags;
	
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

	

	public String getCids() {
		return cids;
	}

	public void setCids(String cids) {
		this.cids = cids;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
