//package com.oz.onestong.services.api.event;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.gexin.rp.sdk.template.TransmissionTemplate;
//import com.oz.onestong.dao.event.TaskItemMapper;
//import com.oz.onestong.dao.event.TaskMapper;
//import com.oz.onestong.dao.event.UserTaskMapper;
//import com.oz.onestong.dao.resources.EventFileMapper;
//import com.oz.onestong.dao.resources.ResourceMapper;
//import com.oz.onestong.dao.user.UserMapper;
//import com.oz.onestong.model.event.Task;
//import com.oz.onestong.model.event.TaskExample;
//import com.oz.onestong.model.event.TaskItem;
//import com.oz.onestong.model.event.TaskItemExample;
//import com.oz.onestong.model.event.UserTask;
//import com.oz.onestong.model.event.UserTaskExample;
//import com.oz.onestong.model.resources.Resource;
//import com.oz.onestong.model.user.User;
//import com.oz.onestong.tools.Constants;
//import com.oz.onestong.tools.MC2PushTools;
//import com.oz.onestong.tools.ResourceHelper;
//import com.oz.onestong.tools.image.CompressPicDemo;
//
//@Service
//public class TaskEventService {
//
//	/**
//	 * 流程变量
//	 */
//
//	public static final Byte USER_TASK_FLOW_STEP_DEFAULT = 0; // 默认 未开始
//
//	public static final Byte USER_TASK_FLOW_STEP_START = 1; // 用户任务开始
//
//	public static final Byte USER_TASK_FLOW_STEP_DONE = 2; // 用户任务完成
//	public static final Byte TASK_FLOW_STEP_DEFAULT = 0; // 主线任务未开始
//	public static final Byte TASK_FLOW_STEP_ING = 1; // 主线任务进行中
//	public static final Byte TASK_FLOW_STEP_WAIT_CHECK = 2; // 主线任务待验收
//	public static final Byte TASK_FLOW_STEP_WAIT_DONE = 3; // 主线任务完成
//
//	public static final Byte USER_TASK_ITEM_FLOW_STEP_DEFAULT = 0; // 用户待办事项 未完成
//	public static final Byte USER_TASK_ITEM_FLOW_STEP_DONE = 1; // 用户待办事项完成
//
//	@Autowired
//	private ResourceMapper resourceMapper;
//	@Autowired
//	private UserTaskMapper userTaskMapper;
//	@Autowired
//	private TaskMapper taskMapper;
//
//	@Autowired
//	private TaskItemMapper taskItemMapper;
//
//	@Autowired
//	private EventFileMapper eventFileMapper;
//
//	// @Transactional(rollbackFor = { Exception.class }, propagation =
//	// Propagation.REQUIRED)
//
//	/**
//	 * 返回单条任务详细
//	 * 
//	 * @param taskId
//	 */
//	@Transactional(readOnly = true)
//	public void findTaskByTaskId(String taskId) {
//
//	}
//	
//	/**
//	 * 返回任务所有的待办详细
//	 * 
//	 * @param taskId
//	 */
//	@Transactional(readOnly = true)
//	public List<TaskItem> findTaskItemsByTask(String taskId) {
//		
//		TaskItemExample tie  = new TaskItemExample();
//		tie.createCriteria().andTaskIdEqualTo(taskId);
//		tie.setOrderByClause("update_time desc");
//		
//		return  this.taskItemMapper.selectByExample(tie);
//	}
//
//	
//
//	/**
//	 * 用户id 返回 他参与得任务 (完成 与 进行中)
//	 * 
//	 * @param userId
//	 */
//	@Transactional(readOnly = true)
//	public void findAllTaskByUserId(Integer userId) {
//
//	}
//
//	/**
//	 * 获取任务的参与者
//	 * 
//	 * @param taskId
//	 * @return
//	 */
//	public List<User> findAllExecutorsByTaskId(String taskId) {
//
//		return this.userTaskMapper.selectUsersByTaskId(taskId);
//
//	}
//
//	/**
//	 * 用户id 返回 他参与得任务 (进行中)
//	 * 
//	 * @param userId
//	 */
//	@Transactional(readOnly = true)
//	public void findIngTaskByUserId(Integer userId) {
//
//	}
//
//	private @Autowired
//	UserMapper userMapper;
//
//	/**
//	 * 用户开始执行任务
//	 * 
//	 * @param excutor
//	 * @param task
//	 * @throws Exception
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public JSONArray startUserTask(User excutor, String taskId)
//			throws Exception {
//		DateFormat df = new SimpleDateFormat("MM月-dd日 HH:mm");
//		Map<String, Object> currentFlow = new HashMap<String, Object>();
//		Date now = new Date();
//		// 修改 usertask 表记录
//
//		UserTask ut = new UserTask();
//		ut.setState(USER_TASK_FLOW_STEP_START);
//		ut.setUpdateTime(now);
//		// 开始时间
//		ut.setStartTime(now);
//		ut.setUpdateTime(now);
//		UserTaskExample ute = new UserTaskExample();
//		ute.createCriteria().andUserIdEqualTo(excutor.getUserId())
//				.andTaskIdEqualTo(taskId);
//
//		this.userTaskMapper.updateByExampleSelective(ut, ute);
//
//		Task waitToStartTask = this.taskMapper.selectByPrimaryKey(taskId);
//
//		JSONObject oldJson = JSONObject.fromObject(waitToStartTask
//				.getTaskDesc());
//
//		JSONArray flowData = oldJson.getJSONArray("flowData");
//
//		currentFlow.put("executorId", excutor.getUserId());
//		currentFlow.put("executionDesc", excutor.getRealname() + ":接收了任务");
//		currentFlow.put("executeTime", df.format(now));
//		currentFlow.put("executeFlow", USER_TASK_FLOW_STEP_START);
//
//		flowData.add(currentFlow);
//		oldJson.put("flowData", flowData);
//
//		waitToStartTask.setTaskDesc(oldJson.toString());
//		waitToStartTask.setState(TASK_FLOW_STEP_ING);
//		waitToStartTask.setUpdateTime(now);
//		waitToStartTask.setUpdator(excutor.getRealname());
//		// 流程数据
//
//		this.taskMapper.updateByPrimaryKeySelective(waitToStartTask);
//
//		User publisher = this.userMapper.selectByPrimaryKey(waitToStartTask
//				.getUserId());
//
//		List<User> _executors = this.findAllExecutorsByTaskId(waitToStartTask
//				.getTaskid());
//		_executors.add(publisher);
//		// 通知任务发布者与执行者
//		// TODO
//		// 通知发布者
//		TransmissionTemplate template = new TransmissionTemplate();
//		template.setAppId(Constants.GETUI_APP_ID);
//		template.setAppkey(Constants.GETUI_APP_KEY);
//		template.setTransmissionType(1);
//		template.setTransmissionContent("test");
//		template.setPushInfo("查看", 2, excutor.getRealname() + " 开始了任务："
//				+ oldJson.getString("taskDesc"), "", "", "", "", "");
//		// 通知执行者 与参与者
//		MC2PushTools tools = new MC2PushTools();
//		tools.setUsers(_executors);
//		tools.setTemplate(template);
//		tools.start();
//		
//		return flowData;
//	}
//
//	/**
//	 * 判断 是否 每个执行者都完成了任务
//	 * 
//	 * @param taskId
//	 * @return
//	 */
//	private boolean isTaskShouldTransToBeChecked(String taskId) {
//		UserTaskExample ute = new UserTaskExample();
//		ute.createCriteria().andTaskIdEqualTo(taskId)
//				.andStateNotEqualTo(USER_TASK_FLOW_STEP_DONE);
//		Integer leave = this.userTaskMapper.countByExample(ute);
//		if(leave == 0){
//			return true;
//			
//		}else{
//			
//			return false;
//			
//		}
//
//	}
//
//	/**
//	 * 用户完成任务
//	 * 
//	 * @param excutor
//	 * @param task
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public JSONArray userDoneWithTask(User excutor, String taskId) throws Exception {
//
//		DateFormat df = new SimpleDateFormat("MM月-dd日 HH:mm");
//		Map<String, Object> currentFlow = new HashMap<String, Object>();
//		Date now = new Date();
//		// 修改 usertask 表记录
//
//		UserTask ut = new UserTask();
//		ut.setState(USER_TASK_FLOW_STEP_DONE);
//		ut.setUpdateTime(now);
//		// 完成时间
//		ut.setEndTime(now);
//		ut.setUpdateTime(now);
//		UserTaskExample ute = new UserTaskExample();
//		ute.createCriteria().andUserIdEqualTo(excutor.getUserId())
//				.andTaskIdEqualTo(taskId);
//
//		this.userTaskMapper.updateByExampleSelective(ut, ute);
//
//		Task waitToEndTask = this.taskMapper.selectByPrimaryKey(taskId);
//
//		JSONObject oldJson = JSONObject.fromObject(waitToEndTask.getTaskDesc());
//
//		JSONArray flowData = oldJson.getJSONArray("flowData");
//
//		currentFlow.put("executorId", excutor.getUserId());
//		currentFlow.put("executionDesc", excutor.getRealname() + ":完成了任务");
//		currentFlow.put("executeTime", df.format(now));
//		currentFlow.put("executeFlow", USER_TASK_FLOW_STEP_DONE);
//
//
//		flowData.add(currentFlow);
//		oldJson.put("flowData", flowData);
//
//		waitToEndTask.setTaskDesc(oldJson.toString());
//
//		// 查看其他人是否完成任务
//		if (isTaskShouldTransToBeChecked(waitToEndTask.getTaskid())) {
//			waitToEndTask.setState(TASK_FLOW_STEP_WAIT_CHECK);
//		}//否则还是在进行中
//		waitToEndTask.setUpdateTime(now);
//		waitToEndTask.setUpdator(excutor.getRealname());
//		// 流程数据
//
//		this.taskMapper.updateByPrimaryKeySelective(waitToEndTask);
//
//		User publisher = this.userMapper.selectByPrimaryKey(waitToEndTask
//				.getUserId());
//		List<User> _executors = this.findAllExecutorsByTaskId(waitToEndTask
//				.getTaskid());
//		_executors.add(publisher);
//		// 通知任务发布者与执行者
//		// TODO
//		// 通知发布者
//		TransmissionTemplate template = new TransmissionTemplate();
//		template.setAppId(Constants.GETUI_APP_ID);
//		template.setAppkey(Constants.GETUI_APP_KEY);
//		template.setTransmissionType(1);
//		template.setTransmissionContent("test");
//		template.setPushInfo("查看", 2, excutor.getRealname() + " 完成了任务："
//				+ oldJson.getString("taskDesc"), "", "", "", "", "");
//		MC2PushTools tools = new MC2PushTools();
//		tools.setUsers(_executors);
//		tools.setTemplate(template);
//		tools.start();
//		return flowData;
//		
//		
//	}
//
//	
//	/**
//	 * 用户完成待办
//	 * 
//	 * @param excutor
//	 * @param task
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public TaskItem userDoneWithTaskItem(User excutor, Integer taskItemId) throws Exception {
//
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Date now = new Date();
//		TaskItem item  =new TaskItem();
//		
//		item.setTaskItemId(taskItemId);
//		item.setUpdateTime(now);
//		item.setState(USER_TASK_ITEM_FLOW_STEP_DONE);
//		item.setUpdator(excutor.getRealname());
//		this.taskItemMapper.updateByPrimaryKeySelective(item);
//		item.setOptional1(df.format(now));
//
////		// 通知任务发布者与执行者
////		// TODO
////		// 通知发布者
////		TransmissionTemplate template = new TransmissionTemplate();
////		template.setAppId(Constants.GETUI_APP_ID);
////		template.setAppkey(Constants.GETUI_APP_KEY);
////		template.setTransmissionType(1);
////		template.setTransmissionContent("test");
////		template.setPushInfo("查看", 2, excutor.getRealname() + " 完成了任务："
////				+ oldJson.getString("taskDesc"), "", "", "", "", "");
////
////		pushtoSingle.PUSH(publisher.getcId(), template);
////		// 通知执行者
////
////		for (User executor : this.findAllExecutorsByTaskId(waitToEndTask
////				.getTaskid())) {
////
////			if (StringUtils.isNotBlank(executor.getcId()))
////				template.setPushInfo("查看", 2, executor.getRealname()
////						+ " 完成了任务：" + oldJson.getString("taskDesc"), "", "",
////						"", "", "");
////			pushtoSingle.PUSH(executor.getcId(), template);
////		}
//
//		return item;
//		
//	}
//	
//	
//	/**
//	 * 新任务
//	 * 
//	 * @param task
//	 *            任务
//	 * @param excutors
//	 *            执行人
//	 * @throws Exception
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void newTask(User creator, Task task, List<User> executors,
//			List<TaskItem> taskItems, MultipartFile[] files) throws Exception {
//		JSONObject content = new JSONObject();
//		JSONArray flowData = new JSONArray();
//		Date now = new Date();
//		DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
//		task.setCreateTime(now);
//		task.setUpdateTime(now);
//		// 任务默认状态
//		task.setState(TASK_FLOW_STEP_DEFAULT);
//		// {}
//		content.put("taskDesc", task.getTaskDesc());
//		content.put("taskTip",
//				"由" + executors.get(0).getRealname() + "等" + executors.size()
//						+ "人执行,应于" + df.format(task.getPlanFinishDate())
//						+ "前完成");
//		content.put("flowData", flowData);
//
//		// 插入任务 详细
//
//		task.setTaskDesc(content.toString());
//		this.taskMapper.insertSelective(task);
//		// 关联执行人用户
//
//		UserTask ut = null;
//		for (User executor : executors) {
//			ut = new UserTask();
//			ut.setUserId(executor.getUserId());
//			ut.setTaskId(task.getTaskid());
//			ut.setCreateTime(now);
//			ut.setUpdateTime(now);
//			ut.setExcutor(executor.getRealname());
//
//			// 用户任务 初始化状态 0
//			ut.setState(USER_TASK_FLOW_STEP_DEFAULT);
//			this.userTaskMapper.insertSelective(ut);
//		}
//
//		// 添加待办事项
//		if (CollectionUtils.isNotEmpty(taskItems)) {
//			for (TaskItem item : taskItems) {
//				item.setCreateTime(now);
//				item.setUpdateTime(now);
//				item.setState(USER_TASK_ITEM_FLOW_STEP_DEFAULT);
//				item.setTaskId(task.getTaskid());
//				this.taskItemMapper.insertSelective(item);
//			}
//
//		}
//
//		List<Integer> waitToInsertResourceIds = new ArrayList<Integer>();
//		List<Resource> resources = new ArrayList<Resource>();
//		// 附件照片 可选
//		if (null != files && files.length > 0) {
//
//			for (MultipartFile file : files) {
//
//				if (file.getSize() > 0 && !file.isEmpty()) {
//
//					String fileName = ResourceHelper.saveFile(file,
//							creator.getEmail());
//
//					Resource r = new Resource();
//					r.setCreateTime(now);
//					r.setUpdateTime(now);
//					r.setCreator(creator.getRealname());
//					r.setUpdator(creator.getRealname());
//					r.setType(Constants.R_IMAGE);
//					r.setName(fileName);
//					// r.setOptional1(VISIT_FLOW_STEP_SIGNIN.toString());
//					// 图片需要压缩 44*3 44*
//					String thumPic = new StringBuilder(fileName).insert(
//							fileName.lastIndexOf("."), "_thum").toString();
//					CompressPicDemo compress = new CompressPicDemo();
//					compress.compressPic(Constants.FILE_SAVE_DIR,
//							Constants.FILE_SAVE_DIR, fileName, thumPic, 44 * 3,
//							44 * 3, true);
//					// 小图 文件名
//					r.setName1(thumPic);
//					resourceMapper.insertSelective(r);
//					// event_files
//					waitToInsertResourceIds.add(r.getResourceId());
//					resources.add(r);
//				}
//
//			}
//			if (CollectionUtils.isNotEmpty(waitToInsertResourceIds))
//				this.eventFileMapper.insertEventAndFiles(task.getTaskid(),
//						waitToInsertResourceIds, Byte.valueOf("0"));
//		}
//
//		// 推送通知
//
//		TransmissionTemplate template = new TransmissionTemplate();
//		template.setAppId(Constants.GETUI_APP_ID);
//		template.setAppkey(Constants.GETUI_APP_KEY);
//		template.setTransmissionType(1);
//		JSONObject obj = new JSONObject();
//		obj.put("bandKey", "event");
//		template.setTransmissionContent(obj.toString());
//		template.setPushInfo("查看", 2, task.getCreator() + " 发布了一条新的任务！", "",
//				"", "", "", "");
//		MC2PushTools tools = new MC2PushTools();
//		tools.setUsers(executors);
//		tools.setTemplate(template);
//		tools.start();
//	}
//
//	
//	
//	
//	/**
//	 * 验收任务
//	 * 
//	 * @param task
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public JSONArray checkTask(String taskId,User publisher) throws Exception {
//		DateFormat df = new SimpleDateFormat("MM月-dd日 HH:mm");
//		Map<String, Object> currentFlow = new HashMap<String, Object>();
//		Date now = new Date();
//		
//		
//		Task waitToCheckTask = this.taskMapper.selectByPrimaryKey(taskId);
//
//		JSONObject oldJson = JSONObject.fromObject(waitToCheckTask
//				.getTaskDesc());
//
//		JSONArray flowData = oldJson.getJSONArray("flowData");
//
//		currentFlow.put("executorId", publisher.getUserId());
//		currentFlow.put("executionDesc", publisher.getRealname() + ":验收了任务");
//		currentFlow.put("executeTime", df.format(now));
//		currentFlow.put("executeFlow", TASK_FLOW_STEP_WAIT_DONE);
//
//		flowData.add(currentFlow);
//		oldJson.put("flowData", flowData);
//
//		waitToCheckTask.setTaskDesc(oldJson.toString());
//		waitToCheckTask.setState(TASK_FLOW_STEP_WAIT_DONE);
//		waitToCheckTask.setUpdateTime(now);
//		waitToCheckTask.setUpdator(publisher.getRealname());
//		// 流程数据
//
//		this.taskMapper.updateByPrimaryKeySelective(waitToCheckTask);
//
//		// 通知执行人
//		TransmissionTemplate template = new TransmissionTemplate();
//		template.setAppId(Constants.GETUI_APP_ID);
//		template.setAppkey(Constants.GETUI_APP_KEY);
//		template.setTransmissionType(1);
//		template.setTransmissionContent("test");
//		template.setPushInfo("查看", 2, publisher.getRealname() + " 验收了任务："
//				+ oldJson.getString("taskDesc"), "", "", "", "", "");
//		MC2PushTools tools = new MC2PushTools();
//		tools.setUsers(this.findAllExecutorsByTaskId(waitToCheckTask
//				.getTaskid()));
//		tools.setTemplate(template);
//		tools.start();
//		return flowData;
//		
//		
//		
//	}
//
//	/**
//	 * 添加用户代办事项
//	 * 
//	 * @param taskItems
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void addUserTaskItem(List<TaskItem> taskItems) {
//
//	}
//
//	/**
//	 * 更新用户代办事项 状态
//	 * 
//	 * @param taskItem
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void doneOrUnDoneUserTaskItem(TaskItem taskItem) {
//
//	}
//
//	/**
//	 * 任务 详细
//	 * 
//	 * @param taskId
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public Task detailTask(String taskId) {
//
//		return this.taskMapper.selectTaskEventAndCommentsByTaskId(taskId);
//	}
//
//	/**
//	 * 分页获取我指派的任务
//	 * @param user
//	 * @param rb
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public List<Task> findAllMyTaskPaged(User user, RowBounds rb) {
//		TaskExample te = new TaskExample();
//		te.createCriteria().andUserIdEqualTo(user.getUserId()).andValidEqualTo(Constants.DATA_VALID);
//		te.setOrderByClause("update_time desc");
//		
//		return this.taskMapper.selectByExample(te,rb);
//		
//	}
//
//	
//	
//	
//	
//	/**
//	 * 分页获取派给我的任务
//	 * @param user
//	 * @param rb
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public List<Task> findAllMyExecutionTaskPaged(User user, RowBounds rb) {
//		return 	this.taskMapper.selectMyExecutionTaskPaged(user.getUserId(),rb);
//				
//		
//	}
//
//	
//	
//	
//	
//	/**
//	 * 0 . execute 1. i published
//	 * @return
//	 */
//	public List<Integer> findTaskIPublishedOrIExecuted(Integer userId){
//		
//	return 	this.taskMapper.selectTaskIPublishedOrIExecuted(userId);
//		
//	}
//	
//	
//	
//}
