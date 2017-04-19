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

	//考勤
	List<Map<String, Object>> selectAttenceByList(@Param("departmentName") String departmentName,
			@Param("start") Date start,@Param("end") Date end,RowBounds rowBounds);
	
	//出差
	List<ApprovalReportDto> selectbusinessTripByList(@Param("deptId") Integer deptId,
			@Param("start") Date start,@Param("end") Date end,RowBounds rowBounds);
	
	//请假
	List<ApprovalReportDto> selectLeaveByList(@Param("deptId") Integer deptId,
			@Param("start") Date start,@Param("end") Date end,RowBounds rowBounds);
}
