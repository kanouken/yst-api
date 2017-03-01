package org.ost.edge.onestong.services.api.event;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.ReflectUtil;
import org.common.tools.exception.ApiException;
import org.common.tools.image.ImageUtils;
import org.ost.edge.onestong.dao.event.VisitEventMapper;
import org.ost.edge.onestong.dao.event.visit.VisitMapper;
import org.ost.edge.onestong.dao.resources.EventFileMapper;
import org.ost.edge.onestong.dao.resources.ResourceMapper;
import org.ost.edge.onestong.dao.tag.CTagMapper;
import org.ost.edge.onestong.dao.tag.PTagMapper;
import org.ost.edge.onestong.dao.tag.TagEventMapper;
import org.ost.edge.onestong.model.event.VisitEvent;
import org.ost.edge.onestong.model.event.VisitEventExample;
import org.ost.edge.onestong.model.resources.Resource;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.tag.PTag;
import org.ost.edge.onestong.model.tag.PTagExample;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.base.EventBaseService;
import org.ost.edge.onestong.tools.Constants;
import org.ost.edge.onestong.tools.ResourceHelper;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.event.VisitEvents;
import org.ost.entity.event.mapper.VisitEventEntityMapper;
import org.ost.entity.event.vo.VisitEventUpdateVo;
import org.ost.entity.project.vo.ProjectVo;
import org.ost.entity.event.vo.VisitEventCreateVo;
import org.ost.entity.event.vo.VisitEventDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 外访业务 逻辑
 * 
 * @author xnq
 * 
 */
@Service
public class VisitEventService extends EventBaseService {

	public static final Byte VISIT_FLOW_STEP_SIGNIN = 1;
	public static final Byte VISIT_FLOW_STEP_SIGNOUT = 2;

	@Autowired
	private TagEventMapper tagEventMapper;

	@Autowired
	private EventFileMapper eventFileMapper;

	@Autowired
	private VisitEventMapper visitEventMapper;

	@Autowired
	private ResourceMapper resourceMapper;

	@Autowired
	private CTagMapper cTagMapper;

	@Autowired
	private PTagMapper pTagMapper;

	/**
	 * 添加考勤事件
	 * 
	 * @param event
	 *            事件 实体
	 * @param files
	 *            图片
	 * @param pTag
	 *            项目标签
	 * @param cTag
	 *            客户标签
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Map<String, Object> addEvent(VisitEvent event, MultipartFile[] files, User user, List<CTag> cTags, PTag pTag)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> waitToInsertResourceIds = new ArrayList<Integer>();
		List<Resource> resources = new ArrayList<Resource>();
		Date currentTime = new Date();

		visitEventMapper.insertSelective(event);
		// 附件照片 可选
		if (null != files && files.length > 0) {

			for (MultipartFile file : files) {

				if (file.getSize() > 0 && !file.isEmpty()) {

					String fileName = ResourceHelper.saveFile(file, user.getEmail());

					Resource r = new Resource();
					r.setCreateTime(currentTime);
					r.setUpdateTime(currentTime);
					r.setCreator(user.getRealname());
					r.setUpdator(user.getRealname());
					r.setType(Constants.R_IMAGE);
					r.setName(fileName);
					r.setOptional1(VISIT_FLOW_STEP_SIGNIN.toString());
					// 图片需要压缩 44*3 44*
					String thumPic = new StringBuilder(fileName).insert(fileName.lastIndexOf("."), "_thum").toString();
					ImageUtils.resize(Constants.FILE_SAVE_DIR + fileName, Constants.FILE_SAVE_DIR + thumPic, 44 * 3,
							44 * 3);
					// 小图 文件名
					r.setName1(thumPic);
					resourceMapper.insertSelective(r);
					// event_files
					waitToInsertResourceIds.add(r.getResourceId());
					resources.add(r);
				}

			}
			if (CollectionUtils.isNotEmpty(waitToInsertResourceIds))
				this.eventFileMapper.insertEventAndFiles(event.getVeId(), waitToInsertResourceIds,
						VISIT_FLOW_STEP_SIGNIN);
		}
		// 客户 标签

		if (null != cTags && 0 < cTags.size()) {
			for (CTag c : cTags) {

				if (null != c.getcTId()) {
					// 添加关联

					tagEventMapper.insertCTagAndEvent(c.getcTId(), event.getVeId());

				} else {
					// 添加新的客户标签并 添加关联

					c.setCreateTime(currentTime);
					c.setUpdateTime(currentTime);
					c.setCreator(user.getRealname());
					c.setUpdator(user.getRealname());
					c.setDomainId(user.getDomainId());
					cTagMapper.insertSelective(c);
					tagEventMapper.insertCTagAndEvent(c.getcTId(), event.getVeId());
				}
			}

		}

		if (null != pTag) {

			if (null != pTag.getpTId()) {
				// 添加 项目标签关联
				tagEventMapper.insertPTagAndEvent(pTag.getpTId(), event.getVeId());
			} else {
				// 检查 客户端输入的标签是否已存在。。检查方式 字符全匹配

				PTag tmp = this.isPTagExistsJudgByName(pTag.getpTName());
				if (null != tmp) {
					// 添加关联
					tagEventMapper.insertPTagAndEvent(tmp.getpTId(), event.getVeId());

				} else {
					// 添加新的客户标签并 添加关联
					pTag.setCreateTime(currentTime);
					pTag.setUpdateTime(currentTime);
					pTag.setCreator(user.getRealname());
					pTag.setUpdator(user.getRealname());
					pTag.setDomainId(user.getDomainId());
					pTagMapper.insertSelective(pTag);
					tagEventMapper.insertPTagAndEvent(pTag.getpTId(), event.getVeId());

				}
			}

		}

		resultMap.put("event", event);
		resultMap.put("pTags", pTag);
		resultMap.put("cTags", cTags);
		resultMap.put("files", resources);

		return resultMap;
	}

	/**
	 * 
	 * @param event
	 * @param files
	 * @param user
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Map<String, Object> updateEvent(VisitEvent event, MultipartFile[] files, User user) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> waitToInsertResourceIds = new ArrayList<Integer>();
		VisitEventExample vee = new VisitEventExample();
		vee.createCriteria().andVeIdEqualTo(event.getVeId());
		Date currentTime = new Date();
		List<Resource> resources = new ArrayList<Resource>();
		visitEventMapper.updateByExampleSelective(event, vee);
		// 附件照片 可选
		if (null != files && files.length > 0) {

			for (MultipartFile file : files) {

				if (file.getSize() > 0 && !file.isEmpty()) {

					String fileName = ResourceHelper.saveFile(file, user.getEmail());

					Resource r = new Resource();
					r.setCreateTime(currentTime);
					r.setUpdateTime(currentTime);
					r.setCreator(user.getRealname());
					r.setUpdator(user.getRealname());
					r.setType(Constants.R_IMAGE);
					r.setName(fileName);
					r.setOptional1(VISIT_FLOW_STEP_SIGNOUT.toString());

					// 图片需要压缩 44*3 44*
					String thumPic = new StringBuilder(fileName).insert(fileName.lastIndexOf("."), "_thum").toString();
					ImageUtils.resize(Constants.FILE_SAVE_DIR + fileName, Constants.FILE_SAVE_DIR + thumPic, 44 * 3,
							44 * 3);
					// 小图 文件名
					r.setName1(thumPic);

					resourceMapper.insertSelective(r);
					// event_files
					waitToInsertResourceIds.add(r.getResourceId());
					resources.add(r);

				}
			}
			if (CollectionUtils.isNotEmpty(waitToInsertResourceIds))
				this.eventFileMapper.insertEventAndFiles(event.getVeId(), waitToInsertResourceIds,
						VISIT_FLOW_STEP_SIGNOUT);
		}

		resultMap.put("event", event);
		resultMap.put("files", resources);
		return resultMap;

	}

	// @Transactional(readOnly = true)
	// public VisitEvent findCompleteEntityById(String eventId){
	//
	// return this.visitEventMapper.selectByPrimaryKey(eventId);
	//
	// }

	/**
	 * 此处返回的 事件对象 包括 评论次数 点赞次数 。项目标签，客户标签，图片等
	 * 
	 * @param user
	 * @param makeDate
	 * @param rb
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findVisitRecordsByUserFilterByMonth(User user, Date start, Date end, RowBounds rb)
			throws IllegalAccessException, InvocationTargetException {
		VisitEventExample vee = new VisitEventExample();
		List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
		Calendar c = Calendar.getInstance();

		if (null == end) {
			c.setTime(start);
			c.add(Calendar.MONTH, 1);
			end = c.getTime();
		}

		vee.createCriteria().andUpdateTimeBetween(start, end).andUserIdEqualTo(user.getUserId());
		vee.setOrderByClause("update_time desc");
		List<VisitEvent> ves = visitEventMapper.selectByExample(vee, rb);

		Map<String, Object> ve_tmp = null;
		for (VisitEvent ve : ves) {
			ve_tmp = ReflectUtil.transBean2Map(ve);
			ve_tmp.put("pTags", this.tagEventMapper.selectPtagsByEventId(ve.getVeId()));
			ve_tmp.put("cTags", this.tagEventMapper.selectCtagsByEventId(ve.getVeId()));
			ve_tmp.put("files", this.eventFileMapper.selectFilesByEventId(ve.getVeId(), VISIT_FLOW_STEP_SIGNIN));
			tmp.add(ve_tmp);
		}

		return tmp;
	}

	@Override
	public void updateComment() {
		// TODO Auto-generated method stub

	}

	/**
	 * event and comments
	 * 
	 * @param eventId
	 * @return
	 */
	@Transactional(readOnly = true)
	public VisitEvent findOneEventDetailByEventId(String eventId) {

		return this.visitEventMapper.selectVisitEventAndCommentsByEventId(eventId);

	}

	/**
	 * 根据标签名字查询 是否存在该标签
	 * 
	 * @param ptName
	 * @return
	 */
	@Transactional(readOnly = true)
	public PTag isPTagExistsJudgByName(String ptName) {
		PTagExample pte = new PTagExample();

		pte.createCriteria().andPTNameEqualTo(ptName).andValidEqualTo(Constants.DATA_VALID);

		List<PTag> pts = this.pTagMapper.selectByExample(pte);

		if (CollectionUtils.isNotEmpty(pts)) {

			return pts.get(0);

		} else {

			return null;
		}

	}

	private Date configEndDate(Date _start, Byte filterType) {
		Date end = null;
		Calendar c = Calendar.getInstance();

		if (filterType == DAY) {

			c.setTime(_start);
			// 第二天（今天 明天 前天）
			c.add(Calendar.DAY_OF_WEEK, 1);
			end = c.getTime();

		} else if (filterType == WEEK) {
			c.setTime(_start);
			c.add(Calendar.WEEK_OF_MONTH, 1);
			end = c.getTime();

		} else if (filterType == MONTH) {
			c.setTime(_start);
			c.add(Calendar.MONTH, 1);
			end = c.getTime();

		} else {

			c.setTime(_start);
			// 默认是查询 1号 到下一个月 1号 的数据
			c.set(Calendar.DAY_OF_MONTH, 1);
			// Date start = _start;
			c.add(Calendar.MONTH, 1);
			end = c.getTime();

		}
		return end;

	}

	/**
	 * 
	 * 
	 * @param departemtnId
	 * @param _rDate
	 * @param filterType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndDeptartId(Integer departemtnId, Date _rDate,
			Byte filterType) {
		// Calendar c = Calendar.getInstance();
		// c.setTime(_rDate);
		// // 默认是查询 1号 到下一个月 1号 的数据
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// Date start = _rDate;
		// c.add(Calendar.MONTH, 1);
		// Date end = c.getTime();

		return this.visitEventMapper.selectShortCutReportByDepartmentAndMonth(departemtnId, _rDate,
				this.configEndDate(_rDate, filterType));

	}

	/**
	 * 
	 * 
	 * @param departemtnId
	 * @param _rDate
	 * @param filterType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndDeptartId(Integer departemtnId, Date start,
			Date end) {
		return this.visitEventMapper.selectShortCutReportByDepartmentAndMonth(departemtnId, start, end);

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndUserId(Integer userId, Date _rDate,
			Byte filterType) {
		// Calendar c = Calendar.getInstance();
		// c.setTime(_rDate);
		// // 默认是查询 1号 到下一个月 1号 的数据
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// Date start = _rDate;
		// c.add(Calendar.MONTH, 1);
		// Date end = c.getTime();
		return this.visitEventMapper.selectShortCutReportByUserAndMonth(userId, _rDate,
				this.configEndDate(_rDate, filterType));

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findShortCutReportFilterByMonthAndUserId(Integer userId, Date start, Date end) {
		return this.visitEventMapper.selectShortCutReportByUserAndMonth(userId, start, end);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findMapTrackingDetailReportFilterByMonthAndUserId(Integer userId, Date _rDate,
			Byte filterType) {
		// Calendar c = Calendar.getInstance();
		// c.setTime(_rDate);
		// // 默认是查询 1号 到下一个月 1号 的数据
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// Date start = _rDate;
		// c.add(Calendar.MONTH, 1);
		// Date end = c.getTime();
		return this.visitEventMapper.selectMapTrackingDetailReportByUserAndMonth(userId, _rDate,
				this.configEndDate(_rDate, filterType));

	}

	/**
	 * 
	 * @param userId
	 * @param date
	 */
	@Transactional(readOnly = true)
	public List<VisitEvent> findMapTrackingEventsFilterByDayAndUserId(Integer userId, Date date) {
		VisitEventExample vee = new VisitEventExample();

		Calendar start_c = Calendar.getInstance();
		start_c.setTime(date);
		start_c.set(Calendar.HOUR_OF_DAY, 0);
		start_c.set(Calendar.MINUTE, 0);
		start_c.set(Calendar.SECOND, 0);
		Date start = start_c.getTime();
		start_c.add(Calendar.DAY_OF_MONTH, 1);
		Date end = start_c.getTime();

		vee.createCriteria().andUserIdEqualTo(userId).andCreateTimeBetween(start, end);

		return this.visitEventMapper.selectByExample(vee);
	}

	@Autowired
	VisitMapper visitMapper;

	/**
	 * 创建外访
	 * 
	 * @param currentUser
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public VisitEvents createVisitEvent(User currentUser, VisitEventCreateVo vo) {
		VisitEvents ves = VisitEventEntityMapper.INSTANCE.visitEventCreateVoToVisitEvents(vo);
		ves.setCreator(currentUser.getRealname());
		ves.setUpdator(currentUser.getRealname());
		ves.setUserId(currentUser.getUserId());
		if (vo.getContactType().equals("电话")) {
			ves.setState(Byte.valueOf("2"));
		}
		this.visitMapper.insert(ves);
		return ves;
	}

	/**
	 * 外访签到
	 * 
	 * @param currentUser
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public VisitEventUpdateVo visitIn(User currentUser, VisitEventUpdateVo vo) {
		String eId = vo.geteId();
		VisitEvents ves = new VisitEvents();
		ves.setUpdateTime(new Date());
		ves.setVisitedTime(new Date());
		ves.setState(Byte.valueOf("1"));
		ves.setUpdateTime(new Date());
		ves.setUpdator(currentUser.getRealname());
		ves.setVistedLongitude(vo.getLocation().getLng());
		ves.setVisitedLatitude(vo.getLocation().getLat());
		ves.setVeId(eId);
		if (0 < this.visitMapper.updateByPrimaryKeySelective(ves)) {
			return vo;
		} else {
			throw new ApiException("外访签到失败");
		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public VisitEventUpdateVo writeResult(User currentUser, VisitEventUpdateVo vo) {
		String eId = vo.geteId();
		VisitEvents ves = new VisitEvents();
		ves.setContent(vo.getContent());
		ves.setUpdateTime(new Date());
		ves.setState(Byte.valueOf("2"));
		ves.setUpdateTime(new Date());
		ves.setUpdator(currentUser.getRealname());
		ves.setVeId(eId);
		ves.setContent(vo.getContent());
		if (0 < this.visitMapper.updateByPrimaryKeySelective(ves)) {
			return vo;
		} else {
			throw new ApiException("外访写结果失败");
		}
	}

	@Transactional(readOnly = true)
	public VisitEventDetailVo getDetail(User user, String eId) {
		VisitEvents vEvents = visitMapper.selectByPrimaryKey(eId);
		VisitEventDetailVo detailVo = VisitEventEntityMapper.INSTANCE.visitEventsToVisitEventDetailVo(vEvents);
		detailVo.setCustomer(new CustomerVo(vEvents.getCustomerId(), vEvents.getCustomerName()));
		detailVo.setProject(new ProjectVo(vEvents.getProjectId(), vEvents.getProjectName()));
		return detailVo;
	}

}
