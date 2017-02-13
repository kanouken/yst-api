package org.ost.edge.onestong.controller.api.event;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.date.CalendarByTimeZoneHelper;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.event.ApproveEvent;
import org.ost.edge.onestong.model.event.ApproveFlow;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.ApproveEventService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("/api/approval")
public class ApproveDataApi extends Action {
	private @Autowired
	ApproveEventService approveEventService;

	private @Autowired
	UsersService usersService;

	/**
	 * mac$ curl -POST -d
	 * 'entity={"userId":"10","aeId":"9834-skfj-234-skfj-234","content":"somethi
	 * n g rong with this
	 * device","curApprovalExcutor":"9","eventType":"6","planEndTime
	 * ":"2015-01-25","
	 * planStartTime":"2015-01-23","title":"3"  }' "http://localhost
	 * :8080/ONESTONG/api/approval/apply/4448086074174c5aa0554f71fcc1e010"
	 * 
	 * { "aeId": "", 事件 uuid
	 * 
	 * "content": "", 申请审批事由
	 * 
	 * 
	 * "curApprovalExcutor": 0, 当前审批人 "destiAddress": "", 目的地 "deviceName": "",
	 * 设备名称 "eventCreateLatitude": "", 事件创建经度 "eventCreateLongitude": "", 事件创建纬度
	 * "eventType": 0, 审批类型
	 * 
	 * 
	 * "planEndTime": 计划开始事件 "planStartTime": 计划结束事件
	 * 
	 * "title": "", 金额、时间天数
	 * 
	 * "variable": "" }
	 * 
	 * 发送审批申请事件(所有类型)
	 * 
	 * @param token
	 *            token
	 * @param entity
	 *            {"aeId"};
	 * @param image
	 *            审批单截图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "apply/{token}", method = RequestMethod.POST)
	public Object apply(
			@PathVariable("token") String token,
			@RequestParam("entity") String entity,
			@RequestParam(value = "images", required = false) MultipartFile[] images) {

		OperateResult op = new OperateResult();
		User user = null;
		ApproveEvent ae = null;
		try {

			String[] dateFmts = new String[] { "yyyy/MM/dd", "yyyy-MM-dd" };
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpher(dateFmts));
			ae = (ApproveEvent) JSONObject.toBean(
					JSONObject.fromObject(entity), ApproveEvent.class);
			if (null == ae.getUserId()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user'id must be provided!");
				op.setData(null);
			} else if (null == ae.getEventType()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("approve'type must be provided!");
				op.setData(null);

			} else if (null == ae.getCurApprovalExcutor()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("current task excutor must be provided!");
				op.setData(null);
			}
			user = this.usersService.findOneById(ae.getUserId());
			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user was not found!");
				op.setData(null);

			}
			ae = this.approveEventService.createApproval(user, ae, images);

			op.setStatusCode(HTTP_200);
			op.setDescription("审批发送成功！");
			op.setData(ae);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("审批发送失败", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription(SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}

	/**
	 * 
	 * mac$ curl -H
	 * "Content-type:appli,"approvalExcutor":"9","approvalSuggestion":"allow you
	 * to rest your","approvalState":"1"}' "http://localhost:8080/ONESTONG/api/
	 * approval/taskExcute/4448086074174c5aa0554f71fcc1e010"
	 * 
	 * @param token
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "taskExcute/{token}", method = RequestMethod.POST)
	public Object method(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {

		OperateResult op = new OperateResult();
		ApproveFlow af = null;
		User operator = null;

		try {
			af = (ApproveFlow) JSONObject.toBean(JSONObject.fromObject(entity),
					ApproveFlow.class);
			if (StringUtils.isBlank(af.getAeId())
					|| null == af.getApprovalExcutor()
					|| null == af.getApprovalState()
					|| StringUtils.isBlank(af.getApprovalSuggestion())

			) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("uuid or approvalExcutor or state or suggestion were must be provided!");
				op.setData(null);
				return op;
			} else {
				operator = this.usersService.findOneById(Integer.valueOf(af
						.getApprovalExcutor()));

				this.approveEventService.updateApproveFlow(af, operator);
			}

			op.setStatusCode(HTTP_200);
			op.setDescription("审批成功！");
			op.setData(null);
			return op;
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("审批任务执行错误！", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * curl -H "Content-type:application/json" -POST -d
	 * '{"aeId":"9834-sst:8080/ONESTONG/api/approval/forward/4448086074174c5aa0554f71fcc1
	 * e 0 1 0 " 审批单转发 {"aeId":"sfssf-sfsfsf-sfsf",approvalExcutor:""}
	 * 
	 * @param token
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "forward/{token}", method = RequestMethod.POST)
	public Object forward(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {

		OperateResult op = new OperateResult();
		ApproveFlow af = null;
		try {
			af = (ApproveFlow) JSONObject.toBean(JSONObject.fromObject(entity),
					ApproveFlow.class);
			if (StringUtils.isBlank(af.getAeId())
					|| null == af.getApprovalExcutor()) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("approve uuid or approvalExcutor  were must be provided!");
				op.setData(null);
				return op;
			} else {
				this.approveEventService.createForward(af);
				op.setStatusCode(HTTP_200);
				op.setDescription("审批成功！");
				op.setData(null);
				return op;

			}
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("审批单转发失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

	/**
	 * 我的审批历史查询
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/history/{year}/{month}/{curPage}/{pageSum}/{token}", method = RequestMethod.GET)
	public Object approvalHistory(@PathVariable("userId") Integer userId,
			@PathVariable("token") String token,
			@PathVariable("year") Integer year,
			@PathVariable("month") Integer month,
			@PathVariable("curPage") Integer curPage,
			@PathVariable("pageSum") Integer pageSum) {
		OperateResult op = new OperateResult();
		User user = null;
		try {

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("拉取历史审批失败--- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			} else {

				Date yearAndMonth =  CalendarByTimeZoneHelper.makeDate(year, month, 1);
				Page page = new Page();
				page.setCurPage(curPage);
				page.setPerPageSum(pageSum);
				RowBounds rb = new RowBounds(page.getNextPage(),
						page.getPerPageSum());
				
				this.approveEventService.getHistoryFilterByMonthPaged(user,yearAndMonth, rb);
			}

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("拉取审批历史失败 用户 =" + user.getRealname(), e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

	public static void main(String[] args) {
		ApproveFlow ae = new ApproveFlow();
		System.out.println(JSONObject.fromObject(ae).toString());

		// String str =
		// "{\"aeId\":\"asfsaf-asfasf-asfasfsf\",\"commentCount\":0,\"content\":\"\",\"creator\":\"\",\"curApprovalExcutor\":0,\"destiAddress\":\"\",\"deviceName\":\"\",\"eventCreateLatitude\":\"\",\"eventCreateLongitude\":\"\",\"eventType\":0,\"likeCount\":0,\"optional1\":\"\",\"optional2\":\"\",\"optional3\":\"\",\"planEndTime\":null,\"planStartTime\":\"2014-01-15\",\"state\":0,\"title\":\"\",\"updateTime\":null,\"updator\":\"\",\"valid\":0,\"variable\":\"\"}";
		// String[] dateFmts = new String[] { "yyyy/MM/dd", "yyyy-MM-dd" };
		// JSONUtils.getMorpherRegistry().registerMorpher(
		// new DateMorpher(dateFmts));
		//
		// // System.out.println(JSONObject.toBean(JSONObject.fromObject(str),
		// ApproveEvent.class));
	}
}
