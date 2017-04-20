package org.ost.crm.services.report;

import java.io.ByteArrayOutputStream;
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
import org.ost.crm.dao.department.DepartmentDao;
import org.ost.crm.dao.report.AttenceReportDao;
import org.ost.entity.org.department.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttenceReportService {

	@Autowired
	private AttenceReportDao attenceReportDao;
	
	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 考勤
	 * @param departmentName
	 * @param signedTime
	 * @param signoutTime
	 * @param curPage
	 * @param perPageSum
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public ByteArrayOutputStream attenceExport(Integer deptId, String signedTime, String signoutTime, Integer curPage,
			Integer perPageSum) throws Exception {
		//日期
		Date start = null, end = null;
		if (StringUtils.isNotEmpty(signedTime)) {
			start = DateUtil.setDayMinTime(DateUtils.parseDate(signedTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty(signoutTime)) {
			end = DateUtil.setDayMinTime(DateUtils.parseDate(signoutTime, "yyyy-MM-dd"));
		}

		//分页
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rowBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Departments departments=new Departments();
		departments.setDeptId(deptId);
		List<Departments> depts = departmentDao.selectByDept(departments);
		result = attenceReportDao.selectAttenceByList(depts, start, end, rowBounds);
		// 定义表头
		// 表头每列有2个字段
		// 1 表头中文名
		// 2 表头对应字段名
		// 注意：因为数据源是HashMap格式，所以表头对应字段为HashMap中key值
		List<String[]> head = new ArrayList<>();
		head.add("部门名称,departmentName".split(","));
		head.add("姓名,userName".split(","));
		head.add("刷卡日期,signedDate".split(","));
		head.add("签到时间,signedTime".split(","));
		head.add("签到地点,signedAddress".split(","));
		head.add("签退时间,signoutTime".split(","));
		head.add("签退地点,signoutAddress".split(","));

		// 获取excel文件二进制流
		ByteArrayOutputStream xlsOutput = null;
		ExcelUtil excelUtil = new ExcelUtil<Map<String, Object>>();
		xlsOutput = excelUtil.exportToExcel("考勤", head, result);
		return xlsOutput;
	}
	
}
