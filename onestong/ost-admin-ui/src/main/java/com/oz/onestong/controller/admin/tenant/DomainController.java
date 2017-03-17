package com.oz.onestong.controller.admin.tenant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.oz.onestong.model.system.worktime.Worktime;
import com.oz.onestong.services.admin.domain.DomainOrderService;
import com.oz.onestong.services.admin.domain.DomainService;
import com.oz.onestong.tools.Constants;

@Controller
@RequestMapping("/ost/domainUser")
public class DomainController extends Action {

	@Autowired
	private DomainService domainService;
	@Autowired
	private DomainOrderService domainOrderService;

	/**
	 * 分页处理 授权用户列表
	 * 
	 * @return
	 */
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("list")
	public ModelAndView domainList(HttpServletRequest request, Domain domain,
			HttpSession session) {

		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("domainName", domain.getDomainName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("admin/domain/domain_list");
		int totalRecords = domainService.findAllDomainUsersCount(domain);
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("domains", domainService.findAllDomainUsers(domain, rb));
		return mv;

	}

	@RequestMapping(value = "detail/{domainId}", method = RequestMethod.GET)
	public ModelAndView domainView(@PathVariable("domainId") Integer domainId) {
		ModelAndView mv = new ModelAndView();
		DomainOrder order = this.domainOrderService
				.findDomainOrderByDomainId(domainId);
		mv.setViewName("admin/domain/domain_detail");
		mv.addObject("order", order);
		return mv;

	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView preAddDomain(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/domain/domain_add");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "update.html", method = RequestMethod.GET)
	public ModelAndView preUpdateDomainUser(
			@RequestParam("domainId") Integer domainId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/domain/domain_update");
		Domain domain = this.domainService.findOneById(domainId);
		DomainOrder order = this.domainOrderService
				.findDomainOrderByDomainId(domainId);
		mv.addObject("domain", domain);
		mv.addObject("domainOrder", order);
		return mv;
	}

	/**
	 * 为用户授权 ，添加新用户
	 * 
	 * @param domain
	 * @param domainOrder
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "doAdd", method = RequestMethod.POST)
	public Object addDomainUser(Domain domain, DomainOrder domainOrder,
			@RequestParam("startworkTime") String startworkTime,
			@RequestParam("offworkTime") String offworkTime,

			HttpSession session) {

		OperateResult op = new OperateResult();

		Map<String, Object> admin = (Map<String, Object>) sessionStroe(session,
				Constants.SESSION_ADMIN);
		String operater = String.valueOf(admin.get("login_name"));

		try {
			if (StringUtils.isBlank(domain.getDomainName())
					|| StringUtils.isBlank(domain.getEmail())
					|| StringUtils.isBlank(domain.getLoginName())
					|| StringUtils.isBlank(domain.getLoginPassword())
					|| null == domainOrder.getDoSum()
					|| null == domainOrder.getStartTime()
					|| null == domainOrder.getEndTime()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("请将参数填写完整！");
				return op;
			} else if (!domainOrder.getEndTime().after(
					domainOrder.getStartTime())) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("激活码的截至时间 必须大于 开始时间！");
				return op;
			} else {
				Date current = new Date();
				domain.setCreateTime(current);
				domain.setUpdateTime(current);
				domainOrder.setCreateTime(current);
				domainOrder.setUpdateTime(current);
				domain.setCreator(operater);
				domain.setUpdator(operater);
				domainOrder.setCreator(operater);
				domainOrder.setUpdator(operater);
				
				DateFormat df = new SimpleDateFormat("HH:mm");
				Worktime wt = new Worktime();
				wt.setCreator(operater);
				wt.setUpdator(operater);
				wt.setCreateTime(new Date());
				wt.setUpdateTime(new Date());
				wt.setStartworkTime(df.parse(startworkTime));
				wt.setOffworkTime(df.parse(offworkTime));
				
				this.domainService
						.addDomainUserAndPreOrder(domain, domainOrder,wt);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("添加成功！，账户与激活码已成功发送至 : " + domain.getEmail());
				op.setData(null);
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("添加域用户失败 " + domain.getDomainName(), e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}

		return op;
	}

	@ResponseBody
	@RequestMapping(value = "preAddActiveCode", method = RequestMethod.GET)
	public Object preAddActiveCode(@RequestParam("domainId") Integer domainId) {
		OperateResult op = new OperateResult();
		try {
			Domain domain = this.domainService.findOneById(domainId);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("");
			op.setData(domain);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
			}
			op.setData(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}
		return op;

	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "doAddActiveCode", method = RequestMethod.POST)
	public Object addActiveCode(DomainOrderDetail domainOrderDetail,
			Domain domain, HttpSession session) {

		OperateResult op = new OperateResult();

		Map<String, Object> admin = (Map<String, Object>) sessionStroe(session,
				Constants.SESSION_ADMIN);
		String operater = String.valueOf(admin.get("login_name"));

		try {

			domain = this.domainService.findOneById(domain.getDomainId());
			DomainOrder order = this.domainOrderService
					.findDomainOrderByDomainId(domain.getDomainId());

			Date current = new Date();
			domainOrderDetail.setCreator(operater);
			domainOrderDetail.setUpdator(operater);
			domainOrderDetail.setCreateTime(current);
			domainOrderDetail.setUpdateTime(current);

			order.setUpdateTime(current);
			order.setUpdator(operater);
			this.domainService.addActiveCode(order, domainOrderDetail);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("已成功添加 " + domainOrderDetail.getOdSum() + "个激活码");
			op.setData(null);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("添加激活码失败 " + domain.getDomainName(), e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}

		return op;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "doUpdate", method = RequestMethod.POST)
	public Object updateDomainUser(Domain domain, DomainOrder domainOrder,
			HttpSession session) {

		OperateResult op = new OperateResult();

		Map<String, Object> admin = (Map<String, Object>) sessionStroe(session,
				Constants.SESSION_ADMIN);
		String operater = String.valueOf(admin.get("login_name"));

		try {
			if (StringUtils.isBlank(domain.getDomainName())
					|| StringUtils.isBlank(domain.getEmail())
					|| StringUtils.isBlank(domain.getLoginName())
					|| null == domainOrder.getStartTime()
					|| null == domainOrder.getEndTime()) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("请将参数填写完整！");
				return op;
			} else if (!domainOrder.getEndTime().after(
					domainOrder.getStartTime())) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("激活码的截至时间 必须大于 开始时间！");
				return op;
			} else if (StringUtils.isNotBlank(domain.getLoginPassword())
					&& !domain.getLoginPassword().equals(domain.getOptional1())) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("两次填写的密码不一致呢！！");
				return op;

			}

			else {
				Date current = new Date();
				domain.setUpdateTime(current);
				domainOrder.setUpdateTime(current);
				domain.setUpdator(operater);
				domainOrder.setUpdator(operater);

				this.domainService.updateDomainUserAndPreOrder(domain,
						domainOrder);

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("修改成功！");
				op.setData(null);
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("更新域用户失败 " + domain.getDomainName(), e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}

		return op;
	}

}
