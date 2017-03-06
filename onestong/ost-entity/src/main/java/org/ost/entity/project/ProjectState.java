package org.ost.entity.project;

public enum ProjectState {

	NORMAL(Byte.parseByte("0"), "正常进展项目"), LONG_ATT(Byte.parseByte("1"), "长期跟进项目"), NOT_SURE(Byte.parseByte("2"),
			"不确定性项目"), FAILED(Byte.parseByte("3"), "失败项目");
	private Byte state;
	private String name;

	public static ProjectState getProjectState(Byte state) {
		ProjectState _state = null;
		switch (state) {
		case 0:
			_state = NORMAL;
			break;
		case 1:
			_state = LONG_ATT;
			break;
		case 2:
			_state = NOT_SURE;
			break;

		case 3:
			_state = FAILED;
			break;
		default:
			break;
		}

		return _state;
	}

	private ProjectState(Byte state, String name) {
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