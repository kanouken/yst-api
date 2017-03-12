package org.ost.edge.onestong.controller.api.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.date.CalendarByTimeZoneHelper;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.event.AttenceEvent;
import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfo;
import org.ost.edge.onestong.model.system.worktime.Worktime;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.AttenceEventService;
import org.ost.edge.onestong.services.scoreSystem.LikeService;
import org.ost.edge.onestong.services.system.WorktimeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.ost.edge.onestong.tools.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 考勤事件 api
 * 
 * @author xnq
 * 
 */
@Controller
@Api(tags="考勤相关")
@RequestMapping("/api/attenceEvent")
public class AttenceEventDataApi extends Action {

	@Autowired
	private LikeService likeService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private AttenceEventService attenceEventService;

	@Autowired
	private WorktimeService worktimeService;

	public int getComeLateMinites(Date signInDate, Worktime wt) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(signInDate);
		// c.add(Calendar.HOUR_OF_DAY, 8);
		try {
			Date shouldTime = wt.getStartworkTime();
			Date actualTime = df.parse(df.format(c.getTime()));
			int lateTime = (int) ((actualTime.getTime() - shouldTime.getTime()) / 1000 / 60);
			if (lateTime > 0) {
				return lateTime;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 签到 签到 需要获取今日 考勤信息 区别于直接签到
	 * 
	 * 服务器判断 是否节假日 optional1 考勤说明 ，optional2 地点偏移
	 * 
	 * @param attence
	 *            经度 纬度 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "signIn", method = RequestMethod.POST)
	public Object signIn(@RequestBody AttenceEvent attence) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		OperateResult op = new OperateResult();
		try {
			if (null == attence.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 用户为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("event' user must be provided!");

				return op;
			} else if (StringUtils.isBlank(attence.getSeId())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 考勤 uuid为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("event's uuid must be provided!");

				return op;

			} else if (StringUtils.isBlank(attence.getSignedAddress())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 签到地点为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("signed Address must be provided!");
				return op;

			} else if (StringUtils.isBlank(attence.getSignedLongitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 签到经度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("signed Longitude must be provided!");

				return op;

			} else if (StringUtils.isBlank(attence.getSignedLatitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 签到纬度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("signed Latitude must be provided!");

				return op;

			}
			User user = null;
			user = this.usersService.findOneById(attence.getUserId());
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("外访签到 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			List<AttenceEvent> attences = attenceEventService.findTodayAttenceInfo(attence.getUserId());

			if (!CollectionUtils.isEmpty(attences)) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 已签到");

				}
				op.setStatusCode(Constants.ALREADY_SIGN);
				op.setDescription("今天已签到，不能重复签到！");
				return op;
			} else {
				JSONObject attenceDesc = new JSONObject();
				JSONObject attenceOffset = new JSONObject();
				// version 维徳 才有偏移量 ，mc2易事通无记录
				if (StringUtils.isNotBlank(attence.getOptional2())) {
					if (StringUtils.isNotBlank(attence.getOptional1())) {
						attenceDesc.put("signIn", attence.getOptional1());
						attence.setOptional1(attenceDesc.toString());
					} else {
						attenceDesc.put("signIn", "");
						attence.setOptional1(attenceDesc.toString());
					}

					attenceOffset.put("signIn", attence.getOptional2());
					attence.setOptional2(attenceOffset.toString());
				}
				// 签到
				Date currentTime = new Date();
				attence.setCreateTime(currentTime);
				attence.setUpdateTime(currentTime);
				attence.setCreator(user.getRealname());
				attence.setUpdator(user.getRealname());
				attence.setSignedTime(currentTime);
				attence.setState(Constants.SIGNIN_BUT_NOT_SIGNOUT);// 签到 未签退
				// 迟到时间
				Integer lateTime = this.getComeLateMinites(attence.getSignedTime(),
						this.worktimeService.findWorktimeByDomain(user.getDomainId()));
				attence.setLatedForTime(lateTime);
				if (0 == lateTime) {
					// attence.setDescription(Constants.SIGN_IN_NORMAL_STATUS_TIP);
				} else {
					// attence.setDescription(Constants.SIGN_IN_LATER_STATUS_TIP
					// .replace("min", String.valueOf(lateTime)));
				}

				// 判断是否是节假日
				String dateStr = df.format(currentTime);
				String urlStr = "http://www.easybots.cn/api/holiday.php?d=" + dateStr;
				JSONObject obj = JSONObject.fromObject(HttpUtils.GET(urlStr));
				if (obj.isNullObject()) {

					obj = new JSONObject();
					obj.put(dateStr, "0");
				}
				// 如果不是工作日 直接将迟到时间 设置为 0
				if (obj.getInt(dateStr) != 0) {

					attence.setLatedForTime(0);

				}

				attenceEventService.addEvent(attence);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription(attence.getDescription());
			}
		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			if (logger.isErrorEnabled()) {
				logger.debug("签到失败，server error ", e);

			}
		} finally {

			df = null;

		}

		return op;
	}

	/**
	 * 
	 * 使用ibeacon 考勤 签到 签到 需要获取今日 考勤信息 区别于直接签到
	 * 
	 * @param attence
	 *            经度 纬度 用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "signInWithIbeacon/{token}", method = RequestMethod.POST)
	public Object signInUseIbeacon(Integer userId, String seId, String uuid, String major, String minor,
			@PathVariable("token") String token) {

		OperateResult op = new OperateResult();
		IbeaconInfo beacon = null;
		try {

			if (null == userId || null == seId || null == uuid || null == major || null == minor) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  、seId、uuid or major must be not null");
				return op;

			}

			User user = null;
			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("ibeacon签到 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			List<AttenceEvent> attences = attenceEventService.findTodayAttenceInfo(userId);

			if (!CollectionUtils.isEmpty(attences)) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签到 --- 已签到");

				}
				op.setStatusCode(Constants.ALREADY_SIGN);
				op.setDescription("今天已签到，不能重复签到！");
				return op;
			} else {
				JSONObject signInDesc = new JSONObject();

				beacon = this.attenceEventService.getIbeaconInfo(uuid, major, minor);
				if (null == beacon) {
					op.setStatusCode(Constants.IBEACON_NOT_SETUP);
					op.setDescription("Ibeacon尚未启用！");
					return op;
				} else {
					beacon = this.attenceEventService.getIbeaconInfo(user.getDeptId());
					if (beacon == null) {
						op.setStatusCode(Constants.IBEACON_NOT_SETUP);
						op.setDescription("Ibeacon尚未启用！");
						return op;
					} else {
						if (!beacon.getIbeaconUuid().equals(uuid) || !beacon.getIbeanconMajor().equals(major)
								|| !beacon.getIbeanconMinor().equals(minor)) {
							signInDesc.put("signIn", "1");
						} else {
							signInDesc.put("signIn", "0");
						}
					}

				}

				AttenceEvent attence = new AttenceEvent();
				// 签到
				Date currentTime = new Date();
				attence.setUserId(userId);
				attence.setCreateTime(currentTime);
				attence.setUpdateTime(currentTime);
				attence.setCreator(user.getRealname());
				attence.setUpdator(user.getRealname());
				attence.setSignedTime(currentTime);
				// fill in the location
				attence.setSeId(seId);
				attence.setSignedAddress(beacon.getAddress());
				attence.setSignedLongitude(beacon.getLongitude());
				attence.setSignedLatitude(beacon.getLatitude());

				attence.setState(Constants.SIGNIN_BUT_NOT_SIGNOUT);// 签到 未签退
				attence.setDescription(signInDesc.toString());
				// 迟到时间
				Integer lateTime = this.getComeLateMinites(attence.getSignedTime(),
						this.worktimeService.findWorktimeByDomain(user.getDomainId()));
				attence.setLatedForTime(lateTime);
				attenceEventService.addEvent(attence);
				op.setStatusCode(Constants.HTTP_200);
				op.setDescription(attence.getDescription());
			}
		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			if (logger.isErrorEnabled()) {
				logger.debug("签到失败，server error ", e);

			}
		}

		return op;
	}

	public int getLeaveEarlyMinites(Date signOutDate, Worktime wt) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(signOutDate);
		try {
			Date shouldTime = wt.getOffworkTime();
			Date actualTime = df.parse(df.format(c.getTime()));
			int lateTime = (int) ((shouldTime.getTime() - actualTime.getTime()) / 1000 / 60);
			if (lateTime > 0) {
				return lateTime;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 签退 需要检查 该条事件是否存在 不存要给出提示
	 * 
	 * @param event
	 *            event uuid
	 */
	@ResponseBody
	@RequestMapping(value = "signOut", method = RequestMethod.POST)
	public Object signOut(@RequestBody AttenceEvent event) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		OperateResult op = new OperateResult();
		String singoutAddress = event.getSignoutAddress();
		String singoutLongitude = event.getSignoutLongitude();
		String singoutLatitude = event.getSignoutLatitude();
		try {
			// if (StringUtils.isBlank(event.getSeId())) {
			// if (logger.isErrorEnabled()) {
			// logger.error("考勤签退 --- 事件UUID 为空");
			//
			// }
			// op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			// op.setDescription("事件UUID不能为空");
			//
			// return op;
			// } else
			if (StringUtils.isBlank(singoutAddress)) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 签退地点为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("地点不能为空");
				return op;

			} else if (StringUtils.isBlank(singoutLongitude)) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 签退经度为空");
				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("经度不能为空");

				return op;

			} else if (StringUtils.isBlank(singoutLatitude)) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 签退纬度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("纬度不能为空");

				return op;

			}
			User user = null;
			user = this.usersService.findOneById(event.getUserId());
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("外访签退 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("用户不存在!");
				return op;
			}
			// 获取签退事件
			List<AttenceEvent> attences = attenceEventService.findTodayAttenceInfo(user.getUserId());

			if (CollectionUtils.isEmpty(attences)) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("还没签到，不能签退！");
				op.setData(null);
				return op;
			}

			String attenceDescStr = event.getOptional1();
			String attenceOffsetStr = event.getOptional2();

			String signOutDescTmp = event.getDescription();
			// convert to json object

			if (StringUtils.isNotBlank(signOutDescTmp)) {

				JSONObject signOutDesc = JSONObject.fromObject(signOutDescTmp.trim());
				event = attences.get(0);
				JSONObject signInDesc = JSONObject.fromObject(event.getDescription().trim());
				JSONArray tmp = new JSONArray();
				tmp.add(signInDesc);
				tmp.add(signOutDesc);
				event.setDescription(tmp.toString());

			} else {
				event = attences.get(0);

			}

			// 考勤批注 与 偏移量
			JSONObject attenceDesc = new JSONObject();
			JSONObject attenceOffset = new JSONObject();

			if (StringUtils.isNotBlank(attenceOffsetStr)) {
				String tmp = "";

				if (StringUtils.isNotBlank(attenceDescStr)) {
					tmp = event.getOptional1();

					if (StringUtils.isNotBlank(tmp)) {
						attenceDesc = JSONObject.fromObject(event.getOptional1());

						attenceDesc.put("signOut", attenceDescStr);

						event.setOptional1(attenceDesc.toString());

					} else {
						attenceDesc.put("signIn", "");
						attenceDesc.put("signOut", attenceDescStr);
						event.setOptional1(attenceDesc.toString());

					}

				} else {

					tmp = event.getOptional1();

					if (StringUtils.isNotBlank(tmp)) {
						attenceDesc = JSONObject.fromObject(event.getOptional1());

						attenceDesc.put("signOut", attenceDescStr);

						event.setOptional1(attenceDesc.toString());

					} else {
						attenceDesc.put("signIn", "");
						attenceDesc.put("signOut", attenceDescStr);
						event.setOptional1(attenceDesc.toString());

					}

				}

				tmp = event.getOptional2();

				if (StringUtils.isNotBlank(tmp)) {

					attenceOffset = JSONObject.fromObject(event.getOptional2());
					attenceOffset.put("signOut", attenceOffsetStr);
					event.setOptional2(attenceOffset.toString());

				} else {
					attenceOffset.put("signIn", "");
					attenceOffset.put("signOut", attenceOffsetStr);
					event.setOptional2(attenceOffset.toString());
				}

			}

			event.setSignoutAddress(singoutAddress);
			event.setSignoutLongitude(singoutLongitude);
			event.setSignoutLatitude(singoutLatitude);
			// 正常 签到后签退
			Date currentTime = new Date();
			event.setSignoutTime(currentTime);
			event.setUpdateTime(currentTime);
			event.setUpdator(user.getRealname());
			event.setState(Constants.BOTH_SIGNIN_AND_SIGNOUT);
			Integer leaveTime = this.getLeaveEarlyMinites(event.getSignoutTime(),
					this.worktimeService.findWorktimeByDomain(user.getDomainId()));
			event.setLeForTime(leaveTime);
			if (0 == leaveTime) {
				// event.setDescription(Constants.SIGN_OUT_NORMAL_STATUS_TIP);
			} else {
				// event.setDescription(Constants.SIGN_OUT_LEAVE_STATUS_TIP
				// .replace("min", String.valueOf(leaveTime)));
			}
			// 判断是否是节假日
			String dateStr = df.format(currentTime);
			String urlStr = "http://www.easybots.cn/api/holiday.php?d=" + dateStr;
			JSONObject obj = JSONObject.fromObject(HttpUtils.GET(urlStr));
			if (obj.isNullObject()) {

				obj = new JSONObject();
				obj.put(dateStr, "0");
			}
			// 如果不是工作日 直接将早退时间 设置为 0
			if (obj.getInt(dateStr) != 0) {

				event.setLeForTime(0);

			}

			attenceEventService.updateEvent(event);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription(event.getDescription());

		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			if (logger.isErrorEnabled()) {

				logger.error("考勤签退失败 --", e);
			}
		} finally {
			df = null;

		}
		return op;
	}

	/**
	 * 使用ibeacon 签退需要检查 该条事件是否存在 不存要给出提示
	 * 
	 * @param event
	 *            event uuid
	 */
	@ResponseBody
	@RequestMapping(value = "signOutWithIbeacon/{token}", method = RequestMethod.POST)
	public Object signOutWithIbeacon(@PathVariable("token") String token, Integer userId, String seId, String uuid,
			String major, String minor) {
		OperateResult op = new OperateResult();
		IbeaconInfo beacon = null;
		try {
			if (null == userId || null == uuid || null == major || null == minor) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  、seId、uuid or major must be not null");
				return op;
			}
			User user = null;
			user = this.usersService.findOneById(userId);
			if (null == user) {
				if (logger.isErrorEnabled()) {
					logger.error("ibeacon 签退 --- 用户不存在");
				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("用户不存在!");
				return op;
			}
			// 获取签退事件
			List<AttenceEvent> attences = attenceEventService.findTodayAttenceInfo(user.getUserId());

			if (CollectionUtils.isEmpty(attences)) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("还没签到，不能签退！");
				op.setData(null);
				return op;
			}
			JSONObject signOutDesc = new JSONObject();
			beacon = this.attenceEventService.getIbeaconInfo(uuid, major, minor);
			if (null == beacon) {
				op.setStatusCode(Constants.IBEACON_NOT_SETUP);
				op.setDescription("Ibeacon尚未启用！");
				return op;
			} else {
				beacon = this.attenceEventService.getIbeaconInfo(user.getDeptId());
				if (beacon == null) {
					op.setStatusCode(Constants.IBEACON_NOT_SETUP);
					op.setDescription("Ibeacon尚未启用！");
					return op;
				} else {
					if (!beacon.getIbeaconUuid().equals(uuid) || !beacon.getIbeanconMajor().equals(major)
							|| !beacon.getIbeanconMinor().equals(minor)) {
						signOutDesc.put("signOut", "1");
					} else {
						signOutDesc.put("signOut", "0");
					}
				}

			}
			AttenceEvent event = attences.get(0);
			JSONObject sginInDesc = JSONObject.fromObject(event.getDescription().trim());

			JSONArray tmp = new JSONArray();
			tmp.add(sginInDesc);
			tmp.add(signOutDesc);
			event.setDescription(tmp.toString());
			event.setSignoutAddress(beacon.getAddress());
			event.setSignoutLongitude(beacon.getLongitude());
			event.setSignoutLatitude(beacon.getLatitude());
			// 正常 签到后签退
			Date currentTime = new Date();
			event.setSignoutTime(currentTime);
			event.setUpdateTime(currentTime);
			event.setUpdator(user.getRealname());
			event.setState(Constants.BOTH_SIGNIN_AND_SIGNOUT);
			Integer leaveTime = this.getLeaveEarlyMinites(event.getSignoutTime(),
					this.worktimeService.findWorktimeByDomain(user.getDomainId()));
			event.setLeForTime(leaveTime);
			attenceEventService.updateEvent(event);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription(event.getDescription());
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			if (logger.isErrorEnabled()) {
				logger.error("ibeacon签退失败 --", e);
			}
		}
		return op;
	}

	/**
	 * 打卡状态查询 查询今天单条 考勤状态 返回考勤实体
	 */
	@ResponseBody
	@RequestMapping(value = "signStatusForToday", method = RequestMethod.GET)
	public Object signStatuQuery(@RequestAttribute(value=LOGIN_USER) User  user) {
		OperateResult op = new OperateResult();
		try {

			if (null == user.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("今日考勤查询 --- 用户为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("用户不能为空");

				return op;
			}

			List<AttenceEvent> attences = attenceEventService.findTodayAttenceInfo(user.getUserId());

			op.setStatusCode(Constants.HTTP_200);
			op.setData(attences);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

			if (logger.isErrorEnabled()) {
				logger.error("查询个人 天 内 单条考勤信息  失败 ", e);

			}

		}

		return op;
	}

	/**
	 * 是否需要打考勤
	 */
	@Deprecated
	public void isSignNeeded() {

	}

	/**
	 * 直接签退
	 * 
	 * @param token
	 * @param event
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "signOutDirectly", method = RequestMethod.POST)
	public Object signOutDirectly( @RequestBody AttenceEvent event) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			if (null == event.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 用户为空");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("用户不能为空");

				return op;
			} else if (StringUtils.isBlank(event.getSeId())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 事件UUID 为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("事件UUID不能为空");

				return op;
			} else if (StringUtils.isBlank(event.getSignoutAddress())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 签退地点为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("地点不能为空");
				return op;

			} else if (StringUtils.isBlank(event.getSignoutLongitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 签退经度为空");
				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("经度不能为空");

				return op;

			} else if (StringUtils.isBlank(event.getSignoutLatitude())) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤签退 --- 签退纬度为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("纬度不能为空");

				return op;

			}

			User user = null;
			user = this.usersService.findOneById(event.getUserId());
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("外访直接签退 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("用户不存在!");
				return op;
			}

			// 由客户端发起直接 签退的请求
			Date currentTime = new Date();
			event.setSignoutTime(currentTime);
			event.setCreateTime(currentTime);
			event.setUpdateTime(currentTime);
			event.setCreator(user.getRealname());
			event.setUpdator(user.getRealname());
			event.setState(Constants.SIGNOUT_BUT_NOT_SIGNIN); // 签退 但是没签到
			Integer leaveTime = this.getLeaveEarlyMinites(event.getSignoutTime(),
					this.worktimeService.findWorktimeByDomain(user.getDomainId()));
			event.setLeForTime(leaveTime);
			if (0 == leaveTime) {
				// event.setDescription(Constants.SIGN_OUT_NORMAL_STATUS_TIP);
			} else {
				// event.setDescription(Constants.SIGN_OUT_LEAVE_STATUS_TIP
				// .replace("min", String.valueOf(leaveTime)));
			}

			JSONObject attenceDesc = new JSONObject();
			JSONObject attenceOffset = new JSONObject();

			if (StringUtils.isNotBlank(event.getOptional2())) {

				attenceDesc.put("signIn", "");
				attenceDesc.put("signOut", StringUtils.isBlank(event.getOptional1()) ? "" : event.getOptional1());
				event.setOptional1(attenceDesc.toString());
				attenceOffset.put("signIn", "");
				attenceOffset.put("signOut", event.getOptional2());
				event.setOptional2(attenceOffset.toString());

			}

			// 判断是否是节假日
			String dateStr = df.format(currentTime);
			String urlStr = "http://www.easybots.cn/api/holiday.php?d=" + dateStr;
			JSONObject obj = JSONObject.fromObject(HttpUtils.GET(urlStr));
			if (obj.isNullObject()) {

				obj = new JSONObject();
				obj.put(dateStr, "0");
			}
			// 如果不是工作日 直接将早退时间 设置为 0
			if (obj.getInt(dateStr) != 0) {
				event.setLeForTime(0);
			}
			attenceEventService.addEvent(event);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription(event.getDescription());

		} catch (Exception e) {

			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			if (logger.isErrorEnabled()) {

				logger.error("考勤直接签退失败 --", e);
			}
		}
		return op;
	}

	/**
	 * 直接签退 用 ibeacon
	 * 
	 * @param token
	 * @param event
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "signOutDirectlyWithIbeacon/{token}", method = RequestMethod.POST)
	public Object signOutDirectlyWithIbeacon(@PathVariable("token") String token, Integer userId, String seId,
			String uuid, String major, String minor) {
		OperateResult op = new OperateResult();
		IbeaconInfo beacon = null;
		User user = null;
		try {
			if (null == userId || null == uuid || null == major || null == seId || null == minor) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  、seId、uuid or major must be not null");
				return op;
			}
			user = this.usersService.findOneById(userId);
			if (null == user) {
				if (logger.isErrorEnabled()) {
					logger.error("外访直接签退 beacon --- 用户不存在");
				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("用户不存在!");
				return op;
			}
			JSONObject signOutDesc = new JSONObject();
			beacon = this.attenceEventService.getIbeaconInfo(uuid, major, minor);
			if (null == beacon) {
				op.setStatusCode(Constants.IBEACON_NOT_SETUP);
				op.setDescription("Ibeacon尚未启用！");
				return op;
			} else {
				beacon = this.attenceEventService.getIbeaconInfo(user.getDeptId());
				if (beacon == null) {
					op.setStatusCode(Constants.IBEACON_NOT_SETUP);
					op.setDescription("Ibeacon尚未启用！");
					return op;
				} else {
					if (!beacon.getIbeaconUuid().equals(uuid) || !beacon.getIbeanconMajor().equals(major)
							|| !beacon.getIbeanconMinor().equals(minor)) {
						signOutDesc.put("signOut", "1");
					} else {
						signOutDesc.put("signOut", "0");
					}
				}
			} // 由客户端发起直接 签退的请求
			Date currentTime = new Date();
			AttenceEvent event = new AttenceEvent();
			event.setSeId(seId);
			event.setUserId(userId);
			event.setDescription(signOutDesc.toString());
			event.setSignoutTime(currentTime);
			event.setSignoutAddress(beacon.getAddress());
			event.setSignoutLongitude(beacon.getLongitude());
			event.setSignoutLatitude(beacon.getLatitude());
			event.setCreateTime(currentTime);
			event.setUpdateTime(currentTime);
			event.setCreator(user.getRealname());
			event.setUpdator(user.getRealname());
			event.setState(Constants.SIGNOUT_BUT_NOT_SIGNIN); // 签退 但是没签到
			Integer leaveTime = this.getLeaveEarlyMinites(event.getSignoutTime(),
					this.worktimeService.findWorktimeByDomain(user.getDomainId()));
			event.setLeForTime(leaveTime);
			attenceEventService.addEvent(event);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription(event.getDescription());
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			if (logger.isErrorEnabled()) {
				logger.error("ibeacon 考勤直接签退失败 --", e);
			}
		}
		return op;
	}

	/**
	 * 
	 * 增加开始时间与结束时间的 按月份分页查询 某人的 考勤记录
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "records/{token}", method = RequestMethod.GET)
	public Object query(User user, @PathVariable("token") String token, Date start, Date end, Page page) {
		OperateResult op = new OperateResult();
		try {

			if (null == user.getUserId()) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤 月度记录拉取  --- 用户为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("用户不能为空");
				return op;

			} else if (null == start) {
				// 默认当月
				start = CalendarByTimeZoneHelper.getTodayOClock();

			} else if (0 == page.getCurPage()) {
				if (logger.isErrorEnabled()) {
					logger.error("考勤 月度记录拉取  --- 请求页码为空");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("请求的页面为空");

				return op;

			} else if (0 == page.getPerPageSum()) {
				// 默认 为5条
				page.setPerPageSum(Constants.PAGESIZE_APP);

			}
			RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
			List<AttenceEvent> ates = this.attenceEventService.findByUserFilterByStartAndEndTime(user, start, end, rb);
			op.setStatusCode(Constants.HTTP_200);
			op.setData(ates);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {

				logger.error("考勤 -- 按时间段查询失败 失败 用户:" + user.getRealname());

			}

		}

		return op;
	}

	/**
	 * { "statusCode": "200", "description": "fetch shortCut report success ",
	 * "data": [ { "norSignOut": "0", "norSignIn": "0", "updator": "徐飞飞",
	 * "leSum": "0", "user_id": "3", "lateSum": "1" }, { "norSignOut": "0",
	 * "norSignIn": "0", "updator": "admin", "leSum": "1", "user_id": "5",
	 * "lateSum": "1" }, { "norSignOut": "0", "norSignIn": "0", "updator":
	 * "Judy", "leSum": "1", "user_id": "7", "lateSum": "1" }, { "norSignOut":
	 * "1", "norSignIn": "0", "updator": "王亮", "leSum": "2", "user_id": "9",
	 * "lateSum": "8" }, { "norSignOut": "0", "norSignIn": "1", "updator":
	 * "夏能权", "leSum": "1", "user_id": "10", "lateSum": "0" } ] }
	 */

	/**
	 * 添加 按 周期类型 来获取数据 月 考勤 事件 的简报 拥有简报查看权限的 人 查看 某个 部门下 所有人的一个月度简报 month
	 * 2015-12-01 week 2015-12-01 day
	 * 
	 * @return {"departmentId":"9",userId:"3","reportDate":"2015/01/01"}
	 */
	@ResponseBody
	@RequestMapping(value = "shortcutReport/{departmentId}/{reportDate}/{type}/{token}", method = RequestMethod.GET)
	public Object shortcutReport(@PathVariable("token") String token,
			@PathVariable("departmentId") Integer departmentId, @PathVariable("reportDate") String reportDate,
			@PathVariable("type") Byte filterType

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

				List<Map<String, Object>> s = this.attenceEventService.findShortCutReportFilterByMonthAndDeptartId(
						Integer.valueOf(departmentId.toString().trim()), _rDate, filterType);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success ");
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询考勤简报 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		}

		catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询考勤简报 时报 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;

	}

	/**
	 * 
	 * 快捷报表 按时间段查询
	 * 
	 * @param token
	 *            秘要
	 * @param departmentId
	 *            部门 id
	 * @param reportDate
	 *            开始时间
	 * @param reportEnd
	 *            结束时间
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "shortcutReportDuring/{departmentId}/{start}/{end}/{token}", method = RequestMethod.GET)
	public Object shortCutReportDuring(@PathVariable("token") String token,
			@PathVariable("departmentId") Integer departmentId, @PathVariable("start") String startTime,
			@PathVariable("end") String reportEnd

	) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			if (null == departmentId || StringUtils.isBlank(startTime) || StringUtils.isBlank(reportEnd)) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("deptId or reprotDate start and end  must be provided!");
				op.setData(null);
				return op;
			} else {
				Date start = df.parse(String.valueOf(startTime.trim()));
				Date end = df.parse(String.valueOf(reportEnd.trim()));

				List<Map<String, Object>> s = this.attenceEventService.findShortCutReportFilterByMonthAndDeptartId(
						Integer.valueOf(departmentId.toString().trim()), start, end);
				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success during start: " + startTime + " ~end :" + reportEnd);
				op.setData(s);
			}
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询考勤简报 失败 月份 " + startTime + " ~end :" + reportEnd, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询考勤简报 时报 ", e);
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

				List<Map<String, Object>> s = this.attenceEventService.findShortCutReportFilterByMonthAndUserId(userId,
						_rDate, filterType);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success ");
				op.setData(s);
			}

		} catch (ParseException e) {

			if (logger.isErrorEnabled()) {
				logger.error("按人/月/week/day份 查询考勤简报 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		}

		catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询考勤简报 时报 ", e);
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
	public Object shortCutReportSinglePeopleDuring(@PathVariable("token") String token,
			@PathVariable("userId") Integer userId, @PathVariable("start") String startTime,
			@PathVariable("end") String endTime

	) {
		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (null == userId || StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId 、 startTime  and endTime there all all  must be provided!");
				op.setData(null);
				return op;
			} else {
				Date start = df.parse(String.valueOf(startTime.trim()));
				Date end = df.parse(String.valueOf(endTime.trim()));

				List<Map<String, Object>> s = this.attenceEventService.findShortCutReportFilterByMonthAndUserId(userId,
						start, end);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("fetch shortCut report success !");
				op.setData(s);
			}
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("按人/月/week/day份 查询考勤简报 失败 ", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("reportDate's format error  ,you must provided like this pattern 'yyyy-MM-dd'  !");
			op.setData(null);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("按 部门月份 查询考勤简报 时间 " + startTime + "~~" + endTime, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}

	/**
	 * 考勤详细
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
		AttenceEvent ae = null;
		try {
			ae = this.attenceEventService.findOneEventDetailByEventId(eventId);
			// resources
			// 获取资源 只获取外访签到 的图片
			// 日志图片暂无

			// 考勤暂无资源附件

			op.setStatusCode(HTTP_200);
			resultMap.put("event", ae);
			op.setData(resultMap);
			// like and creator info espidally pic
			User user = this.usersService.findOneById(ae.getUserId());
			resultMap.put("author", user);
			// like
			resultMap.put("likes", this.likeService.findLikeInfosByEvent(ae.getSeId()));
			op.setDescription("find attenceEvent detail success!");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取考勤事件详细失败！", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

}
