package com.oz.onestong.services.authority;
/**
 * 权限描述
 * @author mac
 *
 */
public enum AuthorityType {
	
	/**
	 * 浏览权限   
	 */
	EVENT_SELFAND_BLOW_SCOPE,//对事件浏览 自己的 或者 自己下属（一级）
	EVENT_SELF,//只能查看自己的事件 
	
	/**
	 * 报表查看 
	 * 
	 */
	REPORT_SELF, //查看自己的报表
	REPORT_SELFAND_BLOW_SCOPE ,//查看自己 以及自己下属的 报表
	REPORT_ALL,
	
	/**
	 * 操作  默认拥有所有 考勤 、 外访 、日志  等权限 
	 */
	/**
	 * 评论与点赞(都不能点自己的 赞)
	 */
	COMMENT_SELF,
	
	
	
}
