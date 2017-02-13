package org.ost.edge.onestong.dao.resources;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.edge.onestong.model.resources.Resource;

/**
 * 处理 事件 与 资源的关联
 * @author mac
 *
 */

public interface EventFileMapper {
		
		/**
		 * 
		 * @param eventId 事件id
		 * @param resourceIds 数组  资源ids 
		 * 事件流程 中 的附件
		 */
		void insertEventAndFiles(@Param("eventId") String eventId,@Param("resourceIds") List<Integer> resourceIds ,@Param("flowType") Byte flowType );
		/**
		 * 查找 事件的附件 所有
		 * @param eventId
		 */
		List<Resource> selectFilesByEventId(@Param("eventId")  String eventId);
		
		/**
		 * 查找 事件的附件 
		 * @param eventId 事件流 类型
		 */
		List<Resource> selectFilesByEventId(@Param("eventId")  String eventId,@Param("flowType") Byte flowType);
		
}
