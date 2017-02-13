package org.ost.edge.onestong.controller.admin.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.edge.onestong.anotations.PageRequired;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.system.SystemService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ost/setting/app_version")
public class AppVersionController extends Action {
	@Autowired
	private SystemService systemService;

	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("list")
	public ModelAndView userList(HttpServletRequest request, User user,
			HttpSession session) {

		// Map<String, Object> admin = (Map<String, Object>)
		// sessionStroe(session,
		// Constants.SESSION_ADMIN);
		// String operater = String.valueOf(admin.get("login_name"));

		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		ModelAndView mv = new ModelAndView("admin/app_version/version_list");
		int totalRecords = this.systemService.findAllVersionCount();
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("versions", this.systemService.findAllVersions());
		return mv;

	}

	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object versionUpdate(@RequestParam("version_id") Integer versionId,
			@RequestParam("version_name") String versionName,
			@RequestParam("download_url") String downloadUrl,
			@RequestParam("update_now") String updateNow

	) {

		OperateResult op = new OperateResult();
		
		try{
			
			
		
		Map<String, Object> attrMap = new HashMap<String, Object>();
		if(versionId == null || versionName == null ||
				downloadUrl == null) {
			
			
			op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			op.setDescription("参数填写不完整！");
			op.setData(null);

			return op;
		}
		attrMap.put("versionName", versionName);
		attrMap.put("downloadUrl", downloadUrl);
		attrMap.put("updateNow", updateNow);
		attrMap.put("versionId", versionId);
		this.systemService.updateVersion(attrMap);
		op.setStatusCode(HTTP_200);
		op.setDescription("update success!");
		op.setData(null);
		}catch(Exception e){
			
			if(logger.isErrorEnabled()){
				
				logger.error("update version faild", e);
			}
			op.setStatusCode(HTTP_500);
			op.setDescription("update failed!");
			op.setData(null);
		}
		
		return op;

	}
}
