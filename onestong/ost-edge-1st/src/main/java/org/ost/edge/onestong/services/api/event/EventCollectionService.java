package org.ost.edge.onestong.services.api.event;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.controller.api.base.BaseService;
import org.ost.edge.onestong.dao.event.EventCollectionMapper;
import org.ost.edge.onestong.dao.resources.EventFileMapper;
import org.ost.edge.onestong.dao.tag.TagEventMapper;
import org.ost.edge.onestong.model.resources.Resource;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.tag.PTag;
import org.ost.edge.onestong.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventCollectionService extends BaseService {
	
	@Autowired
	private EventCollectionMapper  collectionMapper;
	@Autowired
	private EventFileMapper  eventFileMapper;
	
	@Autowired
	private TagEventMapper tagEventMapper;
	/**
	 * 拉取除 考勤以外的 事件
	 * @param userIds
	 * @param rb
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> pullEventsExcludeTypePaged(byte eventType, List<User> users,RowBounds rb){
		
		return this.collectionMapper.selectCollectionExcludeTypePaged(eventType, users, rb);
		
	}
	
	
	/**
	 * pull all event type
	 * @param userIds
	 * @param rb
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> pullEventsAllTypePaged(Integer userId, List<User> users,RowBounds rb,Integer eventType){
		
		return this.collectionMapper.selectCollectionALLTypePaged(userId,users,eventType, rb);
		
	}
	/**
	 * 按照类型 拉取
	 * @param eventType
	 * @param users
	 * @param rb
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> pullEventsByTypePaged(Integer userId,byte eventType, List<User> users,RowBounds rb){
		
		return this.collectionMapper.selectCollectionByTypePaged(userId, eventType, users, rb);
		
	}
	
	
	@Transactional(readOnly = true)
	public List<Resource> findEventRelatedFiles(String eventId,Byte flowType){
		return eventFileMapper.selectFilesByEventId(eventId,flowType);
		
	}
	
	@Transactional(readOnly = true)
	public List<Resource> findEventRelatedFiles(String eventId){
		return eventFileMapper.selectFilesByEventId(eventId);
		
	}

	@Transactional(readOnly = true)
	public List<PTag> findEventRelatedPtags(String eventId) {
		return  this.tagEventMapper.selectPtagsByEventId(eventId);
	}
	
	@Transactional(readOnly = true)
	public List<CTag> findEventRelatedCtags(String eventId) {
		return  this.tagEventMapper.selectCtagsByEventId(eventId);
	}

}
