package org.ost.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

//	@Autowired
//	private UserService userService;
//
//	@RequestMapping(value = "/current", method = RequestMethod.GET)
//	public Principal getUser(Principal principal) {
//		return principal;
//	}
//
//	@RequestMapping(value = "login", method = RequestMethod.POST)
//	public void login() {
//
//	}
	
	@RequestMapping(value = "/delay", method = RequestMethod.GET)
	public String getUser( ) throws InterruptedException {
		
		Thread.sleep(5000);
		
		return "delay";
	}
}
