package org.ost.edge.onestong.dao.event;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.dao.event.base.EventBaseMapper;
import org.ost.edge.onestong.model.event.VisitEvent;
import org.ost.edge.onestong.model.event.VisitEventExample;

public interface VisitEventMapper extends EventBaseMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int countByExample(VisitEventExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int deleteByExample(VisitEventExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int deleteByPrimaryKey(String veId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int insert(VisitEvent record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int insertSelective(VisitEvent record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	List<VisitEvent> selectByExample(VisitEventExample example);

	List<VisitEvent> selectByExample(VisitEventExample example, RowBounds rb);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	VisitEvent selectByPrimaryKey(String veId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int updateByExampleSelective(@Param("record") VisitEvent record,
			@Param("example") VisitEventExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int updateByExample(@Param("record") VisitEvent record,
			@Param("example") VisitEventExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int updateByPrimaryKeySelective(VisitEvent record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table visit_events
	 * 
	 * @mbggenerated Wed Jan 14 10:38:36 CST 2015
	 */
	int updateByPrimaryKey(VisitEvent record);

	VisitEvent selectVisitEventAndCommentsByEventId(
			@Param("eventId") String eventId);

	
	
	
	List<Map<String, Object>> selectShortCutReportByDepartmentAndMonth(
			@Param("departmentId")  Integer departemtnId, @Param("start") Date start, @Param("end")Date end);

	List<Map<String, Object>> selectShortCutReportByUserAndMonth(
			@Param("userId") Integer userId, @Param("start") Date start, @Param("end") Date end);
	
	
	/**
	 * 考虑用时间进行分组
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String, Object>> selectMapTrackingDetailReportByUserAndMonth(
			@Param("userId") Integer userId, @Param("start") Date start, @Param("end") Date end);

	

}