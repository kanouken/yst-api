package org.ost.crm.controller.report;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.XiangMuReportService;
import org.ost.crm.services.report.XiaoShouReportService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by lugia on 2017/3/14.
 * 后台网站报表
 */
@RestController
@RequestMapping("report/cXiangMuReport")
public class XiangMuReportController extends Action {
    @Autowired
    private XiangMuReportService sXiangMuReportService;

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
            @RequestParam(value = "projectStep", required = false) String projectStep,
            HttpServletRequest request
    )
            throws InterruptedException, ExecutionException {
        Map<String, Object> params = this.getRequestParam(request);
        return new OperateResult<Map<String, Object>>(
                sXiangMuReportService.list(user, params, curPage, perPageSum));
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
            @RequestParam(value = "projectStep", required = false) String projectStep,
            HttpServletRequest request
    )
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
            @RequestParam(value = "projectStep", required = false) String projectStep,
            HttpServletRequest request
    )
            throws InterruptedException, ExecutionException {
        Map<String, Object> params = this.getRequestParam(request);
        return new OperateResult<Object>(sXiangMuReportService.chart(user, params, curPage, perPageSum));
    }
}
