package org.ost.edge.onestong.dao.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ost.edge.onestong.model.report.AttenceReport;

public interface AttenceReportMapper {
	
	List<AttenceReport> selectAttenceReportFliterByDepartmentAndDate(@Param("deptId")  Integer departemtnId, @Param("start") Date start, @Param("end")Date end, @Param("domainId") Integer domainId    );
	List<Map<String, Object>> selectVisitReportFliterByDepartmentAndDate(@Param("deptId")  Integer departemtnId, @Param("start") Date start, @Param("end")Date end, @Param("domainId") Integer domainId    );
	
	
}
