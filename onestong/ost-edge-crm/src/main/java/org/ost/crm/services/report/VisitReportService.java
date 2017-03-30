package org.ost.crm.services.report;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Retention;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.record.VCenterRecord;
import org.common.tools.OperateResult;
import org.common.tools.date.DateUtil;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.dao.project.ProjectPaymentExample;
import org.ost.crm.dao.report.VisitReportDao;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.contacts.visit.VisitContacts;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.visitor.functions.If;

@Service
public class VisitReportService {

	@Autowired
	private VisitReportDao reportDao;

	@Autowired
	ContactsServiceClient contactsServiceClient;

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
		// TODO 非部门主管 managerOwnerName 是他自己
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
	 */
	@Transactional(readOnly = true)
	public ByteArrayOutputStream export(String schemaID, String hasBus, String managerOwnerName, String contactType,
			String startDate, String endDate, Integer curPage, Integer perPageSum) throws Exception {
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
		return this.reportDao.selectByMonth(hasBus, managerOwnerName, contactType, null, null);

	}

}
