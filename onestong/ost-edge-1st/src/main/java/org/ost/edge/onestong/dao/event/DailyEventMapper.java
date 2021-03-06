package org.ost.edge.onestong.dao.event;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.model.event.DailyEvent;
import org.ost.edge.onestong.model.event.DailyEventExample;

public interface DailyEventMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int countByExample(DailyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int deleteByExample(DailyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int deleteByPrimaryKey(String deId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int insert(DailyEvent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int insertSelective(DailyEvent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    List<DailyEvent> selectByExample(DailyEventExample example);
    List<DailyEvent> selectByExample(DailyEventExample example,RowBounds rb);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    DailyEvent selectByPrimaryKey(String deId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int updateByExampleSelective(@Param("record") DailyEvent record, @Param("example") DailyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int updateByExample(@Param("record") DailyEvent record, @Param("example") DailyEventExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int updateByPrimaryKeySelective(DailyEvent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_events
     *
     * @mbggenerated Tue Jan 20 15:42:21 CST 2015
     */
    int updateByPrimaryKey(DailyEvent record);

	DailyEvent selectDailyEventAndCommentsByEventId( @Param("eventId") String eventId);
}