package org.ost.edge.onestong.dao.scoreSystem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ost.edge.onestong.model.scoreSystem.Like;
import org.ost.edge.onestong.model.scoreSystem.LikeExample;

public interface LikeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int countByExample(LikeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int deleteByExample(LikeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int deleteByPrimaryKey(Integer likeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int insert(Like record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int insertSelective(Like record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    List<Like> selectByExample(LikeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    Like selectByPrimaryKey(Integer likeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int updateByExampleSelective(@Param("record") Like record, @Param("example") LikeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int updateByExample(@Param("record") Like record, @Param("example") LikeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int updateByPrimaryKeySelective(Like record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table likes
     *
     * @mbggenerated Wed Jan 14 17:26:02 CST 2015
     */
    int updateByPrimaryKey(Like record);
    
    
    List<Map<String, Object>> selectLikedUsersByEventId(@Param("eventId") String eventId);

	List<String> selectMyLikedEventIds(@Param("userId") Integer userId);
    
	
}