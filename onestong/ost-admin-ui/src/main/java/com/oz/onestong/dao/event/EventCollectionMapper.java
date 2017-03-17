package com.oz.onestong.dao.event;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.oz.onestong.model.user.User;

/**
 * 卡片集合 的拉取 处理
 * @author xnq
 *
 */
public interface EventCollectionMapper {
	List<Map<String, Object>> selectCollectionByTypePaged(@Param("userId") Integer userId,@Param("type") byte type, @Param("userScope") List<User> userScope,RowBounds r);
	/**
	 * 不包括 某个类型的事件
	 * @return
	 */
	List<Map<String, Object>> selectCollectionExcludeTypePaged(@Param("type") byte type, @Param("userScope") List<User> userScope,RowBounds rb);
	
	/**
	 * @param userScope 拉取用户范围
	 * 默认不包括 考勤
	 * @return
	 */
	List<Map<String, Object>> selectCollectionDefaultTypePaged(@Param("type") byte type, @Param("userScope") List<Integer> userScope,RowBounds rb);
	
	/**
	 * 所有类型
	 * @return
	 */
	List<Map<String, Object>> selectCollectionALLTypePaged(@Param("userId") Integer userId, @Param("userScope") List<User> userScope,RowBounds rb);
	
	
	
	
	
}
