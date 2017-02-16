package org.ost.edge.onestong.services.web.user;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.image.ImageUtils;
import org.common.tools.md5.Md5Util;
import org.common.tools.spring.UniqueTokenGenerator;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.controller.base.Action.OST_APP_PERM;
import org.ost.edge.onestong.controller.base.Action.OST_OST_PERM;
import org.ost.edge.onestong.dao.account.AccountMapper;
import org.ost.edge.onestong.dao.authority.RolesMoudlesPermsMapper;
import org.ost.edge.onestong.dao.authority.UsersRolesMapper;
import org.ost.edge.onestong.dao.department.DepartmentMapper;
import org.ost.edge.onestong.dao.domain.DomainMapper;
import org.ost.edge.onestong.dao.resources.ResourceMapper;
import org.ost.edge.onestong.dao.scoreSystem.LikeMapper;
import org.ost.edge.onestong.dao.system.ibeacon.IbeaconInfoMapper;
import org.ost.edge.onestong.dao.system.location.AttenceLocationMapper;
import org.ost.edge.onestong.dao.user.UserDao;
import org.ost.edge.onestong.dao.user.UserMapper;
import org.ost.edge.onestong.model.account.Account;
import org.ost.edge.onestong.model.account.AccountExample;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.department.Department;
import org.ost.edge.onestong.model.department.DepartmentExample;
import org.ost.edge.onestong.model.resources.Resource;
import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfo;
import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfoExample;
import org.ost.edge.onestong.model.system.location.AttenceLocation;
import org.ost.edge.onestong.model.system.location.AttenceLocationExample;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.model.user.UserExample;
import org.ost.edge.onestong.model.user.UserExample.Criteria;
import org.ost.edge.onestong.services.admin.domain.DomainOrderService;
import org.ost.edge.onestong.services.authority.AuthorityService;
import org.ost.edge.onestong.tools.Constants;
import org.ost.edge.onestong.tools.ResourceHelper;
import org.ost.entity.user.Users;
import org.ost.entity.user.mapper.UserEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 此类包含与用户相关的业务逻辑 eg：登录 注销 等等
 * 
 * @author mac
 * 
 */
@Service
public class UsersService {

	private static final Logger log = LoggerFactory.getLogger(UsersService.class);
	@Autowired
	private IbeaconInfoMapper ibeaconInfoMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;
	@Autowired
	private RolesMoudlesPermsMapper rolesMoudlesPermsMapper;
	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private DomainOrderService domainOrderService;
	@Autowired
	private DomainMapper domainMapper;

	@Autowired
	private LikeMapper likeMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private AttenceLocationMapper attenceLocationMapper;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void getAllUsers() throws Exception {

	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public int findAllUsersCount(User user, List<Map<String, Object>> perms) {
		UserExample ue = new UserExample();
		Criteria c = ue.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(user.getDomainId());

		if (!AuthorityService.isPermExits(OST_OST_PERM.userListAll, perms)) {
			c.andDeptIdEqualTo(user.getDeptId());

		}
		if (StringUtils.isNotBlank(user.getRealname())) {

			c.andRealnameLike("%" + user.getRealname() + "%");
		}

		return this.userMapper.countByExample(ue);

	}

	@Transactional(readOnly = true)
	public List<User> findAllUsers(User user, RowBounds rb, List<Map<String, Object>> perms) {
		UserExample ue = new UserExample();
		Criteria c = ue.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(user.getDomainId());
		if (!AuthorityService.isPermExits(OST_OST_PERM.userListAll, perms)) {
			c.andDeptIdEqualTo(user.getDeptId());

		}
		if (StringUtils.isNotBlank(user.getRealname())) {

			c.andRealnameLike("%" + user.getRealname() + "%");
		}
		ue.setOrderByClause("update_time desc");

		return userMapper.selectByExample(ue, rb);

	}

	/**
	 * 带 易信 的账户名user accout 链接查询
	 * 
	 * @param integer
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllUsers(Integer integer) {
		// UserExample ue = new UserExample();
		// Criteria c = ue.createCriteria();
		// c.andValidEqualTo(Constants.DATA_VALID);
		// c.andStateEqualTo(Constants.USER_NORMAL);
		// ue.setOrderByClause("update_time desc");

		return userMapper.selectUserAndAccount(integer);

	}

	/**
	 * 是否 拥有管理 权限的标识 目前使用是否有 解绑的 权限
	 * 
	 * @param perms
	 * @return
	 */
	private boolean hasDeptDirectorSymbolOrNot(List<Map<String, Object>> perms) {

		boolean flag = false;
		// Map<String, Object> tmp = null;
		String authTag = "";

		for (Map<String, Object> tmp : perms) {
			authTag = tmp.get("authTag").toString().trim();
			if (authTag.equalsIgnoreCase(Action.OST_OST_PERM.unBindUser.toString())) {

				flag = true;
				break;
			}

		}
		return flag;

	}

	/**
	 * 用户添加 》》》》》 onestong 添加 》》》》》环信添加 》》》》》》
	 * 
	 * @param user
	 *            用户信息
	 * @param account
	 *            账户信息
	 * @param role
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public boolean addUser(User user, Account account, Role role) throws NoSuchAlgorithmException {

		List<Map<String, Object>> perms = this.authorityService.findPermsByRoleAndType(role, Constants.MOUDLE_ADMIN);

		// TODO 添加成功 要发送邮件 //终端登录需要判断 所在域的激活码 是否还有效
		boolean flag = true;
		if (log.isInfoEnabled()) {
			log.info("添加用户开始  操作者：" + user.getCreator());

		}

		/**
		 * 注册IM用户[单个]
		 */
		// TODO 推送修改
		// ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		// String yixinUsername = UUID.randomUUID().toString().replaceAll("-",
		// "");
		// datanode.put("username", yixinUsername);
		// datanode.put("password", Constants.HX_PASSWORD_DEFALULT);
		// datanode.put("nickname", user.getRealname());
		// ObjectNode createNewIMUserSingleNode = EasemobIMUsers
		// .createNewIMUserSingle(datanode);
		//
		// if (null != createNewIMUserSingleNode) {
		//
		// String statusCode = createNewIMUserSingleNode.get("statusCode")
		// .toString();
		//
		// if (!"200".equals(statusCode)) {
		//
		// if (log.isInfoEnabled()) {
		//
		// log.info("环信用户注册 失败 : "
		// + createNewIMUserSingleNode.toString());
		// }
		// flag = false;
		//
		// } else {
		// // 直接 email 登陆
		// account.setLoginName(user.getEmail());
		// account.setYixinAccount(yixinUsername);
		// String orginalPassword = account.getLoginPassword();
		// account.setLoginPassword(Md5Util.md5(account.getLoginPassword()));
		// accountMapper.insertSelective(account);
		// user.setAccountId(account.getAccountId());
		// // 是否 部门管理者
		// if (hasDeptDirectorSymbolOrNot(perms)) {
		// user.setIsDirector(Constants.IS_DEPARTMENT_DIRECTOR);
		//
		// }
		// // 设置部门名称
		// user.setDepartmentName(this.departmentMapper
		// .selectByPrimaryKey(user.getDeptId()).getName());
		//
		// // 设置公司名称
		// user.setCompanyName(this.domainMapper.selectByPrimaryKey(
		// user.getDomainId()).getDomainName());
		// // 设置角色
		// userMapper.insertSelective(user);
		// this.usersRolesMapper.insertRolesAndUser(user.getUserId(),
		// role.getRoleId());
		//
		// if (log.isInfoEnabled()) {
		//
		// log.info("添加用户成功");
		// }
		// MailSenderInfo mailInfo = new MailSenderInfo();
		// mailInfo.setMailServerHost("mx.mc2.cn");
		// mailInfo.setMailServerPort("587");
		// mailInfo.setValidate(true);
		// mailInfo.setUserName("noreply_onestong@mc2.cn");
		// mailInfo.setPassword("Noreply2014WH");
		// mailInfo.setFromAddress("noreply_onestong@mc2.cn");
		// mailInfo.setToAddress(user.getEmail());
		// mailInfo.setSubject("onestong系统管理员");
		// MailHelper sms = new MailHelper();
		// sms.setMailInfo(mailInfo);
		// String contentString = sms.GetSendMsg(user.getRealname(),
		// user.getPhoneNum(), orginalPassword);
		// mailInfo.setContent(contentString);
		// // sms.sendTextMail(mailInfo);
		//
		// sms.start();
		//
		// }
		//
		// } else {
		// flag = false;
		// if (log.isInfoEnabled()) {
		//
		// log.info("环信用户注册 失败 可能的错误 网路异常 ");
		// }
		//
		// }

		return flag;
	}

	@Transactional(readOnly = true)
	public User findUserAndAccountById(Integer userId) {
		return this.userMapper.selectUserAndAccountById(userId);
	}

	@Transactional(readOnly = true)
	public User findOneById(Integer userId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andUserIdEqualTo(userId);

		List<User> users = userMapper.selectByExample(ue);

		if (CollectionUtils.isNotEmpty(users)) {

			return users.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取部门下的人员
	 * 
	 * @param deptId
	 */
	private void fillByUserBlow(Department dept, List<User> users, Boolean fetchDirectorOnly) {

		UserExample ue = new UserExample();
		DepartmentExample de = new DepartmentExample();
		ue.createCriteria().andDeptIdEqualTo(dept.getDeptId()).andValidEqualTo(Constants.DATA_VALID);
		List<User> myDeptMembers = userMapper.selectByExample(ue);
		// 自己部门的人始终需要
		List<User> directors = new ArrayList<User>();
		if (fetchDirectorOnly) {

			for (User u : myDeptMembers) {

				if (u.getIsDirector().equals(Constants.IS_DEPARTMENT_DIRECTOR)) {
					directors.add(u);
				}
			}
			if (CollectionUtils.isNotEmpty(directors)) {
				users.addAll(directors);
			} else {
				users.addAll(myDeptMembers);
			}
		} else {
			users.addAll(myDeptMembers);
		}
		if (dept.getIsLeaf().equals(Constants.IS_LEAF)) {
			return;
		} else {

			// has one or a lot chidren depts

			de.createCriteria().andValidEqualTo(Constants.DATA_VALID).andParentIdEqualTo(dept.getDeptId());
			List<Department> childrenDepts = departmentMapper.selectByExample(de);
			for (Department d : childrenDepts) {

				fillByUserBlow(d, users, true);
			}

		}

	}

	@Transactional(readOnly = true)
	public List<User> findAllUsersDomain(Integer domainId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andDomainIdEqualTo(domainId).andValidEqualTo(Constants.DATA_VALID)
				.andStateEqualTo(Constants.USER_NORMAL);
		return this.userMapper.selectByExample(ue);
	}

	/**
	 * 2015 0602 修改 部门数据递归方式 根据 权限 获取 拉取范围
	 * 
	 * @param user
	 * @param perms
	 * @return
	 */
	@SuppressWarnings("all")
	@Transactional(readOnly = true)
	public List<User> findUserScopes(final User user, List<Map<String, Object>> perms) {
		UserExample ue = new UserExample();
		// 查看所有的事件 ，绝壁 boss 可以做到
		if (AuthorityService.isPermExits(OST_APP_PERM.all_events, perms)) {

			return this.findAllUsersDomain(user.getDomainId());
			// 部门管理员
			// TODO 递归出 下面所有得部门 是否是逻辑部门（部门没有成员 或者 只有一个 人 等）
		} else if (AuthorityService.isPermExits(OST_APP_PERM.self_below_events, perms)) {
			// 部门管理员 可以 看到自己部门的 所有人得 事件
			//
			// ue.createCriteria().andDeptIdEqualTo(user.getDeptId())
			// .andValidEqualTo(Constants.DATA_VALID);
			// 去掉那些 不是发给自己的 任务
			Department dept = departmentMapper.selectByPrimaryKey(user.getDeptId());
			List<User> users = new ArrayList<User>();
			this.fillByUserBlow(dept, users, false);
			return users;
			// return userMapper.selectByExample(ue);
		} else {
			// 屌丝 就只能看看自己的咯
			//
			return new ArrayList<User>() {
				{
					add(user);
				}
			};
		}

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllUsersByDepartId(Integer deptId) {

		return this.userMapper.selectUserAndAccountByDeptId(deptId);

	}

	@Transactional(readOnly = true)
	public User findoneByEmail(String email) {
		UserExample ue = new UserExample();
		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL)
				.andEmailEqualTo(email);
		List<User> users = this.userMapper.selectByExample(ue);
		if (CollectionUtils.isNotEmpty(users)) {
			return users.get(0);
		} else {
			return null;

		}
	}

	@Transactional(readOnly = true)
	public User findoneByDeviceId(String deviceId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL)
				.andDeviceIdEqualTo(deviceId);
		List<User> users = this.userMapper.selectByExample(ue);
		if (CollectionUtils.isNotEmpty(users)) {
			return users.get(0);
		} else {
			return null;

		}
	}

	/**
	 * 根据 账户id查找 用户
	 * 
	 * @param accountId
	 * @return
	 */
	@Transactional(readOnly = true)
	public User findoneByAccountId(Integer accountId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL)
				.andAccountIdEqualTo(accountId);
		List<User> users = this.userMapper.selectByExample(ue);
		if (CollectionUtils.isNotEmpty(users)) {
			return users.get(0);
		} else {
			return null;

		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void bindDeviceId(User user) {

		UserExample ue = new UserExample();
		ue.createCriteria().andUserIdEqualTo(user.getUserId());
		user.setUpdateTime(new Date());
		this.userMapper.updateByExampleSelective(user, ue);
	}

	/**
	 * app 登陆
	 * 
	 * @param email
	 * @param password
	 * @param deviceId
	 * @param clientId
	 *            个推 客户端id
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public OperateResult login(Object email, Object password, Object deviceId, Object clientId)
			throws NoSuchAlgorithmException {
		// 根据 邮箱 查找用户
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OperateResult op = new OperateResult();
		User user = this.findoneByEmail(email.toString());

		if (user == null) {
			// 用户不存在
			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("未知用户!");
			op.setData(null);
			return op;
			// logger.info("login failed because of email not found.");
		} else {

			// 用户存在 ，并且 该用户的 设备id 不为空 ，并且 此ID 不等于 传过来的id 不允许在此设备商登陆
			if (StringUtils.isNotBlank(user.getDeviceId()) && !(user.getDeviceId().equals(deviceId))) {
				op.setStatusCode(Constants.DEVICE_ID_INVALID);
				op.setDescription("您未被授权在此设备登录!");
				op.setData(null);
				return op;
			} else {
				// 如果 该用户的 设备 id 为空
				if (StringUtils.isBlank(user.getDeviceId())) {
					// 查看该请求的 设备 id 有没有被别人注册
					if (null != this.findoneByDeviceId(deviceId.toString().trim())) {
						op.setStatusCode(Constants.DEVICE_ID_ALREADT_RESGISTERED);
						op.setDescription("您的设备已被其他人绑定! ");
						op.setData(null);
						return op;
					} else {
						// 绑定 设备id
						user.setDeviceId(deviceId.toString().trim());
						this.bindDeviceId(user);
					}
				}
				// 获取 账户
				AccountExample ae = new AccountExample();
				ae.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL)
						.andAccountIdEqualTo(user.getAccountId());
				Account account = this.accountMapper.selectByExample(ae).get(0);

				AttenceLocationExample ale = new AttenceLocationExample();
				ale.createCriteria().andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(user.getDomainId());
				List<AttenceLocation> als = this.attenceLocationMapper.selectByExample(ale);
				AttenceLocation al = null;
				if (CollectionUtils.isNotEmpty(als)) {
					al = als.get(0);

				}

				if (!(account.getLoginPassword().equals(Md5Util.md5(password.toString().trim())))) {
					op.setStatusCode(Constants.PASSWORD_INCORRECT);
					op.setDescription("密码不正确！");
					op.setData(null);
					return op;
				} else {

					// 拉取 app权限
					Role r = this.usersRolesMapper.selectRolesByUser(user.getUserId()).get(0);

					List<Map<String, Object>> perms = this.rolesMoudlesPermsMapper
							.selectPermsByRoleAndType(r.getRoleId(), Constants.MOUDLE_APP);
					// 没有 app 权限 不能登陆
					if (CollectionUtils.isEmpty(perms)) {
						op.setStatusCode(Constants.USER_NOT_FOUND);
						op.setDescription("不允许登录！");
						op.setData(null);
						return op;

					}
					// TODO 判断 账户是否在有限期内
					if (!this.domainOrderService.isValidActive(user.getDomainId())) {
						op.setStatusCode(Constants.ACTIVE_CODE_INVALID);
						op.setDescription("您的授权时间已到期了！");
						op.setData(null);
						return op;

					}

					// 更新clientid

					UserExample ue = new UserExample();
					ue.createCriteria().andUserIdEqualTo(user.getUserId());
					if (null != clientId)
						user.setcId(clientId.toString().trim());
					this.userMapper.updateByExampleSelective(user, ue);
					// 更新token
					String token = UniqueTokenGenerator.generateUniqueToken();
					ae.clear();
					ae.createCriteria().andAccountIdEqualTo(account.getAccountId());
					account.setUpdateTime(new Date());
					account.setUpdator(user.getRealname());
					account.setToken(token);
					this.accountMapper.updateByExampleSelective(account, ae);
					op.setStatusCode(Constants.HTTP_200);
					op.setDescription("login success");
					Department dept = this.departmentMapper.selectByPrimaryKey(user.getDeptId());
					resultMap.put("userinfo", user);
					resultMap.put("account", account);
					resultMap.put("department", dept);
					resultMap.put("perms", perms);
					// 标准考勤地点
					resultMap.put("attenceLocation", al);
					// 获取 ibeancon 信息

					IbeaconInfoExample iie = new IbeaconInfoExample();
					iie.createCriteria().andDepartmentIdEqualTo(user.getDeptId());
					List<IbeaconInfo> ibeacons = this.ibeaconInfoMapper.selectByExample(iie);

					// iie.createCriteria().andDepartmentIdEqualTo(user.getDeptId());
					if (!CollectionUtils.isEmpty(ibeacons)) {
						resultMap.put("ibeaconInfo", ibeacons.get(0));
					}
					// 获取所有ibeacon配置 随后 系统升级 客户端请使用coredata 存储
					ibeacons = this.ibeaconInfoMapper.selectAllSetUpIbeacons();
					resultMap.put("ibeacons", ibeacons);
					// 初始化我点赞的事件
					resultMap.put("myLikedEvents", this.likeMapper.selectMyLikedEventIds(user.getUserId()));
					// resultMap.put(key, value)
					op.setData(resultMap);
					return op;

				}
			}
		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void removeToken(User user) {
		AccountExample ae = new AccountExample();
		ae.createCriteria().andAccountIdEqualTo(user.getAccountId());

		Account a = accountMapper.selectByPrimaryKey(user.getAccountId());
		a.setToken(null);

		accountMapper.updateByExample(a, ae);

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateUser(User user, Account account, Role role) throws NoSuchAlgorithmException {
		List<Map<String, Object>> perms = this.authorityService.findPermsByRoleAndType(role, Constants.MOUDLE_ADMIN);
		if (StringUtils.isBlank(account.getLoginPassword())) {
			account.setLoginPassword(null);
		} else {

			account.setLoginPassword(Md5Util.md5(account.getLoginPassword()));
		}

		String newDeptName = this.departmentMapper.selectByPrimaryKey(user.getDeptId()).getName();
		user.setDepartmentName(newDeptName);
		// 是否 部门管理者
		if (hasDeptDirectorSymbolOrNot(perms)) {
			user.setIsDirector(Constants.IS_DEPARTMENT_DIRECTOR);
		}
		this.userMapper.updateByPrimaryKeySelective(user);
		this.accountMapper.updateByPrimaryKeySelective(account);
		// old
		this.usersRolesMapper.deleteRolesAndUser(user.getUserId());
		// new
		this.usersRolesMapper.insertRolesAndUser(user.getUserId(), role.getRoleId());
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void unBindUser(User user) {

		this.userMapper.updateByPrimaryKey(user);

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateUserPic(User user, MultipartFile headPic) throws Exception {
		String thumPic = "";
		String fileName = "";
		if (headPic.getSize() > 0 && !headPic.isEmpty()) {

			fileName = ResourceHelper.saveFile(headPic, user.getEmail());

			Resource r = new Resource();
			r.setCreateTime(user.getUpdateTime());
			r.setUpdateTime(user.getUpdateTime());
			r.setCreator(user.getRealname());
			r.setUpdator(user.getRealname());
			r.setType(Constants.R_IMAGE);
			r.setName(fileName);
			// 图片需要压缩 44*3 44*
			thumPic = new StringBuilder(fileName).insert(fileName.lastIndexOf("."), "_thum").toString();
			ImageUtils.resize(Constants.FILE_SAVE_DIR + fileName, Constants.FILE_SAVE_DIR + thumPic, 44 * 3, 44 * 3);
			// 小图 文件名
			r.setName1(thumPic);
			resourceMapper.insertSelective(r);
			// event_files
		}

		user.setPic(thumPic);
		this.userMapper.updateByPrimaryKeySelective(user);

		return thumPic;
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateUser(User user, Account account) {
		if (null != user)
			this.userMapper.updateByPrimaryKeySelective(user);
		if (null != account)
			this.accountMapper.updateByPrimaryKeySelective(account);
	}

	public static void main(String[] args) {
		System.out.println(new Date(1321942100000l));
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void deleteUser(User user) {

		User old = this.userMapper.selectUserAndAccountById(user.getUserId());
		Account oldAccount = old.getAccount();
		UserExample ue = new UserExample();
		ue.createCriteria().andUserIdEqualTo(user.getUserId());
		old.setValid(Constants.DATA_INVALID);
		this.userMapper.updateByExampleSelective(old, ue);
		oldAccount.setValid(Constants.DATA_INVALID);
		AccountExample ae = new AccountExample();
		ae.createCriteria().andAccountIdEqualTo(oldAccount.getAccountId());
		this.accountMapper.updateByExampleSelective(oldAccount, ae);

	}

	/**
	 * 获取部门管理者
	 * 
	 * @param departmentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<User> findDeptDirectors(Integer departmentId) {
		List<User> tmps = null;
		UserExample ue = new UserExample();
		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andDeptIdEqualTo(departmentId)
				.andIsDirectorEqualTo(Constants.IS_DEPARTMENT_DIRECTOR);

		tmps = this.userMapper.selectByExample(ue);
		return tmps;

	}

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	public Object queryAllContacts(Users currentUser) {
		Users u = new Users();
		u.settId(currentUser.gettId());
		List<Users> users = this.userDao.select(u);
		if (CollectionUtils.isNotEmpty(users)) {
			return UserEntityMapper.INSTANCE.usersToUserListDtos(users);
		}
		return users;
	}
}
