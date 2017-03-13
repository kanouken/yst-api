package org.ost.crm.client;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.user.dto.UserListDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "1stService")
public interface OstServiceClient extends BaseClient {

	@RequestMapping(value = "{userId}/scope", method = RequestMethod.GET)
	public OperateResult<List<UserListDto>> queryAccountScope(@PathVariable("userId") Integer userId,
			@RequestHeader(value = "schemaId") String schemaId);
}
