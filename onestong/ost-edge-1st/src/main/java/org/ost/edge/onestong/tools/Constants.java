package org.ost.edge.onestong.tools;


/**
 * 常量
 * 
 * @author mac
 * 
 */
public class Constants {
	public static String APP_VERSION = "0.5";
	// 工作默认开始时间
	public static String startTime = "09:00";
	// 工作默认结束时间
	public static String endTime = "18:00";

//	// API_HTTP_SCHEMA
//	public static String API_HTTP_SCHEMA = "https";
//	// API_SERVER_HOST
//	public static String API_SERVER_HOST = PropertiesUtils.getProperties()
//			.getProperty("API_SERVER_HOST");
//	// APPKEY
//	public static String APPKEY = PropertiesUtils.getProperties().getProperty(
//			"APPKEY");
//	// APP_CLIENT_ID
//	public static String APP_CLIENT_ID = PropertiesUtils.getProperties()
//			.getProperty("APP_CLIENT_ID");
//	// APP_CLIENT_SECRET
//	public static String APP_CLIENT_SECRET = PropertiesUtils.getProperties()
//			.getProperty("APP_CLIENT_SECRET");
//	// ORG_ADMIN_USERNAME
//	public static String ORG_ADMIN_USERNAME = PropertiesUtils.getProperties()
//			.getProperty("ORG_ADMIN_USERNAME");
//	// ORG_ADMIN_PASSWORD
//	public static String ORG_ADMIN_PASSWORD = PropertiesUtils.getProperties()
//			.getProperty("ORG_ADMIN_PASSWORD");
//	// DEFAULT_PASSWORD
//	public static String DEFAULT_PASSWORD = "1234456";
	
	
	/**
	 * 个推 属性
	 * 
	 */
//	public static String GETUI_APP_ID = PropertiesUtils.getGeXinPushProperties().getProperty("APPID");
//	public static String GETUI_APP_KEY = PropertiesUtils.getGeXinPushProperties().getProperty("APPKEY");
//	public static String GETUI_APP_MASTER = PropertiesUtils.getGeXinPushProperties().getProperty("MASTER");
//	public static String GETUI_HOST = PropertiesUtils.getGeXinPushProperties().getProperty("HOST");
	
	
	
	public static final String FILE_SAVE_DIR = "";
//	
//	
	
	/**
	 * 删除字段
	 */
	public static final byte DATA_VALID = 0;
	public static final byte DATA_INVALID = 1;
	/**
	 * 用户状态 正常 或者 禁用
	 */
	public static final byte USER_NORMAL = 0;
	public static final byte USER_DISABLE = 1;
	public static final String USER_CONFIRM_IN_CORRECT = "u_c_i_c";


	/**
	 * 分页
	 */
	public static final int PAGESIZE = 10;
	public static final int PAGESIZE_APP = 10;

	
	
	/**
	 * 事件  
	 * 
	 */
	/**
	 * 考勤
	 */
	//考勤状态
		public static final String SIGN_STATUS_KEY = "signStatus";
		public static final String SIGN_EVENT_KEY = "signEvent";
		public static final byte NEITHER_SIGNIN_NOR_SIGNOUT = 0;//没有签到
		
		
		public static final byte SIGNOUT_BUT_NOT_SIGNIN = 2;//签退 但是 没有签到
		public static final byte SIGNIN_BUT_NOT_SIGNOUT = 1;//没有签退
		public static final byte BOTH_SIGNIN_AND_SIGNOUT = 3;//既签到又签退
		
		public static final String ALREADY_SIGN = "-1"; //存在签到记录 
		public static final String SIGN_OUT_NORMAL_STATUS_TIP = "签退正常";  
		public static final String SIGN_IN_NORMAL_STATUS_TIP = "签到正常";  
		public static final String SIGN_IN_LATER_STATUS_TIP = "迟到min分钟"; 
		public static final String SIGN_OUT_LEAVE_STATUS_TIP = "早退min分钟"; 
		public static final String SIGN_IN_SUCCESS = "signin_success"; 
		
		public static final String SIGN_OUT_SUCCESS = "signout_success"; 
		public static final String IBEACON_NOT_SETUP = "g_ib_not_set"; 

	/**
	 * 外访状态  	
	 */
		
		public static final byte VISIT_ING = 0;
		public static final byte VISIT_END = 1;
		
	/**
	 * 资源类型 
	 * 
	 */
		public static final byte R_IMAGE = 0;
		public static final byte R_VEDIO = 1;
		public static final byte R_AUDIO = 2;
		
		/**
		 * 部门 
		 */
		
		public static final byte IS_NOT_LEAF = 0;
		public static final byte IS_LEAF = 1;
		
	/**
	 * 客户	
	 */
		public static final byte IS_PUBLIC = 1;
		public static final byte IS_NOT_PUBLIC = 0;
	/**
	 * 全局  消息码
	 */
		
		public static final String ACTIVE_CODE_INVALID = "g_a_c_invalid"; //用户未找到
		public static final String USER_NOT_FOUND = "g_u_notexist"; //用户未找到
		public static final String SERVER_ERROR = "500"; //服务器异常
		public static final String SERVER_ERROR_TIP = "server has gone away just now , please try again later !"; //服务器异常
		public static final String TOKEN_INVALID = "g_t_invalid"; //token 无效
		public static final String PARAMTERS_NOT_COMPLETE = "g_p_not_c"; //参数提供不完整 
		public static final String HTTP_200 = "200"; //请求正常返回
		public static final String ROLE_NOT_FOUND = "g_r_notexist";  //角色未设置
		public static final String SESSION_USER = "entity";  //企业 员工登陆
		public static final String SESSION_ADMIN = "admin";  //平台管理员登陆
		public static final String SESSION_PERM_TIP = "perms";  //平台管理员登陆
		
		
		//public static final String SESSION_ENTERPRISE = "enterprise";  //企业账户登陆
		//public static final String SESSION_DOMAIN_SUPER_USER = "domain";  //角色未设置
		
	/**
	 * 事件类型 
	 */
		public static final byte EVENT_ATTENCE = 1;
		public static final byte EVENT_VISIT = 2;
		
		public static final byte EVENT_DAILY = 3;
		public static final byte EVENT_TASK = 4;
		
		
//		public static final byte EVENT_ = 1;
//		public static final byte EVENT_ATTENCE = 1;
//		public static final byte EVENT_ATTENCE = 1;
		/**
		 * 用户
		 */
		public static final Byte IS_DEPARTMENT_DIRECTOR = 1;
		public static final Byte IS_NOT_DEPARTMENT_DIRECTOR = 0;
		public static final String DEVICE_ID_INVALID = "u_din";
		public static final String DEVICE_ID_ALREADT_RESGISTERED = "u_diar";
		public static final String PASSWORD_INCORRECT = "u_p_in";
		public static final String ORGINAL_PASSWORD_INCORRECT = "u_o_p_in";
		public static final String CONFIRM_PASSWORD_ERROR = "u_c_p_e";
		
		
		/**
		 * 环信 默认密码
		 */
		public static final String HX_PASSWORD_DEFALULT = "123456";
		
		/**
		 * 企业账户（域账户 ） 企业员工 
		 */
		public static final String DOMAIN_SUPER_USER = "1";
		public static final String DOMAIN_USER = "2";
		
		/**
		 * 权限 、功能模块 、
		 */
		public static final Byte MOUDLE_ADMIN = 0;//后台 模块
		public static final Byte MOUDLE_APP = 1;// app 模块
}
