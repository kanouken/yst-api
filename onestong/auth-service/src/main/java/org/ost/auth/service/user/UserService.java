//package org.ost.auth.service.user;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.common.tools.OperateResult;
//import org.common.tools.md5.Md5Util;
//import org.common.tools.spring.UniqueTokenGenerator;
//import org.ost.edge.onestong.model.account.Account;
//import org.ost.edge.onestong.model.account.AccountExample;
//import org.ost.edge.onestong.model.authority.Role;
//import org.ost.edge.onestong.model.department.Department;
//import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfo;
//import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfoExample;
//import org.ost.edge.onestong.model.system.location.AttenceLocation;
//import org.ost.edge.onestong.model.system.location.AttenceLocationExample;
//import org.ost.edge.onestong.model.user.User;
//import org.ost.edge.onestong.model.user.UserExample;
//import org.ost.edge.onestong.tools.Constants;
//import org.ost.entity.user.Users;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Service
//public class UserService {
//
//	
//	
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public OperateResult login(Users user)
//			throws NoSuchAlgorithmException {
//		// 根据 邮箱 查找用户
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		OperateResult op = new OperateResult();
//		User user = this.findoneByEmail(email.toString());
//
//		if (user == null) {
//			// 用户不存在
//			op.setStatusCode(Constants.USER_NOT_FOUND);
//			op.setDescription("未知用户!");
//			op.setData(null);
//			return op;
//			// logger.info("login failed because of email not found.");
//		} else {
//
//			// 用户存在 ，并且 该用户的 设备id 不为空 ，并且 此ID 不等于 传过来的id 不允许在此设备商登陆
//			if (StringUtils.isNotBlank(user.getDeviceId()) && !(user.getDeviceId().equals(deviceId))) {
//				op.setStatusCode(Constants.DEVICE_ID_INVALID);
//				op.setDescription("您未被授权在此设备登录!");
//				op.setData(null);
//				return op;
//			} else {
//				// 如果 该用户的 设备 id 为空
//				if (StringUtils.isBlank(user.getDeviceId())) {
//					// 查看该请求的 设备 id 有没有被别人注册
//					if (null != this.findoneByDeviceId(deviceId.toString().trim())) {
//						op.setStatusCode(Constants.DEVICE_ID_ALREADT_RESGISTERED);
//						op.setDescription("您的设备已被其他人绑定! ");
//						op.setData(null);
//						return op;
//					} else {
//						// 绑定 设备id
//						user.setDeviceId(deviceId.toString().trim());
//						this.bindDeviceId(user);
//					}
//				}
//				// 获取 账户
//				AccountExample ae = new AccountExample();
//				ae.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL)
//						.andAccountIdEqualTo(user.getAccountId());
//				Account account = this.accountMapper.selectByExample(ae).get(0);
//
//				AttenceLocationExample ale = new AttenceLocationExample();
//				ale.createCriteria().andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(user.getDomainId());
//				List<AttenceLocation> als = this.attenceLocationMapper.selectByExample(ale);
//				AttenceLocation al = null;
//				if (CollectionUtils.isNotEmpty(als)) {
//					al = als.get(0);
//
//				}
//
//				if (!(account.getLoginPassword().equals(Md5Util.md5(password.toString().trim())))) {
//					op.setStatusCode(Constants.PASSWORD_INCORRECT);
//					op.setDescription("密码不正确！");
//					op.setData(null);
//					return op;
//				} else {
//
//					// 拉取 app权限
//					Role r = this.usersRolesMapper.selectRolesByUser(user.getUserId()).get(0);
//
//					List<Map<String, Object>> perms = this.rolesMoudlesPermsMapper
//							.selectPermsByRoleAndType(r.getRoleId(), Constants.MOUDLE_APP);
//					// 没有 app 权限 不能登陆
//					if (CollectionUtils.isEmpty(perms)) {
//						op.setStatusCode(Constants.USER_NOT_FOUND);
//						op.setDescription("不允许登录！");
//						op.setData(null);
//						return op;
//
//					}
//					// TODO 判断 账户是否在有限期内
//					if (!this.domainOrderService.isValidActive(user.getDomainId())) {
//						op.setStatusCode(Constants.ACTIVE_CODE_INVALID);
//						op.setDescription("您的授权时间已到期了！");
//						op.setData(null);
//						return op;
//
//					}
//
//					// 更新clientid
//
//					UserExample ue = new UserExample();
//					ue.createCriteria().andUserIdEqualTo(user.getUserId());
//					if (null != clientId)
//						user.setcId(clientId.toString().trim());
//					this.userMapper.updateByExampleSelective(user, ue);
//					// 更新token
//					String token = UniqueTokenGenerator.generateUniqueToken();
//					ae.clear();
//					ae.createCriteria().andAccountIdEqualTo(account.getAccountId());
//					account.setUpdateTime(new Date());
//					account.setUpdator(user.getRealname());
//					account.setToken(token);
//					this.accountMapper.updateByExampleSelective(account, ae);
//					op.setStatusCode(Constants.HTTP_200);
//					op.setDescription("login success");
//					Department dept = this.departmentMapper.selectByPrimaryKey(user.getDeptId());
//					resultMap.put("userinfo", user);
//					resultMap.put("account", account);
//					resultMap.put("department", dept);
//					resultMap.put("perms", perms);
//					// 标准考勤地点
//					resultMap.put("attenceLocation", al);
//					// 获取 ibeancon 信息
//
//					IbeaconInfoExample iie = new IbeaconInfoExample();
//					iie.createCriteria().andDepartmentIdEqualTo(user.getDeptId());
//					List<IbeaconInfo> ibeacons = this.ibeaconInfoMapper.selectByExample(iie);
//
//					// iie.createCriteria().andDepartmentIdEqualTo(user.getDeptId());
//					if (!CollectionUtils.isEmpty(ibeacons)) {
//						resultMap.put("ibeaconInfo", ibeacons.get(0));
//					}
//					// 获取所有ibeacon配置 随后 系统升级 客户端请使用coredata 存储
//					ibeacons = this.ibeaconInfoMapper.selectAllSetUpIbeacons();
//					resultMap.put("ibeacons", ibeacons);
//					// 初始化我点赞的事件
//					resultMap.put("myLikedEvents", this.likeMapper.selectMyLikedEventIds(user.getUserId()));
//					// resultMap.put(key, value)
//					op.setData(resultMap);
//					return op;
//
//				}
//			}
//		}
//	}
//
//}
