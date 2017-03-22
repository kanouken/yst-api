//package com.oz.onestong.services.api.comment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import net.sf.json.JSONObject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.gexin.rp.sdk.template.TransmissionTemplate;
//import com.oz.onestong.dao.comment.CommentMapper;
//import com.oz.onestong.dao.event.base.EventBaseMapper;
//import com.oz.onestong.model.comment.Comment;
//import com.oz.onestong.model.comment.CommentExample;
//import com.oz.onestong.model.user.User;
//import com.oz.onestong.tools.Constants;
//import com.oz.onestong.tools.MC2PushTools;
//
//@Service
//public class CommentService {
//	// @Transactional(rollbackFor = { Exception.class }, propagation =
//	// Propagation.REQUIRED)
//
//	@Autowired
//	private EventBaseMapper eventBaseMapper;
//	@Autowired
//	private CommentMapper commentMapper;
//
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void post(Comment comment, Byte eventType) throws Exception {
//		this.commentMapper.insertSelective(comment);
//		// 更新 评论次数
//
//		String eventTable = "";
//		String eventIdColum = "";
//		switch (eventType) {
//		case Constants.EVENT_ATTENCE:
//
//			// 考勤评论
//			eventTable = "attence_events";
//
//			eventIdColum = "se_id";
//			break;
//		case Constants.EVENT_VISIT:
//			eventTable = "visit_events";
//			eventIdColum = "ve_id";
//			break;
//		case Constants.EVENT_DAILY:
//			eventTable = "daily_events";
//			eventIdColum = "de_id";
//			break;
//		case Constants.EVENT_TASK:
//			eventTable = "task";
//			eventIdColum = "taskId";
//
//			break;
//		default:
//			break;
//		}
//		eventBaseMapper.updateCommentCount(comment.getEventId(), eventTable,
//				"+1", eventIdColum);
//		;
//		// 推送通知
//		String clientId = eventBaseMapper.selectUserByEvent(
//				comment.getEventId(), eventTable, eventIdColum);
//		TransmissionTemplate template = new TransmissionTemplate();
//		template.setAppId(Constants.GETUI_APP_ID);
//		template.setAppkey(Constants.GETUI_APP_KEY);
//		template.setTransmissionType(1);
//		JSONObject obj = new JSONObject();
//		obj.put("bandKey", "event_comment");
//		obj.put("event_type",eventType);
//		obj.put("event_id", comment.getEventId());
//		template.setTransmissionContent(obj.toString());
//		
//		template.setPushInfo("查看", 2, "您有一条新的评论！", "", "", "", "", "");
//		List<User> users = new ArrayList<User>();
//		User u = new User();
//		u.setcId(clientId);
//		users.add(u);
//		MC2PushTools tools = new MC2PushTools();
//		tools.setUsers(users);
//		tools.setTemplate(template);
//		tools.start();
//
//	}
//
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void deleteComment(Comment comment, Byte eventType, String eventId) {
//
//		this.commentMapper.updateByPrimaryKeySelective(comment);
//		// 更新 评论次数
//
//		String eventTable = "";
//		String eventIdColum = "";
//		switch (eventType) {
//		case Constants.EVENT_ATTENCE:
//			break;
//		case Constants.EVENT_VISIT:
//			eventTable = "visit_events";
//			eventIdColum = "ve_id";
//			break;
//		default:
//			break;
//		}
//		eventBaseMapper.updateCommentCount(eventId, eventTable, "-1",
//				eventIdColum);
//		;
//
//	}
//
//	@Transactional(readOnly = true)
//	public List<Comment> findCommentsFilterByEvent(String eventId) {
//		CommentExample ce = new CommentExample();
//		ce.createCriteria().andValidEqualTo(Constants.DATA_VALID)
//				.andEventIdEqualTo(eventId);
//		ce.setOrderByClause("update_time desc");
//		return this.commentMapper.selectByExample(ce);
//	}
//}
