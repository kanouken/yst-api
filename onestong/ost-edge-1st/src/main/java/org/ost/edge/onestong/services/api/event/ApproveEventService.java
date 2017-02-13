package org.ost.edge.onestong.services.api.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.image.ImageUtils;
import org.ost.edge.onestong.dao.event.ApproveEventMapper;
import org.ost.edge.onestong.dao.event.ApproveFlowMapper;
import org.ost.edge.onestong.dao.resources.EventFileMapper;
import org.ost.edge.onestong.dao.resources.ResourceMapper;
import org.ost.edge.onestong.dao.user.UserMapper;
import org.ost.edge.onestong.model.event.ApproveEvent;
import org.ost.edge.onestong.model.event.ApproveEventExample;
import org.ost.edge.onestong.model.event.ApproveFlow;
import org.ost.edge.onestong.model.event.ApproveFlowExample;
import org.ost.edge.onestong.model.resources.Resource;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.tools.Constants;
import org.ost.edge.onestong.tools.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ApproveEventService {

	private @Autowired
	ApproveEventMapper approveEventMapper;
	private @Autowired
	ApproveFlowMapper approveFlowMapper;
	private @Autowired
	ResourceMapper resourceMapper;
	private @Autowired
	EventFileMapper eventFileMapper;
	private @Autowired
	UserMapper userMapper;

	public static final byte FLOW_DEFAULT = 0;

	public enum APPROVE {
		INIT("待审批", Byte.valueOf("0")), ACCEPT("同意", Byte.valueOf("1")), IGNOR(
				"不同意", Byte.valueOf("2")), REIMBURSEMENT("报销审批", Byte
				.valueOf("5")), LEAVE("请假审批", Byte.valueOf("6")), BUSINESS_TRIP(
				"出差审批", Byte.valueOf("7"));

		// 成员变量
		private String name;
		private byte index;

		// 构造方法
		private APPROVE(String name, byte index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (APPROVE c : APPROVE.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		// get set 方法
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(byte index) {
			this.index = index;
		}
	}

	/**
	 * 
	 * @param event
	 *            审批事件实体
	 * @param images
	 *            审批单据的副本（由客户端拍照上传）
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public ApproveEvent createApproval(User operator, ApproveEvent event,
			MultipartFile[] images) throws Exception {
		Date current = new Date();
		event.setCreateTime(current);
		event.setUpdateTime(current);
		event.setCreator(operator.getRealname());
		event.setUpdator(operator.getRealname());

		// varible
		// {"currentTaskExcutor":"",“apply.suggestion”:"生
		// 病不舒服",day:5天，"startTime":"2012/01/05"}
		JSONArray flowData = new JSONArray();
		JSONObject one = new JSONObject();
		one.put("currentTaskExcutor", "");
		one.put("apply.suggestion", event.getContent());
		one.put("method", APPROVE.INIT.index);

		JSONObject two = new JSONObject();
		// 由谁审批
		two.put("currentTaskExcutor",
				this.userMapper.selectByPrimaryKey(
						event.getCurApprovalExcutor()).getRealname());
		two.put("apply.suggestion", "");
		two.put("method", APPROVE.INIT.index);
		flowData.add(one);
		flowData.add(two);

		event.setVariable(flowData.toString());
		// presistance object
		this.approveEventMapper.insertSelective(event);

		ApproveFlow af = new ApproveFlow();
		af.setAeId(event.getAeId());
		// 当前审批人
		af.setApprovalExcutor(event.getCurApprovalExcutor());
		// 初始化审批
		af.setApprovalState(APPROVE.INIT.index);
		af.setApprovalName(this.userMapper.selectByPrimaryKey(
				event.getCurApprovalExcutor()).getRealname());
		af.setCreateTime(current);
		af.setUpdateTime(current);
		this.approveFlowMapper.insertSelective(af);
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
				this.eventFileMapper.insertEventAndFiles(event.getAeId(),
						waitToInsertResourceIds, FLOW_DEFAULT);

			event.setResources(handledResources);
		}

		return event;
	}

	/**
	 * 更新审批历史
	 * 
	 * @param operator
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateApproveFlow(ApproveFlow flow, User operator) {

		// varible
		ApproveEvent ae = new ApproveEvent();
		// ae.setAeId(flow.getAeId());
		ae = this.approveEventMapper.selectByPrimaryKey(flow.getAeId());
		JSONArray flowData = JSONArray.fromObject(ae.getVariable());
		JSONObject nowFlow = new JSONObject();
		// 当前审批人
		nowFlow.put("currentTaskExcutor", operator.getRealname());
		// 审批人意见
		nowFlow.put("apply.suggestion", flow.getApprovalSuggestion());
		// 审批通过状态
		nowFlow.put("method", flow.getApprovalState());

		flowData.set(flowData.size() - 1, nowFlow);
		ae.setVariable(flowData.toString());
		ae.setCurApprovalExcutor(null);
		ae.setUpdateTime(new Date());
		this.approveEventMapper.updateByPrimaryKey(ae);

		Date current = new Date();
		flow.setApprovalTime(current);
		flow.setUpdateTime(current);
		ApproveFlowExample afe = new ApproveFlowExample();
		afe.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andAeIdEqualTo(flow.getAeId())
				.andApprovalExcutorEqualTo(flow.getApprovalExcutor());
		this.approveFlowMapper.updateByExampleSelective(flow, afe);
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createForward(ApproveFlow af) {
		User taskExcutor = this.userMapper.selectByPrimaryKey(af
				.getApprovalExcutor());
		Date current = new Date();

		ApproveEvent ae = new ApproveEvent();
		ae = this.approveEventMapper.selectByPrimaryKey(af.getAeId());
		JSONObject forwardFlow = new JSONObject();
		JSONArray flowData = JSONArray.fromObject(ae.getVariable());

		// 当前审批人
		forwardFlow.put("currentTaskExcutor", taskExcutor.getRealname());
		// 审批人意见
		forwardFlow.put("apply.suggestion", "");
		// 审批通过状态
		forwardFlow.put("method", APPROVE.INIT.index);

		flowData.add(forwardFlow);
		ae.setVariable(flowData.toString());
		ae.setUpdateTime(current);
		ae.setCurApprovalExcutor(af.getApprovalExcutor());

		this.approveEventMapper.updateByPrimaryKeySelective(ae);
		// 历史审批
		af.setCreateTime(current);
		af.setApprovalName(taskExcutor.getRealname());
		af.setApprovalState(APPROVE.INIT.index);
		af.setUpdateTime(current);
		this.approveFlowMapper.insertSelective(af);

	}

	@Transactional(readOnly = true)
	public List<ApproveEvent> getHistoryFilterByMonthPaged(User user, Date yearAndMonth,
			RowBounds rb) {
		Date start  =  yearAndMonth;
		Calendar c = Calendar.getInstance();
		c.setTime(yearAndMonth);
		c.add(Calendar.MONTH, 1);
		Date end = c.getTime();
		
		ApproveEventExample aee = new ApproveEventExample();
		aee.createCriteria().andValidEqualTo(Constants.DATA_VALID).andCreateTimeBetween(start, end).andUserIdEqualTo(user.getUserId());
		return this.approveEventMapper.selectByExample(aee,rb);
	}

}
