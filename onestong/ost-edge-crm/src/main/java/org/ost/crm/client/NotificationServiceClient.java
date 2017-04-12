package org.ost.crm.client;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.notification.PushBody;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "notificationService")
public interface NotificationServiceClient {

	/**
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "getui/single/", method = RequestMethod.POST)
	public OperateResult<String> pushSingle(@RequestBody PushBody body);

	@RequestMapping(value = "getui/batch/", method = RequestMethod.POST)
	public OperateResult<List<String>> pushBatch(@RequestBody PushBody body);

}
