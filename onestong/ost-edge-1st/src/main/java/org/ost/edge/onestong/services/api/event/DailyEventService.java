package org.ost.edge.onestong.services.api.event;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.image.ImageUtils;
import org.ost.edge.onestong.dao.department.DepartmentMapper;
import org.ost.edge.onestong.dao.event.DailyEventMapper;
import org.ost.edge.onestong.dao.resources.EventFileMapper;
import org.ost.edge.onestong.dao.resources.ResourceMapper;
import org.ost.edge.onestong.dao.user.UserMapper;
import org.ost.edge.onestong.model.department.Department;
import org.ost.edge.onestong.model.event.DailyEvent;
import org.ost.edge.onestong.model.event.DailyEventExample;
import org.ost.edge.onestong.model.resources.Resource;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.model.user.UserExample;
import org.ost.edge.onestong.tools.Constants;
import org.ost.edge.onestong.tools.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DailyEventService {
	private @Autowired
	ResourceMapper resourceMapper;
	private @Autowired
	EventFileMapper eventFileMapper;
	private @Autowired
	UserMapper userMapper;
	private @Autowired
	DailyEventMapper dailyEventMapper;

	private @Autowired
	DepartmentMapper departmentMapper;

	public static final byte FLOW_DEFAULT = 0;
	public static final byte FLOW_DRAFT = 1;

	/**
	 * 推送至 部门主管
	 * 
	 * @param event
	 * @param operator
	 * @param images
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void create(DailyEvent event, User operator, MultipartFile[] images)
			throws Exception {

		Date current = new Date();
		event.setCreateTime(current);
		event.setUpdateTime(current);
		event.setCreator(operator.getRealname());
		event.setEventType(Constants.EVENT_DAILY);
		event.setUpdator(operator.getRealname());
		this.dailyEventMapper.insertSelective(event);
		// 附件照片 可选
		if (null != images && images.length > 0) {

			List<Integer> waitToInsertResourceIds = new ArrayList<Integer>(
					images.length);
			List<Resource> handledResources = new ArrayList<Resource>(
					images.length);
			for (MultipartFile file : images) {

				if (file.getSize() > 0 && !file.isEmpty()) {

					String fileName = ResourceHelper.saveFile(file,
							operator.getEmail());

					Resource r = new Resource();
					r.setCreateTime(event.getCreateTime());
					r.setUpdateTime(event.getCreateTime());
					r.setCreator(operator.getRealname());
					r.setUpdator(operator.getRealname());
					r.setType(Constants.R_IMAGE);
					r.setName(fileName);
					// 图片需要压缩 44*3 44*
					String thumPic = new StringBuilder(fileName).insert(
							fileName.lastIndexOf("."), "_thum").toString();
					ImageUtils.resize(Constants.FILE_SAVE_DIR + fileName, Constants.FILE_SAVE_DIR + thumPic, 44 * 3,
							44 * 3);
					// 小图 文件名
					r.setName1(thumPic);
					resourceMapper.insertSelective(r);
					// event_files
					waitToInsertResourceIds.add(r.getResourceId());
					handledResources.add(r);
				}

			}
			if (CollectionUtils.isNotEmpty(waitToInsertResourceIds))
				this.eventFileMapper.insertEventAndFiles(event.getDeId(),
						waitToInsertResourceIds, FLOW_DEFAULT);

			// event.setResources(handledResources);
		}

		// TODO 推送至 部门主管
		// ///////
		// 推送通知
		// 草稿就不存
		//TODO 推送修改
//		if (!event.getState().equals(FLOW_DRAFT)) {
//			TransmissionTemplate template = new TransmissionTemplate();
//			template.setAppId(Constants.GETUI_APP_ID);
//			template.setAppkey(Constants.GETUI_APP_KEY);
//			template.setTransmissionType(1);
//			JSONObject obj = new JSONObject();
//			obj.put("bandKey", "event_daily");
//			obj.put("event_id", event.getDeId());
//			template.setTransmissionContent(obj.toString());
//			template.setPushInfo("查看", 1, operator.getRealname()
//					+ " 发表了一篇新的日志！", "", "", "", "", "");
//
//			MC2PushTools tools = new MC2PushTools();
//			tools.setUsers(findMyReportMan(operator));
//			tools.setTemplate(template);
//			tools.start();
//
//		}

	}

	private List<User> findMyReportMan(User user) {
		List<User> directors = new ArrayList<User>();
		UserExample ue = new UserExample();
		// FIXME 只是 回溯了 一个 level
		// 自己是部门主管 2中情况 1.要么往上一级汇报 。2要么这就是第一级
		if (user.getIsDirector().equals(Constants.IS_DEPARTMENT_DIRECTOR)) {
			// 上到上级 部门

			Department dept = this.departmentMapper.selectByPrimaryKey(user
					.getDeptId());
			ue.createCriteria().andValidEqualTo(Constants.DATA_VALID)
					.andDeptIdEqualTo(dept.getParentId())
					.andIsDirectorEqualTo(Constants.IS_DEPARTMENT_DIRECTOR)
					.andStateEqualTo(Constants.USER_NORMAL);
			directors = userMapper.selectByExample(ue);

		} else {

			ue.createCriteria().andValidEqualTo(Constants.DATA_VALID)
					.andDeptIdEqualTo(user.getDeptId())
					.andIsDirectorEqualTo(Constants.IS_DEPARTMENT_DIRECTOR);
			directors = userMapper.selectByExample(ue);

		}
		return directors;
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void update(DailyEvent event, User operator, MultipartFile[] images)
			throws Exception {

		Date current = new Date();
		// 草稿得发送时间 变成 当前时间
		event.setCreateTime(current);
		event.setUpdateTime(current);
		// event.setCreator(operator.getRealname());
		event.setEventType(Constants.EVENT_DAILY);
		event.setUpdator(operator.getRealname());
		// 草稿发布
		this.dailyEventMapper.updateByPrimaryKeySelective(event);

		// 推送通知
//TODO 推送修改
//		TransmissionTemplate template = new TransmissionTemplate();
//		template.setAppId(Constants.GETUI_APP_ID);
//		template.setAppkey(Constants.GETUI_APP_KEY);
//		template.setTransmissionType(1);
//		JSONObject obj = new JSONObject();
//		obj.put("bandKey", "event_daily");
//		obj.put("event_id", event.getDeId());
//		template.setTransmissionContent(obj.toString());
//		template.setPushInfo("查看", 1, operator.getRealname() + " 发表了一篇新的日志！",
//				"", "", "", "", "");
//		MC2PushTools tools = new MC2PushTools();
//		tools.setUsers(findMyReportMan(operator));
//		tools.setTemplate(template);
//		tools.start();

		// 附件照片 可选
		if (null != images && images.length > 0) {

			List<Integer> waitToInsertResourceIds = new ArrayList<Integer>(
					images.length);
			List<Resource> handledResources = new ArrayList<Resource>(
					images.length);
			for (MultipartFile file : images) {

				if (file.getSize() > 0 && !file.isEmpty()) {

					String fileName = ResourceHelper.saveFile(file,
							operator.getEmail());

					Resource r = new Resource();
					r.setCreateTime(event.getCreateTime());
					r.setUpdateTime(event.getCreateTime());
					r.setCreator(operator.getRealname());
					r.setUpdator(operator.getRealname());
					r.setType(Constants.R_IMAGE);
					r.setName(fileName);
					// 图片需要压缩 44*3 44*
					String thumPic = new StringBuilder(fileName).insert(
							fileName.lastIndexOf("."), "_thum").toString();
					ImageUtils.resize(Constants.FILE_SAVE_DIR + fileName, Constants.FILE_SAVE_DIR + thumPic, 44 * 3,
							44 * 3);
					// 小图 文件名
					r.setName1(thumPic);
					resourceMapper.insertSelective(r);
					// event_files
					waitToInsertResourceIds.add(r.getResourceId());
					handledResources.add(r);
				}

			}
			if (CollectionUtils.isNotEmpty(waitToInsertResourceIds))
				this.eventFileMapper.insertEventAndFiles(event.getDeId(),
						waitToInsertResourceIds, FLOW_DEFAULT);

			// event.setResources(handledResources);
		}
	}

	/**
	 * 保存草稿
	 * 
	 * @param event
	 * @param operator
	 * @param images
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createTmp(DailyEvent event, User operator,
			MultipartFile[] images) throws Exception {

		Date current = new Date();
		event.setCreateTime(current);
		event.setUpdateTime(current);
		event.setCreator(operator.getRealname());
		event.setEventType(Constants.EVENT_DAILY);
		event.setUpdator(operator.getRealname());
		this.dailyEventMapper.insertSelective(event);
		// 附件照片 可选
		if (null != images && images.length > 0) {

			List<Integer> waitToInsertResourceIds = new ArrayList<Integer>(
					images.length);
			List<Resource> handledResources = new ArrayList<Resource>(
					images.length);
			for (MultipartFile file : images) {

				if (file.getSize() > 0 && !file.isEmpty()) {

					String fileName = ResourceHelper.saveFile(file,
							operator.getEmail());

					Resource r = new Resource();
					r.setCreateTime(event.getCreateTime());
					r.setUpdateTime(event.getCreateTime());
					r.setCreator(operator.getRealname());
					r.setUpdator(operator.getRealname());
					r.setType(Constants.R_IMAGE);
					r.setName(fileName);
					// 图片需要压缩 44*3 44*
					String thumPic = new StringBuilder(fileName).insert(
							fileName.lastIndexOf("."), "_thum").toString();
					ImageUtils.resize(Constants.FILE_SAVE_DIR + fileName, Constants.FILE_SAVE_DIR + thumPic, 44 * 3,
							44 * 3);
					// 小图 文件名
					r.setName1(thumPic);
					resourceMapper.insertSelective(r);
					// event_files
					waitToInsertResourceIds.add(r.getResourceId());
					handledResources.add(r);
				}

			}
			if (CollectionUtils.isNotEmpty(waitToInsertResourceIds))
				this.eventFileMapper.insertEventAndFiles(event.getDeId(),
						waitToInsertResourceIds, FLOW_DEFAULT);

			// event.setResources(handledResources);
		}
	}

	@Transactional(readOnly = true)
	public List<DailyEvent> findByUserFilterByMonth(User user, Date start,
			Date end, RowBounds rb) throws IllegalAccessException,
			InvocationTargetException {
		DailyEventExample dee = new DailyEventExample();

		Calendar c = Calendar.getInstance();

		if (null == end) {
			c.setTime(start);
			c.add(Calendar.MONTH, 1);
			end = c.getTime();
		}
		dee.createCriteria().andUpdateTimeBetween(start, end)
				.andUserIdEqualTo(user.getUserId());
		dee.setOrderByClause("update_time desc");
		List<DailyEvent> des = this.dailyEventMapper.selectByExample(dee, rb);
		for (DailyEvent de : des) {
			// 日志图片
			de.setFiles(this.eventFileMapper.selectFilesByEventId(de.getDeId(),
					FLOW_DEFAULT));
		}
		return des;
	}

	@Transactional(readOnly = true)
	public DailyEvent findOneEventDetailByEventId(String eventId) {
		return this.dailyEventMapper
				.selectDailyEventAndCommentsByEventId(eventId);
	}

	/**
	 * 获取当天草稿日志 过去的就让他过去吧
	 * 
	 * @param user
	 * @param object
	 * @param rb
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<DailyEvent> findDraftByUserFilterCurrentDay(User user,
			Object object, RowBounds rb) {
		Calendar start_c = Calendar.getInstance();
		start_c.set(Calendar.HOUR_OF_DAY, 0);
		start_c.set(Calendar.MINUTE, 0);
		start_c.set(Calendar.SECOND, 0);
		Date start = start_c.getTime();
		start_c.add(Calendar.DAY_OF_MONTH, 1);
		Date end = start_c.getTime();

		DailyEventExample dee = new DailyEventExample();
		dee.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andStateEqualTo(FLOW_DRAFT).andCreateTimeBetween(start, end)
				.andUserIdEqualTo(user.getUserId());
		dee.setOrderByClause("update_time desc");
		List<DailyEvent> des = this.dailyEventMapper.selectByExample(dee, rb);
		List<Resource> tmpResourcs = null;
		for (DailyEvent de : des) {
			// 日志图片

			tmpResourcs = this.eventFileMapper.selectFilesByEventId(
					de.getDeId(), FLOW_DEFAULT);
			if(CollectionUtils.isNotEmpty(tmpResourcs)){
				
				de.setFiles(tmpResourcs);
			}else{
				
				de.setFiles(null);
			}
		}

		return des;
	}
}
