package org.ost.crm.controller.report;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.XiangMuReportService;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.customer.Customer;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.project.dto.ProjectDetailDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDto;
import org.ost.entity.user.Users;
import org.ost.crm.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by lugia on 2017/3/14. 后台网站报表
 */
@RestController
@RequestMapping("report/cXiangMuReport")
public class XiangMuReportController extends Action {
	@Autowired
	private XiangMuReportService sXiangMuReportService;

	@Autowired
	private ProjectService projectService;

	/**
	 * 项目报表 获取列表数据
	 *
	 */
	@GetMapping(value = "list")
	public OperateResult<Map<String, Object>> list(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "projectType", required = false) String projectType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "projectState", required = false) String projectState,
			@RequestParam(value = "projectStep", required = false) String projectStep, HttpServletRequest request)
					throws InterruptedException, ExecutionException {
		Map<String, Object> params = this.getRequestParam(request);
		return new OperateResult<Map<String, Object>>(sXiangMuReportService.list(user, params, curPage, perPageSum));
	}

	/**
	 * 项目报表 获取统计数据
	 *
	 */
	@GetMapping(value = "count")
	public OperateResult<Object> count(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "projectType", required = false) String projectType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "projectState", required = false) String projectState,
			@RequestParam(value = "projectStep", required = false) String projectStep, HttpServletRequest request)
					throws InterruptedException, ExecutionException {
		Map<String, Object> params = this.getRequestParam(request);
		return new OperateResult<Object>(sXiangMuReportService.count(user, params, curPage, perPageSum));
	}

	/**
	 * 项目报表 获取图表数据
	 *
	 */
	@GetMapping(value = "chart")
	public OperateResult<Object> chart(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "projectType", required = false) String projectType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "projectState", required = false) String projectState,
			@RequestParam(value = "projectStep", required = false) String projectStep, HttpServletRequest request)
					throws InterruptedException, ExecutionException {
		Map<String, Object> params = this.getRequestParam(request);
		return new OperateResult<Object>(sXiangMuReportService.chart(user, params, curPage, perPageSum));
	}

	/**
	 * 项目报表 导出数据
	 *
	 */
	@GetMapping(value = "export")
	public void export(@RequestParam(value = "schemaID", required = false) String schemaID,
			@RequestParam(value="userId",required = false) Integer userId,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "projectType", required = false) String projectType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "projectState", required = false) String projectState,
			@RequestParam(value = "projectStep", required = false) String projectStep, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> params = this.getRequestParam(request);
		this.responseWriteFile(response, sXiangMuReportService.export(userId,params, 1, 100000), "项目报表.xlsx");
	}

	/**
	 * 导出项目详情
	 * 
	 * @param projectId
	 * @param schemaId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "项目详情", notes = "项目详情")
	@GetMapping(value = "projectDetail/export")
	public void projectExport(@RequestParam(value = "id") Integer projectId,
			@RequestParam(value = "schemaId") String schemaId, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
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

		// HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
		// sheet = workbook.createSheet("项目详情");// Sheet名字
		HSSFSheet sheet = workbook.createSheet("项目详情");

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
				HSSFCell cellContacts_5 = rowContacts.createCell(4);
				cellContacts_5.setCellValue(new HSSFRichTextString(""));
				cellContacts_5.setCellStyle(allStyle);
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
				HSSFCell forCell_5 = forRow.createCell(4);
				forCell_5.setCellValue(new HSSFRichTextString(""));
				forCell_5.setCellStyle(allStyle);
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
	}

}
