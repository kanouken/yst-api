package org.ost.edge.onestong.controller.api.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.date.CalendarByTimeZoneHelper;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.event.VisitEvent;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.tag.PTag;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.EventCollectionService;
import org.ost.edge.onestong.services.api.event.VisitEventService;
import org.ost.edge.onestong.services.scoreSystem.LikeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.ost.entity.event.vo.VisitEventUpdateVo;
import org.ost.entity.event.vo.VisitEventVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 外访 数据api 接口 版本 0.5 1.从项目 角度 串联 事件 ， 一个项目 对应多个事件 1 ,n 2.从客户 角度 串联 事件 ， n,n
 * 
 * @author xnq
 * 
 */
@Controller
@Api(tags = "外访")
@RequestMapping("/api/visitEvent")
public class VisitEventDataApi extends Action {
	@Autowired
	private EventCollectionService collectionService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private VisitEventService visitEventService;

	@Autowired
	private LikeService likeService;

	/**
	 * 新增外访
	 * 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object createVisit(HttpServletRequest request, @RequestBody VisitEventVo vo) {
		return this.visitEventService.createVisitEvent(currentUser(), vo);
	}

	/**
	 * 外访签到
	 * 
	 * @return
	 */
	@RequestMapping(value = "visitIn", method = RequestMethod.POST)
	public Object visitIn(HttpServletRequest request, @RequestBody VisitEventUpdateVo vo) {
		return this.visitEventService.visitIn(currentUser(),vo);
	}
	
	/**
	 * 外访写结果
	 * 
	 * @return
	 */
	@RequestMapping(value = "result", method = RequestMethod.POST)
	public Object writeResult(HttpServletRequest request, @RequestBody VisitEventUpdateVo vo) {
		return this.visitEventService.writeResult(currentUser(),vo);
	}


	/**
	 * 添加 项目标签与客户标签 时 设置 它的 domainId
	 * 
	 * @param cTag
	 *            客户标签
	 * @param pTag
	 *            项目标签
	 * @param token
	 *            {"event":{"userId":"3","veId":"sfsfsf-sfsfsf-sfsf",
	 *            "vistedAddress"
	 *            :"湖北武汉"},"pTag":{"pTId":"2","pTName":"韦德"},"cTags"
	 *            :[{"cTId":"11"
	 *            ,"cTName":"进行中"},{"cTId":"12"},{"cTName":"新增test"}] }
	 * @param event
	 * @param files
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "visitIn/{token}", method = RequestMethod.POST)
	public Object method(@PathVariable("token") @RequestParam("entity") String entity,
			@RequestParam(required = false) MultipartFile[] images) {
		OperateResult op = new OperateResult();

		JSONObject requestAtt = JSONObject.fromObject(entity);
		JSONObject event_obj = requestAtt.getJSONObject("event");
		JSONObject pTag_obj = requestAtt.getJSONObject("pTag");
		JSONArray cTag_objs = null;
		if (null != requestAtt.get("cTags")) {
			cTag_objs = requestAtt.getJSONArray("cTags");
		}
		VisitEvent event = (VisitEvent) JSONObject.toBean(event_obj, VisitEvent.class);

		PTag pTag = null;

		if (null != pTag_obj) {

			pTag = (PTag) JSONObject.toBean(pTag_obj, PTag.class);
		}

		List<CTag> cTag = null;
		if (null != cTag_objs) {

			cTag = (List<CTag>) JSONArray.toCollection(cTag_objs, CTag.class);

		}
		User user = null;

		try {

			if (null == event.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 用户为空");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("event's user  must be provided");

				return op;
			} else if (StringUtils.isBlank(event.getVeId())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 外访事件 uuid为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("event' uuid must be provided");

				return op;

			} else if (StringUtils.isBlank(event.getVistedAddress())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 签到地点为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("VistedAddress  must be provided! ");
				return op;

			} else if (StringUtils.isBlank(event.getVistedLongitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 签到经度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("VistedLongitude must be provided");

				return op;

			} else if (StringUtils.isBlank(event.getTitle())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 签到标题为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("title must be provided !");

				return op;

			}

			// else if (StringUtils.isBlank(event.getContent())) {
			// if (logger.isErrorEnabled()) {
			// logger.error("外访签到 --- 签到内容（描述）为空");
			//
			// }
			// op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			// op.setDescription("content must be provided !");
			//
			// return op;
			//
			// }

			else if (StringUtils.isBlank(event.getVisitedLatitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 签到纬度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("VisitedLatitude must be provided ");

				return op;

			}

			user = this.usersService.findOneById(event.getUserId());
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			// 外访签到
			Date currentTime = new Date();

			event.setCreateTime(currentTime);
			event.setUpdateTime(currentTime);
			event.setCreator(user.getRealname());
			event.setUpdator(user.getRealname());
			event.setVisitedTime(currentTime);
			event.setState(Constants.VISIT_ING); // 进行中
			Map<String, Object> pEntity = visitEventService.addEvent(event, images, user, cTag, pTag);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("visit in  success!");
			op.setData(pEntity);
		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("外访签到 失败 ", e);

			}
		}

		return op;
	}

	/**
	 * 外访签退 //TODO 检查 传过来的事件是否存在
	 * 
	 * @param entity
	 * @param images
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "visitOut/{token}", method = RequestMethod.POST)
	public Object visitOut(@PathVariable("token") @RequestParam("entity") String entity,
			@RequestParam(required = false) MultipartFile[] images) {
		OperateResult op = new OperateResult();

		JSONObject requestAtt = JSONObject.fromObject(entity);
		JSONObject event_obj = requestAtt.getJSONObject("event");

		VisitEvent event = (VisitEvent) JSONObject.toBean(event_obj, VisitEvent.class);
		User user = null;
		try {
			if (null == event.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 用户为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("event's user  must be provided");

				return op;
			} else if (StringUtils.isBlank(event.getVeId())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 外访事件 uuid为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("event' uuid must be provided");

				return op;

			} else if (StringUtils.isBlank(event.getVisitoutAddress())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 签退地点为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("VistedAddress  must be provided! ");
				return op;

			} else if (StringUtils.isBlank(event.getVisitoutLongitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 签退经度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("Vistout Longitude must be provided");

				return op;

			}

			// else if (StringUtils.isBlank(event.getVisitSummary())) {
			// if (logger.isErrorEnabled()) {
			// logger.error("外访签退 --- 签退内容（描述）为空");
			//
			// }
			// op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			// op.setDescription("visit Summary must be provided !");
			//
			// return op;
			//
			// }

			else if (StringUtils.isBlank(event.getVisitoutLatitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 签退纬度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("Visitout Latitude must be provided ");

				return op;

			}

			user = this.usersService.findOneById(event.getUserId());
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			// 外访签退
			Date currentTime = new Date();

			event.setUpdateTime(currentTime);
			event.setUpdator(user.getRealname());
			event.setVisitoutTime(currentTime);
			event.setState(Constants.VISIT_END); // 完成
			Map<String, Object> resultEntity = visitEventService.updateEvent(event, images, user);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("visit Out   success!");
			op.setData(resultEntity);
			// 附件信息返回

		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("外访签退 失败 ", e);

			}
		}

		return op;
	}

	/**
	 * 
	 * @param token
	 * @param eventId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "detail/{eventId}/{token}", method = RequestMethod.GET)
	public Object detail(@PathVariable("token") String token, @PathVariable("eventId") String eventId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OperateResult op = new OperateResult();
		VisitEvent ve = null;
		try {
			ve = this.visitEventService.findOneEventDetailByEventId(eventId);
			// resources
			// 获取资源 只获取外访签到 的图片
			// ve.setFiles(this.collectionService.findEventRelatedFiles(
			// ve.getVeId(), VisitEventService.VISIT_FLOW_STEP_SIGNIN));

			ve.setFiles(this.collectionService.findEventRelatedFiles(ve.getVeId(), null));
			ve.setpTags(this.collectionService.findEventRelatedPtags(ve.getVeId()));
			ve.setcTags(this.collectionService.findEventRelatedCtags(ve.getVeId()));
			op.setStatusCode(HTTP_200);
			resultMap.put("event", ve);
			op.setData(resultMap);
			// like and creator info espidally pic
			User user = this.usersService.findOneById(ve.getUserId());
			resultMap.put("author", user);
			// like
			resultMap.put("likes", this.likeService.findLikeInfosByEvent(ve.getVeId()));
			op.setDescription("find visitevent detail success!");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取外访事件详细失败！", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

	/**
	 * 
	 * 增加开始时间与结束时间的 按月份分页查询 某人的 外访记录
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "records/{token}", method = RequestMethod.GET)
	public Object records(User user, @PathVariable("token") String token, Date start, Date end, Page page) {
		OperateResult op = new OperateResult();
		try {

			if (null == user.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("外访记录拉取  --- 用户为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("用户不能为空");
				return op;

			} else if (null == start) {
				// 默认当月
				start = CalendarByTimeZoneHelper.getTodayOClock();

			} else if (0 == page.getCurPage()) {
				if (logger.isErrorEnabled()) {
					logger.error("外访记录拉取  --- 请求页码为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("请求的页面为空");

				return op;

			} else if (0 == page.getPerPageSum()) {
				// 默认 为5条
				page.setPerPageSum(Constants.PAGESIZE_APP);

			}
			RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());

			List<Map<String, Object>> ves = this.visitEventService.findVisitRecordsByUserFilterByMonth(user, start, end,
					rb);

			op.setStatusCode(Constants.HTTP_200);
			op.setData(ves);
			op.setDescription("fetch visit event success during time :start" + start + "and end:" + end);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {

				logger.error("外访 -- 按时间段查询失败 失败 用户:" + user.getRealname());

			}

		}

		return op;
	}

	/**
	 * 按月份分页查询 某人的 外访记录
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */

	@Deprecated
	@ResponseBody
	@RequestMapping(value = "records/{userId}/{year}/{month}/{day}/{curPage}/{perPageSum}/{token}", method = RequestMethod.GET)
	public Object query(@PathVariable(value = "userId") Integer userId, @PathVariable("year") Integer year,
			@PathVariable("month") Integer month, @PathVariable("day") Integer day,
			@PathVariable("curPage") Integer curPage, @PathVariable("perPageSum") Integer perPageSum,
			@PathVariable("token") String token) {
		OperateResult op = new OperateResult();
		User user = null;
		try {

			if (0 > curPage) {

				curPage = 1;

			} else if (0 > perPageSum) {
				// 默认 为5条
				perPageSum = Constants.PAGESIZE_APP;

			}
			Page page = new Page();
			page.setCurPage(curPage);
			page.setPerPageSum(perPageSum);
			// RowBounds rb = new RowBounds(page.getNextPage(),
			// page.getPerPageSum());

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("外访月度 记录拉取 --- 用户不存在");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user can not found!");
				return op;
			}

			// List<Map<String, Object>> ves = visitEventService
			// .findByUserFilterByMonth(
			// // 默认是 1号
			// user,
			// //CalendarByTimeZoneHelper.makeDate(year, month, 1),
			// rb);
			// op.setStatusCode(Constants.HTTP_200);
			// op.setData(ves);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error("外访 -- 拉取月记录 失败 用户:" + user.getRealname());
			}

		}
		return op;
	}

	/**
	 * 按部门 与时间查询 部门的 所有人的外访记录的一个总数
	 * 
	 * @param token
	 * @param departmentId
	 *            部门名称
	 * @param reportDate
	 *            查询开始时间
	 * @param filterType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "shortcutReport/{departmentId}/{reportDate}/{type}/{token}", method = RequestMethod.GET)
	public Object method(@PathVariable("token") String token, @PathVariable("departmentId") Integer departmentId,
			@PathVariable("reportDate") String reportDate, @PathVariable("type") Byte filterType

	) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			if (null == departmentId || StringUtils.isBlank(reportDate)) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("deptId or reprotDate  must be provided!");
				op.setData(null);
				return op;
			} else {
				Date _rDate = df.parse(String.valueOf(reportDate.trim()));

				List<Map<String, Object>> s = this.visitEventService.findShortCutReportFilterByMonthAndDeptartId(
						Integer.valueOf(departmentId.toString().trim()), _rDate, filterType);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success ");
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询外访简报 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		}

		catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询外访简报 时报 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;

	}

	/**
	 * 按部门 与时间查询 部门的 所有人的外访记录的一个总数
	 * 
	 * @param token
	 * @param departmentId
	 *            部门名称
	 * @param reportDate
	 *            查询开始时间
	 * @param filterType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "shortcutReportDuring/{departmentId}/{start}/{end}/{token}", method = RequestMethod.GET)
	public Object shortCutReportDuring(@PathVariable("token") String token,
			@PathVariable("departmentId") Integer departmentId, @PathVariable("start") String startTime,
			@PathVariable("end") String endTime) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (null == departmentId || StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("deptId 、reprotDate and endTime there all  must be provided!");
				op.setData(null);
				return op;
			} else {
				Date start = df.parse(String.valueOf(startTime.trim()));
				Date end = df.parse(String.valueOf(endTime.trim()));

				List<Map<String, Object>> s = this.visitEventService.findShortCutReportFilterByMonthAndDeptartId(
						Integer.valueOf(departmentId.toString().trim()), start, end);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success during time start :" + startTime + " ~ " + endTime);
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询外访简报 失败 时间  " + startTime + " ~ " + endTime, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询外访简报 时报 " + startTime + " ~ " + endTime, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;

	}

	/**
	 * 获取 部门内某个人 的 某个月份 的 考勤简报 (每一天的的数据)
	 * 
	 * @param token
	 * @param departmentId
	 * @param reportDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "shortcutReportSingle/{userId}/{reportDate}/{type}/{token}", method = RequestMethod.GET)
	public Object shortCutReportSinglePeople(@PathVariable("token") String token,
			@PathVariable("userId") Integer userId, @PathVariable("reportDate") String reportDate,
			@PathVariable("type") Byte filterType

	) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			if (null == userId || StringUtils.isBlank(reportDate)) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId or reprotDate  must be provided!");
				op.setData(null);
				return op;
			} else {
				Date _rDate = df.parse(String.valueOf(reportDate.trim()));

				List<Map<String, Object>> s = this.visitEventService.findShortCutReportFilterByMonthAndUserId(userId,
						_rDate, filterType);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success ");
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按人/月/week/day份 查询外访简报 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		}

		catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询外访简报 时报 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;

	}

	/**
	 * 获取 部门内某个人 的 某个月份 的 考勤简报 (每一天的的数据)
	 * 
	 * @param token
	 * @param departmentId
	 * @param reportDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "shortcutReportSingleDuring/{userId}/{start}/{end}/{token}", method = RequestMethod.GET)
	public Object shortCutReportSinglePeople(@PathVariable("token") String token,
			@PathVariable("userId") Integer userId, @PathVariable("start") String startTime,
			@PathVariable("end") String endTime

	) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			if (null == userId || StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  、 reprotDate and reportEndDate there are all   must be provided!");
				op.setData(null);
				return op;
			} else {
				Date start = df.parse(String.valueOf(startTime.trim()));
				Date end = df.parse(String.valueOf(endTime.trim()));

				List<Map<String, Object>> s = this.visitEventService.findShortCutReportFilterByMonthAndUserId(userId,
						start, end);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success ");
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按人/月/week/day份 查询外访简报 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		}

		catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门 时间段 查询外访简报 时报 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;

	}

	/**
	 * 按 用户id 外访日期查询某一天内的拜访事件
	 * 
	 * @param token
	 * @param userId
	 * @param date
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "mapTrackingEvents/{userId}/{date}/{token}", method = RequestMethod.GET)
	public Object trackiongEvents(

			@PathVariable("token") String token, @PathVariable("userId") Integer userId,
			@PathVariable("date") String date

	) {

		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			Date _rDate = df.parse(String.valueOf(date.trim()));
			List<VisitEvent> ves = this.visitEventService.findMapTrackingEventsFilterByDayAndUserId(userId, _rDate);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("fetch map tracking events success!!!");
			op.setData(ves);
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("获取轨迹地图失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

	/**
	 * 查询 部门某个员工 在时间段内 存在的外访记录的详细 以日期为单位
	 * 
	 * @param token
	 * @param userId
	 * @param reportDate
	 * @param filterType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "mapTrackingDetail/{userId}/{reportDate}/{type}/{token}", method = RequestMethod.GET)
	public Object employeeMapTrackingDetail(@PathVariable("token") String token, @PathVariable("userId") Integer userId,
			@PathVariable("reportDate") String reportDate, @PathVariable("type") Byte filterType

	) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			if (null == userId || StringUtils.isBlank(reportDate)) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId or reprotDate  must be provided!");
				op.setData(null);
				return op;
			} else {
				Date _rDate = df.parse(String.valueOf(reportDate.trim()));

				List<Map<String, Object>> s = this.visitEventService
						.findMapTrackingDetailReportFilterByMonthAndUserId(userId, _rDate, filterType);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch employee map tracking detail report success ");
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按人/月/week/day份 查询外访轨迹 详细 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		}

		catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按人/月/week/day份 查询外访轨迹 详细 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;

	}

}
