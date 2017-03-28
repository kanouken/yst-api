package org.ost.crm.controller.web.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/user")
public class UserController {

	@PostMapping("login")
	public Object login(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		return null;
	}
}
