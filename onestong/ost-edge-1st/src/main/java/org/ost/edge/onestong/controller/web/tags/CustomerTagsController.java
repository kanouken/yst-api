package org.ost.edge.onestong.controller.web.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.edge.onestong.anotations.AuthTarget;
import org.ost.edge.onestong.anotations.PageRequired;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.common.tag.CustomerTagService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tags/customer")
public class CustomerTagsController extends Action {
	private @Autowired CustomerTagService customerTagService;
	
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("list")
	public ModelAndView cTagList(HttpServletRequest request, CTag  tag,
			HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		tag.setDomainId(sessionUser.getDomainId());
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("cTName", tag.getcTName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("tags/customer/tag_list");
		int totalRecords = customerTagService.findCustomerTagCountByTag(tag);
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("tags", customerTagService.findCustomerTagsByTag(tag, rb));
		return mv;

	}
	/**
	 * 通过客户标签查找相关联的事件
	 * @param request
	 * @param tag
	 * @param session
	 * @return
	 */
	@AuthTarget({"cTagList"})
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("relateEvents/{tagId}/list")
	public ModelAndView pTagRelatedEventList(HttpServletRequest request, CTag tag, @PathVariable("tagId") Integer tagId,
			HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		tag.setDomainId(sessionUser.getDomainId());
		tag.setcTId(tagId);
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("cTName", tag.getcTName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("tags/customer/tag_event_list");
		int totalRecords = customerTagService.findCustomerRelatedEventByTag(tag, tag.getcTName());
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("events", customerTagService.findCustomerRelatedEvents(tag, tag.getcTName(), rb));
		mv.addObject("ctag",  this.customerTagService.findOneById(tagId));
		return mv;

	}
	
	/**
	 * timeline 形式展示 相关事件
	 * @param request
	 * @param tag
	 * @param session
	 * @return
	 */
	@AuthTarget({"cTagList"})
	@SuppressWarnings("all")
	@RequestMapping("relateEventsTimeLine/{tagId}/list")
	public ModelAndView pTagRelatedEventTimeLineList( CTag tag, @PathVariable("tagId") Integer tagId,
			HttpSession session) {
		
		
		tag.setcTId(tagId);
		ModelAndView mv = new ModelAndView("tags/customer/tag_event_timeline");
		mv.addObject("events", customerTagService.findCustomerRelatedEvents(tag, tag.getcTName()));
		mv.addObject("ctag", this.customerTagService.findOneById(tagId));
		return mv;

	}
	
	
	
	
	
	
	
	
}
