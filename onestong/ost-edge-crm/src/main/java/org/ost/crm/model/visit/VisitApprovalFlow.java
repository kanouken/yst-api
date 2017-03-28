package org.ost.crm.model.visit;

/**
 * 外访审批状态枚举 审批状态 -1:审批未通过 0:审批未完成 1:审批通过
 * 
 * @author xnq
 *
 */
public enum VisitApprovalFlow {
	NO_PASS(Byte.parseByte("-1"), "审批未通过"), NOT_COMPLETE(Byte.parseByte("0"), "审批未完成"), PASSED(Byte.parseByte("1"),
			"审批通过");

	private Byte state;
	private String stateName;

	public static VisitApprovalFlow getApprovalFlow(short state) {
		VisitApprovalFlow _state = null;
		switch (state) {
		case -1:
			_state = NO_PASS;
			break;
		case 0:
			_state = NOT_COMPLETE;
			break;
		case 1:
			_state = PASSED;
			break;
		default:
			break;
		}

		return _state;
	}

	private VisitApprovalFlow(Byte state, String stateName) {
		this.state = state;
		this.stateName = stateName;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
