package com.oz.onestong.dao.event;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.oz.onestong.model.event.Task;
import com.oz.onestong.model.event.TaskExample;

public interface TaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int countByExample(TaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int deleteByExample(TaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int deleteByPrimaryKey(String taskid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int insert(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int insertSelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    List<Task> selectByExample(TaskExample example);
    
    List<Task> selectByExample(TaskExample example,RowBounds rb);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    Task selectByPrimaryKey(String taskid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int updateByExampleSelective(@Param("record") Task record, @Param("example") TaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int updateByExample(@Param("record") Task record, @Param("example") TaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int updateByPrimaryKeySelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    int updateByPrimaryKey(Task record);
    
    Task selectTaskEventAndCommentsByTaskId(
			@Param("eventId") String eventId);

    
    /**
     * create by  xnq 分页获取我执行的任务
     * @param userId
     * @param rb
     * @return
     */
	List<Task> selectMyExecutionTaskPaged(@Param("userId") Integer userId, RowBounds rb);

	/**
	 * 获取我的任务 与 我参与的任务总数
	 * @param userId
	 * @return
	 */
	List<Integer> selectTaskIPublishedOrIExecuted(@Param("userId")Integer userId);

    
    
}