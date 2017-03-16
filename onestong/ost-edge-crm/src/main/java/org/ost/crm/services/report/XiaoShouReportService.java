package org.ost.crm.services.report;

import com.alibaba.druid.util.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.excel.ExcelUtil;
import org.common.tools.exception.ApiException;
import org.common.tools.string.StringUtil;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.dao.project.ProjectDao;
import org.ost.crm.dao.report.XiaoShouReportDao;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.project.dto.ProjectListDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by lugia on 2017/3/14.
 */
@Service
public class XiaoShouReportService extends BaseService {
    @Autowired
    private CustomerServiceClient _CustomerServiceClient;

    @Autowired
    private XiaoShouReportDao _XiaoShouReportDao;

    /**
     * 销售（金额）报表 获取统计数据
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

        result = _XiaoShouReportDao.searchListTotalCount(params);
        return result;
    }

    /**
     * 销售（金额）报表 获取图表数据
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

        result = _XiaoShouReportDao.searchListChart(params);
        return result;
    }

    /**
     * 销售（金额）报表 获取图表数据
     *
     */
    @Transactional(readOnly = true)
    public ByteArrayOutputStream export(
            Map<String, Object> params,
            Integer curPage,
            Integer perPageSum
    ) throws Exception {
        ByteArrayOutputStream xlsOutput = null;
        // 检索数据
        List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();
        Page page = new Page();
        page.setCurPage(curPage);
        page.setPerPageSum(perPageSum);
        params.put("page", page);
        params.put("schemaID", params.get("schemaID"));
        result = searchData(params);

        // 整理数据
        for (XiaoShouReportDto d : result) {
            String name = "";
            for (ProjectContactsDto user : d.getManagerOwner()) {
                name += user.getName() + " ";
            }
            d.setManagerOwnerName(name);
        }

        //定义表头
        //表头每列有2个字段
        //1 表头中文名
        //2 表头对应字段名
        //注意：表头对应字段名字首字母要大写，因为反射方法需要拼接get来获取值
        List<String[]> head = new ArrayList<>();
        head.add("客户名称,CustomerName".split(","));
        head.add("项目名称,ProjectName".split(","));
        head.add("项目预计金额(万),ProjectAmount".split(","));
        head.add("项目类型,ProjectType".split(","));
        head.add("回款比例(%),ProjectPaymentRate".split(","));
        head.add("客户经理,ManagerOwnerName".split(","));
        head.add("项目时间,CreateTimeStr".split(","));

        //获取excel文件二进制流
        ExcelUtil excelUtil = new ExcelUtil<XiaoShouReportDto>();
        xlsOutput = excelUtil.exportToExcel("销售（金额）报表", head, result);
        return xlsOutput;
    }

    /**
     * 销售（金额）报表 获取列表数据
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

        Integer totalRecords = _XiaoShouReportDao.searchListCount(params);
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
        result = _XiaoShouReportDao.searchList(params);

        //查询项目客户信息 并发查询
        int[] customerIds = result.stream().filter(p -> !StringUtil.isNullOrEmpty(p.getCustomerID())).mapToInt(p -> p.getCustomerID()).toArray();
        CompletableFuture<OperateResult<List<CustomerListDto>>> customerFuture = CompletableFuture.supplyAsync(() ->
                this._CustomerServiceClient.queryByIds(params.get("schemaID").toString(), customerIds)
        );

        //查询项目经理
        params.put("projects", result);
        List<ProjectContactsDto> projectUsers = _XiaoShouReportDao.selectProjectUser(params);

        //填充客户信息
        OperateResult<List<CustomerListDto>> customerResult = customerFuture.get();
        if (!customerResult.success()) {
            throw new ApiException("客户信息获取失败", customerResult.getInnerException());
        }

        //填充数据
        for (XiaoShouReportDto project:result) {
            List<ProjectContactsDto> projectUser =  projectUsers.stream()
                    .filter(p -> p.getProjectID().equals(project.getId()))
                    .collect(Collectors.toList());

            if (projectUser != null){
                project.setManagerOwner(projectUser);
            }


            if (customerResult.getData() != null) {
                Optional<CustomerListDto> customer =  customerResult.getData().stream().filter(p -> p.getId().equals(project.getCustomerID())).findFirst();
                if (customer.isPresent() && !StringUtil.isNullOrEmpty(customer.get())){
                    project.setCustomerName(customer.get().getName());
                }
            }
        }
        return result;
    }
}
