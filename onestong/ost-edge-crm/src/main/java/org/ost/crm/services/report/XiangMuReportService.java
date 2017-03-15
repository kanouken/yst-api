package org.ost.crm.services.report;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.common.tools.string.StringUtil;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.dao.report.XiangMuReportDao;
import org.ost.crm.dao.report.XiaoShouReportDao;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by lugia on 2017/3/15.
 */
@Service
public class XiangMuReportService extends BaseService {
    @Autowired
    private CustomerServiceClient _CustomerServiceClient;

    @Autowired
    private XiangMuReportDao _XiangMuReportDao;

    /**
     * 项目报表 获取统计数据
     *
     */
    @Transactional(readOnly = true)
    public Object count(Users user,
                        Map<String, Object> params,
                        Integer curPage,
                        Integer perPageSum
    ) throws InterruptedException, ExecutionException {
        XiaoShouReportDto result = new XiaoShouReportDto();
        params.put("schemaID", user.getSchemaId());

        result = _XiangMuReportDao.searchListTotalCount(params);
        return result;
    }

    /**
     * 项目报表 获取图表数据
     *
     */
    @Transactional(readOnly = true)
    public Object chart(Users user,
                        Map<String, Object> params,
                        Integer curPage,
                        Integer perPageSum
    ) throws InterruptedException, ExecutionException {
        List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();

        params.put("schemaID", user.getSchemaId());

        result = _XiangMuReportDao.searchListChart(params);
        return result;
    }

    /**
     * 项目报表 获取列表数据
     *
     */
    @Transactional(readOnly = true)
    public Map<String, Object> list(Users user,
                                    Map<String, Object> params,
                                    Integer curPage,
                                    Integer perPageSum
    ) throws InterruptedException, ExecutionException {
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
        // 查询项目销售数据
        result = _XiangMuReportDao.searchList(params);

        //查询项目客户信息 并发查询
        int[] customerIds = result.stream().filter(p -> !StringUtil.isNullOrEmpty(p.getCustomerID())).mapToInt(p -> p.getCustomerID()).toArray();
        CompletableFuture<OperateResult<List<CustomerListDto>>> customerFuture = CompletableFuture.supplyAsync(() ->
                this._CustomerServiceClient.queryByIds(user.getSchemaId(), customerIds)
        );

        //填充客户信息
        OperateResult<List<CustomerListDto>> customerResult = customerFuture.get();
        if (!customerResult.success()) {
            throw new ApiException("客户信息获取失败", customerResult.getInnerException());
        }

        //填充数据
        for (XiaoShouReportDto project:result) {
            if (customerResult.getData() != null) {
                Optional<CustomerListDto> customer =  customerResult.getData().stream().filter(p -> p.getId().equals(project.getCustomerID())).findFirst();
                if (customer.isPresent() && !StringUtil.isNullOrEmpty(customer.get())){
                    project.setCustomerName(customer.get().getName());
                }
            }
        }

        return OperateResult.renderPage(page, result);
    }
}
