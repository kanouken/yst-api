package com.oz.onestong.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.md5.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oz.onestong.controller.base.Action;
import com.oz.onestong.model.account.Account;
import com.oz.onestong.model.authority.Role;
import com.oz.onestong.model.domain.Domain;
import com.oz.onestong.model.user.User;
import com.oz.onestong.services.admin.domain.DomainService;
import com.oz.onestong.services.authority.AuthorityService;
import com.oz.onestong.services.web.account.AccountService;
import com.oz.onestong.services.web.user.UsersService;
import com.oz.onestong.tools.Constants;

@Controller
public class IndexController extends Action {
	@Autowired
	private UsersService usersService;
	@Autowired
	private AccountService accountService;

	@Autowired
	private DomainService domainService;
	@Autowired
	private AuthorityService authorityService;

	/**
	 * 登陆时 区分是 域的超级用户 还是 普通 用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(Account account, HttpSession session, String accountType) {
		Map<String, Object> localInfoStore = new HashMap<String, Object>();
		OperateResult op = new OperateResult();
		User user = null;
		try {
			if (Constants.DOMAIN_SUPER_USER.equals(accountType.trim())) {

				Domain domainUser = this.domainService.findOneByLoginName(account.getLoginName());
				if (null == domainUser) {
					op.setStatusCode(Constants.USER_NOT_FOUND);
					op.setDescription("用户不存在！");
					op.setData(null);
					return op;
				} else {
					if (!account.getLoginPassword().equals(domainUser.getLoginPassword())) {
						op.setStatusCode(Constants.PASSWORD_INCORRECT);
						op.setDescription("密码不正确！");
						op.setData(null);
						return op;
					} else {

						op.setStatusCode(Constants.HTTP_200);
						op.setDescription("login success!");
						op.setData(null);
						// user
						User superUser = new User();

						Domain domain = new Domain();
						domain.setDomainId(domainUser.getDomainId());
						superUser.setDomainId(domainUser.getDomainId());
						superUser.setRealname(domainUser.getLoginName());
						superUser.setOptional1(domainUser.getDomainName());
						localInfoStore.put("user", superUser);
						localInfoStore.put("accountType", Constants.DOMAIN_SUPER_USER);
						localInfoStore.put("domain", domain);
						session.setAttribute(Constants.SESSION_USER, localInfoStore);
					}
				}

			} else {
				if (StringUtils.isBlank(account.getLoginName()) || StringUtils.isBlank(account.getLoginPassword())) {
					op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
					op.setDescription("email or password must be provided!");
					op.setData(null);

					return op;
				}

				// user = usersService.findoneByEmail(account.getLoginName());
				Account _account = accountService.findOneByAccountLoginName(account.getLoginName());
				if (null == _account) {
					op.setStatusCode(Constants.USER_NOT_FOUND);
					op.setDescription("user not found");
					op.setData(null);
					return op;
				} else {
					if (!account.getLoginPassword().equals(_account.getLoginPassword())) {

						op.setStatusCode(Constants.PASSWORD_INCORRECT);
						op.setDescription("password was not correct!");
						op.setData(null);
						return op;
					} else {
						// 登陆成功 获取权限

						user = this.usersService.findoneByAccountId(_account.getAccountId());
						// TODO null check
						Role role = this.authorityService.findRoleByUser(user);
						if (null == role) {

							op.setStatusCode(Constants.ROLE_NOT_FOUND);
							op.setDescription("user's role can not found!");
							op.setData(null);
							// 登陆失败
							return op;

						}

						// TODO 获取域
						localInfoStore.put("domain", this.domainService.findOneById(user.getDomainId()));
						localInfoStore.put("role", role);
						localInfoStore.put("user", user);
						localInfoStore.put("account", _account);
						// TODO 没有后台权限的用户 禁止登陆 FIXME
						localInfoStore.put(Constants.SESSION_PERM_TIP, this.authorityService.findPermsByRole(role));
						localInfoStore.put("moudles",
								this.authorityService.findMoudleByRole(role, Constants.MOUDLE_ADMIN));

						session.setAttribute(Constants.SESSION_USER, localInfoStore);
					}
					op.setStatusCode(Constants.HTTP_200);
					op.setDescription("login success!");
					op.setData(null);
				}
			}

		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {

				logger.error("管理平台用户登陆 失败", e);
			}
		}

		return op;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView preLogin(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, HttpSession session,
			HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		if (StringUtils.isEmpty(username)) {
			mView.setViewName("login");
		} else {
			// login
			Account account = new Account();
			account.setLoginName(username);
			account.setLoginPassword(password);
			OperateResult result = (OperateResult) this.login(account, session, Constants.DOMAIN_USER);
			if (result.getStatusCode().equals(Constants.HTTP_200)) {
				mView.setViewName("forward:user/list");
			} else {
				this.renderHTML(response, result.getDescription());
				return null;
			}
		}
		return mView;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logOut(HttpSession session,HttpServletRequest request) {
		session.removeAttribute(Constants.SESSION_USER);
		session.invalidate();
		return "redirect:/index";
	}

	@RequestMapping(value = "weide", method = RequestMethod.GET)
	public String test(HttpSession session) {

		return "test/weide";
	}

}
