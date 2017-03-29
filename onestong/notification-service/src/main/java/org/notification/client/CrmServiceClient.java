package org.notification.client;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.notification.visit.UnSignOutVisit;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "crmService")
public interface CrmServiceClient {
	@RequestMapping(value = "crm/visitEvent/noSignOut", method = RequestMethod.GET)
	public OperateResult<List<UnSignOutVisit>> queryUnSignOut(@RequestHeader("Authorization") String Authorization);
}
