package org.ost.crm.dao.report;

import org.apache.ibatis.annotations.Param;
import org.ost.entity.project.Project;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.report.dto.XiaoShouReportDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lugia on 2017/3/14.
 */
public interface XiaoShouReportDao {
    Integer searchListCount(@Param("params") Map<String, Object> params);

    List<XiaoShouReportDto> searchList(@Param("params") Map<String, Object> params);

    List<ProjectContactsDto> selectProjectUser(@Param("params") Map<String, Object> params);

    XiaoShouReportDto searchListTotalCount(@Param("params") Map<String, Object> params);

    List<XiaoShouReportDto> searchListChart(@Param("params") Map<String, Object> params);
}
