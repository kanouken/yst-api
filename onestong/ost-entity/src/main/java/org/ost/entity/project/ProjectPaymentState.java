package org.ost.entity.project;

public enum ProjectPaymentState {

	CONFIRMED(Byte.parseByte("1"), "已确认"), NO_CONFIRM(Byte.parseByte("0"), "未确认");
	private Byte state;
	private String name;

	public static ProjectPaymentState getProjectPaymentState(Byte state) {
		ProjectPaymentState _state = null;
		switch (state) {
		case 0:
			_state = NO_CONFIRM;
			break;
		case 1:
			_state = CONFIRMED;
			break;

		default:
			break;
		}

		return _state;
	}

	private ProjectPaymentState(Byte state, String name) {
		this.state = state;
		this.name = name;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}