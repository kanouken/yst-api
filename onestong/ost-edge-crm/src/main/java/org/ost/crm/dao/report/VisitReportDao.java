package org.ost.crm.dao.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitReportDao {

	List<Map<String, Object>> selectBy(@Param("hasBus") String hasBus,
			@Param("managerOwnerName") String managerOwnerName, @Param("contactType") String contactType,
			@Param("start") Date start, @Param("end") Date end, RowBounds rBounds);

	Map<String, Object> selectSummary(@Param("hasBus") String hasBus,
			@Param("managerOwnerName") String managerOwnerName, @Param("contactType") String contactType,
			@Param("start") Date start, @Param("end") Date end);

	List<Map<String, Object>> selectByMonth(@Param("hasBus") String hasBus,
			@Param("managerOwnerName") String managerOwnerName, @Param("contactType") String contactType, @Param("start") Date start, @Param("end") Date end);

}
