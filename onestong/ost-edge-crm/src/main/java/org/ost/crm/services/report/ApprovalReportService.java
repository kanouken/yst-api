package org.ost.crm.services.report;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.common.tools.date.DateUtil;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.ost.crm.dao.department.DepartmentDao;
import org.ost.crm.dao.report.AttenceReportDao;
import org.ost.entity.org.department.Departments;
import org.ost.entity.report.dto.ApprovalReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalReportService {

	@Autowired
	private AttenceReportDao attenceReportDao;

	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 请假_出差
	 * 
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
	public ByteArrayOutputStream approvalExport(Integer deptId, String signedTime, String signoutTime,
			Integer curPage, Integer perPageSum) throws Exception {
		// 日期
		Date start = null, end = null;
		if (StringUtils.isNotEmpty(signedTime)) {
			start = DateUtil.setDayMinTime(DateUtils.parseDate(signedTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty(signoutTime)) {
			end = DateUtil.setDayMaxTime(DateUtils.parseDate(signoutTime, "yyyy-MM-dd"));
		}

		// 分页
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rowBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());

		// 1、输出的文件地址及名称
		OutputStream outputStream = new FileOutputStream("请假_出差.xlsx");
		// 2、sheet表中的标题行内容，需要输入excel的汇总数据
		String[] businessTrip = { "部门名称", "姓名", "出差开始时间", "出差结束时间", "出差事由", "行程安排", "审批人", "审批结果", "创建时间" };
		List<List<String>> businessTripData = new ArrayList<List<String>>();
		Departments departments=new Departments();
		departments.setDeptId(deptId);
		List<Departments> depts = departmentDao.selectByDept(departments);
		List<ApprovalReportDto> _listBusinessTrip = attenceReportDao.selectbusinessTripByList(depts.get(0).getDeptId(),
					start, end, rowBounds);
			for (ApprovalReportDto businessTripARDto : _listBusinessTrip) {
				List<String> rowData = new ArrayList<String>();
				rowData.add(businessTripARDto.getDepartmentName());
				rowData.add(businessTripARDto.getUserName());
				rowData.add(businessTripARDto.getStartTime());
				rowData.add(businessTripARDto.getEndTime());
				rowData.add(businessTripARDto.getContent());
				rowData.add(businessTripARDto.getSchedules());
				rowData.add(businessTripARDto.getUpdateBy());
				rowData.add(businessTripARDto.getState());
				rowData.add(businessTripARDto.getCreateTime());
				businessTripData.add(rowData);
			}
			// 请假
			String[] leave = { "部门名称", "姓名", "请假开始时间", "请假结束时间", "请假类型", "请假原因", "审批人", "审批结果", "创建时间" };
			List<List<String>> leaveData = new ArrayList<List<String>>();
			List<ApprovalReportDto> _listLeave = attenceReportDao.selectLeaveByList(depts.get(0).getDeptId(), start,
					end, rowBounds);
			for (ApprovalReportDto leaveARDto : _listLeave) {
				List<String> rowData = new ArrayList<String>();
				rowData.add(leaveARDto.getDepartmentName());
				rowData.add(leaveARDto.getUserName());
				rowData.add(leaveARDto.getStartTime());
				rowData.add(leaveARDto.getEndTime());
				rowData.add(leaveARDto.getLeaveType());
				rowData.add(leaveARDto.getContent());
				rowData.add(leaveARDto.getUpdateBy());
				rowData.add(leaveARDto.getState());
				rowData.add(leaveARDto.getCreateTime());
				leaveData.add(rowData);
			}
		
		// 3、写入
		ExcelUtil excelUtil = new ExcelUtil<Map<String, Object>>();
		ByteArrayOutputStream xlsOutput = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 第一个表格内容
		excelUtil.exportExcel(workbook, 0, "出差", businessTrip, businessTripData, outputStream);
		// 第二个表格内容
		excelUtil.exportExcel(workbook, 1, "请假", leave, leaveData, outputStream);

		// 写出
		workbook.write(xlsOutput);

		return xlsOutput;

	}
}
