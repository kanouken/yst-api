package org.ost.crm.dao.report;

import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.report.dto.XiaoShouReportDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lugia on 2017/3/15.
 */
public interface XiangMuReportDao {
    Integer searchListCount(@Param("params") Map<String, Object> params);

    List<XiaoShouReportDto> searchList(@Param("params") Map<String, Object> params);

    List<ProjectContactsDto> selectProjectUser(@Param("params") Map<String, Object> params);

    XiaoShouReportDto searchListTotalCount(@Param("params") Map<String, Object> params);

    List<XiaoShouReportDto> searchListChart(@Param("params") Map<String, Object> params);
}
