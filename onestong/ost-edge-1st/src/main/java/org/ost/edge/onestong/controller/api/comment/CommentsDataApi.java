package org.ost.edge.onestong.controller.api.comment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.comment.Comment;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.comment.CommentService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对事件 的评论操作
 * 
 * @author mac
 * 
 */
@Controller
@RequestMapping("/api/comments")
public class CommentsDataApi extends Action {

	@Autowired
	private CommentService commentService;
	@Autowired
	private UsersService usersService;

	/**
	 * 对某个事件发起评论
	 * 
	 * 
	 * 内容 xxxxxxx 评论于 2015-01-14 10:24:00 xx对xx 说 （其实都是内容）； 谁发的 事件里面加个 评论数量。
	 * 点赞数量
	 * 
	 * @param token
	 * @param entity
	 *            {userId eventId content toUserId ,eventType}
	 */
	@ResponseBody
	@RequestMapping(value = "post/{token}", method = RequestMethod.POST)
	public Object post(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		Object userId = entity.get("userId");
		Object eventId = entity.get("eventId");
		Object content = entity.get("content");
		Object eventType = entity.get("eventType");
		Object toUser = entity.get("toUserId");
		Comment comment = null;
		User user = null;
		try {
			if (null == userId || null == eventId || null == content
					|| null == eventType) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("找不到用户，或者发送内容为空！");
				op.setData(null);
				return op;
			} else {
				user = this.usersService.findOneById(Integer.valueOf(userId
						.toString().trim()));
				if (null == user) {

					if (logger.isErrorEnabled()) {
						logger.error("发布评论 --- 用户不存在");

					}
					op.setStatusCode(Constants.USER_NOT_FOUND);
					op.setDescription("user can not found!");
					return op;
				} else {

					comment = new Comment();
					Date current = new Date();
					comment.setUserId(user.getUserId());
					comment.setCreator(user.getRealname());
					comment.setUpdator(user.getRealname());
					comment.setCreateTime(current);
					comment.setUpdateTime(current);
					comment.setContent(content.toString());
					comment.setEventId(eventId.toString().trim());
					if (null != toUser) {
						comment.setToUserId(Integer.valueOf(toUser.toString()
								.trim()));

					}

					this.commentService.post(comment,
							Byte.valueOf(eventType.toString().trim()));

				}

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("评论发送成功！");
				op.setData(null);

			}

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("评论发送失败 --" + user.getRealname() + "eventid =  "
						+ eventId, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		} finally {
			eventId = null;
			content = null;
			toUser = null;
			comment = null;
			user = null;
		}
		return op;

	}

	/**
	 * 删除某条评论
	 * 
	 * @param token
	 * @param entity
	 *            {userId commentId,eventId, eventType}
	 */
	@ResponseBody
	@RequestMapping(value = "delete/{token}", method = RequestMethod.POST)
	public Object delete(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		Object userId = entity.get("userId");
		Object eventId = entity.get("eventId");
		Object commentId = entity.get("commentId");
		Object eventType = entity.get("eventType");

		Comment comment = null;
		User user = null;
		try {

			if (null == userId || null == eventId || null == commentId
					|| null == eventType) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("找不到用户，删除内容为空！");
				op.setData(null);
				return op;
			} else {

				user = this.usersService.findOneById(Integer.valueOf(userId
						.toString().trim()));
				if (null == user) {

					if (logger.isErrorEnabled()) {
						logger.error("发布评论 --- 用户不存在");

					}
					op.setStatusCode(Constants.USER_NOT_FOUND);
					op.setDescription("user can not found!");
					return op;
				} else {
					Date current = new Date();

					comment = new Comment();

					comment.setCommentId(Integer.valueOf(commentId.toString()
							.trim()));
					comment.setUpdateTime(current);
					comment.setUpdator(user.getRealname());
					comment.setValid(Constants.DATA_INVALID);
					this.commentService.deleteComment(comment,
							Byte.valueOf(eventType.toString().trim()),
							String.valueOf(eventId.toString().trim()));

				}

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("评论删除成功！");
				op.setData(null);

			}

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("删除评论失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 获取某个事件的评论
	 * 
	 * @param token
	 * @param entity
	 *            {userid eventid content }
	 */
	@ResponseBody
	@RequestMapping(value = "pull/{eventId}/{token}", method = RequestMethod.GET)
	public Object pull(@PathVariable("token") String token,
			@PathVariable("eventId") String eventId) {

		OperateResult op = new OperateResult();

		try {

			List<Comment> comments = this.commentService
					.findCommentsFilterByEvent(eventId);

			op.setStatusCode(HTTP_200);
			op.setDescription("find comments filter event success!");
			op.setData(comments);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("拉取事件评论列表失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}

}
