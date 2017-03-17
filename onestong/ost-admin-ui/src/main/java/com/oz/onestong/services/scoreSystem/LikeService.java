package com.oz.onestong.services.scoreSystem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oz.onestong.dao.event.base.EventBaseMapper;
import com.oz.onestong.dao.scoreSystem.LikeMapper;
import com.oz.onestong.model.scoreSystem.Like;
import com.oz.onestong.model.scoreSystem.LikeExample;
import com.oz.onestong.tools.Constants;

/**
 * 
 * @author mac
 * 
 */
@Service
public class LikeService {
	@Autowired
	private LikeMapper likeMapper;
	@Autowired
	private EventBaseMapper eventBaseMapper;
	/**
	 * 查找我点赞的事件 TODO 建议此处做 query cache
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<String> findMyLikedEvents(Integer userId) {
		
		
		
		
		return this.likeMapper.selectMyLikedEventIds(userId);
	}

	/**
	 * 点赞
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public Integer didLike(Like like, Byte eventType, String eventId) {
		this.likeMapper.insertSelective(like);

		String eventTable = "";
		String eventIdColum = "";
		switch (eventType) {
		case Constants.EVENT_ATTENCE:
			eventTable = "attence_events";
			eventIdColum = "se_id";
			
			
			
			break;
		case Constants.EVENT_VISIT:
			eventTable = "visit_events";
			eventIdColum = "ve_id";
			break;

		case Constants.EVENT_DAILY:
			eventTable = "daily_events";
			eventIdColum = "de_id";
			break;
			
		case Constants.EVENT_TASK:
			eventTable = "task";
			eventIdColum = "taskId";
			break;
		default:
			break;
		}
		eventBaseMapper
				.updateLikeCount(eventId, eventTable, "+1", eventIdColum);
		//返回当前点赞数量
		return this.eventBaseMapper.selectLikeTotalCount(eventId, eventTable, eventIdColum);
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public Integer cancelLike(Like like, Byte eventType, String eventId) {
		
		LikeExample le = new LikeExample();
		le.createCriteria().andEventIdEqualTo(eventId).andUserIdEqualTo(like.getUserId());
		
		this.likeMapper.updateByExampleSelective(like, le);

		String eventTable = "";
		String eventIdColum = "";
		switch (eventType) {
		case Constants.EVENT_ATTENCE:
			
			eventTable = "attence_events";
			eventIdColum = "se_id";
			break;
		case Constants.EVENT_VISIT:
			eventTable = "visit_events";
			eventIdColum = "ve_id";
			break;

		case Constants.EVENT_DAILY:
			eventTable = "daily_events";
			eventIdColum = "de_id";
			break;
			
		case Constants.EVENT_TASK:
			eventTable = "task";
			eventIdColum = "taskId";
			break;
		default:
			break;
		}
		eventBaseMapper
				.updateLikeCount(eventId, eventTable, "-1", eventIdColum);
		
		//返回当前点赞数量
		return this.eventBaseMapper.selectLikeTotalCount(eventId, eventTable, eventIdColum);
	}

	/**
	 * 根据事件 获取 like的人的信息
	 * 
	 * @param eventId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findLikeInfosByEvent(String eventId) {

		return this.likeMapper.selectLikedUsersByEventId(eventId);
	}
	@Transactional(readOnly = true)
	public boolean isILikedThisEvent(String eventId,Integer userId){
		LikeExample le = new LikeExample();
		le.createCriteria().andValidEqualTo(Constants.DATA_VALID).andEventIdEqualTo(eventId).andUserIdEqualTo(userId);
		return this.likeMapper.countByExample(le)>0?true:false;
	}

}
