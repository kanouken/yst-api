package org.ost.edge.onestong.controller.api.worktime;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.system.worktime.Worktime;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.system.WorktimeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/worktime")
public class WorktimeDataApi extends Action {
	@Autowired
	private WorktimeService worktimeService;
	@Autowired
	private UsersService usersServicer;
	/**
	 * 此处的上班时间与 下班时间 已经格式化为   HH:mm  
	 * @param token
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/{token}", method = RequestMethod.GET)
	public Object getMyWorktime(@PathVariable("token") String token,
			@PathVariable("userId") Integer userId) {
		Worktime wt = null;
		OperateResult op = new OperateResult();
		User user = null;
		try {

			user = this.usersServicer.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("获取工作时间--- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			wt = this.worktimeService.findWorktimeByDomain(user.getDomainId());
			op.setDescription("");
			op.setData(wt);
			op.setStatusCode(HTTP_200);
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("获取工作时间失败", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

}
