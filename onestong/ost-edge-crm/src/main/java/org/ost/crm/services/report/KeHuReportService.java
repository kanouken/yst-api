package org.ost.crm.services.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.services.department.DepartmentService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.org.department.Departments;
import org.ost.entity.report.dto.KeHuReportDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.ost.entity.user.UsersRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeHuReportService {

	@Autowired
	private CustomerServiceClient customerServiceClient;

	@Autowired
	private DepartmentService departmentService;

	/**
	 * FIXME YSTCRM-367 客户报表-获取列表数据
	 * 
	 * @param params
	 * @param page
	 * @param users
	 * @return
	 */
	public Map<String, Object> queryCustomersReport(Map<String, Object> params, Page page, Users users) {
		Boolean isDirector = false;
		if (users.getRole() != null) {
			if (users.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		// 获取keHuReportDto中的相关信息值
		KeHuReportDto keHuReportDto = new KeHuReportDto();
		if (isDirector) {
			keHuReportDto.setManagerOwnerName(MapUtils.getString(params, "managerOwnerName"));
		} else {
			keHuReportDto.setManagerOwnerName(users.getRealname());
		}
		keHuReportDto.setDealFrequency(MapUtils.getString(params, "dealFrequency"));
		keHuReportDto.setType(MapUtils.getString(params, "type"));
		keHuReportDto.setTurnover(MapUtils.getString(params, "turnover"));
		keHuReportDto.setIsPlc(MapUtils.getString(params, "isPlc"));
		keHuReportDto.setNature(MapUtils.getString(params, "nature"));
		keHuReportDto.setSource(MapUtils.getString(params, "source"));
		keHuReportDto.setBelongIndustry(MapUtils.getString(params, "belongIndustry"));
		keHuReportDto.setStartCreateTime(MapUtils.getString(params, "startDate"));
		keHuReportDto.setEndCreateTime(MapUtils.getString(params, "endDate"));

		// 通过customerServiceClient拿Customers的值
		OperateResult<PageEntity<KeHuReportDto>> result = this.customerServiceClient
				.queryCustomersReport(users.getSchemaId(), page.getCurPage(), page.getPerPageSum(), keHuReportDto);
		// 获取集合
		List<KeHuReportDto> keHuReportDtoList = new ArrayList<KeHuReportDto>();
		if (result.success()) {
			keHuReportDtoList = result.getData().getObjects();
		}
		return OperateResult.renderPage(page, keHuReportDtoList);
	}

	/**
	 * 客户报表-获取图表数据
	 * 
	 * @param params
	 * @param users
	 * @return
	 */
	@Transactional(readOnly = true)
	public Object reportChart(Map<String, Object> params, Users users) {
		Boolean isDirector = false;
		if (users.getRole() != null) {
			if (users.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		// 获取keHuReportDto中的相关信息值
		KeHuReportDto keHuReportDto = new KeHuReportDto();
		if (isDirector) {
			keHuReportDto.setManagerOwnerName(MapUtils.getString(params, "managerOwnerName"));
		} else {
			keHuReportDto.setManagerOwnerName(users.getRealname());
		}
		keHuReportDto.setDealFrequency(MapUtils.getString(params, "dealFrequency"));
		keHuReportDto.setType(MapUtils.getString(params, "type"));
		keHuReportDto.setTurnover(MapUtils.getString(params, "turnover"));
		keHuReportDto.setIsPlc(MapUtils.getString(params, "isPlc"));
		keHuReportDto.setNature(MapUtils.getString(params, "nature"));
		keHuReportDto.setSource(MapUtils.getString(params, "source"));
		keHuReportDto.setBelongIndustry(MapUtils.getString(params, "belongIndustry"));
		keHuReportDto.setStartCreateTime(MapUtils.getString(params, "startDate"));
		keHuReportDto.setEndCreateTime(MapUtils.getString(params, "endDate"));

		OperateResult<Object> result = this.customerServiceClient.reportChart(users.getSchemaId(), keHuReportDto);
		return result;
	}

	/**
	 * 客户报表-获取统计数据
	 * 
	 * @param params
	 * @param users
	 * @return
	 */
	@Transactional(readOnly = true)
	public Object reportCount(Map<String, Object> params, Users users) {
		Boolean isDirector = false;
		if (users.getRole() != null) {
			if (users.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		// 获取keHuReportDto中的相关信息值
		KeHuReportDto keHuReportDto = new KeHuReportDto();
		
		if (isDirector) {
			keHuReportDto.setManagerOwnerName(MapUtils.getString(params, "managerOwnerName"));
		} else {
			keHuReportDto.setManagerOwnerName(users.getRealname());
		}
		
		keHuReportDto.setDealFrequency(MapUtils.getString(params, "dealFrequency"));
		keHuReportDto.setType(MapUtils.getString(params, "type"));
		keHuReportDto.setTurnover(MapUtils.getString(params, "turnover"));
		keHuReportDto.setIsPlc(MapUtils.getString(params, "isPlc"));
		keHuReportDto.setNature(MapUtils.getString(params, "nature"));
		keHuReportDto.setSource(MapUtils.getString(params, "source"));
		keHuReportDto.setBelongIndustry(MapUtils.getString(params, "belongIndustry"));
		keHuReportDto.setStartCreateTime(MapUtils.getString(params, "startDate"));
		keHuReportDto.setEndCreateTime(MapUtils.getString(params, "endDate"));

		OperateResult<Object> result = this.customerServiceClient.queryReportCount(users.getSchemaId(), keHuReportDto);
		return result;
	}

}