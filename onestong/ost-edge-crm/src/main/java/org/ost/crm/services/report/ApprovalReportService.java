package org.ost.crm.services.report;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.date.DateUtil;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.ost.crm.dao.report.AttenceReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalReportService {

	@Autowired
	private AttenceReportDao attenceReportDao;
	
	@Transactional(readOnly = true)
	public ByteArrayOutputStream approvalExport(String approvalType,Integer deptId, String signedTime, String signoutTime, Integer curPage,
			Integer perPageSum) throws Exception {
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

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = attenceReportDao.selectApprovalByList(approvalType,deptId, start, end, rowBounds);

		// 定义表头
		// 表头每列有2个字段
		// 1 表头中文名
		// 2 表头对应字段名
		// 注意：因为数据源是HashMap格式，所以表头对应字段为HashMap中key值
		List<String[]> head = new ArrayList<>();
		head.add("部门名称,deptId".split(","));
		head.add("姓名,userName".split(","));
		head.add("出差开始时间,startTime".split(","));
		head.add("出差结束时间,endTime".split(","));
		head.add("出差事由,content".split(","));
		head.add("行程安排,schedules".split(","));
		head.add("审批人,updateBy".split(","));
		head.add("审批结果,state".split(","));
		head.add("创建时间,createTime".split(","));
		
		// 获取excel文件二进制流
		ByteArrayOutputStream xlsOutput = null;
		ExcelUtil excelUtil = new ExcelUtil<Map<String, Object>>();
		xlsOutput = excelUtil.exportToExcel("请假_出差报表", head, result);

		return xlsOutput;
	}
}
