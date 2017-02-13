package org.ost.edge.onestong.controller.api.diary;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.date.CalendarByTimeZoneHelper;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.event.DailyEvent;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.DailyEventService;
import org.ost.edge.onestong.services.api.event.EventCollectionService;
import org.ost.edge.onestong.services.scoreSystem.LikeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

/**
 * 发日志
 * 
 * @author mac
 * 
 */
@Controller
@RequestMapping("/api/daily")
public class DiaryDataApi extends Action {
	
	@Autowired
	private EventCollectionService collectionService;
	private @Autowired
	DailyEventService dailyEventService;
	private @Autowired
	UsersService usersService;
	private @Autowired
	LikeService likeService;

	/**
	 * 
	 * @param token
	 * @param eventId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "detail/{eventId}/{token}", method = RequestMethod.GET)
	public Object detail(@PathVariable("token") String token,
			@PathVariable("eventId") String eventId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OperateResult op = new OperateResult();
		DailyEvent de = null;
		try {
			de = this.dailyEventService.findOneEventDetailByEventId(eventId);
			// resources
			// 获取日志图片
			 de.setFiles(this.collectionService.findEventRelatedFiles(
			 de.getDeId(), DailyEventService.FLOW_DEFAULT));
			
			
			op.setStatusCode(HTTP_200);
			resultMap.put("event", de);
			op.setData(resultMap);
			// like and creator info espidally pic
			User user = this.usersService.findOneById(de.getUserId());
			resultMap.put("author", user);
			// like
			resultMap.put("likes",
					this.likeService.findLikeInfosByEvent(de.getDeId()));
			op.setDescription("find dailyEvent detail success!");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取日志事件详细失败！", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

	/**
	 * 直接 提交 { "content": "", "deId": "", "eventType": 0, "userId": 0,state 0 }
	 * 
	 * @param token
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "post/{token}", method = RequestMethod.POST)
	public Object post(
			@PathVariable("token") String token,
			@RequestParam("entity") String entity,
			@RequestParam(value = "images", required = false) MultipartFile[] images) {
		OperateResult op = new OperateResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DailyEvent de = null;
		User user = null;
		try {
			de = (DailyEvent) JSONObject.toBean(JSONObject.fromObject(entity),
					DailyEvent.class);

			if (StringUtils.isBlank(de.getContent()) || null == de.getUserId()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user'id or daily's content must were provided!");
				op.setData(null);
				return op;
			}

			user = this.usersService.findOneById(de.getUserId());

			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user wsa not found!");
				op.setData(null);
				return op;

			}

			this.dailyEventService.create(de, user, images);

			op.setStatusCode(HTTP_200);
			op.setDescription("发送日志成功！");
			op.setData(null);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("用户修改失败", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 保存草稿 { "content": "", "deId": "", "eventType": 0, "userId": 0,state 0 }
	 * 
	 * @param token
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "post/tmp/{token}", method = RequestMethod.POST)
	public Object tmp(
			@PathVariable("token") String token,
			@RequestParam("entity") String entity,
			@RequestParam(value = "images", required = false) MultipartFile[] images) {
		OperateResult op = new OperateResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DailyEvent de = null;
		User user = null;
		try {
			de = (DailyEvent) JSONObject.toBean(JSONObject.fromObject(entity),
					DailyEvent.class);

			if (StringUtils.isBlank(de.getContent()) || null == de.getUserId()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user'id or daily's content must were provided!");
				op.setData(null);
				return op;
			}

			user = this.usersService.findOneById(de.getUserId());

			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user wsa not found!");
				op.setData(null);
				return op;

			}
			// 通用的 创建操作
			this.dailyEventService.create(de, user, images);
			resultMap.put("eventId", de.getDeId());
			op.setStatusCode(HTTP_200);
			op.setDescription("发送日志成功！");
			op.setData(resultMap);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("用户修改失败", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 将草稿发送 { "content": "", "deId": "", "eventType": 0, "userId": 0,state 0 }
	 * 
	 * @param token
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateOrSendTmp/{token}", method = RequestMethod.POST)
	public Object update(
			@PathVariable("token") String token,
			@RequestParam("entity") String entity,
			@RequestParam(value = "images", required = false) MultipartFile[] images) {
		OperateResult op = new OperateResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DailyEvent de = null;
		User user = null;
		try {
			de = (DailyEvent) JSONObject.toBean(JSONObject.fromObject(entity),
					DailyEvent.class);

			if (StringUtils.isBlank(de.getContent()) || null == de.getUserId()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user'id or daily's content must were provided!");
				op.setData(null);
				return op;
			}

			user = this.usersService.findOneById(de.getUserId());

			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user wsa not found!");
				op.setData(null);
				return op;

			}
			// 更新操作
			this.dailyEventService.update(de, user, images);

			op.setStatusCode(HTTP_200);
			op.setDescription("发送日志成功！");
			op.setData(null);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("用户修改失败", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}
	
	
	
	/**
	 * 
	 * 增加开始时间与结束时间的
	 * 按月份分页查询 某人的 外访记录
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "records/{token}", method = RequestMethod.GET)
	public Object records(User user, @PathVariable("token") String token,
			Date start,Date end, Page page) {
		OperateResult op = new OperateResult();
		try {

			if (null == user.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("日志记录拉取  --- 用户为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("用户不能为空");
				return op;

			} else if (null == start) {
				// 默认当月
				start = CalendarByTimeZoneHelper.getTodayOClock();

			} else if (0 == page.getCurPage()) {
				if (logger.isErrorEnabled()) {
					logger.error("日志记录拉取  --- 请求页码为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("请求的页面为空");

				return op;

			} else if (0 == page.getPerPageSum()) {
				// 默认 为5条
				page.setPerPageSum(Constants.PAGESIZE_APP);

			}
			RowBounds rb = new RowBounds(page.getNextPage(),
					page.getPerPageSum());
			
			List<DailyEvent> des = this.dailyEventService.findByUserFilterByMonth(user, start, end, rb);
			
			op.setStatusCode(Constants.HTTP_200);
			op.setData(des);
			op.setDescription("fetch daily event success during time :start"+start +"and end:"+end);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {

				logger.error("日志 -- 按时间段查询失败 失败 用户:" + user.getRealname());

			}

		}

		return op;
	}
	
	

	/**
	 * 按月份分页查询 某人的 日志记录
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
	public Object query(@PathVariable(value = "userId") Integer userId,
			@PathVariable("year") Integer year,
			@PathVariable("month") Integer month,
			@PathVariable("day") Integer day,
			@PathVariable("curPage") Integer curPage,
			@PathVariable("perPageSum") Integer perPageSum,
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
//			RowBounds rb = new RowBounds(page.getNextPage(),
//					page.getPerPageSum());

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("日志月度 记录拉取 --- 用户不存在");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user can not found!");
				return op;
			}

//			List<DailyEvent> des = this.dailyEventService
//					.findByUserFilterByMonth(
//							// 默认是 1号
//							user,
//							CalendarByTimeZoneHelper.makeDate(year, month, 1),
//							rb);
//			op.setStatusCode(Constants.HTTP_200);
//			op.setData(des);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error("日志 -- 拉取月记录 失败 用户:" + user.getRealname());
			}

		}
		return op;
	}

	// 删除日志
	public void delete() {

	}

	public static void main(String[] args) {
		DailyEvent de = new DailyEvent();
		System.out.println(JSONObject.fromObject(de));
	}

	/**
	 * 查找当天日志草稿
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "draft/{userId}/{curPage}/{perPageSum}/{token}", method = RequestMethod.GET)
	public Object query(@PathVariable(value = "userId") Integer userId,
	@PathVariable("curPage") Integer curPage,
			@PathVariable("perPageSum") Integer perPageSum,
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
			RowBounds rb = new RowBounds(page.getNextPage(),
					page.getPerPageSum());

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("拉取当天日志草稿 失败 --- 用户不存在");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user can not found!");
				return op;
			}

			List<DailyEvent> de = this.dailyEventService.findDraftByUserFilterCurrentDay(user,null,rb);
					
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("pull dailyevent draft success current day!");
			op.setData(de);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error(" 拉取当天日志草稿 失败 用户:" + user.getRealname(),e);
			}

		}
		return op;
	}

}
