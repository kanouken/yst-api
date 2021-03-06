package org.ost.edge.onestong.controller.api.tag;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.tag.PTag;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.common.tag.ProjectTagService;
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
@RequestMapping("/api/tag/project")
public class ProjectTagDataApi extends Action {
	@Autowired
	private UsersService usersService;
	@Autowired
	private ProjectTagService projectTagService;

	@ResponseBody
	@RequestMapping(value = "{tagId}/relatedEvents/{token}", method = RequestMethod.GET)
	public Object findTagRelatedEvents(@PathVariable("tagId") Integer tagId,
			@PathVariable("token") String token) {

		OperateResult op = new OperateResult();
		PTag tag = new PTag();
		tag.setpTId(tagId);
		try {
		List<Map<String, Object>>  events = 	this.projectTagService.findProjectRelatedEvents(tag, null);
		
		op.setStatusCode(HTTP_200);
		op.setDescription("find project tag related events success!");
		op.setData(events);
		//TODO 此处应关联附件图片
		
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("查找项目关联事件失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

	/**
	 * 获取所有 项目标签列表 如果 pageSum 等于 0 则查询所有记录 {"keyword":}
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/search/{curPage}/{pageSum}/{token}", method = RequestMethod.POST)
	public Object projectTagSarchPaged(@PathVariable("userId") Integer userId,
			@PathVariable("curPage") Integer curPage,
			@PathVariable("pageSum") Integer pageSum,
			@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {

		OperateResult op = new OperateResult();
		PTag tag = null;
		Page page = null;
		List<PTag> tags = null;
		RowBounds rb = null;
		Object keyword = entity.get("keyword");
		User user = null;
		try {
			if (StringUtils.isBlank(keyword == null ? "" : keyword.toString())) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("search words must be provided!");
				return op;
			}

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("项目标签搜索失败 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			tag = new PTag();
			// 设置domainId
			tag.setDomainId(user.getDomainId());
			tag.setpTName(keyword.toString());
			// totalRecord =
			// this.projectTagService.findProjectTagCountByTag(tag);
			if (0 != pageSum) {
				page = new Page();
				page.setCurPage(curPage);
				page.setPerPageSum(pageSum);
				rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
			} else {
				rb = null;
			}
			tags = this.projectTagService.findProjectTagsByTag(tag, rb);
			op.setStatusCode(HTTP_200);
			op.setDescription("search project tags paged success ");
			op.setData(tags);
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("分页 查询 获取项目标签失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		} finally {

			tag = null;
			page = null;
			tags = null;
			rb = null;
		}

		return op;

	}

	/**
	 * 获取所有 项目标签列表 如果 pageSum 等于 0 则查询所有记录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/allPaged/{userId}/{curPage}/{pageSum}/{token}", method = RequestMethod.GET)
	public Object projectTagsList(@PathVariable("userId") Integer userId,
			@PathVariable("curPage") Integer curPage,
			@PathVariable("pageSum") Integer pageSum,
			@PathVariable("token") String token) {

		OperateResult op = new OperateResult();
		PTag tag = null;
		Page page = null;
		List<PTag> tags = null;
		RowBounds rb = null;
		try {

			User user = null;
			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("项目标签拉取失败 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			tag = new PTag();
			// 设置domainId
			tag.setDomainId(user.getDomainId());
			// totalRecord =
			// this.projectTagService.findProjectTagCountByTag(tag);
			if (0 != pageSum) {
				page = new Page();
				page.setCurPage(curPage);
				page.setPerPageSum(pageSum);
				rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
			} else {
				rb = null;
			}
			tags = this.projectTagService.findProjectTagsByTag(tag, rb);
			op.setStatusCode(HTTP_200);
			op.setDescription("find project tags paged success ");
			op.setData(tags);
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("分页获取项目标签失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		} finally {

			tag = null;
			page = null;
			tags = null;
			rb = null;
		}

		return op;

	}

}
