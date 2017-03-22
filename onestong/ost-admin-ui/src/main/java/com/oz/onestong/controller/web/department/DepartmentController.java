package com.oz.onestong.controller.web.department;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oz.onestong.anotations.PageRequired;
import com.oz.onestong.controller.base.Action;
import com.oz.onestong.model.Page;
import com.oz.onestong.model.department.Department;
import com.oz.onestong.model.user.User;
import com.oz.onestong.services.web.department.DepartmentService;
import com.oz.onestong.tools.Constants;

/**
 * 部门管理
 * 
 * @author xnq
 * 
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/department")
public class DepartmentController extends Action {
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 分页展示 部门列表  域 id
	 * 
	 * @return
	 */
	
	@PageRequired
	@RequestMapping("list")
	public ModelAndView departmentList(HttpServletRequest request ,Department dept) {
		
		
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				request.getSession(), Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		dept.setDomainId(sessionUser.getDomainId());
		Page  page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("name", dept.getName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("department/department_list");
		int totalRecords = departmentService.findAllDepartmentsCount(dept);
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("departments", departmentService.findAllDepartments(dept,rb));
		return mv;
	}

	/**
	 * 添加前获取准备数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "pre_add")
	public ModelAndView preAddDepartment(HttpServletRequest request) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				request.getSession(), Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		ModelAndView mv = new ModelAndView("department/department_add");
		//获取所有可选上级部门
		List<Department> departments = this.departmentService.findAllTopDepartments(sessionUser.getDomainId());
		mv.addObject("departments", departments);
		return mv;
	}
	/**
	 * 更新前获取数据
	 * @param department
	 * @return
	 */
	@ResponseBody
	@RequestMapping("pre_update_department")
	public Object preUpdateDepartment(HttpServletRequest request,Department  department){
		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				request.getSession(), Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		try{
			
		   Department dept =   this.departmentService.findOne( department.getDeptId());
		   List<Department> departments = this.departmentService.findAllTopDepartments(sessionUser.getDomainId());
		   Map<String, Object> resultData = new HashMap<String, Object>();
		   resultData.put("dept", dept);
		   resultData.put("departments", departments);
		   op.setStatusCode(OPARATION_SUCCESS);
		   op.setDescription("");
		   op.setData(resultData);
		}catch(Exception  e){
			
			if(logger.isErrorEnabled()){
				
				logger.error("部门信息拉取失败 ",e);
			}
			
			op.setStatusCode(OPARATION_FAILED);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}
	
	
	/**
	 * 更新 部门信息
	 * @param department
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update_department", method = RequestMethod.POST)
	public Object updateDepartment(Department department) {
		OperateResult op = new OperateResult();

		if (null == department) {

			op.setStatusCode(OPARATION_FAILED);
			op.setDescription("参数填写不完整哦！");
			return op;
		} else {
			try {
				Date currentDate = new Date();
				department.setUpdator("admin");
				department.setUpdateTime(currentDate);

				departmentService.updateDepartment(department);

				op.setStatusCode(OPARATION_SUCCESS);
				op.setDescription("修改成功！");

			} catch (Exception e) {

				if (logger.isErrorEnabled()) {
					logger.error("部门信息修改失败！", e);
				}
				op.setStatusCode(OPARATION_FAILED);
				op.setDescription("服务器异常！稍后再试");
			}

		}

		
		return op;

		
	}

	
	
	/**
	 * 添加部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add_department", method = RequestMethod.POST)
	public Object addDepartment(Department department,HttpSession session) {
		//ModelAndView mv = new ModelAndView("forward:pre_add");
		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		if (null == department) {

			op.setStatusCode(OPARATION_FAILED);
			op.setDescription("参数填写不完整哦！");
			return op;
		} else {
			try {
				Date currentDate = new Date();
				department.setCreator(sessionUser.getRealname());
				department.setUpdator(sessionUser.getRealname());
				department.setCreateTime(currentDate);
				department.setUpdateTime(currentDate);

				departmentService.addDepartment(department);

				op.setStatusCode(Constants.HTTP_200);
				// 显示该提示信息后 页面跳转到 用户列表页面
				op.setDescription("添加成功！");

			} catch (Exception e) {

				if (logger.isErrorEnabled()) {
					logger.error("部门添加失败", e);
				}
				op.setStatusCode(Constants.SERVER_ERROR);
				op.setDescription(Constants.SERVER_ERROR_TIP);
			}

		}

		
		return op;

		
	}

	/**
	 * 删除部门信息
	 * 
	 * @return
	 */
	public String delete() {

		return null;
	}

	}
