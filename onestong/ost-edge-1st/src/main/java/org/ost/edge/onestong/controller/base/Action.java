package org.ost.edge.onestong.controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.common.tools.db.Page;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.tools.Constants;
import org.ost.entity.user.Users;

/**
 * 控制器基类
 * 
 * @author 夏苗苗
 * 
 */
public class Action {
	public static final String PAGE_CURRENT = "curPage";
	public static final String PAGE_CURRENT_DEFAULT = "1";
	public static final String PAGE_PER_SIZE = "perPageSum";
	public static final String PAGE_PER_SIZE_DEFAULT = "20";
	/**
	 * 个信属性
	 */

	// @Value("#{application['applicationPath']}")
	// public String applicationPath;
	// @Value("#{application['app_version']}")
	// public String appVersion;
	public static final String OPARATION_FAILED = "0";
	public static final String OPARATION_SUCCESS = "1";

	protected static final String PAGE = "page";

	public static final String HTTP_200 = Constants.HTTP_200;
	public static final String HTTP_500 = Constants.SERVER_ERROR;
	public static final String SERVER_ERROR_TIP = Constants.SERVER_ERROR_TIP;

	public static final String SUCCESS_MSG = "操作成功";
	public static final String FAIL_MSG = "操作失败";
	public final static String INDEX = "admin/index";

	protected Log logger = LogFactory.getLog(this.getClass());

	@SuppressWarnings("rawtypes")
	protected class BSIHttpServletRequestWrapper extends HttpServletRequestWrapper {
		HttpServletRequest req;

		public BSIHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			this.req = request;
		}

		@SuppressWarnings("unchecked")
		public Map getParameterMap() {

			Map map = new HashMap();
			Map m = req.getParameterMap();
			for (Object k : m.keySet()) {
				map.put(k, req.getParameter(k.toString()));
			}

			return map;
		}
	}

	protected Object sessionStroe(HttpSession session, String key) {
		return session.getAttribute(key);
	}

	/**
	 * 渲染数据。主要功能如下： 1. 非缓存数据 2. 写入内容 3. 强制刷新输出缓存
	 * 
	 * @param res
	 * @param s
	 * @param contextType
	 */

	protected int getPageCount(Page g) {

		return g.getTotalRecords() % g.getPerPageSum() == 0 ? g.getTotalRecords() / g.getPerPageSum()
				: g.getTotalRecords() / g.getPerPageSum() + 1;

	}

	public enum OST_MOUDLES {
		ost_moudle_users, // 用户管理
		ost_moudle_depts, // 部门管理
		ost_moulde_roles, // 角色管理
		ost_moudle_chart, ost_moudle_p_tags, // 项目标签
		ost_moudle_c_tags, // 客户标签

		ost_moudle_worktime, // 工作时间设置
		ost_moudle_attence_report,
		/**
		 * 客户端模块
		 */
		ost_app_moudle_events, // 事件
		ost_app_moudle_my, // 我
		ost_app_moudle_find, // 发现

	}

	// 客户端权限 标识
	public enum OST_APP_PERM {
		self_below_events, // 自己以及下属的事件 ，例如部门管理员 或其他
		all_events, // 所有事件 ，例如总经理 等等
		create_attence, // 创建 签到 签退 事件
		create_visit, // 创建 外访事件
		self_below_reports, // 自己以及自己下属的 报表（快捷报表 与 轨迹地图）
		all_reports

	}

	/**
	 * 
	 */
	/**
	 * {moudles=[{moudleId=1, moudleName=用户管理, moudleIco=am-icon-user,
	 * moudleUri=/user/list, moudleTag=ost_moudle_users}, {moudleId=2,
	 * moudleName=部门管理, moudleIco=am-icon-group, moudleUri=/department/list,
	 * moudleTag=ost_moudle_depts}, {moudleId=3, moudleName=角色管理,
	 * moudleIco=am-icon-wrench, moudleUri=/role/list,
	 * moudleTag=ost_moudle_roles}], perms=[{permName=添加用户, pmId=1,
	 * methodName=addUser}, {permName=更新用户, pmId=1, methodName=updateUser},
	 * {permName=禁用用户, pmId=1, methodName=deleteUser}, {permName=解绑用户设备, pmId=1,
	 * methodName=unBindUser}, {permName=查看用户(本部门成员), pmId=1,
	 * methodName=userListOwn}, {permName=添加部门, pmId=2,
	 * methodName=addDepartment}, {permName=删除部门, pmId=2,
	 * methodName=deleteDepartment}, {permName=查看部门, pmId=2,
	 * methodName=departmentList}, {permName=更新部门, pmId=2,
	 * methodName=updateDepartment}, {permName=添加角色, pmId=3,
	 * methodName=addRole}, {permName=更新角色, pmId=3, methodName=updateRole},
	 * {permName=查看角色列表, pmId=3, methodName=roleList}, {permName=删除角色, pmId=3,
	 * methodName=deleteRole}, {permName=接收来自所有其他成员的事件推送, pmId=4,
	 * methodName=all_events}, {permName=创建考勤事件, pmId=4,
	 * methodName=create_attence}, {permName=创建外访事件, pmId=4,
	 * methodName=create_visit}, {permName=查看部门内其他成员的事件简报, pmId=5,
	 * methodName=self_below_reports}],
	 * domain=com.oz.onestong.model.domain.Domain@60b78948,
	 * account=com.oz.onestong.model.account.Account@31960793,
	 * role=com.oz.onestong.model.authority.Role@70cb5d53,
	 * user=com.oz.onestong.model.user.User@375c2dff}
	 */

	public enum OST_OST_PERM {
		addUser, deleteUser, unBindUser, userListOwn, userListAll, listChartOwn, listChartAll

	}

	protected User currentUser() {
//		Users u = new Users();
//		u.settId(1);
//		u.setRealname("test");
		return null;
	}
}
