package org.ost.crm.services.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections.MapUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.base.PageEntity;
import org.ost.entity.report.dto.KeHuReportDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeHuReportService {

	@Autowired
	private CustomerServiceClient customerServiceClient;

	/**
	 * 客户报表-获取列表数据
	 * 
	 * @param managerOwnerName
	 * @param params
	 * @param page
	 * @return
	 */
	public Map<String, Object> reportCustomer(String managerOwnerName, Map<String, Object> params, Page page) {
		KeHuReportDto kh = new KeHuReportDto();
		kh.setDealFrequency(MapUtils.getString(params, "dealFrequency"));
		kh.setType(MapUtils.getString(params, "type"));
		kh.setTurnover(MapUtils.getString(params, "turnover"));
		kh.setIsPlc(MapUtils.getString(params, "isPlc"));
		kh.setNature(MapUtils.getString(params, "nature"));
		kh.setSource(MapUtils.getString(params, "source"));
		kh.setBelongIndustry(MapUtils.getString(params, "belongIndustry"));
		OperateResult<PageEntity<KeHuReportDto>> result = this.customerServiceClient
				.queryCustomerByParam(page.getCurPage(), page.getPerPageSum(), managerOwnerName, kh);
		List<KeHuReportDto> ke = new ArrayList<KeHuReportDto>();
		if (result.success()) {
			ke = result.getData().getObjects();
		}
		return OperateResult.renderPage(page, ke);
	}

	@Transactional(readOnly = true)
	public Object reportChart(String managerOwnerName,Map<String, Object> params){
		KeHuReportDto kh = new KeHuReportDto();
		kh.setDealFrequency(MapUtils.getString(params, "dealFrequency"));
		kh.setType(MapUtils.getString(params, "type"));
		kh.setTurnover(MapUtils.getString(params, "turnover"));
		kh.setIsPlc(MapUtils.getString(params, "isPlc"));
		kh.setNature(MapUtils.getString(params, "nature"));
		kh.setSource(MapUtils.getString(params, "source"));
		kh.setBelongIndustry(MapUtils.getString(params, "belongIndustry"));
		OperateResult<Object> result=this.customerServiceClient.queryReportCount(managerOwnerName);
		return result;
	}

}