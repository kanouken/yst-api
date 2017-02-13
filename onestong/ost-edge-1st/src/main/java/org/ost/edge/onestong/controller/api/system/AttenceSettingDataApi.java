package org.ost.edge.onestong.controller.api.system;

import java.util.Date;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.system.location.AttenceLocation;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.system.AttenceSettingService;
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
 * 进行与考勤配置相关的数据交互
 * 
 * @author xnq
 * 
 */
@Controller
@RequestMapping("/api/system/attenceSetting")
public class AttenceSettingDataApi extends Action {

	private @Autowired
	AttenceSettingService attenceSettingService;
	private @Autowired
	UsersService usersService;

	/**
	 * 配置考勤中心坐标 与部门初始化 只有部门管理员才有权限更改 此配置
	 * 
	 * @param token
	 *            精度 纬度 地址 ，允许偏移
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "location/{token}", method = RequestMethod.POST)
	public Object method(@PathVariable("token") String token,
			@RequestParam("entity") String entity) {
		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		JSONObject ibeacon_obj = requestAtt.getJSONObject("attenceLocation");
		AttenceLocation location = (AttenceLocation) JSONObject.toBean(
				ibeacon_obj, AttenceLocation.class);
		Integer userId = requestAtt.getInt("userId");
		User user = null;

		try {
			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("配置 考勤位置 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			Date now = new Date();
			location.setCreateTime(now);
			location.setUpdateTime(now);
			location.setCreator(user.getRealname());
			location.setUpdator(user.getRealname());
			location.setDomainId(user.getDomainId());
			this.attenceSettingService.saveOrUpdateLocation(location);
			op.setData(null);
			op.setDescription("config attence location  success!");
			op.setStatusCode(HTTP_200);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("location 配置失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

}
