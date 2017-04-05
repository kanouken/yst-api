package org.ost.edge.onestong.controller.api.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.md5.Md5Util;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.account.Account;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.web.account.AccountService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

/**
 * 员工 模块 API
 * 
 * @author mac
 * 
 */
@Api(tags = "用户相关")
@RestController
@RequestMapping("/api/users")
@SuppressWarnings({"unchecked","rawtypes"})
public class UsersDataApi extends Action {

	@Autowired
	private UsersService usersService;
	@Autowired
	private AccountService accountService;

	public void passwordModify() {

	}

	/**
	 * 登陆 首次 登陆 需要绑定事件 验证逻辑为 1. 验证用户登录是否合法（该账号没有呀与其他设备ID绑定，返回 登陆非法 （username
	 * deveid） , ）
	 */
	/**
	 * 登陆 email,password(明码) deviceId 设备id
	 * 
	 * @param entity
	 * @return
	 * 
	 * 
	 * 
	 * 		{"statusCode":"200","description":"login success","data":{
	 *         "account"
	 *         :{"accountId":"7","loginName":"test0127@126.cn","loginPassword"
	 *         :"e10adc3949ba59abbe56e057f20f883e"
	 *         ,"yixinAccount":"b61c4633afa745a79544df6ccd0cc391"
	 *         ,"token":"4ba0a11feaff4a568b11aec26ba65808"
	 *         ,"createTime":"1420608467000"
	 *         ,"updateTime":"1420616024029","creator"
	 *         :"admin","updator":"Judy","valid"
	 *         :"0","state":"0","optional1":"","optional2"
	 *         :"","optional3":""},"userinfo"
	 *         :{"userId":"7","realname":"Judy","email"
	 *         :"test0127@126.cn","deptId"
	 *         :"","accountId":"7","valid":"0","state"
	 *         :"0","pic":"","isDirector":
	 *         "1","createTime":"1420608466000","updateTime"
	 *         :"1420615957660","creator"
	 *         :"","updator":"","optional1":"","optional2"
	 *         :"","optional3":"","deviceId":"3453535-3543543sfsf0sfsf"}}}
	 */
	@ResponseBody
	@PostMapping("login")
	public Object login(@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		try {
			Object email = entity.get("email");
			Object password = entity.get("password");
			Object deviceId = entity.get("deviceId");

			Object clientId = entity.get("clientId");
			if (null == email || null == password || null == deviceId) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("参数不完整 email、password、deviceId、clientId");
				op.setData(null);

				return op;
			}
			op = this.usersService.login(email, password, deviceId, clientId);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("login failed ", e);
				op.setStatusCode(Constants.SERVER_ERROR);
				op.setDescription("服务器异常");
				op.setData(null);
			}
		}
		return op;
	}

	/**
	 * 注销 entity {userId}
	 */
	@ResponseBody
	@PostMapping("logout")
	public Object logout(@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		User user = null;
		try {
			Object userId = entity.get("userId");
			if (null == userId) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid must be provided!");
				op.setData(null);
				return op;
			}

			user = this.usersService.findOneById(Integer.valueOf(userId.toString().trim()));

			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can't found!");
				op.setData(null);
				return op;
			}

			this.usersService.removeToken(user);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("logout success!");
			op.setData(null);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("loginOut failed ", e);
				op.setStatusCode(Constants.SERVER_ERROR);
				op.setDescription("server has gone away ,try again later!");
				op.setData(null);
			}
		}

		return op;
	}

	/**
	 * 修改 手机号 密码
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/profile/update/{token}", method = RequestMethod.POST)
	public Object profileUpdate(@PathVariable("token") String token, @PathVariable("userId") Integer userId,
			@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		User user = null;

		Account account = null;
		// 手机号
		String cellPhone = entity.get("cellPhone") == null ? "" : entity.get("cellPhone").toString();
		// 密码
		String newPassword = entity.get("newPassword") == null ? "" : entity.get("newPassword").toString();
		// 确认密码
		String confirmPassword = entity.get("confirmPassword") == null ? "" : entity.get("confirmPassword").toString();

		String orginalPassword = entity.get("orginalPassword") == null ? "" : entity.get("orginalPassword").toString();

		try {

			user = this.usersService.findOneById(Integer.valueOf(userId.toString().trim()));
			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can't found!");
				op.setData(null);
				return op;
			}
			account = this.accountService.findOneById(user.getAccountId());

			// 密码修改
			if (StringUtils.isNotBlank(orginalPassword) && StringUtils.isNotBlank(newPassword)
					&& StringUtils.isNotBlank(confirmPassword)) {

				if (!account.getLoginPassword().equals(Md5Util.md5(orginalPassword))) {
					op.setStatusCode(Constants.ORGINAL_PASSWORD_INCORRECT);
					op.setDescription("原密码错误！");
					op.setData(null);
					return op;

				} else if (!newPassword.equals(confirmPassword)) {
					op.setStatusCode(Constants.CONFIRM_PASSWORD_ERROR);
					op.setDescription("两次输入的密码不一致！");
					op.setData(null);
					return op;
				} else {
					account.setLoginPassword(Md5Util.md5(newPassword));
					account.setUpdateTime(new Date());
					this.usersService.updateUser(null, account);
					op.setStatusCode(HTTP_200);
					op.setDescription("密码修改成功！");
					op.setData(null);
					return op;
				}
			} else {
				// 其他信息修改
				user.setPhoneNum(cellPhone);
				user.setUpdateTime(new Date());
				this.usersService.updateUser(user, null);

			}

			op.setStatusCode(HTTP_200);
			op.setDescription("update user profile success");
			op.setData(user);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("用户信息修改失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 修改图片 {}
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/headpic/update/{token}", method = RequestMethod.POST)
	public Object headPicUpdate(@PathVariable("token") String token, @PathVariable("userId") Integer userId,
			MultipartFile headPic) {
		OperateResult op = new OperateResult();
		User user = null;
		try {

			user = this.usersService.findOneById(Integer.valueOf(userId.toString().trim()));

			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can't found!");
				op.setData(null);
				return op;
			}

			user.setUpdateTime(new Date());

			this.usersService.updateUserPic(user, headPic);

			op.setStatusCode(HTTP_200);
			op.setDescription("headpic update success");
			op.setData(user);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("用户头像修改失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 返回所有用户 domain用户
	 */
	@ResponseBody
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public Object getAllContacts(@RequestAttribute(value = LOGIN_USER) User user) {
		OperateResult op = new OperateResult();
		List<Map<String, Object>> users = this.usersService.findAllUsers(user.getDomainId());
		if (CollectionUtils.isNotEmpty(users)) {
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("get all contacts success");
			op.setData(users);
		} else {
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("no contacts!");
			op.setData(null);
		}
		return op;

	}

	@RequestMapping(value = "/depts/contacts", method = RequestMethod.GET)
	public OperateResult<List<Map<String,Object>> > queryColleguesByDeptId(@RequestAttribute(value = LOGIN_USER) User user,
			@RequestParam(value = "deptIds") String deptids) {
		return new OperateResult<List<Map<String,Object>> >(this.usersService.findUsersByDeptIds(user, deptids));
	}

	/**
	 * 由 客户端 发起 在易信 添加好友时 通过 昵称 search 返回 在服务器存储的 用户 的易信账户信息
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "search/{nickName}/{token}", method = RequestMethod.GET)
	public Object searchByNickName(@PathVariable("nickName") String nickName, @PathVariable("token") String token)
			throws UnsupportedEncodingException {
		OperateResult op = new OperateResult();
		nickName = new String(nickName.getBytes("iso8859-1"), "UTF-8");
		List<Map<String, Object>> data = null;
		try {
			data = this.accountService.findHxNameByNickName(nickName);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("serach by nick name success!");
			op.setData(data);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("用户真实姓名搜索易信账户 失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("server has gone away,try again later!");
			op.setData(null);
		}
		return op;

	}

	/**
	 * 获取客户标签 与客户分组
	 */
	public void getAllCustomerTag() {

	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "profile/{userId}/{token}", method = RequestMethod.GET)
	public Object userProfile(@PathVariable("userId") Integer userId, @PathVariable("token") String token) {

		OperateResult op = new OperateResult();
		User user = null;

		try {

			user = this.usersService.findUserAndAccountById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("获取个人资料 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			op.setStatusCode(HTTP_200);
			op.setDescription("get user profile success");
			op.setData(user);
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("获取用户信息失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}
}
