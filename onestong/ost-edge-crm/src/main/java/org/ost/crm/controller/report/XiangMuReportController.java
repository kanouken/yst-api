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
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "projectType", required = false) String projectType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "projectState", required = false) String projectState,
			@RequestParam(value = "projectStep", required = false) String projectStep, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> params = this.getRequestParam(request);
		this.responseWriteFile(response, sXiangMuReportService.export(userId, params, 1, 100000), "项目报表.xlsx");
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
	public void projectExports(
			@RequestParam(value = "id") Integer projectId,
			@RequestParam(value = "schemaId") String schemaId, 
			HttpServletRequest request, 
			HttpServletResponse response)
					throws Exception {
		this.sXiangMuReportService.projectExports(
				1, 
				100000, 
				projectId, 
				schemaId, 
				response);
	}

}
