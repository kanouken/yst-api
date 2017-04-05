package org.ost.crm.services.report;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.common.tools.date.DateUtil;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.ost.crm.dao.report.AttenceReportDao;
import org.ost.entity.report.dto.ApprovalReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalReportService {

	@Autowired
	private AttenceReportDao attenceReportDao;

	@Transactional(readOnly = true)
	public ByteArrayOutputStream approvalExport(Integer deptId, String signedTime,
			String signoutTime, Integer curPage, Integer perPageSum) throws Exception {
		Date start = null, end = null;
		if (StringUtils.isNotEmpty(signedTime)) {
			start = DateUtil.setDayMinTime(DateUtils.parseDate(signedTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty(signoutTime)) {
			end = DateUtil.setDayMaxTime(DateUtils.parseDate(signoutTime, "yyyy-MM-dd"));
		}

		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rowBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());

		//1、输出的文件地址及名称
		OutputStream out = new FileOutputStream("请假_出差报表.xlsx");
		//2、sheet表中的标题行内容，需要输入excel的汇总数据
		String[] summary = { "部门名称", "姓名","出差开始时间" ,"出差结束时间","出差事由","行程安排","审批人","审批结果","创建时间"};
		List<List<String>> summaryData = new ArrayList<List<String>>();
		List<ApprovalReportDto> _listApproval=attenceReportDao.selectApprovalByList(deptId, start, end, rowBounds);
		for (ApprovalReportDto approvalReport:_listApproval) {
		List<String> rowData = new ArrayList<String>();
		rowData.add(approvalReport.getDeptId().toString());
		rowData.add(approvalReport.getUserName());
		rowData.add(approvalReport.getStartTime());
		rowData.add(approvalReport.getEndTime());
		rowData.add(approvalReport.getContent());
		rowData.add(approvalReport.getSchedules());
		rowData.add(approvalReport.getUpdateBy());
		rowData.add(approvalReport.getState());
		rowData.add(approvalReport.getCreateTime());
		summaryData.add(rowData);
		}

		String[] water = { "部门名称", "姓名" ,"请假开始时间","请假结束时间","请假类型"
		,"请假原因","审批人","审批结果","创建时间"};
		List<List<String>> waterData = new ArrayList<List<String>>();
		List<ApprovalReportDto> _listWater=attenceReportDao.selectByParam(deptId, start, end, rowBounds);
		for (ApprovalReportDto wat:_listWater) {
		List<String> rowData = new ArrayList<String>();
		rowData.add(wat.getDeptId().toString());
		rowData.add(wat.getUserName());
		rowData.add(wat.getStartTime());
		rowData.add(wat.getEndTime());
		rowData.add(wat.getLeaveType());
		rowData.add(wat.getContent());
		rowData.add(wat.getUpdateBy());
		rowData.add(wat.getState());
		rowData.add(wat.getCreateTime());
		waterData.add(rowData);
		}
		ExcelUtil excelUtil = new ExcelUtil<Map<String, Object>>();
		ByteArrayOutputStream xlsOutput = null;
		XSSFWorkbook workbook = new XSSFWorkbook();
			//第一个表格内容
		xlsOutput=excelUtil.exportExcel(workbook, 0, "出差报表", summary, summaryData,out);
			//第二个表格内容
		xlsOutput=excelUtil.exportExcel(workbook, 1, "请假报表", water, waterData,out);
		
		return xlsOutput;
	}
}
