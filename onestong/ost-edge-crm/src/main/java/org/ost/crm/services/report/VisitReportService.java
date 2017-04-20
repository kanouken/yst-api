package org.ost.crm.services.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.common.tools.OperateResult;
import org.common.tools.date.DateUtil;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.dao.auth.UsersRolesMapper;
import org.ost.crm.dao.department.DepartmentDao;
import org.ost.crm.dao.report.VisitReportDao;
import org.ost.crm.dao.web.user.UserDao;
import org.ost.crm.model.visit.dto.VisitReportDetailDto;
import org.ost.crm.model.visit.dto.VisitSupportDto;
import org.ost.crm.services.visit.VisitService;
import org.ost.entity.auth.Role;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.org.department.Departments;
import org.ost.entity.user.Users;
import org.ost.entity.user.UsersRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitReportService {

	@Autowired
	private VisitReportDao reportDao;

	@Autowired
	ContactsServiceClient contactsServiceClient;

	@Autowired
	private UserDao userDao;
	@Autowired
	UsersRolesMapper userRoleMapper;

	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 
	 * @param user
	 * @param page
	 *            分页参数
	 * @param hasBus
	 *            有无商机 0 无 1 有
	 * @param managerOwnerName
	 *            客户经理
	 * @param contactType
	 *            联系类型
	 * @param startDate
	 *            yyyy/MM/dd
	 * @param endDate
	 *            yyyy/MM/dd
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> queryList(Users user, Page page, String hasBus, String managerOwnerName,
			String contactType, String startDate, String endDate) throws Exception {

		Boolean isDirector = false;
		if (user.getRole() != null) {
			if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		if (!isDirector) {
			managerOwnerName = user.getRealname();
		}
		Date start = null, end = null;
		List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(startDate)) {
			start = DateUtil.setDayMinTime(DateUtils.parseDate(startDate, "yyyy/MM/dd"));
		}
		if (StringUtils.isNotEmpty(endDate)) {
			end = DateUtil.setDayMaxTime(DateUtils.parseDate(endDate, "yyyy/MM/dd"));
		}
		RowBounds rBounds = new RowBounds(-10, page.getPerPageSum());
		List<Map<String, Object>> result = reportDao.selectBy(hasBus, managerOwnerName, contactType, start, end,
				rBounds);

		Integer totalRecords = MapUtils.getInteger(result.get(0), "total");

		if (totalRecords != null && totalRecords > 0) {
			rBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());
			// 获取关联联系人
			records = reportDao.selectBy(hasBus, managerOwnerName, contactType, start, end, rBounds);
			int[] visitIds = records.stream().mapToInt(r -> MapUtils.getInteger(r, "id")).toArray();
			OperateResult<List<VisitContactsDto>> contactsOperateResult = this.contactsServiceClient
					.queryByVisits(user.getSchemaId(), visitIds);
			if (contactsOperateResult.success()) {
				List<VisitContactsDto> vistContactsDtos = contactsOperateResult.getData();
				records.forEach(r -> {
					Optional<VisitContactsDto> _r = vistContactsDtos.stream()
							.filter(vc -> vc.getVisitEventId() == MapUtils.getInteger(r, "id")).findFirst();
					if (_r.isPresent()) {
						r.put("contact",
								_r.get().getContacts().stream().map(c -> c.getName()).collect(Collectors.joining(",")));
					}
				});
			} else {
				throw new ApiException("获取外访联系人失败", contactsOperateResult.getInnerException());
			}

		}
		page.setTotalRecords(totalRecords);
		return OperateResult.renderPage(page, records);
	}

	/**
	 * 联系活动报表 导出数据
	 * 
	 * @param userId
	 *            当前用户id
	 * @param schemaID
	 * @param hasBus
	 * @param managerOwnerName
	 * @param contactType
	 * @param startDate
	 * @param endDate
	 * @param curPage
	 * @param perPageSum
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ByteArrayOutputStream export(Integer userId, String schemaID, String hasBus, String managerOwnerName,
			String contactType, String startDate, String endDate, Integer curPage, Integer perPageSum)
					throws Exception {
		if (null != userId) {
			// check role
			Users currentUser = this.userDao.selectByPrimaryKey(userId);
			Role role = this.userRoleMapper.selectRolesByUser(userId).get(0);
			Boolean isDirector = false;
			if (role.getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
			if (!isDirector) {
				managerOwnerName = currentUser.getRealname();
			}
		}

		ByteArrayOutputStream xlsOutput = null;
		// 检索数据
		Date start = null, end = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(startDate)) {
			start = DateUtil.setDayMinTime(DateUtils.parseDate(startDate, "yyyy/MM/dd"));
		}
		if (StringUtils.isNotEmpty(endDate)) {
			end = DateUtil.setDayMaxTime(DateUtils.parseDate(endDate, "yyyy/MM/dd"));
		}

		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());
		result = reportDao.selectBy(hasBus, managerOwnerName, contactType, start, end, rBounds);

		// 定义表头
		// 表头每列有2个字段
		// 1 表头中文名
		// 2 表头对应字段名
		// 注意：因为数据源是HashMap格式，所以表头对应字段为HashMap中key值
		List<String[]> head = new ArrayList<>();
		head.add("客户名称,name".split(","));
		head.add("联系类型,contactType".split(","));
		head.add("联系人,contact".split(","));
		head.add("商机转化,hasBusStr".split(","));
		head.add("联系时间,createTimeStr".split(","));

		// 获取excel文件二进制流
		ExcelUtil excelUtil = new ExcelUtil<Map<String, Object>>();
		xlsOutput = excelUtil.exportToExcel("联系活动报表", head, result);
		return xlsOutput;
	}

	/**
	 * a 外访客户数： group by customerid b 外访总次数：totalRecords c 客户平均外访次数： b/a d
	 * 外访商机转换率 hasBus/b
	 * 
	 * @param user
	 * @param hasBus
	 * @param managerOwnerName
	 * @param contactType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> querySummary(Users user, String hasBus, String managerOwnerName, String contactType,
			String startDate, String endDate) throws ParseException {
		Boolean isDirector = false;
		if (user.getRole() != null) {
			if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		if (!isDirector) {
			managerOwnerName = user.getRealname();
		}
		Date start = null, end = null;
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(startDate)) {
			start = DateUtil.setDayMinTime(DateUtils.parseDate(startDate, "yyyy/MM/dd"));
		}
		if (StringUtils.isNotEmpty(endDate)) {
			end = DateUtil.setDayMaxTime(DateUtils.parseDate(endDate, "yyyy/MM/dd"));
		}
		result = this.reportDao.selectSummary(hasBus, managerOwnerName, contactType, start, end);

		Integer totalRecords = MapUtils.getInteger(result, "totalRecords", 0);
		Integer totalBusChance = MapUtils.getInteger(result, "hasBusChance", 0);
		Integer totalCustomer = MapUtils.getInteger(result, "totalCustomer", 0);

		result.clear();
		result.put("avg", totalCustomer == 0 ? 0 : totalRecords / totalCustomer);
		result.put("count", totalCustomer);
		BigDecimal rate = null;
		if (totalRecords != 0) {
			rate = new BigDecimal(totalBusChance).divide(new BigDecimal(totalRecords), 2, RoundingMode.HALF_UP);
		} else {
			rate = BigDecimal.ZERO;
		}
		result.put("rate", (rate.multiply(new BigDecimal(100)) + "%"));
		result.put("total", totalRecords);
		return result;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> queryChart(Users user, String hasBus, String managerOwnerName,
			String contactType) {
		Boolean isDirector = false;
		if (user.getRole() != null) {
			if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		if (!isDirector) {
			managerOwnerName = user.getRealname();
		}

		return this.reportDao.selectByMonth(hasBus, managerOwnerName, contactType, null, null);

	}

	/**
	 * 外访列表导出 部门名称 姓名 外访客户 外访类型 外访日期 签到时间（销售人员） 签到地点（销售人员） 签退时间（销售人员） 签退地点（销售人员）
	 * 支持人员 签到时间（支持人员） 签到地点（支持人员） 签退时间（支持人员） 签退地点（支持人员）
	 * 
	 * @param departmentId
	 * @param startTime
	 * @param endTime
	 * @throws ParseException
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public ByteArrayOutputStream exportVisit(Integer departmentId, String startTime, String endTime)
			throws ParseException, IOException {
		Date start = null, end = null;
		start = DateUtils.parseDate(startTime, "yyyy-MM-dd");
		end = DateUtils.parseDate(endTime, "yyyy-MM-dd");
		start = DateUtil.setDayMinTime(start);
		end = DateUtil.setDayMinTime(end);

		Departments departments = new Departments();
		departments.setDeptId(departmentId);
		List<Departments> depts = departmentDao.selectByDept(departments);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", start);
		params.put("endTime", end);
		params.put("depts", depts);
		List<VisitReportDetailDto> reportDetailDtos = reportDao.selectByDepartmentAndTime(params);

		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = null;
		Row head = null;
		Cell c = null;

		sheet = workbook.createSheet("外访");

		// head
		// 支持人员 签到时间（支持人员） 签到地点（支持人员） 签退时间（支持人员） 签退地点（支持人员）

		head = sheet.createRow(0);
		c = head.createCell(0);
		c.setCellValue("部门名称");
		c = head.createCell(1);
		c.setCellValue("姓名");
		c = head.createCell(2);
		c.setCellValue("外访客户");
		c = head.createCell(3);
		c.setCellValue("外访类型");
		c = head.createCell(4);
		c.setCellValue("外访日期");
		c = head.createCell(5);
		c.setCellValue("签到时间（销售人员）");
		c = head.createCell(6);
		c.setCellValue("签到地点（销售人员）");
		c = head.createCell(7);
		c.setCellValue("签退时间（销售人员）");
		c = head.createCell(8);
		c.setCellValue("签退地点（销售人员）");
		VisitReportDetailDto reportDetailDto = null;
		VisitSupportDto createDto = null;
		VisitSupportDto supporter = null;
		Row body = null;
		for (int i = 0; i < reportDetailDtos.size(); i++) {
			body = sheet.createRow(i + 1);
			reportDetailDto = reportDetailDtos.get(i);

			Map<Byte, List<VisitSupportDto>> roleGroup = reportDetailDto.getSupporters().stream()
					.collect(Collectors.groupingBy(VisitSupportDto::getRole));
			// 创建者
			List<VisitSupportDto> supporters = (List<VisitSupportDto>) MapUtils.getObject(roleGroup,
					VisitService.ROLE_VISIT_CREATOR);
			createDto = supporters.get(0);

			body.createCell(0).setCellValue(createDto.getOrganizeName());
			body.createCell(1).setCellValue(createDto.getUserName());
			body.createCell(2).setCellValue(reportDetailDto.getCustomerName());
			body.createCell(3).setCellValue(reportDetailDto.getVisitType());
			body.createCell(4).setCellValue(reportDetailDto.getVisitTimeStr());
			body.createCell(5)
					.setCellValue(createDto.getAttence() != null ? createDto.getAttence().getSignInTimeStr() : null);
			body.createCell(6)
					.setCellValue(createDto.getAttence() != null ? createDto.getAttence().getSignInAddress() : null);
			body.createCell(7)
					.setCellValue(createDto.getAttence() != null ? createDto.getAttence().getSignOutTimeStr() : null);
			body.createCell(8)
					.setCellValue(createDto.getAttence() != null ? createDto.getAttence().getSignOutAddress() : null);

			// 支持者

			supporters = (List<VisitSupportDto>) MapUtils.getObject(roleGroup, VisitService.ROLE_VISIT_SUPPORT);
			// 支持人员 签到时间（支持人员） 签到地点（支持人员） 签退时间（支持人员） 签退地点（支持人员）
			if (CollectionUtils.isNotEmpty(supporters)) {
				for (int j = 0; j < supporters.size(); j++) {
					supporter = supporters.get(j);
					head.createCell(8 + j * 5 + 1).setCellValue("支持人员" + (j + 1));
					head.createCell(8 + j * 5 + 2).setCellValue("签到时间（支持人员）");
					head.createCell(8 + j * 5 + 3).setCellValue("签到地点（支持人员）");
					head.createCell(8 + j * 5 + 4).setCellValue("签退时间（支持人员）");
					head.createCell(8 + j * 5 + 5).setCellValue("签退地点（支持人员）");

					body.createCell(8 + j * 5 + 1).setCellValue(supporter.getUserName());
					body.createCell(8 + j * 5 + 2).setCellValue(
							supporter.getAttence() != null ? supporter.getAttence().getSignInTimeStr() : null);
					body.createCell(8 + j * 5 + 3).setCellValue(
							supporter.getAttence() != null ? supporter.getAttence().getSignInAddress() : null);
					body.createCell(8 + j * 5 + 4).setCellValue(
							supporter.getAttence() != null ? supporter.getAttence().getSignOutTimeStr() : null);
					body.createCell(8 + j * 5 + 5).setCellValue(
							supporter.getAttence() != null ? supporter.getAttence().getSignOutAddress() : null);
				}

			}
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		workbook.write(output);

		return output;
	}

}
