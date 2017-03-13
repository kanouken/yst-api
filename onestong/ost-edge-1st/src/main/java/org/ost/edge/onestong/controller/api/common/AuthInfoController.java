package org.ost.edge.onestong.controller.api.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.authority.AuthorityService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.ost.entity.user.dto.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common/auth")
public class AuthInfoController extends Action {
	@Autowired
	private UsersService userService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 获取用户权限下查看范围
	 * 
	 * @param userId
	 * @param schemaId
	 * @return
	 */
	@GetMapping(value = "{userId}/scope")
	public OperateResult<List<UserListDto>> queryAccountScope(@PathVariable("userId") Integer userId,
			@RequestHeader(value = "schemaId") String schemaId) {
		User user = new User();
		user.setUserId(userId);
		try {
			user.setDomainId(Integer.valueOf(schemaId));
		} catch (NumberFormatException e) {
			throw new ApiException("租户信息异常", e.getMessage());
		}
		Role role = this.authorityService.findRoleByUser(user);
		List<Map<String, Object>> perms = this.authorityService.findPermsByRoleAndType(role, Constants.MOUDLE_APP);
		List<User> scopedUsers = this.userService.findUserScopes(user, perms);
		List<UserListDto> records = new ArrayList<UserListDto>();
		if (CollectionUtils.isNotEmpty(scopedUsers)) {
			scopedUsers.forEach(su -> {
				records.add(new UserListDto(su.getUserId() + "", su.getRealname()));
			});
		}
		return new OperateResult<List<UserListDto>>(records);
	}
}
