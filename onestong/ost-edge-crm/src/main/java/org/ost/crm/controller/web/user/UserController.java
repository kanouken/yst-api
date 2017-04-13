package org.ost.crm.controller.web.user;

import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.web.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/user")
public class UserController extends Action {

	@Autowired
	private UserService userService;

	@PostMapping("login")
	public OperateResult<Map<String,Object>> queryLogin(
			@RequestParam(value="email") String email,
			@RequestParam(value="password")String password) throws Exception{
		return new OperateResult<Map<String,Object>>(this.userService.login(email, password));
	}
}
