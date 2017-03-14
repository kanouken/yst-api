package org.notification.controller;

import org.apache.commons.lang.StringUtils;
import org.common.tools.exception.ApiException;
import org.notification.model.EasemobUser;
import org.notification.service.EasemobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/easemob")
public class Easemob {
	@Autowired
	private EasemobService easemobservice;

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public EasemobUser create(EasemobUser user) throws Exception {
		if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())
				|| StringUtils.isEmpty(user.getAccount())) {
			throw new ApiException("参数不完整", "");
		}
		this.easemobservice.createUser(user);
		return user;
	}

	@RequestMapping(value = "message", method = RequestMethod.GET)
	public Object messages() {
		return "message";
	}

}
