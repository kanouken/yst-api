package org.ost.edge.onestong.controller.api.scoreSystem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.scoreSystem.Like;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.scoreSystem.LikeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/like")
public class LikeDataApi extends Action {
	@Autowired
	private UsersService usersService;
	@Autowired
	private LikeService likeService;

	/**
	 * 点赞
	 * 
	 * @param token
	 * @param entity
	 *            userid eventid eventType 在所有事件 表中增加一个 回复次数 与 赞次数的 字段
	 */
	@ResponseBody
	@RequestMapping(value = "add/{token}", method = RequestMethod.POST)
	public Object didLike(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		OperateResult op = new OperateResult();
		Object userId = entity.get("userId");
		Object eventId = entity.get("eventId");
		Object eventType = entity.get("eventType");
		Like like = null;
		User user = null;
		try {
			if (null == userId || null == eventId || null == eventType) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid must be provided!");
				op.setData(null);
				return op;
			} else {
				user = this.usersService.findOneById(Integer.valueOf(userId
						.toString().trim()));
				if (null == user) {

					if (logger.isErrorEnabled()) {
						logger.error("点赞 --- 用户不存在");

					}
					op.setStatusCode(Constants.USER_NOT_FOUND);
					op.setDescription("user can not found!");
					return op;
				} else {
					if(this.likeService.isILikedThisEvent(String.valueOf(eventId.toString()),Integer.valueOf(userId.toString()))){
						op.setStatusCode(Constants.HTTP_200);
						op.setDescription("点过了！");
						op.setData(null);
						return op;
					}
					
					
					like = new Like();
					Date current = new Date();
					like.setUserId(user.getUserId());
					like.setCreator(user.getRealname());
					like.setUpdator(user.getRealname());
					like.setCreateTime(current);
					like.setUpdateTime(current);
					like.setEventId(eventId.toString().trim());
					Integer totalCount =  this.likeService.didLike(like,
							Byte.valueOf(eventType.toString().trim()),
							String.valueOf(eventId.toString().trim()));
					
					op.setStatusCode(HTTP_200);
					op.setDescription("success");
					returnMap.put("totalCount", totalCount);
					op.setData(returnMap);
				}

				
			}

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("点赞失败 --" + user.getRealname() + "eventid =  "
						+ eventId, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		} finally {
			eventId = null;
			like = null;
			user = null;
		}
		return op;

	}

	/**
	 * 取消赞
	 * 
	 * @param token
	 * @param entity
	 *            likeId, userid eventid eventType 在所有事件 表中增加一个 回复次数 与 赞次数的 字段
	 */
	@ResponseBody
	@RequestMapping(value = "cancel/{token}", method = RequestMethod.POST)
	public Object cancelLike(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		OperateResult op = new OperateResult();
		Object userId = entity.get("userId");
		Object eventId = entity.get("eventId");
		Object eventType = entity.get("eventType");
		Like like = null;
		User user = null;
		try {
			if ( null == userId || null == eventId
					|| null == eventType) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid must be provided!");
				op.setData(null);
				return op;
			} else {
				user = this.usersService.findOneById(Integer.valueOf(userId
						.toString().trim()));
				if (null == user) {

					if (logger.isErrorEnabled()) {
						logger.error("取消赞 --- 用户不存在");

					}
					op.setStatusCode(Constants.USER_NOT_FOUND);
					op.setDescription("user can not found!");
					return op;
				} else {
					
					if(!this.likeService.isILikedThisEvent(String.valueOf(eventId.toString()),Integer.valueOf(userId.toString()))){
						
						op.setStatusCode(Constants.HTTP_200);
						op.setDescription("你没点过这个赞");
						op.setData(null);
						return op;
					}
					

					like = new Like();
					//like.setLikeId(Integer.valueOf(likeId.toString()));
					Date current = new Date();
					like.setUserId(Integer.valueOf(userId.toString()));
					like.setUpdator(user.getRealname());
					like.setUpdateTime(current);
					like.setValid(Constants.DATA_INVALID);
					Integer totalCount = this.likeService.cancelLike(like,
							Byte.valueOf(eventType.toString().trim()),
							String.valueOf(eventId.toString().trim()));
					
					
					
					returnMap.put("totalCount", totalCount);

				}

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("取消赞成功！");
				op.setData(returnMap);
			}

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("取消赞失败 --" + user.getRealname() + "eventid =  "
						+ eventId, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		} finally {
			eventId = null;
			like = null;
			user = null;
		}
		return op;

	}

}
