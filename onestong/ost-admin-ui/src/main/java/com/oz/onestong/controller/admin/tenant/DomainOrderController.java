package com.oz.onestong.controller.admin.tenant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oz.onestong.anotations.PageRequired;
import com.oz.onestong.controller.base.Action;
import com.oz.onestong.model.Page;
import com.oz.onestong.model.domain.Domain;
import com.oz.onestong.model.domain.DomainOrder;
import com.oz.onestong.model.domain.DomainOrderDetail;
import com.oz.onestong.services.admin.domain.DomainOrderService;
import com.oz.onestong.services.admin.domain.DomainService;

@Controller
@RequestMapping("/ost/domain/order")
public class DomainOrderController extends Action{
	@Autowired
	private DomainOrderService domainOrderService;
	@Autowired
	private DomainService domainService;
	
	
	@PageRequired
	@ResponseBody
	@RequestMapping("history/list.html")
	public  ModelAndView  orderList(@RequestParam("domainId") Integer domainId,HttpServletRequest request) {
		Domain domain = this.domainService.findOneById(domainId);
		DomainOrder domainOrder = this.domainOrderService.findDomainOrderByDomainId(domainId);
		DomainOrderDetail dod = null;
		if(0 == domainOrder.getDetails().size()){
			 dod = new DomainOrderDetail();
			dod.setCreateTime(domainOrder.getCreateTime());
			dod.setUpdateTime(domainOrder.getUpdateTime());
			dod.setCreator(domainOrder.getCreator());
			dod.setUpdator(domainOrder.getUpdator());
			dod.setOdSum(domainOrder.getDoSum());
			domainOrder.getDetails().add(dod);
			
		}
		
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("domainId",domainId);
	//	pageAttr.put("domain", value)//好像没有搜索 功能
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("admin/domain/domainOrder_list");
		int totalRecords = domainOrder.getDetails().size();
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		domainOrder = domainOrderService.findDomainOrderByDomainId(domainId, rb);
		if(null != dod){
			domainOrder.getDetails().add(dod);
			
		}
		mv.addObject("domain", domain);
		mv.addObject("domainOrder", domainOrder);
		return mv;
	}
}
