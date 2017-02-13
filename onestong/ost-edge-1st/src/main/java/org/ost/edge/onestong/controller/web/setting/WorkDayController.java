package org.ost.edge.onestong.controller.web.setting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.anotations.AuthTarget;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.system.worktime.Worktime;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.system.WorktimeService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 工作日 /排班设计
 * 
 * @author mac
 * 
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/setting/worktime")
public class WorkDayController extends Action {

	private @Autowired
	WorktimeService worktimeService;

	@AuthTarget(value = "updateWorktime")
	@RequestMapping(value = "initUpdate", method = RequestMethod.GET)
	public ModelAndView initUpdate(HttpSession session) {
		ModelAndView mv = new ModelAndView("system/worktime/worktime_update");
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		mv.addObject("worktime", this.worktimeService
				.findWorktimeByDomain(sessionUser.getDomainId()));

		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "updateWorktime", method = RequestMethod.POST)
	public Object updateWorktime(
			@RequestParam("startworkTime") String startworkTime,
			@RequestParam("offworkTime") String offworkTime,
			@RequestParam("domainId") Integer domainId,
			@RequestParam("wtId") Integer wtId,HttpSession session
			) {
		
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("HH:mm");
		Worktime wt = null;
		try {
			wt = new Worktime();
			wt.setWtId(wtId);
			wt.setUpdator(sessionUser.getRealname());
			wt.setUpdateTime(new Date());
			wt.setStartworkTime(df.parse(startworkTime));
			wt.setOffworkTime(df.parse(offworkTime));
			wt.setDomainId(domainId);
			this.worktimeService.updateWorktime(wt);
			op.setStatusCode(HTTP_200);
			op.setDescription("工作时间修改成功！");
			op.setData(null);
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("工作时间修改失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

}
