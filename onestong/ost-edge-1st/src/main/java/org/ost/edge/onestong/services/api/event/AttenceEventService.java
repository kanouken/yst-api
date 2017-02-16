package org.ost.edge.onestong.services.api.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.common.tools.date.CalendarByTimeZoneHelper;
import org.ost.edge.onestong.controller.api.base.BaseService;
import org.ost.edge.onestong.dao.event.AttenceEventMapper;
import org.ost.edge.onestong.dao.report.AttenceReportMapper;
import org.ost.edge.onestong.dao.system.ibeacon.IbeaconInfoMapper;
import org.ost.edge.onestong.model.event.AttenceEvent;
import org.ost.edge.onestong.model.event.AttenceEventExample;
import org.ost.edge.onestong.model.report.AttenceReport;
import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfo;
import org.ost.edge.onestong.model.system.ibeacon.IbeaconInfoExample;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.tools.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

@SuppressWarnings("all")
@Service
public class AttenceEventService extends BaseService {

	private static final Logger log = LoggerFactory
			.getLogger(AttenceEventService.class);
	@Autowired
	private AttenceEventMapper eventMapper;

	@Autowired
	private AttenceReportMapper attenceReportMapper;

	@Autowired
	private IbeaconInfoMapper ibeaconInfoMapper;

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

		return this.attenceReportMapper
				.selectAttenceReportFliterByDepartmentAndDate(deptId, start,
						end, domainId);

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
			Integer departemtnId, Date start,Date end) {
		return this.eventMapper.selectShortCutReportByDepartmentAndMonth(
				departemtnId, start,end);

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
		if(startMonth == endMonth){
			
			c.setTime(start);

			String weekStr = "";
			Integer _start = Integer.valueOf(sdf.format(start));
			Integer _end = Integer.valueOf(sdf.format(end));
			for (int i = _start; i <= _end; i++) {
				weekStr = CalendarByTimeZoneHelper.getWeekOfDate(c.getTime());

				tmp.put(sdf1.format(c.getTime()), sdf1.format(c.getTime()) + " ("
						+ weekStr + ")");

				c.add(c.DAY_OF_MONTH, 1);
			}
			
		}else{
			//隔着月份 4. 5 20
			String weekStr = "";
			c.setTime(start);
			for(int i = startMonth ;i< endMonth;i++){
				Integer _start = Integer.valueOf(sdf.format(c.getTime()));
				Integer _end = CalendarByTimeZoneHelper.getMonthDays(c.getTime());
				for (int j = _start; j <= _end; j++) {
					weekStr = CalendarByTimeZoneHelper.getWeekOfDate(c.getTime());
					tmp.put(sdf1.format(c.getTime()), sdf1.format(c.getTime()) + " ("
							+ weekStr + ")");
					c.add(c.DAY_OF_MONTH, 1);
				}
				c.set(c.MONTH, i);
			}
			//最后一个月
			Integer _start =1;
			
			Integer _end =  Integer.valueOf(sdf.format(end));
			c.setTime(end);
			c.set(c.DAY_OF_MONTH, 1);
			for (int j = _start; j <= _end; j++) {
				weekStr = CalendarByTimeZoneHelper.getWeekOfDate(c.getTime());

				tmp.put(sdf1.format(c.getTime()), sdf1.format(c.getTime()) + " ("
						+ weekStr + ")");

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
		
		HSSFPatriarch p=sheet.createDrawingPatriarch();
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

//				// 地址
//				//此处 加入 考勤批注 
				c = r1.createCell(dateRowIndexMap.get(daysArray[j]));
				ts = new HSSFRichTextString(addressArray[j]);
				ts.applyFont(font_9);
				c.setCellValue(ts);
				c.setCellStyle(style);
				
				
				
				if(!attenceNotice[j].equals("null")){
					HSSFComment attenceComment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					JSONObject tmpObj  =  JSONObject.fromObject(attenceNotice[j]);
					JSONObject tmpObj1 =  JSONObject.fromObject(attenceOffset[j]);
					Object signInNotice = tmpObj.get("signIn");
					Object signOutNotice = tmpObj.get("signOut");
					Object signInOffset = tmpObj1.get("signIn");
					Object signOutOffset = tmpObj1.get("signOut");
					attenceNoticeOrOffsetTmp = ("签到:\n"+(signInNotice == null ?"":signInNotice))+(signInOffset == null? "" :"("+ signInOffset +"米)\n"  )+("签退:\n"+(signOutNotice == null ?"":signOutNotice))+(signOutOffset == null? "" :"("+ signOutOffset +"米)\n"  );
					attenceComment.setString(new HSSFRichTextString(attenceNoticeOrOffsetTmp));
					attenceComment.setAuthor(userName);
					
					
					if(signInNotice != null && !signInNotice.toString().trim().equals("")){
						c.setCellComment(attenceComment);
						
					}else if(signOutNotice != null && !signOutNotice.toString().trim().equals("")){
					
						
						c.setCellComment(attenceComment);

						
					}
					
				}
				
			

			}

		}

		return hs;

	}

	public static void main(String[] args) throws IOException, ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = sdf.parse("2015-10-01");
		Date end = sdf.parse("2015-10-30");

		new AttenceEventService().exportCustomAttenceReport(null, null, start,
				end);
	}

	
	
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

}
