package org.notification.controller;

import java.io.IOException;
import java.util.List;

import org.common.tools.OperateResult;
import org.notification.model.PushBody;
import org.notification.model.PushGroup;
import org.notification.service.GeTuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/getui")
public class Getui {
	@Autowired
	private GeTuiService gtService;

	@RequestMapping(value = "single", method = RequestMethod.POST)
	public OperateResult<String> pushSingle(@RequestBody PushBody body) throws IOException {
		return new OperateResult<String>(this.gtService.push(body));
	}

	@RequestMapping(value = "batch", method = RequestMethod.POST)
	public OperateResult<List<String>> pushBatch(@RequestBody PushBody body) throws IOException {
		return new OperateResult<List<String>>(this.gtService.pushBatch(body));
	}

	@RequestMapping(value = "buildgroup", method = RequestMethod.POST)
	public Object buildGroup(PushGroup body) throws IOException {
		return this.gtService.buildGroup(body);
	}

	@RequestMapping(value = "group", method = RequestMethod.POST)
	public Object pushGroup(@RequestBody PushGroup body) throws IOException {
		return this.gtService.pushMessageToGroup(body);
	}
}
