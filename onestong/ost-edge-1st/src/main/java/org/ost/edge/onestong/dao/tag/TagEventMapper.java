package org.ost.edge.onestong.dao.tag;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.tag.PTag;

/**
 * 客户标签与事件关联关系
 * 
 * @author xnq
 * 
 */
public interface TagEventMapper {

	/**
	 * 
	 * @param ctagIds
	 *            客户标签id 数组
	 * @param eventId
	 *            事件 id
	 */
	void insertCTagAndEvent(@Param("ctagId") Integer ctagId,
			@Param("eventId") String eventId);

	/**
	 * 项目
	 */
	void insertPTagAndEvent(@Param("pTagId") Integer pTagId,
			@Param("eventId") String eventId);

	List<PTag> selectPtagsByEventId(@Param("eventId") String eventId);

	List<CTag> selectCtagsByEventId(@Param("eventId") String eventId);

	/**
	 * 
	 * 
	 * 
	 * 查询 与 标签相关联的事件
	 * 
	 * @param tagId
	 * @return
	 */
	List<Map<String, Object>> selectProjectTagRelatedEvent(
			@Param("tagId") Integer tagId, @Param("keyword") String keyword,
			RowBounds rb);

	List<Map<String, Object>> selectProjectTagRelatedEvent(
			@Param("tagId") Integer tagId, @Param("keyword") String keyword);

	int selectProjectTagRelatedEventCount(@Param("tagId") Integer tagId,
			@Param("keyword") String keyword);

	/**
	 * 客户标签
	 */

	List<Map<String, Object>> selectCustomerTagRelatedEvent(
			@Param("tagId") Integer tagId, @Param("keyword") String keyword,
			RowBounds rb);

	List<Map<String, Object>> selectCustomerTagRelatedEvent(
			@Param("tagId") Integer tagId, @Param("keyword") String keyword);

	int selectCustomerTagRelatedEventCount(@Param("tagId") Integer tagId,
			@Param("keyword") String keyword);
}
