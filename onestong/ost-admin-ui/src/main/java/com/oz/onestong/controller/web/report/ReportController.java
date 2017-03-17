package com.oz.onestong.controller.web.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.oz.onestong.anotations.AuthTarget;
import com.oz.onestong.controller.base.Action;
import com.oz.onestong.model.department.Department;
import com.oz.onestong.model.report.AttenceReport;
import com.oz.onestong.model.user.User;
import com.oz.onestong.services.api.event.AttenceEventService;
import com.oz.onestong.services.authority.AuthorityService;
import com.oz.onestong.services.web.department.DepartmentService;
import com.oz.onestong.tools.Constants;

@Controller
@RequestMapping("/report")
public class ReportController extends Action {
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private AttenceEventService attenceEventService;

	/**
	 * 考勤数据模板变更
	 * 
	 * @param deptId
	 * @param startTime
	 * @param endTime
	 * @param reportType
	 * @param session
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("all")
	@AuthTarget({ "listChartAll", "listChartOwn" })
	@RequestMapping(value = "exportAttence", method = RequestMethod.POST)
	public void exportAttence(Integer deptId, String startTime, String endTime, Integer reportType, HttpSession session,
			HttpServletResponse response) throws FileNotFoundException, IOException {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date start = null;
		Date end = null;
		OutputStream out = null;
		try {

			start = df.parse(startTime);
			end = df.parse(endTime);
			List<Map<String, Object>> reports = null;
			String tempFile = this.getClass().getClassLoader().getResource("").getFile();

			File f = null;
			// XSSFWorkbook
			// HSSFWorkbook
			HSSFWorkbook hs = null;
			Sheet sheet = null;
			// sheet one
			// Row r = sheet.getRow(0);
			Row r = null;
			Cell c = null;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Map<String, Object> report = null;
			response.reset();
			Department d = this.departmentService.findOne(deptId);
			response.setContentType("application/msexcel;charset=UTF-8");

			if (reportType == 0) {
				// 考勤 报表
				f = new File(tempFile, "attenceReportTemplate.xls");
				hs = new HSSFWorkbook(new FileInputStream(f));
				sheet = hs.getSheetAt(0);
				List<AttenceReport> attenceReports = this.attenceEventService
						.selectAttenceReportFliterByDepartmentAndDate(deptId, start, end, sessionUser.getDomainId());
				hs = this.attenceEventService.exportCustomAttenceReport(attenceReports, null, start, end);
				String fileName = d.getName() + startTime + "~" + endTime + ".xls";
				response.addHeader("Content-Disposition",
						"attachment;filename=\"" + new String((fileName).getBytes("GBK"), "ISO8859_1") + "\"");
			} else if (reportType == 1) {
				// 导出简版
				hs = this.attenceEventService.selectAttenceReportBasicFliterByDepartmentAndDate(deptId, start, end,
						sessionUser.getDomainId());
				String fileName = d.getName() + startTime + "~" + endTime + ".xls";
				response.addHeader("Content-Disposition",
						"attachment;filename=\"" + new String((fileName).getBytes("GBK"), "ISO8859_1") + "\"");
			} else if (reportType == 2) {
				// 外访报表
				f = new File(tempFile, "visitReportTemplate.xls");
				hs = new HSSFWorkbook(new FileInputStream(f));
				sheet = hs.getSheetAt(0);

				reports = this.attenceEventService.selectVisitReportFliterByDepartmentAndDate(deptId, start, end,
						sessionUser.getDomainId());
				for (int i = 0; i < reports.size(); i++) {

					r = sheet.createRow(i + 1);
					report = reports.get(i);
					c = r.createCell(0);

					c.setCellValue(report.get("name") == null ? "" : report.get("name").toString());

					c = r.createCell(1);
					c.setCellValue(report.get("realname") == null ? "" : report.get("realname").toString());
					c = r.createCell(2);
					c.setCellValue(report.get("title") == null ? "" : report.get("title").toString());
					c = r.createCell(3);
					c.setCellValue(report.get("content") == null ? "" : report.get("content").toString());
					c = r.createCell(4);
					c.setCellValue(report.get("visited_time") == null ? "" : report.get("visited_time").toString());
					c = r.createCell(5);

					c.setCellValue(report.get("visted_address") == null ? "" : report.get("visted_address").toString());
					c = r.createCell(6);
					c.setCellValue(report.get("visitout_time") == null ? "" : report.get("visitout_time").toString());

					c = r.createCell(7);
					c.setCellValue(
							report.get("visitout_address") == null ? "" : report.get("visitout_address").toString());

				}

				String fileName = d.getName() + startTime + "~" + endTime + ".xls";
				response.addHeader("Content-Disposition",
						"attachment;filename=\"" + new String((fileName).getBytes("GBK"), "ISO8859_1") + "\"");

			}

			out = response.getOutputStream();

			hs.write(out);
			out.flush();

		} catch (ParseException e) {
			logger.error(e);
		} finally {
			if (null != out) {

				out.close();
			}

		}

	}

	@SuppressWarnings("all")
	@AuthTarget({ "listChartAll", "listChartOwn" })
	@RequestMapping(value = "init", method = RequestMethod.GET)
	public ModelAndView initExportAttence(HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");

		List<Department> depts = new ArrayList<Department>();

		//
		ModelAndView mv = new ModelAndView();
		if (this.authorityService.isPermExits(OST_OST_PERM.listChartAll,
				(List<Map<String, Object>>) sessionLocal.get(Constants.SESSION_PERM_TIP))) {

			Department d = new Department();

			d.setDomainId(sessionUser.getDomainId());

			mv.addObject("single", "1");
			depts = this.departmentService.findAllDepartments(d);

		} else {
			// department 部门管理员 导出报表
			depts.add(this.departmentService.findOne(sessionUser.getDeptId()));

		}

		mv.addObject("depts", depts);
		mv.setViewName("report/initReport");

		return mv;

	}

}
