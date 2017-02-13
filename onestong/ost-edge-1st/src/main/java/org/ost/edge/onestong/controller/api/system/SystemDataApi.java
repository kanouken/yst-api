package org.ost.edge.onestong.controller.api.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.dao.authority.RolesMoudlesPermsMapper;
import org.ost.edge.onestong.dao.authority.UsersRolesMapper;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.department.Department;
import org.ost.edge.onestong.model.event.AttenceEvent;
import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfo;
import org.ost.edge.onestong.model.system.location.AttenceLocation;
import org.ost.edge.onestong.model.system.worktime.Worktime;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.AttenceEventService;
import org.ost.edge.onestong.services.system.AttenceSettingService;
import org.ost.edge.onestong.services.system.SystemService;
import org.ost.edge.onestong.services.system.WorktimeService;
import org.ost.edge.onestong.services.web.department.DepartmentService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

/**
 * 保留与 系统 相关的数据接口 例如 版本控制 、 数据同步
 * 
 * @author mac
 * 
 */
@Controller
@RequestMapping("/api/system")
public class SystemDataApi extends Action {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UsersService usersService;

	@Autowired
	private WorktimeService worktimeService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private AttenceEventService  attenceEventService;  	
	@Autowired
	private AttenceSettingService  attenceSettingService;
	/**
	
	 * 版本验证
	 */
	@ResponseBody
	@RequestMapping(value = "app/version/check", method = RequestMethod.GET)
	public Object appVersionValidate() {
		OperateResult op = new OperateResult();
		try {

			Map<String, Object> obj = systemService.appVersionByDevice("ios");
			obj.put("co", obj.get("version_name"));
			obj.put("iu", obj.get("update_now"));

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("get app version success!");
			op.setData(obj);
		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error("get app version failed device:ios", e);
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("server has gone away ,try again later!");
			op.setData(null);
		}
		return op;
	}

	/**
	 * 版本验证
	 */
	@ResponseBody 
	@RequestMapping(value = "app/version/check/{deviceName}", method = RequestMethod.GET)
	public Object appVersionValidateNew(
			@PathVariable("deviceName") String deviceName) {
		OperateResult op = new OperateResult();
		try {
			Map<String, Object> obj = systemService
					.appVersionByDevice(deviceName);
			obj.put("co", obj.get("version_name"));
			obj.put("iu", obj.get("update_now"));
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("get app version success!");
			op.setData(obj);
		} catch (Exception e) {
			if (logger.isErrorEnabled())
				logger.error("get app version failed device:ios", e);
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("server has gone away ,try again later!");
			op.setData(null);
		}
		return op;
	}

	private  @Autowired  RolesMoudlesPermsMapper  rolesMoudlesPermsMapper;
	
	private @Autowired  UsersRolesMapper  usersRolesMapper;
	
	/**
	 * 数据同步接口 在首次登录app 或者 app从后台被唤醒时 进行调用
	 * 
	 * 同步内容 ： 1.工作时间 2.部门数据 3.用户数据
	 * 
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "dataSync/{userId}/{token}", method = RequestMethod.GET)
	public Object dataSync(@PathVariable("userId") Integer userId,
			@PathVariable("token") String token) {
		// 工作时间
		Worktime wt = null;
		
		//考勤地点
		AttenceLocation al = null;
		// 消息返回体
		OperateResult op = new OperateResult();
		User user = null;
		List<Map<String, Object>> domainUsersContact = null;
		List<Map<String, Object>> depts = null;
		
		

		Map<String, Object> toBeWaitedSyncData = new HashMap<String, Object>();
		try {
			// 用户检测
			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("获取工作时间--- 用户不存在");
				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			// 用户检测结束
			// 获取上下班时间 TODO 此处应有复杂的工作时间检测
			wt = this.worktimeService.findWorktimeByDomain(user.getDomainId());
			// 同事数据获取
			domainUsersContact = this.usersService.findAllUsers(user
					.getDomainId());

			// 组织架构数据同步
			depts = this.departmentService
					.findAllDepartmentsAndParentNameFilterByDomain(user
							.getDomainId());
			
			//拉取 app权限
			Role r = this.usersRolesMapper.selectRolesByUser(user.getUserId()).get(0);
			List<Map<String, Object>>  perms =  this.rolesMoudlesPermsMapper.selectPermsByRoleAndType(r.getRoleId(),Constants.MOUDLE_APP);
			toBeWaitedSyncData.put("userinfo", user);
			List<AttenceEvent> attences = attenceEventService
					.findTodayAttenceInfo(user.getUserId());
			
			//考勤地点 同步
			al = this.attenceSettingService.findAttenceLocationByDomain(user.getDomainId());
			toBeWaitedSyncData.put("userTodayAttence", attences);
			toBeWaitedSyncData.put("worktime", wt);
			toBeWaitedSyncData.put("attenceLocation", al);
			toBeWaitedSyncData.put("contacts", domainUsersContact);
			toBeWaitedSyncData.put("departments", depts);
			toBeWaitedSyncData.put("ibeaconInfo",this.systemService.findIbeaconInfo(user.getDeptId()));
			toBeWaitedSyncData.put("ibeacons", this.systemService.findAllIbeacon());
			toBeWaitedSyncData.put("perms",perms);
			op.setDescription("");
			op.setData(toBeWaitedSyncData);
			op.setStatusCode(HTTP_200);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("数据同步失败", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("服务器异常！稍后再试");
			op.setData(toBeWaitedSyncData);
		} finally {
			wt = null;
			user = null;
			domainUsersContact = null;
			depts = null;
			toBeWaitedSyncData = null;
			// op = null;
		}

		return op;
	}

	/**
	 * ibeacon 与部门初始化 只有部门管理员才有权限更改 此配置
	 * 
	 * @param token    {"ibeacon":{"ibeaconUuid":"2222","ibeanconMajor":"333",
	 *            "ibeanconMinor"
	 *            :"222"},"userId":"10"}
	 *            {"ibeacon":{"uuid":""},"userId":"10"}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "ibeaconInfoUpdate/{token}", method = RequestMethod.POST)
	public Object method(@PathVariable("token") String token,
			@RequestParam("entity") String entity) {
 		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		JSONObject ibeacon_obj = requestAtt.getJSONObject("ibeacon");
		IbeaconInfo bean = (IbeaconInfo) JSONObject.toBean(ibeacon_obj,
				IbeaconInfo.class);
		Integer userId = requestAtt.getInt("userId");
		User user = null;
		Department dept;
		try {

			
			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("绑定ibeacon --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			
			dept = this.departmentService.findOne(user.getDeptId());
			Date now = new Date();
			bean.setCreateTime(now);
			bean.setUpdateTime(now);
			bean.setCreator(user.getRealname());
			bean.setUpdator(user.getRealname());
			
			this.systemService.bindIbeaconWithDepartment(bean, dept);
			
			op.setData(null);
			op.setDescription("bind ibeacon success!");
			op.setStatusCode(HTTP_200);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("ibeacon 绑定失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

	
	/**
	 * 查询ibeacon 状态
	 * @param token    
	 *            
	 *           
	 *           
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "ibeaconStatus/{token}", method = RequestMethod.GET)
	public Object ibeaconValid(@PathVariable("token") String token,
			Integer userId, String uuid,String major,String minor) {
 		OperateResult op = new OperateResult();
		try {
			IbeaconInfo info =  attenceEventService.getIbeaconInfo(uuid, major, minor);
			if(null == info){
				op.setData(null);
				op.setDescription("the ibeacon can not connect to any department  !");
				op.setStatusCode(Constants.IBEACON_NOT_SETUP);
			}else{
				op.setData(info);
				op.setDescription("the ibeacon is valid now !");
				op.setStatusCode(HTTP_200);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("ibeacon verfiy error", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

	
}
