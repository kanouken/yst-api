package org.ost.crm.services.report;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.dao.project.ProjectDao;
import org.ost.crm.dao.report.XiaoShouReportDao;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.project.dto.ProjectListDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by lugia on 2017/3/14.
 */
@Service
public class XiaoShouReportService extends BaseService {

    @Autowired
    private XiaoShouReportDao cXiaoShouReportDao;


    @Transactional(readOnly = true)
    public Map<String, Object> queryProjects(Users user,
                                             Map<String, Object> params,
                                             Integer curPage,
                                             Integer perPageSum
    ) throws InterruptedException, ExecutionException {
        List<XiaoShouReportDto> result = new ArrayList<XiaoShouReportDto>();
        Page page = new Page();
        page.setCurPage(curPage);
        page.setPerPageSum(perPageSum);
        params.put("page", page);

        Integer totalRecords = cXiaoShouReportDao.searchListCount(params);
        if (totalRecords > 0) {
            result = cXiaoShouReportDao.searchList(params);
        }
        page.setTotalRecords(totalRecords);
        return OperateResult.renderPage(page, result);
    }
}
