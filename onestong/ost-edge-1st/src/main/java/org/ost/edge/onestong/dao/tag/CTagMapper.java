package org.ost.edge.onestong.dao.tag;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.tag.CTagExample;

public interface CTagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int countByExample(CTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int deleteByExample(CTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int deleteByPrimaryKey(Integer cTId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int insert(CTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int insertSelective(CTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    List<CTag> selectByExample(CTagExample example);
    
    List<CTag> selectByExample(CTagExample example,RowBounds rb);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    CTag selectByPrimaryKey(Integer cTId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int updateByExampleSelective(@Param("record") CTag record, @Param("example") CTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int updateByExample(@Param("record") CTag record, @Param("example") CTagExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int updateByPrimaryKeySelective(CTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table c_tags
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    int updateByPrimaryKey(CTag record);
}