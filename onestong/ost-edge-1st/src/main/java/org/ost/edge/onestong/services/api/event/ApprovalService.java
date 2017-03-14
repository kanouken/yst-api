package org.ost.edge.onestong.services.api.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.openxmlformats.schemas.drawingml.x2006.chart.STTickLblPos;
import org.ost.edge.onestong.client.NotificationServiceClient;
import org.ost.edge.onestong.dao.event.approval.ApprovalDao;
import org.ost.edge.onestong.dao.event.approval.ApprovalUsersDao;
import org.ost.edge.onestong.dao.event.approval.ApprovalUsersExample;
import org.ost.edge.onestong.dao.user.UserMapper;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.entity.event.approval.ApprovalEvent;
import org.ost.entity.event.approval.ApprovalUsers;
import org.ost.entity.event.approval.dto.ApprovalEventDto;
import org.ost.entity.event.approval.dto.ApprovalListDto;
import org.ost.entity.event.mapper.ApprovalEventEntityMapper;
import org.ost.entity.notification.PushBody;
import org.ost.entity.user.dto.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalService {

	@Autowired
	private UsersService userService;
	@Autowired
	private NotificationServiceClient notificationServiceClient;

	private final static String NEW_APPLY = "您有一条新的approvalType审批，请及时审批";

	public enum ApprovalType {
		请假, 出差

	}

	public enum ApprovalState {

		INIT(Short.parseShort("0"), "未审批"), PASSED(Short.parseShort("1"), "审批通过"), NOT_PASS(Short.parseShort("2"),
				"审批不通过");
		private short state;
		private String name;

		public static ApprovalState getApprovalState(short state) {
			ApprovalState _state = null;
			switch (state) {
			case 0:
				_state = INIT;
				break;
			case 1:
				_state = PASSED;
				break;
			case 2:
				_state = NOT_PASS;
				break;
			default:
				break;
			}

			return _state;
		}

		private ApprovalState(short state, String name) {
			this.state = state;
			this.name = name;
		}

		public short getState() {
			return state;
		}

		public void setState(short state) {
			this.state = state;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@Autowired
	private ApprovalDao approvalDao;

	@Autowired
	private ApprovalUsersDao approvalUsersDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public ApprovalEventDto createApprovalEvent(User users, ApprovalEventDto dto) {
		ApprovalEvent aEvent = ApprovalEventEntityMapper.INSTANCE.approvalEventDtoToApprovalEvent(dto);
		aEvent.setUserId(users.getUserId());
		aEvent.setContent(
				StringUtils.isNotEmpty(dto.getBusTripReason()) ? dto.getBusTripReason() : dto.getLeaveReason());
		aEvent.setCreateBy(users.getRealname());
		aEvent.setUpdateBy(users.getRealname());
		StringBuffer tip = new StringBuffer("");
		tip.append("由").append(users.getRealname()).append("申请").append("，").append("由")
				.append(dto.getAppprovers().get(0).getName()).append("等").append(dto.getAppprovers().size())
				.append("人审批");
		aEvent.setApprovalTip(tip.toString());
		approvalDao.insertSelective(aEvent);
		// approvers
		List<UserListDto> approvers = dto.getAppprovers();
		ApprovalUsers approvalUser = new ApprovalUsers();
		approvers.forEach(approver -> {
			approvalUser.setApprovalEventId(aEvent.getAeId());
			approvalUser.setCreateBy(aEvent.getCreateBy());
			approvalUser.setUpdateBy(aEvent.getUpdateBy());
			approvalUser.setCreateTime(new Date());
			approvalUser.setUpdateTime(new Date());
			approvalUser.setUserId(Integer.parseInt(approver.getId()));
			approvalUsersDao.insertSelective(approvalUser);
		});
		//send notification
		CompletableFuture.runAsync(() -> {
			List<Integer> approverIds = dto.getAppprovers().stream().map(u -> Integer.valueOf(u.getId()))
					.collect(Collectors.toList());
			List<User> approveruers = userService.findUsersByIds(approverIds);
			if (CollectionUtils.isNotEmpty(approveruers)) {
				PushBody pb = new PushBody();
				pb.setApplication("onestong-crm");
				pb.setCids(approveruers.stream().map(u -> u.getcId()).collect(Collectors.toList()));
				pb.setContent("");
				String payLoad = NEW_APPLY.replaceFirst("approvalType", aEvent.getApprovalType());
				pb.setPayLoadBody(payLoad);
				notificationServiceClient.pushBatch(pb);
			}
		});
		return dto;
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String approval(User users, String action, String eventId) {
		Integer approvalId = users.getUserId();

		ApprovalUsers approvalUser = new ApprovalUsers();
		approvalUser.setState(
				action.equalsIgnoreCase("pass") ? ApprovalState.PASSED.getState() : ApprovalState.NOT_PASS.getState());
		approvalUser.setUpdateTime(new Date());
		approvalUser.setUpdateBy(users.getRealname());
		ApprovalUsersExample aue = new ApprovalUsersExample();
		aue.createCriteria().andUserIdEqualTo(approvalId).andApprovalEventIdEqualTo(eventId);
		approvalUsersDao.updateByExampleSelective(approvalUser, aue);

		// 检查是否全部审批通过
		approvalUser = new ApprovalUsers();
		approvalUser.setApprovalEventId(eventId);
		List<ApprovalUsers> approvers = approvalUsersDao.select(approvalUser);
		int result = approvers.stream().mapToInt(approver -> approver.getState().intValue()).reduce(1, (a, b) -> a * b);
		if (result == 1) {
			ApprovalEvent aEvent = new ApprovalEvent();
			aEvent.setUpdateTime(new Date());
			aEvent.setUpdateBy(users.getRealname());
			aEvent.setState(ApprovalState.PASSED.getState());
			aEvent.setAeId(eventId);
			approvalDao.updateByPrimaryKeySelective(aEvent);
		} else {
			ApprovalEvent aEvent = new ApprovalEvent();
			aEvent.setUpdateTime(new Date());
			aEvent.setUpdateBy(users.getRealname());
			aEvent.setState(ApprovalState.NOT_PASS.getState());
			aEvent.setAeId(eventId);
			approvalDao.updateByPrimaryKeySelective(aEvent);
		}
		// TODO notifaction
		return eventId;
	}

	@Autowired
	UserMapper userMapper;

	@Transactional(readOnly = true)
	public ApprovalEventDto queryDetail(User user, String eId) {

		ApprovalEvent aEvent = new ApprovalEvent();
		aEvent.setAeId(eId);
		aEvent.setIsDelete(Short.parseShort("0"));
		aEvent = approvalDao.selectOne(aEvent);
		ApprovalEventDto detailDto = ApprovalEventEntityMapper.INSTANCE.approvalEventToApprovalEventDto(aEvent);
		detailDto.setStatus(ApprovalState.getApprovalState(aEvent.getState()).getName());

		if (aEvent.getApprovalType().equalsIgnoreCase(ApprovalType.请假.toString())) {
			detailDto.setLeaveReason(aEvent.getContent());
		} else {
			detailDto.setBusTripReason(aEvent.getContent());
		}
		//
		ApprovalUsers users = new ApprovalUsers();
		users.setApprovalEventId(eId);

		List<ApprovalUsers> approvers = approvalUsersDao.select(users);

		List<UserListDto> _approvers = new ArrayList<UserListDto>();

		approvers.forEach(approver -> {
			UserListDto _user = new UserListDto();
			_user.setId(String.valueOf(approver.getUserId()));
			_user.setName(approver.getUpdateBy());
			_approvers.add(_user);
		});
		detailDto.setAppprovers(_approvers);
		detailDto.setUserPic(this.userMapper.selectByPrimaryKey(user.getUserId()).getPic());
		return detailDto;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> queryApprovalsByUser(User user, String type, Integer curPage, Integer perPageSum) {
		Map<String, Object> reqMap = new HashMap<String, Object>();

		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		List<ApprovalListDto> records = new ArrayList<ApprovalListDto>();
		// 我的申请
		ApprovalEvent aEvent = new ApprovalEvent();
		aEvent.setIsDelete(Short.parseShort("0"));
		aEvent.setUserId(user.getUserId());
		Integer myApprovalTotalRecord = approvalDao.selectCount(aEvent);
		// 待审批
		ApprovalUsers approvalUser = new ApprovalUsers();
		approvalUser.setIsDelete(Short.parseShort("0"));
		approvalUser.setUserId(user.getUserId());
		Integer todoTotalRecord = approvalUsersDao.selectCount(approvalUser);

		if (type.trim().equals("0")) {
			page.setTotalRecords(myApprovalTotalRecord);
			if (myApprovalTotalRecord > 0) {
				// 我的申请
				aEvent = new ApprovalEvent();
				aEvent.setIsDelete(Short.parseShort("0"));
				aEvent.setUserId(user.getUserId());
				List<ApprovalEvent> aEvents = approvalDao.selectByRowBounds(aEvent, rb);

				records = ApprovalEventEntityMapper.INSTANCE.approvalEventToApprovalListDto(aEvents);
			}
		} else {
			page.setTotalRecords(todoTotalRecord);
			if (todoTotalRecord > 0) {
				// 待审批
				List<ApprovalEvent> aEvents = approvalDao.selectTodoByUserId(user.getUserId());
				records = ApprovalEventEntityMapper.INSTANCE.approvalEventToApprovalListDto(aEvents);
			}
		}
		reqMap.put("page", page);
		reqMap.put("objects", records);
		Map<String, Integer> summaries = new HashMap<String, Integer>();
		summaries.put("alreayApplied", myApprovalTotalRecord);
		summaries.put("waitApproved", todoTotalRecord);
		reqMap.put("summaries", summaries);
		return reqMap;
	}

}
