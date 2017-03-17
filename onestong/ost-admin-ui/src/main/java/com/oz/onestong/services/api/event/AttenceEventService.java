package com.oz.onestong.services.api.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.common.tools.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oz.onestong.controller.api.base.BaseService;
import com.oz.onestong.dao.event.AttenceEventMapper;
import com.oz.onestong.dao.report.AttenceReportMapper;
import com.oz.onestong.dao.system.ibeacon.IbeaconInfoMapper;
import com.oz.onestong.dao.system.worktime.WorktimeMapper;
import com.oz.onestong.dao.user.UserMapper;
import com.oz.onestong.model.event.AttenceEvent;
import com.oz.onestong.model.event.AttenceEventExample;
import com.oz.onestong.model.report.AttenceReport;
import com.oz.onestong.model.system.ibeacon.IbeaconInfo;
import com.oz.onestong.model.system.ibeacon.IbeaconInfoExample;
import com.oz.onestong.model.system.worktime.Worktime;
import com.oz.onestong.model.system.worktime.WorktimeExample;
import com.oz.onestong.model.user.User;
import com.oz.onestong.tools.CalendarByTimeZoneHelper;
import com.oz.onestong.tools.Constants;
import com.oz.onestong.tools.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
@Service
public class AttenceEventService extends BaseService {

	private static final Log logger = LogFactory
			.getLog(AttenceEventService.class);

	@Autowired
	private AttenceEventMapper eventMapper;

	@Autowired
	private AttenceReportMapper attenceReportMapper;

	@Autowired
	private IbeaconInfoMapper ibeaconInfoMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private WorktimeMapper worktimeMapper;

	public Object signOutDirectly(AttenceEvent event) {

		OperateResult op = new OperateResult();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");

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
		user = this.userMapper.selectByPrimaryKey(event.getUserId());
		if (null == user) {

			if (logger.isErrorEnabled()) {
				logger.error("外访直接签退 --- 用户不存在");

			}
			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("用户不存在!");
			return op;
		}
		// 还是需要判断今天的考勤状态

		List<AttenceEvent> todayAttence = this.findTodayAttenceInfo(event
				.getUserId());
		AttenceEvent today = null;
		if (CollectionUtils.isNotEmpty(todayAttence)) {
			today = todayAttence.get(0);
			if (today.getState() == Constants.SIGNIN_BUT_NOT_SIGNOUT) {
				// 走签退流程
				return this.signOut(event);
			} else if (today.getState() == Constants.SIGNOUT_BUT_NOT_SIGNIN) {

				op.setData(null);
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("考勤已签退!");
				return op;
			} else if (today.getState() == Constants.BOTH_SIGNIN_AND_SIGNOUT) {
				op.setData(null);
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("考勤已签退!");
				return op;
			}

		}
		// 由客户端发起直接 签退的请求
		Date currentTime = new Date();
		event.setSignoutTime(currentTime);
		event.setCreateTime(currentTime);
		event.setUpdateTime(currentTime);
		event.setCreator(user.getRealname());
		event.setUpdator(user.getRealname());
		event.setState(Constants.SIGNOUT_BUT_NOT_SIGNIN); // 签退 但是没签到

		WorktimeExample we = new WorktimeExample();

		we.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andDomainIdEqualTo(user.getDomainId());

		List<Worktime> wes = this.worktimeMapper.selectByExample(we);
		Worktime wt = null;
		if (CollectionUtils.isNotEmpty(wes)) {
			wt = wes.get(0);
		} else {

			op.setData(null);
			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("考勤时间未设置!");
			return op;
		}

		Integer leaveTime = CalendarByTimeZoneHelper.getLeaveEarlyMinites(
				event.getSignoutTime(), wt);
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
			attenceDesc.put(
					"signOut",
					StringUtils.isBlank(event.getOptional1()) ? "" : event
							.getOptional1());
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
		this.addEvent(event);
		op.setStatusCode(Constants.HTTP_200);
		op.setDescription(event.getDescription());

		return op;

	}

	@Transactional(readOnly = true)
	public IbeaconInfo getIbeaconInfo(String uuid, String major, String minor) {

		IbeaconInfo beacon = null;
		IbeaconInfoExample iie = new IbeaconInfoExample();
		iie.createCriteria().andIbeaconUuidEqualTo(uuid)
				.andIbeanconMajorEqualTo(major).andIbeanconMinorEqualTo(minor);
		List<IbeaconInfo> beacons = new ArrayList<IbeaconInfo>();
		beacons = this.ibeaconInfoMapper.selectByExample(iie);
		if (CollectionUtils.isNotEmpty(beacons)) {

			beacon = beacons.get(0);
		}
		iie = null;
		beacons = null;
		return beacon;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<AttenceReport> selectAttenceReportFliterByDepartmentAndDate(
			Integer deptId, Date start, Date end, Integer domainId) {

		Calendar end_c = Calendar.getInstance();
		end_c.setTime(end);
		end_c.set(Calendar.HOUR_OF_DAY, 24);
		end_c.set(Calendar.MINUTE, 0);
		end_c.set(Calendar.SECOND, 0);

		return this.attenceReportMapper
				.selectAttenceReportFliterByDepartmentAndDate(deptId, start,
						end_c.getTime(), domainId);

	}

	/**
	 * 查询考勤数据 基本版
	 * 
	 * @param deptId
	 * @param start
	 * @param end
	 * @param domainId
	 * @return
	 * @throws ParseException
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public HSSFWorkbook selectAttenceReportBasicFliterByDepartmentAndDate(
			Integer deptId, Date start, Date end, Integer domainId)
			throws ParseException {
		Calendar end_c = Calendar.getInstance();
		end_c.setTime(end);
		end_c.set(Calendar.HOUR_OF_DAY, 23);
		end_c.set(Calendar.MINUTE, 59);
		end_c.set(Calendar.SECOND, 59);
		List<Map<String, Object>> attences = this.attenceReportMapper
				.selectAttenceReportBasicFliterByDepartmentAndDate(deptId,
						start, end_c.getTime(), domainId);

		// write excel begin -----

		WorktimeExample we = new WorktimeExample();
		we.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andDomainIdEqualTo(domainId);
		List<Worktime> wes = this.worktimeMapper.selectByExample(we);
		Worktime wt = null;
		if (CollectionUtils.isNotEmpty(wes)) {
			wt = wes.get(0);
		} else {
		}
		HSSFWorkbook hs = null;
		HSSFSheet sheet = null;
		Row r = null;
		Cell c = null;
		// 第一行标题 ID,姓名，部门，日期，年月(如201601) ，签入时间，签出时间，偏移差1(分)，偏移差2(分）,签入地点，签出地点

		hs = new HSSFWorkbook();
		sheet = hs.createSheet();
		r = sheet.createRow(0);
		c = r.createCell(0);
		c.setCellValue("ID");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(1);
		c.setCellValue("姓名");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(2);
		c.setCellValue("部门");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(3);
		c.setCellValue("日期");
		c.setCellType(Cell.CELL_TYPE_STRING);
		c = r.createCell(4);
		c.setCellValue("年月");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(5);
		c.setCellValue("周几");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(6);
		c.setCellValue("签入时间");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(7);
		c.setCellValue("签出时间");
		c.setCellType(Cell.CELL_TYPE_STRING);
		c = r.createCell(8);
		c.setCellValue("偏移差1(分)");
		c.setCellType(Cell.CELL_TYPE_STRING);
		c = r.createCell(9);
		c.setCellValue("偏移差2(分)");
		c.setCellType(Cell.CELL_TYPE_STRING);
		c = r.createCell(10);
		c.setCellValue("签入地点");
		c.setCellType(Cell.CELL_TYPE_STRING);

		c = r.createCell(11);
		c.setCellValue("签出地点");
		c.setCellType(Cell.CELL_TYPE_STRING);

		/**
		 * u.user_id, u.realname, u.department_name, ae.create_time,
		 * ae.signed_time, ae.signout_time, ae.lated_for_time, ae.le_for_time
		 */
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat df1 = new SimpleDateFormat("HH:mm");
		// 数据行
		Map<String, Object> attence = null;
		Object createTime = null;
		Object signedTime = null;
		Object signoutTime = null;
		Integer lateForTime = null;
		Integer leForTime = null;
		String signedAddress = null;
		String signoutAddress = null;
		Map<String, Integer> noramlAndOverTimes = null;
		HSSFDataFormat format = hs.createDataFormat();
		HSSFCellStyle style = hs.createCellStyle();
		HSSFCellStyle style1 = hs.createCellStyle();
		HSSFCellStyle style2 = hs.createCellStyle();
		for (int i = 1; i < attences.size(); i++) {
			attence = attences.get(i - 1);
			createTime = attence.get("create_time");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime((Date) createTime);
			c2.setTime((Date) createTime);
			signedTime = attence.get("signed_time");
			signoutTime = attence.get("signout_time");
			lateForTime = attence.get("lated_for_time") == null ? null
					: Integer.valueOf(attence.get("lated_for_time").toString());
			leForTime = attence.get("le_for_time") == null ? null : Integer
					.valueOf(attence.get("le_for_time").toString());
			signedAddress = attence.get("signed_address") == null ? ""
					: attence.get("signed_address").toString();
			signoutAddress = attence.get("signout_address") == null ? ""
					: attence.get("signout_address").toString();
			r = sheet.createRow(i);
			c = r.createCell(0);
			c.setCellValue(attence.get("user_id") == null ? 0 : Integer
					.valueOf(attence.get("user_id").toString()));
			c = r.createCell(1);
			c.setCellValue(attence.get("realname") == null ? "" : attence.get(
					"realname").toString());
			c = r.createCell(2);
			c.setCellValue(attence.get("department_name") == null ? "department_name"
					: attence.get("department_name").toString());
			c = r.createCell(3);
			style.setDataFormat(format.getFormat("yyyy-MM-dd"));
			c.setCellStyle(style);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			c.setCellValue(c1.getTime());
			c = r.createCell(4);
			style1.setDataFormat(format.getFormat("yyyyMM"));
			c.setCellStyle(style1);
			c.setCellValue(((Date) createTime));

			c = r.createCell(5);
			c.setCellValue(CalendarByTimeZoneHelper
					.getWeekOfDate(((Date) createTime)));

			// 签入时间
			c = r.createCell(6);
			style2.setDataFormat(format.getFormat("HH:mm"));
			c.setCellStyle(style2);
			if (signedTime != null) {
				if (CalendarByTimeZoneHelper
						.dateDiff(df1.parse(df1.format((Date)signedTime)),
								wt.getStartworkTime()) <= 0) {
					
					Calendar tmpCal = Calendar.getInstance();
					tmpCal.setTime((Date)signedTime);
					Calendar tmpCal1 = Calendar.getInstance();
					tmpCal1.setTime(wt.getStartworkTime());
					
					tmpCal1.set(Calendar.YEAR, tmpCal.get(Calendar.YEAR));
					tmpCal1.set(Calendar.MONTH, tmpCal.get(Calendar.MONTH));
					tmpCal1.set(Calendar.DAY_OF_MONTH, tmpCal.get(Calendar.DAY_OF_MONTH));
					signedTime = tmpCal1.getTime();
					c.setCellValue((Date)signedTime);
				} else {
					c.setCellValue((Date) signedTime);
				}
			}

			c = r.createCell(7);
			c.setCellStyle(style2);
			if (null != signoutTime) {
				c.setCellValue((Date) signoutTime);
			}
			System.out.println(signedTime +"--"+signoutTime);
			// 针对偏移量 计算
			if ((signedTime != null&&(!"".equals(signedTime) )&& (signoutTime != null)&&(!"".equals(signoutTime) ) && CalendarByTimeZoneHelper.dateDiff(
					df1.parse(df1.format((Date)signedTime)), wt.getOffworkTime()) < 0)
					&& CalendarByTimeZoneHelper.dateDiff(
							(Date)signoutTime,
							(Date)signedTime) > 0) {

				int signinOutDiff = CalendarByTimeZoneHelper.dateDiff(
						(Date)signoutTime,
						(Date)signedTime);

				int tmpPy2 = signinOutDiff
						- (CalendarByTimeZoneHelper.dateDiff(
								df1.parse(Constants.ATTENCE_PY), wt.getStartworkTime()));

				// set py1
				if (tmpPy2 <= 0) {
					c = r.createCell(8);
					c.setCellValue(signinOutDiff
							- CalendarByTimeZoneHelper.dateDiff(
									wt.getOffworkTime(), wt.getStartworkTime()));
					c = r.createCell(9);
					c.setCellValue("0");
				} else if (tmpPy2 > 0) {
					c = r.createCell(8);
					c.setCellValue(CalendarByTimeZoneHelper.dateDiff(
							df1.parse(Constants.ATTENCE_PY), wt.getOffworkTime()));
					c = r.createCell(9);
					c.setCellValue(tmpPy2);
				}

			} else {
				c = r.createCell(8);
				c.setCellValue("");
				c = r.createCell(9);
				c.setCellValue("");
			}

			c = r.createCell(10);
			c.setCellValue(signedAddress);
			c = r.createCell(11);
			c.setCellValue(signoutAddress);
		}

		// wirte excel end ------
		return hs;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<Map<String, Object>> selectVisitReportFliterByDepartmentAndDate(
			Integer deptId, Date start, Date end, Integer domainId) {

		return this.attenceReportMapper
				.selectVisitReportFliterByDepartmentAndDate(deptId, start, end,
						domainId);

	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void addEvent(AttenceEvent event) {
		eventMapper.insertSelective(event);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void updateEvent(AttenceEvent event) {
		AttenceEventExample aee = new AttenceEventExample();
		aee.createCriteria().andSeIdEqualTo(event.getSeId());

		this.eventMapper.updateByExampleSelective(event, aee);

	}

	@Transactional(readOnly = true)
	public List<AttenceEvent> findTodayAttenceInfo(Integer userId) {
		AttenceEventExample aee = new AttenceEventExample();
		Calendar start_c = Calendar.getInstance();
		start_c.set(Calendar.HOUR_OF_DAY, 0);
		start_c.set(Calendar.MINUTE, 0);
		start_c.set(Calendar.SECOND, 0);
		Date start = start_c.getTime();
		start_c.add(Calendar.DAY_OF_MONTH, 1);
		Date end = start_c.getTime();
		aee.createCriteria().andUpdateTimeBetween(start, end)
				.andUserIdEqualTo(userId);
		return this.eventMapper.selectByExample(aee);
	}

	public static void main1(String[] args) {
		// Date start = CalendarByTimeZoneHelper.getTodayOClock();
		// Date end = CalendarByTimeZoneHelper.getTomorrowOClock();
		// Calendar c = Calendar.getInstance();
		// c.set(Calendar.HOUR_OF_DAY, 0);
		// c.set(Calendar.MINUTE, 0);
		// c.set(Calendar.SECOND, 0);
		//
		//
		// Calendar c1 = Calendar.getInstance();
		// c1.add(Calendar.DAY_OF_MONTH, 1);
		// c1.set(Calendar.HOUR_OF_DAY, 0);
		// c1.set(Calendar.MINUTE, 0);
		// c1.set(Calendar.SECOND, 0);
		//
		// System.out.println(c.getTime());
		// System.out.println(c1.getTime());
		// System.out.println(start);
		// System.out.println(end);

		Calendar start_c = Calendar.getInstance();
		start_c.set(Calendar.HOUR_OF_DAY, 0);
		start_c.set(Calendar.MINUTE, 0);
		start_c.set(Calendar.SECOND, 0);
		Date start = start_c.getTime();
		start_c.add(Calendar.DAY_OF_MONTH, 1);
		Date end = start_c.getTime();
		System.out.println(start);
		System.out.println(end);
	}

	/**
	 * 
	 * 按时间段或者 上月 当月 下月 查询某人的考勤记录
	 * 
	 * @param user
	 * @param month
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<AttenceEvent> findByUserFilterByStartAndEndTime(User user,
			Date start, Date end, RowBounds rb) {
		AttenceEventExample aee = new AttenceEventExample();

		Calendar c = Calendar.getInstance();
		if (null == end) {

			c.setTime(start);
			// 默认是查询 1号 到下一个月 1号 的数据
			c.set(Calendar.DAY_OF_MONTH, 1);
			start = c.getTime();
			c.add(Calendar.MONTH, 1);
			end = c.getTime();

		}
		aee.createCriteria().andUserIdEqualTo(user.getUserId())
				.andUpdateTimeBetween(start, end)
				.andValidEqualTo(Constants.DATA_VALID);
		aee.setOrderByClause("update_time desc");
		return this.eventMapper.selectByExample(aee, rb);

	}

	private Date configEndDate(Date _start, Byte filterType) {
		Date end = null;
		Calendar c = Calendar.getInstance();

		if (filterType == DAY) {

			c.setTime(_start);
			// 第二天（今天 明天 前天）
			c.add(Calendar.DAY_OF_WEEK, 1);
			end = c.getTime();

		} else if (filterType == WEEK) {
			c.setTime(_start);
			c.add(Calendar.WEEK_OF_MONTH, 1);
			end = c.getTime();

		} else if (filterType == MONTH) {
			c.setTime(_start);
			c.add(Calendar.MONTH, 1);
			end = c.getTime();

		} else {

			c.setTime(_start);
			// 默认是查询 1号 到下一个月 1号 的数据
			c.set(Calendar.DAY_OF_MONTH, 1);
			Date start = _start;
			c.add(Calendar.MONTH, 1);
			end = c.getTime();

		}
		return end;

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndDeptartId(
			Integer departemtnId, Date _rDate, Byte filterType) {
		// Calendar c = Calendar.getInstance();
		// c.setTime(_rDate);
		// // 默认是查询 1号 到下一个月 1号 的数据
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// Date start = _rDate;
		// c.add(Calendar.MONTH, 1);
		// Date end = c.getTime();
		return this.eventMapper.selectShortCutReportByDepartmentAndMonth(
				departemtnId, _rDate, this.configEndDate(_rDate, filterType));

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndDeptartId(
			Integer departemtnId, Date start, Date end) {
		return this.eventMapper.selectShortCutReportByDepartmentAndMonth(
				departemtnId, start, end);

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndUserId(
			Integer userId, Date _rDate, Byte filterType) {
		// Calendar c = Calendar.getInstance();
		// c.setTime(_rDate);
		// // 默认是查询 1号 到下一个月 1号 的数据
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// Date start = _rDate;
		// c.add(Calendar.MONTH, 1);
		// Date end = c.getTime();
		return this.eventMapper.selectShortCutReportByUserAndMonth(userId,
				_rDate, this.configEndDate(_rDate, filterType));

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndUserId(
			Integer userId, Date start, Date end) {

		return this.eventMapper.selectShortCutReportByUserAndMonth(userId,
				start, end);

	}

	/**
	 * 获取单条考勤的详细
	 * 
	 * @param eventId
	 * @return
	 */
	@Transactional(readOnly = true)
	public AttenceEvent findOneEventDetailByEventId(String eventId) {
		return this.eventMapper.selectAttenceEventAndCommentsByEventId(eventId);
	}

	public Map<String, Object> analyDuringTime(Date start, Date end) {
		Map<String, Object> tmp = new TreeMap<String, Object>();

		Calendar c = Calendar.getInstance();
		//
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("M");

		Integer startMonth = Integer.valueOf(sdf2.format(start));
		Integer endMonth = Integer.valueOf(sdf2.format(end));
		if (startMonth == endMonth) {

			c.setTime(start);

			String weekStr = "";
			Integer _start = Integer.valueOf(sdf.format(start));
			Integer _end = Integer.valueOf(sdf.format(end));
			for (int i = _start; i <= _end; i++) {
				weekStr = CalendarByTimeZoneHelper.getWeekOfDate(c.getTime());

				tmp.put(sdf1.format(c.getTime()), sdf1.format(c.getTime())
						+ " (" + weekStr + ")");

				c.add(c.DAY_OF_MONTH, 1);
			}

		} else {
			// 隔着月份 4. 5 20
			String weekStr = "";
			c.setTime(start);
			for (int i = startMonth; i < endMonth; i++) {
				Integer _start = Integer.valueOf(sdf.format(c.getTime()));
				Integer _end = CalendarByTimeZoneHelper.getMonthDays(c
						.getTime());
				for (int j = _start; j <= _end; j++) {
					weekStr = CalendarByTimeZoneHelper.getWeekOfDate(c
							.getTime());
					tmp.put(sdf1.format(c.getTime()), sdf1.format(c.getTime())
							+ " (" + weekStr + ")");
					c.add(c.DAY_OF_MONTH, 1);
				}
				c.set(c.MONTH, i);
			}
			// 最后一个月
			Integer _start = 1;

			Integer _end = Integer.valueOf(sdf.format(end));
			c.setTime(end);
			c.set(c.DAY_OF_MONTH, 1);
			for (int j = _start; j <= _end; j++) {
				weekStr = CalendarByTimeZoneHelper.getWeekOfDate(c.getTime());

				tmp.put(sdf1.format(c.getTime()), sdf1.format(c.getTime())
						+ " (" + weekStr + ")");

				c.add(c.DAY_OF_MONTH, 1);
			}

		}

		return tmp;
	}

	/**
	 * weide 提供的考勤模板
	 * 
	 * @param attences
	 * @param templeFile
	 * @throws IOException
	 */
	public HSSFWorkbook exportCustomAttenceReport(List<AttenceReport> attences,
			FileInputStream templeFile, Date start, Date end)
			throws IOException {
		// 考勤开始时间 与结束时间
		Map<String, Object> duringDate = this.analyDuringTime(start, end);
		Map<String, Integer> dateRowIndexMap = new TreeMap<String, Integer>();
		String title = "";

		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String start_str = df.format(start);
		String end_str = df.format(end);
		title = start_str + "  ~  " + end_str + "考勤";
		HSSFWorkbook hs = null;

		// FileOutputStream fos = new FileOutputStream(new
		// File("/Users/mac/Desktop/test.xls"));
		HSSFSheet sheet = null;
		Row r = null;
		Cell c = null;

		hs = new HSSFWorkbook();
		// 16
		HSSFFont font_16 = hs.createFont();
		font_16.setFontHeightInPoints((short) 16); // 字体高度
		font_16.setFontName("Times"); // 字体

		HSSFRichTextString ts = new HSSFRichTextString(title);
		ts.applyFont(font_16);
		sheet = hs.createSheet();
		sheet.setDefaultColumnWidth(10);
		sheet.setDefaultRowHeightInPoints(80);
		r = sheet.createRow(0);

		HSSFPatriarch p = sheet.createDrawingPatriarch();
		HSSFFont font_9 = hs.createFont();
		font_9.setFontHeightInPoints((short) 9); // 字体高度
		font_9.setFontName("Times"); // 字体

		// border style
		HSSFCellStyle style = hs.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);

		// 223 134 176

		HSSFCellStyle style1 = hs.createCellStyle();
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setWrapText(true);
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style1.setFillForegroundColor(IndexedColors.ROSE.index);

		HSSFCellStyle style2 = hs.createCellStyle();
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setFillForegroundColor(IndexedColors.YELLOW.index);

		// 标题
		c = r.createCell(0);
		c.setCellValue(ts);
		//
		r = sheet.createRow(1);
		ts = new HSSFRichTextString("序号");
		ts.applyFont(font_9);

		c = r.createCell(0);
		c.setCellValue(ts);
		c.setCellStyle(style);
		ts = new HSSFRichTextString("姓名");
		ts.applyFont(font_9);
		c = r.createCell(1);
		c.setCellValue(ts);
		c.setCellStyle(style);
		// 周期 开始
		int tmpI = 2;
		for (String key : duringDate.keySet()) {

			c = r.createCell(tmpI);

			if (duringDate.get(key).toString().contains("周六")
					|| duringDate.get(key).toString().contains("周日")) {

				sheet.setDefaultColumnStyle(c.getColumnIndex(), style1);
				// sheet.getRow(0).getCell(c.getColumnIndex()).setCellStyle(style);
				// c.setCellStyle(style1);
			} else {
				c.setCellStyle(style);
			}
			ts = new HSSFRichTextString(duringDate.get(key).toString());
			ts.applyFont(font_9);
			c.setCellValue(ts);
			dateRowIndexMap.put(key, tmpI);

			tmpI++;

		}

		// 数据开始
		AttenceReport attence = null;
		String[] timesArray = null;
		String[] addressArray = null;
		String[] daysArray = null;
		String[] onNotTimes = null;
		String[] attenceNotice = null;
		String[] attenceOffset = null;
		String userName = "";
		String attenceNoticeOrOffsetTmp = "";
		Row r1 = null;
		sheet.setDefaultColumnStyle(0, style);
		for (int i = 0; i < attences.size(); i++) {
			attence = attences.get(i);
			userName = attence.getCreator();
			timesArray = attence.getTimes();
			addressArray = attence.getAddress();
			daysArray = attence.getDays();
			onNotTimes = attence.getNotOnTimes();
			attenceNotice = attence.getAttenceNotice().split("_");
			attenceOffset = attence.getAttenceOffset().split("_");
			// 分两行 写入
			r = sheet.createRow(i + 2 + (i * 1));
			// 序号
			c = r.createCell(0);
			ts = new HSSFRichTextString((i + 1) + "");
			ts.applyFont(font_9);
			c.setCellValue(ts);
			c.setCellStyle(style);

			c.setCellValue(ts);

			// 名字

			// 先合并单元格
			sheet.addMergedRegion(new CellRangeAddress(r.getRowNum(), r
					.getRowNum() + 1, c.getColumnIndex() + 1, c
					.getColumnIndex() + 1));
			c = r.createCell(1);
			ts = new HSSFRichTextString(userName);
			ts.applyFont(font_9);
			c.setCellValue(ts);
			c.setCellStyle(style);

			c.setCellValue(ts);

			r1 = sheet.createRow(i + 2 + (i * 1) + 1);
			r1.setHeightInPoints(80);
			// 确定写入 cell
			Integer lateForTime = null;
			for (int j = 0; j < daysArray.length; j++) {

				c = r.createCell(dateRowIndexMap.get(daysArray[j]));
				// 写入考勤时间 迟到时间大于 15分钟 算旷工
				String[] tmp = onNotTimes[j].split("-");

				try {
					lateForTime = Integer.valueOf(tmp[0]);
				} catch (NumberFormatException e) {
					lateForTime = null;

				}
				if (lateForTime != null && lateForTime > 15) {
					ts = new HSSFRichTextString(timesArray[j].replace(
							timesArray[j].split("-")[0],
							timesArray[j].split("-")[0] + "旷工"));
					c.setCellStyle(style2);
				} else {

					ts = new HSSFRichTextString(timesArray[j]);
					c.setCellStyle(style);
				}
				ts.applyFont(font_9);
				c.setCellValue(ts);

				// // 地址
				// //此处 加入 考勤批注
				c = r1.createCell(dateRowIndexMap.get(daysArray[j]));
				ts = new HSSFRichTextString(addressArray[j]);
				ts.applyFont(font_9);
				c.setCellValue(ts);
				c.setCellStyle(style);

				if (!attenceNotice[j].equals("null")) {
					HSSFComment attenceComment = p
							.createComment(new HSSFClientAnchor(0, 0, 0, 0,
									(short) 3, 3, (short) 5, 6));
					JSONObject tmpObj = JSONObject.fromObject(attenceNotice[j]);
					JSONObject tmpObj1 = JSONObject
							.fromObject(attenceOffset[j]);
					Object signInNotice = tmpObj.get("signIn");
					Object signOutNotice = tmpObj.get("signOut");
					Object signInOffset = tmpObj1.get("signIn");
					Object signOutOffset = tmpObj1.get("signOut");
					attenceNoticeOrOffsetTmp = ("签到:\n" + (signInNotice == null ? ""
							: signInNotice))
							+ (signInOffset == null ? "" : "(" + signInOffset
									+ "米)\n")
							+ ("签退:\n" + (signOutNotice == null ? ""
									: signOutNotice))
							+ (signOutOffset == null ? "" : "(" + signOutOffset
									+ "米)\n");
					attenceComment.setString(new HSSFRichTextString(
							attenceNoticeOrOffsetTmp));
					attenceComment.setAuthor(userName);

					// if(signInNotice != null &&
					// !signInNotice.toString().trim().equals("")){
					// c.setCellComment(attenceComment);

					// }else if(signOutNotice != null &&
					// !signOutNotice.toString().trim().equals("")){

					c.setCellComment(attenceComment);

					// }

				}

			}

		}

		return hs;

	}

	/**
	 * 
	 * 加入 6:00 ～ 9:00 时间，9:00 以后时间
	 * 
	 * @param attences
	 * @param templeFile
	 * @throws IOException
	 * @throws ParseException
	 */
	public HSSFWorkbook exportCustomAttenceReportV_1_1(
			List<AttenceReport> attences, FileInputStream templeFile,
			Date start, Date end, User user) throws IOException, ParseException {
		WorktimeExample we = new WorktimeExample();
		we.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andDomainIdEqualTo(user.getDomainId());
		List<Worktime> wes = this.worktimeMapper.selectByExample(we);
		Worktime wt = null;
		if (CollectionUtils.isNotEmpty(wes)) {
			wt = wes.get(0);
		} else {
		}
		// 考勤开始时间 与结束时间
		Map<String, Object> duringDate = this.analyDuringTime(start, end);
		Map<String, Integer> dateRowIndexMap = new TreeMap<String, Integer>();
		String title = "";

		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String start_str = df.format(start);
		String end_str = df.format(end);
		title = start_str + "  ~  " + end_str + "考勤";
		HSSFWorkbook hs = null;

		// FileOutputStream fos = new FileOutputStream(new
		// File("/Users/mac/Desktop/test.xls"));
		HSSFSheet sheet = null;
		Row r = null;
		Cell c = null;

		hs = new HSSFWorkbook();
		// 16
		HSSFFont font_16 = hs.createFont();
		font_16.setFontHeightInPoints((short) 16); // 字体高度
		font_16.setFontName("Times"); // 字体

		HSSFRichTextString ts = new HSSFRichTextString(title);
		ts.applyFont(font_16);
		sheet = hs.createSheet();
		sheet.setDefaultColumnWidth(10);
		sheet.setDefaultRowHeightInPoints(80);
		r = sheet.createRow(0);

		HSSFPatriarch p = sheet.createDrawingPatriarch();
		HSSFFont font_9 = hs.createFont();
		font_9.setFontHeightInPoints((short) 9); // 字体高度
		font_9.setFontName("Times"); // 字体

		// border style
		HSSFCellStyle style = hs.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);

		// 223 134 176

		HSSFCellStyle style1 = hs.createCellStyle();
		style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setWrapText(true);
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style1.setFillForegroundColor(IndexedColors.ROSE.index);

		HSSFCellStyle style2 = hs.createCellStyle();
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setFillForegroundColor(IndexedColors.YELLOW.index);

		// 标题
		c = r.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(r.getRowNum(),
				r.getRowNum(), c.getColumnIndex(), c.getColumnIndex()
						+ attences.size()));
		r.setHeightInPoints(60);
		c.setCellValue(ts);
		c.setCellStyle(style);

		//
		r = sheet.createRow(1);
		ts = new HSSFRichTextString("序号");
		ts.applyFont(font_9);

		c = r.createCell(0);
		c.setCellValue(ts);
		c.setCellStyle(style);
		ts = new HSSFRichTextString("姓名");
		ts.applyFont(font_9);
		c = r.createCell(1);
		c.setCellValue(ts);
		c.setCellStyle(style);
		// 周期 开始
		int tmpI = 2;
		for (String key : duringDate.keySet()) {

			c = r.createCell(tmpI);

			if (duringDate.get(key).toString().contains("周六")
					|| duringDate.get(key).toString().contains("周日")) {

				sheet.setDefaultColumnStyle(c.getColumnIndex(), style1);
				// sheet.getRow(0).getCell(c.getColumnIndex()).setCellStyle(style);
				// c.setCellStyle(style1);
			} else {
				c.setCellStyle(style);
			}
			ts = new HSSFRichTextString(duringDate.get(key).toString());
			ts.applyFont(font_9);
			c.setCellValue(ts);
			dateRowIndexMap.put(key, tmpI);

			tmpI++;

		}

		// 数据开始
		AttenceReport attence = null;
		String[] timesArray = null;
		String[] addressArray = null;
		String[] daysArray = null;
		String[] onNotTimes = null;
		String[] attenceNotice = null;
		String[] attenceOffset = null;
		String userName = "";
		String attenceNoticeOrOffsetTmp = "";
		Row r1 = null;
		Row overTimeRow = null;
		sheet.setDefaultColumnStyle(0, style);
		int skippRow = 2;
		for (int i = 0; i < attences.size(); i++) {
			attence = attences.get(i);
			userName = attence.getCreator();
			timesArray = attence.getTimes();
			addressArray = attence.getAddress();
			daysArray = attence.getDays();
			onNotTimes = attence.getNotOnTimes();
			attenceNotice = attence.getAttenceNotice().split("_");
			attenceOffset = attence.getAttenceOffset().split("_");
			// 分三行 写入

			r = sheet.createRow(skippRow + (i * 3));

			// 序号

			c = r.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(r.getRowNum(), r
					.getRowNum() + 2, c.getColumnIndex(), c.getColumnIndex()));
			ts = new HSSFRichTextString((i + 1) + "");
			ts.applyFont(font_9);
			c.setCellValue(ts);
			c.setCellStyle(style);

			c.setCellValue(ts);

			// 名字

			// 先合并单元格

			sheet.addMergedRegion(new CellRangeAddress(r.getRowNum(), r
					.getRowNum() + 2, c.getColumnIndex() + 1, c
					.getColumnIndex() + 1));
			c = r.createCell(1);
			ts = new HSSFRichTextString(userName);
			ts.applyFont(font_9);
			c.setCellValue(ts);
			c.setCellStyle(style);
			c.setCellValue(ts);
			r1 = sheet.createRow(r.getRowNum() + 1);
			overTimeRow = sheet.createRow(r.getRowNum() + 2);
			r1.setHeightInPoints(60);
			// 确定写入 cell
			Integer lateForTime = null;
			Integer leaveForTime = null;
			for (int j = 0; j < daysArray.length; j++) {

				c = r.createCell(dateRowIndexMap.get(daysArray[j]));
				// 写入考勤时间 迟到时间大于 15分钟 算旷工
				String[] tmp = onNotTimes[j].split("-");

				try {
					lateForTime = Integer.valueOf(tmp[0]);
					leaveForTime = Integer.valueOf(tmp[1]);
				} catch (NumberFormatException e) {
					lateForTime = null;

				}
				if (lateForTime != null && lateForTime > 15) {
					ts = new HSSFRichTextString(timesArray[j].replace(
							timesArray[j].split("-")[0],
							timesArray[j].split("-")[0] + "旷工"));
					c.setCellStyle(style2);
				} else {

					ts = new HSSFRichTextString(timesArray[j]);
					c.setCellStyle(style);
				}
				ts.applyFont(font_9);
				c.setCellValue(ts);

				// // 地址
				// //此处 加入 考勤批注
				c = r1.createCell(dateRowIndexMap.get(daysArray[j]));
				ts = new HSSFRichTextString(addressArray[j]);
				ts.applyFont(font_9);
				c.setCellValue(ts);
				c.setCellStyle(style);

				// 工时
				c = overTimeRow.createCell(dateRowIndexMap.get(daysArray[j]));
				// 6~ 9:00 9:00 ~
				ts = new HSSFRichTextString(this.getOverTime(
						timesArray[j].split("-")[0],
						timesArray[j].split("-")[1], lateForTime, leaveForTime,
						wt));

				ts.applyFont(font_9);
				c.setCellValue(ts);
				c.setCellStyle(style);
				if (!attenceNotice[j].equals("null")) {
					HSSFComment attenceComment = p
							.createComment(new HSSFClientAnchor(0, 0, 0, 0,
									(short) 3, 3, (short) 5, 6));
					JSONObject tmpObj = JSONObject.fromObject(attenceNotice[j]);
					JSONObject tmpObj1 = JSONObject
							.fromObject(attenceOffset[j]);
					Object signInNotice = tmpObj.get("signIn");
					Object signOutNotice = tmpObj.get("signOut");
					Object signInOffset = tmpObj1.get("signIn");
					Object signOutOffset = tmpObj1.get("signOut");
					attenceNoticeOrOffsetTmp = ("签到:\n" + (signInNotice == null ? ""
							: signInNotice))
							+ (signInOffset == null ? "" : "(" + signInOffset
									+ "米)\n")
							+ ("签退:\n" + (signOutNotice == null ? ""
									: signOutNotice))
							+ (signOutOffset == null ? "" : "(" + signOutOffset
									+ "米)\n");
					attenceComment.setString(new HSSFRichTextString(
							attenceNoticeOrOffsetTmp));
					attenceComment.setAuthor(userName);

					// if(signInNotice != null &&
					// !signInNotice.toString().trim().equals("")){
					// c.setCellComment(attenceComment);

					// }else if(signOutNotice != null &&
					// !signOutNotice.toString().trim().equals("")){

					c.setCellComment(attenceComment);

					// }

				}

			}

		}

		return hs;

	}

	@Transactional(readOnly = true)
	public IbeaconInfo getIbeaconInfo(Integer deptId) {
		IbeaconInfo beacon = null;
		IbeaconInfoExample iie = new IbeaconInfoExample();
		iie.createCriteria().andDepartmentIdEqualTo(deptId);
		List<IbeaconInfo> beacons = new ArrayList<IbeaconInfo>();
		beacons = this.ibeaconInfoMapper.selectByExample(iie);
		if (CollectionUtils.isNotEmpty(beacons)) {
			beacon = beacons.get(0);
		}
		iie = null;
		beacons = null;
		return beacon;
	}

	public static void main(String[] args) throws ParseException {

	}

	/**
	 * 考勤签退
	 * 
	 * @param event
	 * @return
	 */
	public Object signOut(AttenceEvent event) {

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		OperateResult op = new OperateResult();
		String singoutAddress = event.getSignoutAddress();
		String singoutLongitude = event.getSignoutLongitude();
		String singoutLatitude = event.getSignoutLatitude();

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
		user = this.userMapper.selectByPrimaryKey(event.getUserId());
		if (null == user) {

			if (logger.isErrorEnabled()) {
				logger.error("外访签退 --- 用户不存在");

			}
			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("用户不存在!");
			return op;
		}
		// 获取签退事件
		List<AttenceEvent> attences = this.findTodayAttenceInfo(user
				.getUserId());

		if (CollectionUtils.isEmpty(attences)) {
			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("还没签到，不能签退！");
			op.setData(null);
			return op;
		}

		String attenceDescStr = event.getOptional1();
		String attenceOffsetStr = event.getOptional2();

		String signOutDescTmp = event.getDescription();

		if (StringUtils.isNotBlank(signOutDescTmp)) {

			JSONObject signOutDesc = JSONObject.fromObject(signOutDescTmp
					.trim());
			event = attences.get(0);
			JSONObject signInDesc = JSONObject.fromObject(event
					.getDescription().trim());
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

		WorktimeExample we = new WorktimeExample();

		we.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andDomainIdEqualTo(user.getDomainId());

		List<Worktime> wes = this.worktimeMapper.selectByExample(we);
		Worktime wt = null;
		if (CollectionUtils.isNotEmpty(wes)) {
			wt = wes.get(0);
		} else {

			op.setData(null);
			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("考勤时间未设置!");
			return op;
		}

		Integer leaveTime = CalendarByTimeZoneHelper.getLeaveEarlyMinites(
				event.getSignoutTime(), wt);
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

		this.updateEvent(event);
		op.setStatusCode(Constants.HTTP_200);
		op.setDescription(event.getDescription());

		return op;

	}

	/**
	 * 计算 当天的加班工时
	 * 
	 * @return
	 * @throws ParseException
	 */
	private String getOverTime(String signInDateStr, String signOutDateStr,
			Integer lateMin, Integer leaveFor, Worktime wt)
			throws ParseException {
		String normalTimeStr = ""; // 6~ 9 点时间 － 迟到时间
		String overTimeStr = "";
		DateFormat df = new SimpleDateFormat("HH:mm");

		/**
		 * Date shouldTime = wt.getOffworkTime(); Date actualTime =
		 * df.parse(df.format(c.getTime())); int lateTime = (int)
		 * ((shouldTime.getTime() - actualTime.getTime()) / 1000 / 60); if
		 * (lateTime > 0) { return lateTime; }
		 */
		if (null != leaveFor && leaveFor == 0 && null != lateMin) {
			if (null != signOutDateStr && !"null".equals(signOutDateStr)) {

				Date signOutDate = df.parse(signOutDateStr);
				Date afterNineO = df.parse("21:00");
				Date shouldSignOutDate = wt.getOffworkTime();

				int lateTime = (int) ((signOutDate.getTime() - shouldSignOutDate
						.getTime()) / 1000 / 60);
				int overTime = (int) ((signOutDate.getTime() - afterNineO
						.getTime()) / 1000 / 60);
				int tmp = lateTime - lateMin;
				if (tmp < 0) {
					normalTimeStr = tmp + "(差)";
				} else {
					normalTimeStr = tmp + "(全)";
				}
				// 加班工时
				overTimeStr = overTime > 0 ? overTime + "" : "0";
			} else {

				normalTimeStr = "null";
			}

		} else {

			normalTimeStr = "/";
			overTimeStr = "/";
		}
		return normalTimeStr + "-" + overTimeStr;
	}

	/**
	 * 计算 当天的加班工时
	 * 
	 * @return
	 * @throws ParseException
	 */
	private Map<String, Integer> getNormalAndOverTimes(String signInDateStr,
			String signOutDateStr, Integer lateMin, Integer leaveFor,
			Worktime wt) throws ParseException {
		Integer normalTimeStr = 0; // 6~ 9 点时间 － 迟到时间
		Integer overTimeStr = 0;
		DateFormat df = new SimpleDateFormat("HH:mm");
		if (null != leaveFor && leaveFor == 0 && null != lateMin) {
			if (null != signOutDateStr && !"".equals(signOutDateStr)) {
				Date signOutDate = df.parse(signOutDateStr);
				Date afterNineO = df.parse("21:00");
				Date shouldSignOutDate = wt.getOffworkTime();

				int lateTime = (int) ((signOutDate.getTime() - shouldSignOutDate
						.getTime()) / 1000 / 60);
				int overTime = (int) ((signOutDate.getTime() - afterNineO
						.getTime()) / 1000 / 60);
				int tmp = lateTime - lateMin;
				normalTimeStr = tmp;
				// 加班工时
				overTimeStr = overTime > 0 ? overTime : 0;
			} else {
				normalTimeStr = 0;
			}
		} else {
			normalTimeStr = 0;
			overTimeStr = 0;
		}
		Map<String, Integer> tmp = new HashMap<String, Integer>();
		tmp.put("normal", normalTimeStr);
		tmp.put("overTime", overTimeStr);
		return tmp;
	}

}
