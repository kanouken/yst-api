package com.oz.onestong.tools;

import java.util.List;
import java.util.Map;

import com.oz.onestong.model.Page;


/**
 * 用于页面调用 分页用的控件
 * @author mac
 *
 */
@SuppressWarnings("all")
public class PageTools {

	
//	 <ul class="am-pagination">
//     <li class="am-disabled"><a href="#">«</a></li>
//     <li class="am-active"><a href="#">1</a></li>
//     <li><a href="#">2</a></li>
//     <li><a href="#">3</a></li>
//     <li><a href="#">4</a></li>
//     <li><a href="#">5</a></li>
//     <li><a href="#">»</a></li>
//   </ul>
//	
	/**
	 * <ul class="pagination">
                <li><a href="#">上一页</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li class="active"><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">下一页</a></li>
              </ul>

	 * @param g
	 * @return
	 */
	public String pages(Page g) {
		// create hidden from
//		if(g.getTotalRecords()==0){
//			return "";
//		}
		StringBuffer sb = new StringBuffer();
		
		sb.append("<form id='page' action='").append(g.getPageUrl())
				.append("' method='").append(g.getMethod()).append("' >");
		// 当前请求的页码
		sb.append("<input type='hidden' id='curPage' name='curPage' value='"+g.getCurPage()+"'/>");
		// 每页显示多少条
		//sb.append("<input type='hidden' id='perPageSum' name='perPageSum' value='"+g.getPerPageSum()+"'/>");
		// 总条数 第一次查询时取出
		//sb.append("<input type='hidden' id='totalRecords' name='totalRecords' value='"+ g.getTotalRecords()+"'/>");
		
		//sb.append("<input type='hidden' id='pageUrl' name='pageUrl' value='"+ g.getPageUrl()+"'/>");
		sb.append("<input type='hidden' id='pageCount' name='pageCount' value='"+ g.getPageCount()+"'/>");
		
		//页面参数
		
		Map<String, Object> pageAttr = g.getPageAttrs();
		if(pageAttr!= null && pageAttr.size()>0){
			Object  attr = null;
			
			for(String keys : pageAttr.keySet()){
				attr = pageAttr.get(keys);
				
				sb.append("<input type='hidden' id='"+ keys+"' name='"+keys+"' value='"+ (attr!=null?attr:"")+"'/>");
				
			}
			
		}
		
		// 计算需要多少页码 （第一次取数据时需计算 之后可以直接出前台取出）
		int pageCount = g.getPageCount();


		sb.append("<ul class=\"am-pagination\">");
		
		
		// <li><a href="#">上一页</a></li>

		sb.append("<li><a id='pre'  href=\"javascript:void(0);\">«</a>  </li> ");

		for (int i = 0; i < pageCount; i++) {
			
			
			if(i <= 9){
				if(g.getCurPage() == (i+1)){
					//class="active
					
					sb.append("<li class='am-active' ><a href=\"javascript:void(0)\" class='index'>"+(i+1)+"</a></li>");
				}else{
					sb.append("<li><a href=\"javascript:void(0)\" class='index'>"+(i+1)+"</a></li>");
					
					
				}
			} else{
				
				sb.append("<li><a href=\"javascript:void(0)\" class='index'>...</a></li>");
				break;
			}
			
		}

		//sb.append("<a href=\"#\">1</a><a href=\"#\">2</a><a href=\"#\">3</a><a href=\"#\">4</a><span class=\"no_operating\">...</span>");

		sb.append("<li><a id='next'  href=\"javascript:void(0);\">»</a></li>");

		//sb.append("</td> </tr> </table>");
		sb.append("</ul>");
		sb.append("</form>");

		return sb.toString();
	}

	/**
	 * <ul class="am-list admin-sidebar-list">
      <li><a href="index.do"><span class="am-icon-home"></span> 首页<span class="am-icon-angle-right am-fr am-margin-right"></span></a></li>
      <li><a href="$!basePath/user/list"><span class="am-icon-user"></span> 用户管理</a></li>
      <li><a href="$!basePath/department/list"><span class="am-icon-group"></span> 部门管理</a></li>
      <li><a href="admin-index.html"><span class="am-icon-cogs"></span> 权限管理</a></li>
      <li><a href="admin-index.html"><span class="am-icon-rocket"></span> 事件</a></li>
      <li><a href="admin-index.html"><span class="am-icon-bar-chart"></span> 报表</a></li>
      
    </ul>
	 * @param moudles
	 * @return
	 */
	public String generalMoudles(List<Map<String, Object>> moudles,String ctx){
		StringBuilder sb = new StringBuilder("<ul class=\"am-list admin-sidebar-list\">");
		
		for(Map<String, Object> tmp : moudles){
			sb.append("<li>").append("<a href='").append(ctx+tmp.get("moudleUri")).append("'>").append("<span class='").append(tmp.get("moudleIco")).append("'>")
			.append(" "+tmp.get("moudleName")).append("</a></li>");
		}
		
		sb.append("</ul>");
		return sb.toString();
	}
	
	
	public String test(){
		
		
		return "wocao";
	}
	
	
	/**
	 * 角色 添加 /更新 helper
	 */
	public boolean perm(List<Map<String, Object>> perms,String methodName){
		boolean flag = false;
		for(Map<String, Object> tmp : perms){
			if(methodName.equals(tmp.get("methodName").toString().trim())){
				
				flag = true;
				
			}
			
		}
		
		return flag;
	}
}
