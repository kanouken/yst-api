package com.oz.onestong.dao.event;

import com.oz.onestong.model.event.ApproveFlow;
import com.oz.onestong.model.event.ApproveFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApproveFlowMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    int countByExample(ApproveFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    int deleteByExample(ApproveFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    int insert(ApproveFlow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    int insertSelective(ApproveFlow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    List<ApproveFlow> selectByExample(ApproveFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    int updateByExampleSelective(@Param("record") ApproveFlow record, @Param("example") ApproveFlowExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table approve_flows
     *
     * @mbggenerated Tue Jan 20 13:36:44 CST 2015
     */
    int updateByExample(@Param("record") ApproveFlow record, @Param("example") ApproveFlowExample example);
}