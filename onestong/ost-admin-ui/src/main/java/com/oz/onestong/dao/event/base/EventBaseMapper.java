package com.oz.onestong.dao.event.base;

import org.apache.ibatis.annotations.Param;
/**
 *  事件 的公用 数据层
 * @author mac
 *
 */
public interface EventBaseMapper {
	
	/**
	 * 提供给所有事件 的评论 数量的 增加 或者减少
	 * @param eventId
	 * @param table
	 * @param plusOrReduce
	 * @param idColum
	 */
	void updateCommentCount(@Param("eventId") String eventId,
			@Param("eventTable") String table,
			@Param("plusOrReduce") String plusOrReduce,
			@Param("eventIdCol") String idColum);
	
	
	/**
	 * 提供给所有事件 的点赞 
	 * @param eventId
	 * @param table
	 * @param plusOrReduce
	 * @param idColum
	 */
	void updateLikeCount(@Param("eventId") String eventId,
			@Param("eventTable") String table,
			@Param("plusOrReduce") String plusOrReduce,
			@Param("eventIdCol") String idColum);
	
	
	Integer selectLikeTotalCount(@Param("eventId") String eventId,
			@Param("eventTable") String table,
			@Param("eventIdCol") String idColum);
	
	/**
	 * 根据事件返回 作者信息 
	 * @param eventId
	 * @param eventTable
	 * @return
	 */
	String selectUserByEvent(@Param("eventId") String eventId,
			@Param("eventTable") String table,
			@Param("eventIdCol") String idColum );
	
}
