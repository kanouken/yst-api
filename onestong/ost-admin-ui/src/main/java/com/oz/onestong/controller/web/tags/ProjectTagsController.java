package com.oz.onestong.controller.web.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oz.onestong.anotations.AuthTarget;
import com.oz.onestong.anotations.PageRequired;
import com.oz.onestong.controller.base.Action;
import com.oz.onestong.model.Page;
import com.oz.onestong.model.tag.PTag;
import com.oz.onestong.model.user.User;
import com.oz.onestong.services.common.tag.ProjectTagService;
import com.oz.onestong.tools.Constants;

@Controller
@RequestMapping("/tags/project")
public class ProjectTagsController extends Action {
	private @Autowired ProjectTagService projectTagService;
	
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("list")
	public ModelAndView pTagList(HttpServletRequest request, PTag  tag,
			HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		tag.setDomainId(sessionUser.getDomainId());
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("pTName", tag.getpTName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("tags/project/tag_list");
		int totalRecords = projectTagService.findProjectTagCountByTag(tag);
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("tags", projectTagService.findProjectTagsByTag(tag, rb));
		return mv;

	}
	/**
	 * 通过项目标签查找相关联的事件
	 * @param request
	 * @param tag
	 * @param session
	 * @return
	 */
	@AuthTarget({"pTagList"})
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("relateEvents/{tagId}/list")
	public ModelAndView pTagRelatedEventList(HttpServletRequest request, PTag tag, @PathVariable("tagId") Integer tagId,
			HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		tag.setDomainId(sessionUser.getDomainId());
		tag.setpTId(tagId);
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("pTName", tag.getpTName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("tags/project/tag_event_list");
		int totalRecords = projectTagService.findProjectsRelatedEventByTag(tag, tag.getpTName());
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("events", projectTagService.findProjectRelatedEvents(tag, tag.getpTName(), rb));
		mv.addObject("ptag",  this.projectTagService.findOneById(tagId));
		return mv;

	}
	
	/**
	 * timeline 形式展示 相关事件
	 * @param request
	 * @param tag
	 * @param session
	 * @return
	 */
	@AuthTarget({"pTagList"})
	@SuppressWarnings("all")
	@RequestMapping("relateEventsTimeLine/{tagId}/list")
	public ModelAndView pTagRelatedEventTimeLineList( PTag tag, @PathVariable("tagId") Integer tagId,
			HttpSession session) {
		
		
		tag.setpTId(tagId);
		ModelAndView mv = new ModelAndView("tags/project/tag_event_timeline");
		mv.addObject("events", projectTagService.findProjectRelatedEvents(tag, tag.getpTName()));
		mv.addObject("ptag", this.projectTagService.findOneById(tagId));
		return mv;

	}
	
	
	
	
	
	
	
	
}
