package org.ost.edge.onestong.controller.api.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.event.Task;
import org.ost.edge.onestong.model.event.TaskItem;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.EventCollectionService;
import org.ost.edge.onestong.services.api.event.TaskEventService;
import org.ost.edge.onestong.services.scoreSystem.LikeService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 处理任务 模块业务 restful 接口
 * 
 * @author mac
 * 
 */
@Controller
@RequestMapping("/api/taskEvent")
public class TaskEventDataApi extends Action {

	@Autowired
	private UsersService usersService;
	@Autowired
	private TaskEventService taskEventService;

	/**
	 * {taskId:"",userId:""}
	 * 
	 * @param entity
	 * @param images
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "startTask/{token}", method = RequestMethod.POST)
	public Object startTask(
			@PathVariable("token") @RequestParam("entity") String entity) {
		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		String taskId = requestAtt.getString("taskId");
		Integer userId = requestAtt.getInt("userId");
		User user = null;
		try {

			if (StringUtils.isBlank(taskId) || null == userId) {
				op.setData(null);
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid or taskId is null ! please check it again");
			}

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("开始任务 --- 未知用户");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("unkown task excutor !! please check it out later!");
				op.setData(null);
				return op;
			}

			JSONArray flowData = this.taskEventService.startUserTask(user,
					taskId);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("success to start task!");
			op.setData(flowData);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("开始任务失败", e);

			}
		}

		return op;
	}

	/**
	 * {taskId:"",userId:""}
	 * 
	 * @param entity
	 * @param images
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "doneTask/{token}", method = RequestMethod.POST)
	public Object doneTask(
			@PathVariable("token") @RequestParam("entity") String entity) {
		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		String taskId = requestAtt.getString("taskId");
		Integer userId = requestAtt.getInt("userId");
		User user = null;
		try {

			if (StringUtils.isBlank(taskId) || null == userId) {
				op.setData(null);
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid or taskId is null ! please check it again");
			}

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("完成任务 --- 未知用户");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("unkown task excutor !! please check it out later!");
				op.setData(null);
				return op;
			}

			JSONArray flowData = this.taskEventService.userDoneWithTask(user,
					taskId);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("success to end task!");
			op.setData(flowData);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("完成任务失败", e);

			}
		}

		return op;
	}

	/**
	 * {taskId:"",userId:""}
	 * 
	 * @param entity
	 * @param images
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "checkTask/{token}", method = RequestMethod.POST)
	public Object checkTask(
			@PathVariable("token") @RequestParam("entity") String entity) {
		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		String taskId = requestAtt.getString("taskId");
		Integer userId = requestAtt.getInt("userId");
		User user = null;
		try {

			if (StringUtils.isBlank(taskId) || null == userId) {
				op.setData(null);
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid or taskId is null ! please check it again");
			}

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("验收任务 --- 未知用户");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("unkown task publisher !! please check it out later!");
				op.setData(null);
				return op;
			}

			JSONArray flowData = this.taskEventService.checkTask(taskId, user);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("success to end task!");
			op.setData(flowData);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("验收任务失败", e);

			}
		}

		return op;
	}

	/**
	 * {taskId:"",userId:""} 完成待办事项
	 * 
	 * @param entity
	 * @param images
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "doneTaskItem/{token}", method = RequestMethod.POST)
	public Object userDoneTaskItem(
			@PathVariable("token") @RequestParam("entity") String entity) {
		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		Integer taskItemId = requestAtt.getInt("taskItemId");
		Integer userId = requestAtt.getInt("userId");
		User user = null;
		try {

			if (null == taskItemId || null == userId) {
				op.setData(null);
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userid or taskItemId is null ! please check it again");
			}

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("完成待办 --- 未知用户");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("unkown taskItem   executor  !! please check it out later!");
				op.setData(null);
				return op;
			}

			TaskItem item = this.taskEventService.userDoneWithTaskItem(user,
					taskItemId);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("success to end taskItem!");
			op.setData(item);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("完成待办任务失败", e);

			}
		}

		return op;
	}

	/**
	 * 
	 * {"task":{"userId":"3","taskId":"sfsfsf-sfsfsf-sfsf",
	 * "taskDesc":"","title":"" },"taskItems" :[{"cTId":"11"
	 * ,"cTName":"进行中"},{"cTId":"12"},{"cTName":"新增test"}] } 发布新得任务
	 * 
	 * @entity {event_id,excutor:"1,2,3",creator ，userId:,taskDesc： }
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "newTask/{token}", method = RequestMethod.POST)
	public Object newTask(
			@PathVariable("token") @RequestParam("entity") String entity,
			@RequestParam(required = false) MultipartFile[] images) {
		OperateResult op = new OperateResult();
		JSONObject requestAtt = JSONObject.fromObject(entity);
		JSONObject task_obj = requestAtt.getJSONObject("event");

		JSONArray taskItems_obj = null;
		if (null != requestAtt.get("taskItems")) {
			taskItems_obj = requestAtt.getJSONArray("taskItems");
		}

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd HH:mm" }));

		Task task = (Task) JSONObject.toBean(task_obj, Task.class);

		List<TaskItem> taskItems = null;
		if (null != taskItems_obj) {

			taskItems = (List<TaskItem>) JSONArray.toCollection(taskItems_obj,
					TaskItem.class);

		}
		User user = null;
		try {

			user = this.usersService.findOneById(task.getUserId());
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("发布任务 --- 未知发布者");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("unkown task publisher!! please check it out later!");
				op.setData(null);
				return op;
			}

			task.setCreator(user.getRealname());
			task.setUpdator(user.getRealname());
			// TODO fetch the single taskItem from taskItems and decide to how
			// many guys join the task
			// then store task entity presistence
			this.taskEventService.newTask(user, task,
					this.getTheExcutorsByTaskItemsDefault(taskItems),
					taskItems, images);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("new publish task  success!");
			op.setData(null);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.debug("发布任务失败", e);

			}
		}

		return op;
	}

	private @Autowired
	EventCollectionService collectionService;
	private @Autowired
	LikeService likeService;

	/**
	 * 
	 * @param token
	 * @param eventId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "detail/{eventId}/{token}", method = RequestMethod.GET)
	public Object detail(@PathVariable("token") String token,
			@PathVariable("eventId") String eventId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OperateResult op = new OperateResult();
		Task task = null;
		try {
			task = this.taskEventService.detailTask(eventId);
			task.setFiles(this.collectionService.findEventRelatedFiles(
					task.getTaskid(), null));
			op.setStatusCode(HTTP_200);
			resultMap.put("event", task);
			//获取执行人
			List<User> executors =  this.taskEventService.findAllExecutorsByTaskId(eventId);
			resultMap.put("executors", executors);
			op.setData(resultMap);
			// like and creator info espidally pic
			User user = this.usersService.findOneById(task.getUserId());
			resultMap.put("author", user);
			// like
			resultMap.put("likes",
					this.likeService.findLikeInfosByEvent(task.getTaskid()));
			op.setDescription("find task detail success!");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取任务详细失败！", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

	/**
	 * 获取 任务的所有待办事项
	 * 
	 * @param token
	 * @param eventId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "taskItems/{eventId}/{token}", method = RequestMethod.GET)
	public Object findAllTaskItems(@PathVariable("token") String token,
			@PathVariable("eventId") String taskId) {
		OperateResult op = new OperateResult();
		try {

			op.setStatusCode(Constants.HTTP_200);
			op.setData(this.taskEventService.findTaskItemsByTask(taskId));
			op.setDescription("fetch taskItems success!");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("获取任务待办列表失败！", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}
		return op;
	}

	
	

	/**
	 * 返回我指派的任务
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "iPublishTask/{userId}/{curPage}/{perPageSum}/{token}", method = RequestMethod.GET)
	public Object myTask(@PathVariable(value = "userId") Integer userId,
	@PathVariable("curPage") Integer curPage,
			@PathVariable("perPageSum") Integer perPageSum,
			@PathVariable("token") String token) {
		OperateResult op = new OperateResult();
		User user = null;
		try {

			if (0 > curPage) {

				curPage = 1;

			} else if (0 > perPageSum) {
				// 默认 为10条
				perPageSum = Constants.PAGESIZE_APP;

			}
			Page page = new Page();
			page.setCurPage(curPage);
			page.setPerPageSum(perPageSum);
			RowBounds rb = new RowBounds(page.getNextPage(),
					page.getPerPageSum());

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("获取我指派的任务 失败 --- 用户不存在");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user can not found!");
				return op;
			}

			List<Integer> attr = this.taskEventService.findTaskIPublishedOrIExecuted(user.getUserId());
			List<Task> tasks =  this.taskEventService.findAllMyTaskPaged(user,rb);
			long day = 0;
			long hour = 0;
			long min = 0;
			long sec = 0;
			for(Task t :tasks){
				
				
				
				 long time1 = t.getPlanFinishDate().getTime();  
		            long time2 = new Date().getTime();  
		            long diff ;  
		            if(time1<time2) {  
		                diff = time2 - time1;  
		            } else {  
		                //已经逾期了  如果是未完成 需要显示 延期，完成了就不显示好了
		            	diff = time1 - time2;  
		            }  
		            day = diff / (24 * 60 * 60 * 1000);  
		            hour = (diff / (60 * 60 * 1000) - day * 24);  
		            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
		            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
				
		            if(t.getState() == TaskEventService.TASK_FLOW_STEP_WAIT_DONE){
		            	//已完成
		            	
		            }else{
		            	//没有完成既延期
		            	t.setOptional1("延期"+day+"天"+hour+"小时");
		            }
		            
			}
			
			
			
			
			Map<String, Object> req = new HashMap<String, Object>();
			
			req.put("tipCount", attr);
			req.put("tasks", tasks);
			
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("pull my tasks succeed  !");
			op.setData(req);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error(" 获取我的任务  失败 用户:" + user.getRealname(),e);
			}

		}
		return op;
	}
	

	/**
	 * 返回我执行的任务
	 * 
	 * @param user
	 * @param token
	 * @param month
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "executionTask/{userId}/{curPage}/{perPageSum}/{token}", method = RequestMethod.GET)
	public Object myExecutionTask(@PathVariable(value = "userId") Integer userId,
	@PathVariable("curPage") Integer curPage,
			@PathVariable("perPageSum") Integer perPageSum,
			@PathVariable("token") String token) {
		OperateResult op = new OperateResult();
		User user = null;
		try {

			if (0 > curPage) {

				curPage = 1;

			} else if (0 > perPageSum) {
				// 默认 为10条
				perPageSum = Constants.PAGESIZE_APP;

			}
			Page page = new Page();
			page.setCurPage(curPage);
			page.setPerPageSum(perPageSum);
			RowBounds rb = new RowBounds(page.getNextPage(),
					page.getPerPageSum());

			user = this.usersService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("获取我的任务（执行或者发布的） 失败 --- 用户不存在");

				}
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("user can not found!");
				return op;
			}

			List<Integer> attr = this.taskEventService.findTaskIPublishedOrIExecuted(user.getUserId());
			List<Task> tasks =  this.taskEventService.findAllMyExecutionTaskPaged(user,rb);
			Map<String, Object> req = new HashMap<String, Object>();
			
			req.put("tipCount", attr);
			req.put("tasks", tasks);
			
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("pull my tasks succeed  !");
			op.setData(req);
		} catch (Exception e) {
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setData(Constants.SERVER_ERROR_TIP);
			op.setData(null);
			if (logger.isErrorEnabled()) {
				logger.error(" 获取我的任务  失败 用户:" + user.getRealname(),e);
			}

		}
		return op;
	}
	
	
	
	/**
	 * get the excutors by the default taskItems were published by the publisher
	 * 
	 * @param taskItems
	 */
	private List<User> getTheExcutorsByTaskItemsDefault(List<TaskItem> taskItems) {

		List<User> executor = new ArrayList<User>();

		for (TaskItem taskItem : taskItems) {

			executor.add(this.usersService.findOneById(taskItem.getExcutorId()));
		}
		return executor;
	}

}
