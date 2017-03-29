package org.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class BaseService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${apiToken}")
	protected String apiToken;
	
	
}
