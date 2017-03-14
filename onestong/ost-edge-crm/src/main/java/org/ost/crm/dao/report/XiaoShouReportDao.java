package org.ost.crm.dao.report;

import org.ost.entity.project.Project;
import org.ost.entity.report.dto.XiaoShouReportDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lugia on 2017/3/14.
 */
public interface XiaoShouReportDao extends Mapper<Project> {
    Integer searchListCount(Map<String, Object> params);

    List<XiaoShouReportDto> searchList(Map<String, Object> params);
}
