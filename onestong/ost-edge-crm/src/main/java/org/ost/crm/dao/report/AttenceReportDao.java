package org.ost.crm.dao.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.report.dto.ApprovalReportDto;
import org.springframework.stereotype.Repository;

@Repository
public interface AttenceReportDao {

	List<Map<String, Object>> selectByList(@Param("deptId") Integer deptId,
			@Param("start") Date start,@Param("end") Date end,RowBounds rowBounds);
	
	List<ApprovalReportDto> selectApprovalByList(@Param("deptId") Integer deptId,
			@Param("start") Date start,@Param("end") Date end,RowBounds rowBounds);
	
	List<ApprovalReportDto> selectByParam(@Param("deptId") Integer deptId,
			@Param("start") Date start,@Param("end") Date end,RowBounds rowBounds);
}
