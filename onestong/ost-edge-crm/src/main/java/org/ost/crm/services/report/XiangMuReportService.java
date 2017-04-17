package org.ost.crm.services.report;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.common.tools.exception.ApiException;
import org.common.tools.string.StringUtil;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.dao.auth.UsersRolesMapper;
import org.ost.crm.dao.report.XiangMuReportDao;
import org.ost.crm.dao.web.user.UserDao;
import org.ost.crm.services.base.BaseService;
import org.ost.crm.services.project.ProjectService;
import org.ost.entity.auth.Role;
import org.ost.entity.contacts.contactsinfo.dto.ContactsInfoDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.project.dto.ProjectDetailDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.ost.entity.user.UsersRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by lugia on 2017/3/15.
 */
@Service
public class XiangMuReportService extends BaseService {
	@Autowired
	private CustomerServiceClient _CustomerServiceClient;

	@Autowired
	private XiangMuReportDao _XiangMuReportDao;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserDao userDao;
	@Autowired
	UsersRolesMapper userRoleMapper;

	/**
	 * 项目报表 获取统计数据
	 *
	 */
	@Transactional(readOnly = true)
	public Object count(Users user, Map<String, Object> params, Integer curPage, Integer perPageSum)
			throws InterruptedException, ExecutionException {
		Boolean isDirector = false;
		if (user.getRole() != null) {
			if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		if (!isDirector) {
			params.put("managerOwnerName", user.getRealname());
		}
		XiaoShouReportDto result = new XiaoShouReportDto();
		params.put("schemaID", user.getSchemaId());
		// 总筛选数量
		Integer totalRecords = _XiangMuReportDao.searchListCount(params);
		// 其中失败的项目数量
		params.put("projectState", "3");
		Integer totalFailRecords = _XiangMuReportDao.searchListCount(params);

		result.setTotalCount(totalRecords);
		Integer totalConversionRate = Math.round(
				(Float.valueOf(totalRecords) - Float.valueOf(totalFailRecords)) / Float.valueOf(totalRecords) * 100);
		result.setTotalConversionRate(totalConversionRate);
		return result;
	}

	/**
	 * 项目报表 获取图表数据
	 *
	 */
	@Transactional(readOnly = true)
	public Object chart(Users user, Map<String, Object> params, Integer curPage, Integer perPageSum)
			throws InterruptedException, ExecutionException {
		Boolean isDirector = false;
		if (user.getRole() != null) {
			if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		if (!isDirector) {
			params.put("managerOwnerName", user.getRealname());
		}
		List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();

		params.put("schemaID", user.getSchemaId());

		result = _XiangMuReportDao.searchListChart(params);
		return result;
	}

	/**
	 * 项目报表 导出数据
	 *
	 */
	@Transactional(readOnly = true)
	public ByteArrayOutputStream export(Integer userId, Map<String, Object> params, Integer curPage, Integer perPageSum)
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
				params.put("managerOwnerName", currentUser.getRealname());
			}
		}

		ByteArrayOutputStream xlsOutput = null;
		// 检索数据
		List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		params.put("page", page);
		params.put("schemaID", params.get("schemaID"));
		result = searchData(params);

		// 定义表头
		// 表头每列有2个字段
		// 1 表头中文名
		// 2 表头对应字段名
		// 注意：表头对应字段名字首字母要大写，因为反射方法需要拼接get来获取值
		List<String[]> head = new ArrayList<>();
		head.add("客户名称,CustomerName".split(","));
		head.add("项目名称,ProjectName".split(","));
		head.add("项目分类,ProjectType".split(","));
		head.add("项目状态,ProjectState".split(","));
		head.add("项目阶段,ProjectStep".split(","));
		head.add("项目时间,CreateTimeStr".split(","));

		// 获取excel文件二进制流
		ExcelUtil excelUtil = new ExcelUtil<XiaoShouReportDto>();
		xlsOutput = excelUtil.exportToExcel("项目报表", head, result);
		return xlsOutput;
	}

	/**
	 * 项目报表 获取列表数据
	 *
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> list(Users user, Map<String, Object> params, Integer curPage, Integer perPageSum)
			throws InterruptedException, ExecutionException {
		Boolean isDirector = false;
		if (user.getRole() != null) {
			if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		if (!isDirector) {
			params.put("managerOwnerName", user.getRealname());
		}
		List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		params.put("page", page);
		params.put("schemaID", user.getSchemaId());

		Integer totalRecords = _XiangMuReportDao.searchListCount(params);
		page.setTotalRecords(totalRecords);
		if (totalRecords <= 0) {
			return OperateResult.renderPage(page, result);
		}

		return OperateResult.renderPage(page, searchData(params));
	}

	private List<XiaoShouReportDto> searchData(Map<String, Object> params)
			throws InterruptedException, ExecutionException {
		List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();

		// 查询项目销售数据
		result = _XiangMuReportDao.searchList(params);

		// 查询项目客户信息 并发查询
		int[] customerIds = result.stream().filter(p -> !StringUtil.isNullOrEmpty(p.getCustomerID()))
				.mapToInt(p -> p.getCustomerID()).toArray();
		CompletableFuture<OperateResult<List<CustomerListDto>>> customerFuture = CompletableFuture.supplyAsync(
				() -> this._CustomerServiceClient.queryByIds(params.get("schemaID").toString(), customerIds));

		// 填充客户信息
		OperateResult<List<CustomerListDto>> customerResult = customerFuture.get();
		if (!customerResult.success()) {
			throw new ApiException("客户信息获取失败", customerResult.getInnerException());
		}

		// 填充数据
		for (XiaoShouReportDto project : result) {
			if (customerResult.getData() != null) {
				Optional<CustomerListDto> customer = customerResult.getData().stream()
						.filter(p -> p.getId().equals(project.getCustomerID())).findFirst();
				if (customer.isPresent() && !StringUtil.isNullOrEmpty(customer.get())) {
					project.setCustomerName(customer.get().getName());
				}
			}
		}

		return result;
	}

	@Transactional(readOnly = true)
	public ByteArrayOutputStream projectExport(Integer curPage, Integer perPageSum) {

		// 分页
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rowBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());

		return null;
	}

	@Transactional(readOnly = true)
	public HSSFWorkbook projectExports(Integer curPage, Integer perPageSum, Integer projectId, String schemaId,
			HttpServletResponse response) throws InterruptedException, ExecutionException, Exception {

		// 分页
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rowBounds = new RowBounds(page.getNextPage(), page.getPerPageSum());

		Users user = new Users();
		user.setSchemaId(schemaId);

		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件

		// 样式设置
		HSSFCellStyle columnHeadStyle = (HSSFCellStyle) workbook.createCellStyle();
		columnHeadStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景
		columnHeadStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 背景色
		columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		columnHeadStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		columnHeadStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		columnHeadStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		// 全局样式设置
		HSSFCellStyle allStyle = (HSSFCellStyle) workbook.createCellStyle();
		columnHeadStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 背景
		allStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());// 填充白色
		allStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		allStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		allStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		allStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		HSSFSheet sheet = workbook.createSheet("项目详情");// 创建一个Excel的Sheet

		ProjectDetailDto projectDetailDto = this.projectService.queryDetail(projectId, user);// 创建对象

		HSSFRow row0 = sheet.createRow(0);// 创建第一行
		HSSFCell cel0_1 = row0.createCell(0);// 创建第一行第一列
		cel0_1.setCellValue(new HSSFRichTextString("项目名称"));
		cel0_1.setCellStyle(columnHeadStyle);
		HSSFCell cel0_2 = row0.createCell(1);// 创建第一行第二列
		cel0_2.setCellValue(new HSSFRichTextString(projectDetailDto.getName()));
		cel0_2.setCellStyle(allStyle);
		HSSFCell cel0_3 = row0.createCell(2);
		cel0_3.setCellStyle(allStyle);
		HSSFCell cel0_4 = row0.createCell(3);
		cel0_4.setCellStyle(allStyle);
		HSSFCell cel0_5 = row0.createCell(4);
		cel0_5.setCellStyle(allStyle);

		HSSFRow row1 = sheet.createRow(1); // 创建第二行
		HSSFCell cell1_1 = row1.createCell(0); // 第二行第一列
		cell1_1.setCellValue(new HSSFRichTextString("客户名称"));
		cell1_1.setCellStyle(columnHeadStyle);
		HSSFCell cell1_2 = row1.createCell(1); // 第二行 第二列
		cell1_2.setCellValue(new HSSFRichTextString(projectDetailDto.getCustomer().getName()));
		cell1_2.setCellStyle(allStyle);
		HSSFCell cell1_3 = row1.createCell(2);
		cell1_3.setCellStyle(allStyle);
		HSSFCell cell1_4 = row1.createCell(3);
		cell1_4.setCellStyle(allStyle);
		HSSFCell cell1_5 = row1.createCell(4);
		cell1_5.setCellStyle(allStyle);

		HSSFRow row2 = sheet.createRow(2); // 创建第三行
		HSSFCell cell2_1 = row2.createCell(0); // 第三行第一列
		cell2_1.setCellValue(new HSSFRichTextString("项目状态"));
		cell2_1.setCellStyle(columnHeadStyle);
		HSSFCell cell2_2 = row2.createCell(1); // 第三行 第二列
		cell2_2.setCellValue(new HSSFRichTextString(projectDetailDto.getStateName()));
		cell2_2.setCellStyle(allStyle);
		HSSFCell cell2_3 = row2.createCell(2);
		cell2_3.setCellStyle(allStyle);
		HSSFCell cell2_4 = row2.createCell(3);
		cell2_4.setCellStyle(allStyle);
		HSSFCell cell2_5 = row2.createCell(4);
		cell2_5.setCellStyle(allStyle);

		HSSFRow row3 = sheet.createRow(3); // 创建第四行
		HSSFCell cell3_1 = row3.createCell(0); // 第四行第一列
		cell3_1.setCellValue(new HSSFRichTextString("项目总金额"));
		cell3_1.setCellStyle(columnHeadStyle);
		HSSFCell cell13_2 = row3.createCell(1); // 第四行 第二列
		cell13_2.setCellValue(new HSSFRichTextString(projectDetailDto.getAmount()));
		cell13_2.setCellStyle(allStyle);
		HSSFCell cell3_3 = row3.createCell(2);
		cell3_3.setCellStyle(allStyle);
		HSSFCell cell3_4 = row3.createCell(3);
		cell3_4.setCellStyle(allStyle);
		HSSFCell cell3_5 = row3.createCell(4);
		cell3_5.setCellStyle(allStyle);

		HSSFRow row4 = sheet.createRow(4); // 创建第五行
		HSSFCell cell4_1 = row4.createCell(0); // 第五行第一列
		cell4_1.setCellValue(new HSSFRichTextString("启动时间"));
		cell4_1.setCellStyle(columnHeadStyle);
		HSSFCell cell14_2 = row4.createCell(1); // 第五行 第二列
		cell14_2.setCellValue(new HSSFRichTextString(projectDetailDto.getStartTimeStr()));
		cell14_2.setCellStyle(allStyle);
		HSSFCell cell14_3 = row4.createCell(2);
		cell14_3.setCellStyle(allStyle);
		HSSFCell cell14_4 = row4.createCell(3);
		cell14_4.setCellStyle(allStyle);
		HSSFCell cell14_5 = row4.createCell(4);
		cell14_5.setCellStyle(allStyle);

		HSSFRow row5 = sheet.createRow(5); // 创建第六行
		HSSFCell cell5_1 = row5.createCell(0); // 第六行第一列
		cell5_1.setCellValue(new HSSFRichTextString("项目人员"));
		cell5_1.setCellStyle(columnHeadStyle);
		HSSFCell cell5_2 = row5.createCell(1);// 第六行 第二列
		if (projectDetailDto.getContacts().size() > 0) {
			cell5_2.setCellValue(new HSSFRichTextString(projectDetailDto.getContacts().get(0).getName()));
		}
		cell5_2.setCellStyle(allStyle);
		HSSFCell cell5_3 = row5.createCell(2);
		cell5_3.setCellStyle(allStyle);
		HSSFCell cell5_4 = row5.createCell(3);
		cell5_4.setCellStyle(allStyle);
		HSSFCell cell5_5 = row5.createCell(4);
		cell5_5.setCellStyle(allStyle);

		HSSFRow row6 = sheet.createRow(6); // 创建第七行
		HSSFCell cell6_1 = row6.createCell(0); // 第七行第一列
		cell6_1.setCellValue(new HSSFRichTextString("归属部门"));
		cell6_1.setCellStyle(columnHeadStyle);
		HSSFCell cell6_2 = row6.createCell(1);// 第七行 第二列
		if (projectDetailDto.getDeptOwner().size() != 0) {
			cell6_2.setCellValue(new HSSFRichTextString(projectDetailDto.getDeptOwner().get(0).getName()));
		}
		cell6_2.setCellStyle(allStyle);
		HSSFCell cell6_3 = row6.createCell(2);
		cell6_3.setCellStyle(allStyle);
		HSSFCell cell6_4 = row6.createCell(3);
		cell6_4.setCellStyle(allStyle);
		HSSFCell cell6_5 = row6.createCell(4);
		cell6_5.setCellStyle(allStyle);

		int rowCount = 7;
		HSSFRow row7 = sheet.createRow(rowCount); // 创建第八行
		HSSFCell row7_1 = row7.createCell(0);
		row7_1.setCellValue(new HSSFRichTextString("项目阶段"));
		row7_1.setCellStyle(columnHeadStyle);
		List<ProjectStepsDto> steps = projectDetailDto.getSteps();
		for (int i = 0; i < steps.size(); i++) {
			if (i == 0) {
				HSSFCell cell17_2 = row7.createCell(1);
				cell17_2.setCellValue(new HSSFRichTextString(steps.get(i).getMemo()));
				cell17_2.setCellStyle(allStyle);
				HSSFCell cell17_3 = row7.createCell(2);
				cell17_3.setCellValue(new HSSFRichTextString(steps.get(i).getTimeStr()));
				cell17_3.setCellStyle(allStyle);
				HSSFCell cell17_4 = row7.createCell(3);
				cell17_4.setCellStyle(allStyle);
				HSSFCell cell17_5 = row7.createCell(4);
				cell17_5.setCellStyle(allStyle);
				rowCount++;
			} else {
				HSSFRow forRow = sheet.createRow(rowCount);
				HSSFCell forRow_1 = forRow.createCell(0);
				forRow_1.setCellStyle(columnHeadStyle);
				HSSFCell forRow_2 = forRow.createCell(1);
				forRow_2.setCellValue(new HSSFRichTextString(steps.get(i).getMemo()));
				forRow_2.setCellStyle(allStyle);
				HSSFCell forRow_3 = forRow.createCell(2);
				forRow_3.setCellValue(new HSSFRichTextString(steps.get(i).getTimeStr()));
				forRow_3.setCellStyle(allStyle);

				HSSFCell forRow_4 = forRow.createCell(3);
				forRow_4.setCellStyle(allStyle);
				HSSFCell forRow_5 = forRow.createCell(4);
				forRow_5.setCellStyle(allStyle);
				rowCount++;
			}
		}
		if (steps.size() == 0) { // 如果steps没有数据 rowCount+1 表示显示标题 跳到下一行
			HSSFCell cell17_2 = row7.createCell(1);
			cell17_2.setCellStyle(allStyle);
			HSSFCell cell17_3 = row7.createCell(2);
			cell17_3.setCellStyle(allStyle);
			HSSFCell cell17_4 = row7.createCell(3);
			cell17_4.setCellStyle(allStyle);
			HSSFCell cell17_5 = row7.createCell(4);
			cell17_5.setCellStyle(allStyle);
			rowCount++;
		}

		HSSFRow rowPayment = sheet.createRow(rowCount); // 回款阶段
		HSSFCell rowPayment_1 = rowPayment.createCell(0);
		rowPayment_1.setCellValue(new HSSFRichTextString("回款阶段"));
		rowPayment_1.setCellStyle(columnHeadStyle);
		List<ProjectPaymentDto> Payments = projectDetailDto.getPayment();
		for (int j = 0; j < Payments.size(); j++) {
			if (j == 0) {
				HSSFCell cell1Payment_2 = rowPayment.createCell(1);
				cell1Payment_2.setCellValue(new HSSFRichTextString(Payments.get(j).getRate()));
				cell1Payment_2.setCellStyle(allStyle);
				HSSFCell cell1Payment_3 = rowPayment.createCell(2);
				cell1Payment_3.setCellValue(new HSSFRichTextString(Payments.get(j).getStateName()));
				cell1Payment_3.setCellStyle(allStyle);
				HSSFCell cell1Payment_4 = rowPayment.createCell(3);
				cell1Payment_4.setCellValue(new HSSFRichTextString(Payments.get(j).getTimeStr()));
				cell1Payment_4.setCellStyle(allStyle);

				HSSFCell cell1Payment_5 = rowPayment.createCell(4);
				cell1Payment_5.setCellStyle(allStyle);
				rowCount++;
			} else {
				HSSFRow forRow = sheet.createRow(rowCount);
				HSSFCell forCell_1 = forRow.createCell(0);
				forCell_1.setCellStyle(columnHeadStyle);
				HSSFCell forCell_2 = forRow.createCell(1);
				forCell_2.setCellValue(new HSSFRichTextString(Payments.get(j).getRate()));
				forCell_2.setCellStyle(allStyle);
				HSSFCell forCell_3 = forRow.createCell(2);
				forCell_3.setCellValue(new HSSFRichTextString(Payments.get(j).getStateName()));
				forCell_3.setCellStyle(allStyle);
				HSSFCell forCell_4 = forRow.createCell(3);
				forCell_4.setCellValue(new HSSFRichTextString(Payments.get(j).getTimeStr()));
				forCell_4.setCellStyle(allStyle);
				HSSFCell forCell_5 = forRow.createCell(4);
				forCell_5.setCellStyle(allStyle);
				rowCount++;
			}
		}
		if (Payments.size() == 0) { // 如果payments没有数据 rowCount+1 表示显示标题 跳到下一行
			HSSFCell cell1Payment_2 = rowPayment.createCell(1);
			cell1Payment_2.setCellStyle(allStyle);
			HSSFCell cell1Payment_3 = rowPayment.createCell(2);
			cell1Payment_3.setCellStyle(allStyle);
			HSSFCell cell1Payment_4 = rowPayment.createCell(3);
			cell1Payment_4.setCellStyle(allStyle);
			HSSFCell cell1Payment_5 = rowPayment.createCell(4);
			cell1Payment_5.setCellStyle(allStyle);
			rowCount++;
		}

		HSSFRow rowContacts = sheet.createRow(rowCount); // 联系人
		HSSFCell cellContacts_1 = rowContacts.createCell(0);
		cellContacts_1.setCellValue(new HSSFRichTextString("联系人"));
		cellContacts_1.setCellStyle(columnHeadStyle);
		List<ContactsListDto> Contacts = projectDetailDto.getContacts();
		for (int k = 0; k < Contacts.size(); k++) {
			List<ContactsInfoDto> contactsInfoList = Contacts.get(k).getContactsInfoList();
			String val = "";
			if (k == 0) {
				HSSFCell cellContacts_2 = rowContacts.createCell(1);
				cellContacts_2.setCellValue(new HSSFRichTextString(Contacts.get(k).getName()));
				cellContacts_2.setCellStyle(allStyle);
				HSSFCell cellContacts_3 = rowContacts.createCell(2);
				cellContacts_3.setCellValue(new HSSFRichTextString(Contacts.get(k).getDept()));
				cellContacts_3.setCellStyle(allStyle);
				HSSFCell cellContacts_4 = rowContacts.createCell(3);
				cellContacts_4.setCellValue(new HSSFRichTextString(Contacts.get(k).getRole()));
				cellContacts_4.setCellStyle(allStyle);
				for (int c = 0; c < contactsInfoList.size(); c++) {
					val = val + "  " + contactsInfoList.get(c).getVal() + "  ";
					val = val.substring(0, val.length() - 1);
					HSSFCell cellContacts_5 = rowContacts.createCell(4);
					cellContacts_5.setCellValue(new HSSFRichTextString(val));
					cellContacts_5.setCellStyle(allStyle);
				}
				rowCount++;
			} else {
				HSSFRow forRow = sheet.createRow(rowCount);
				HSSFCell forCell_1 = forRow.createCell(0);
				forCell_1.setCellStyle(columnHeadStyle);
				HSSFCell forCell_2 = forRow.createCell(1);
				forCell_2.setCellValue(new HSSFRichTextString(Contacts.get(k).getName()));
				forCell_2.setCellStyle(allStyle);
				HSSFCell forCell_3 = forRow.createCell(2);
				forCell_3.setCellValue(new HSSFRichTextString(Contacts.get(k).getDept()));
				forCell_3.setCellStyle(allStyle);
				HSSFCell forCell_4 = forRow.createCell(3);
				forCell_4.setCellValue(new HSSFRichTextString(Contacts.get(k).getRole()));
				forCell_4.setCellStyle(allStyle);
				for (int c = 0; c < contactsInfoList.size(); c++) {
					val = val + " " + contactsInfoList.get(c).getVal() + " ";
					val = val.substring(0, val.length() - 1);
					HSSFCell cellContacts_5 = rowContacts.createCell(4);
					cellContacts_5.setCellValue(new HSSFRichTextString(val));
					cellContacts_5.setCellStyle(allStyle);
				}
				rowCount++;
			}
		}
		if (Contacts.size() == 0) { // 如果contacts没有数据 rowCount+1 表示显示标题 跳到下一行
			HSSFCell cellContacts_2 = rowContacts.createCell(1);
			cellContacts_2.setCellStyle(allStyle);
			HSSFCell cellContacts_3 = rowContacts.createCell(2);
			cellContacts_3.setCellStyle(allStyle);
			HSSFCell cellContacts_4 = rowContacts.createCell(3);
			cellContacts_4.setCellStyle(allStyle);
			HSSFCell cellContacts_5 = rowContacts.createCell(4);
			cellContacts_5.setCellStyle(allStyle);
			rowCount++;
		}

		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 5);

		String filename = "项目详情.xls";// 设置下载时客户端Excel的名称
		filename = new String((filename).getBytes("GBK"), "ISO8859_1");
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
		return workbook;
	}

}
