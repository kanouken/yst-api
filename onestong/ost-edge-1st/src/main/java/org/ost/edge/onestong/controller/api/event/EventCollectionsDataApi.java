package org.ost.edge.onestong.controller.api.event;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.DailyEventService;
import org.ost.edge.onestong.services.api.event.EventCollectionService;
import org.ost.edge.onestong.services.api.event.VisitEventService;
import org.ost.edge.onestong.services.authority.AuthorityService;
import org.ost.edge.onestong.services.scoreSystem.LikeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 事件集合的 卡片流 逻辑
 * 
 * @author xnq
 * 
 */
@Api(tags = "首页事件")
@RestController
@RequestMapping("/api/eventCards")
public class EventCollectionsDataApi extends Action {
	@Autowired
	private UsersService usersService;
	@Autowired
	private EventCollectionService collectionService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 推送
	 */
	public void push() {

	}

	/**
	 * 按类型拉取 根据权限判断
	 * 
	 * @param userId
	 * @param type
	 * @param curPage
	 * @param perPageSum
	 * @param token
	 */
	@ResponseBody
	@RequestMapping("pull/{userId}/{type}/{curPage}/{perPageSum}/{token}")
	public Object pull(@PathVariable("userId") Integer userId, @PathVariable("type") Byte type,
			@PathVariable("curPage") Integer curPage, @PathVariable("perPageSum") Integer perPageSum,
			@PathVariable("token") String token) {

		OperateResult op = new OperateResult();

		User user = null;
		try {
			user = this.usersService.findOneById(userId);

			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("事件拉取 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			Role role = this.authorityService.findRoleByUser(user);
			List<Map<String, Object>> perms = this.authorityService.findPermsByRoleAndType(role, Constants.MOUDLE_APP);
			// [{authDesc=接收来自部门其他成员的事件推送, authTag=self_below_events},
			// {authDesc=创建考勤事件, authTag=create_attence}, {authDesc=创建外访事件,
			// authTag=create_visit}, {authDesc=查看部门内其他成员的事件简报,
			// authTag=self_below_reports}]

			Page page = new Page();
			page.setCurPage(curPage);
			page.setPerPageSum(perPageSum);
			RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());

			List<Map<String, Object>> collections = collectionService.pullEventsByTypePaged(user.getUserId(), type,
					usersService.findUserScopes(user, perms), rb);
			if (type == Constants.EVENT_VISIT) {
				for (Map<String, Object> map : collections) {

					// 获取资源 只获取外访签到 的图片
					map.put("files", this.collectionService.findEventRelatedFiles(map.get("id").toString().trim(),
							VisitEventService.VISIT_FLOW_STEP_SIGNIN));

					map.put("pTags", this.collectionService.findEventRelatedPtags(map.get("id").toString().trim()));
					map.put("cTags", this.collectionService.findEventRelatedCtags(map.get("id").toString().trim()));
				}

			} else if (type == Constants.EVENT_DAILY) {
				for (Map<String, Object> map : collections) {

					// 日志图片
					map.put("files", this.collectionService.findEventRelatedFiles(map.get("id").toString().trim(),
							DailyEventService.FLOW_DEFAULT));

				}

				// 任务图片
			} else if (type == Constants.EVENT_TASK) {

				for (Map<String, Object> map : collections) {

					map.put("files",
							this.collectionService.findEventRelatedFiles(map.get("id").toString().trim(), null));

				}

			}

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("pull data success!");
			op.setData(collections);

		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error("事件集合  -- 拉取类型  失败 类型:" + type, e);
			}
		}
		return op;
	}

	/**
	 * 拉取所有类型的事件
	 */
	@ResponseBody
	@RequestMapping("pullall/{userId}/{curPage}/{perPageSum}/{token}")
	public Object pull(@PathVariable("userId") Integer userId, @PathVariable("curPage") Integer curPage,
			@PathVariable("perPageSum") Integer perPageSum, @PathVariable("token") String token) {
		OperateResult op = new OperateResult();
		User user = null;
		try {
			// TODO 判断权限 拉取 范围
			user = this.usersService.findOneById(userId);

			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("事件拉取 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			Role role = this.authorityService.findRoleByUser(user);
			List<Map<String, Object>> perms = this.authorityService.findPermsByRoleAndType(role, Constants.MOUDLE_APP);
			Page page = new Page();
			page.setCurPage(curPage);
			page.setPerPageSum(perPageSum);
			RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
			List<Map<String, Object>> collections = collectionService.pullEventsAllTypePaged(user.getUserId(),
					usersService.findUserScopes(user, perms), rb);
			for (Map<String, Object> map : collections) {

				if (Byte.valueOf(map.get("type").toString().trim()) == Constants.EVENT_VISIT) {

					// 获取资源
					map.put("files", this.collectionService.findEventRelatedFiles(map.get("id").toString().trim(),
							VisitEventService.VISIT_FLOW_STEP_SIGNIN));

					map.put("pTags", this.collectionService.findEventRelatedPtags(map.get("id").toString().trim()));
					map.put("cTags", this.collectionService.findEventRelatedCtags(map.get("id").toString().trim()));
				} else if (Byte.valueOf(map.get("type").toString().trim()) == Constants.EVENT_DAILY) {

					// 获取资源
					map.put("files", this.collectionService.findEventRelatedFiles(map.get("id").toString().trim(),
							DailyEventService.FLOW_DEFAULT));

				} else if (Byte.valueOf(map.get("type").toString().trim()) == Constants.EVENT_TASK) {

					// 获取资源
					map.put("files",
							this.collectionService.findEventRelatedFiles(map.get("id").toString().trim(), null));

				}

			}

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("pull data success!");
			op.setData(collections);

		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error("事件集合  -- 拉取默认 失败 用户:" + user.getRealname(), e);
			}
		}

		return op;

	}

	/**
	 * 所有类型事件
	 * 
	 * @param user
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	@GetMapping(value = "")
	public List<Map<String, Object>> pull(@RequestAttribute(value = LOGIN_USER) User user,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum) {
		Role role = this.authorityService.findRoleByUser(user);
		List<Map<String, Object>> perms = this.authorityService.findPermsByRoleAndType(role, Constants.MOUDLE_APP);
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		List<Map<String, Object>> collections = collectionService.pullEventsAllTypePaged(user.getUserId(),
				usersService.findUserScopes(user, perms), rb);
		return collections;
	}

}
