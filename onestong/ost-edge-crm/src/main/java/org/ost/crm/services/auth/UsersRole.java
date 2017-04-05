package org.ost.crm.services.auth;

public enum UsersRole {

	ADMIN("管理员", "admin"), DEPARTMENT_MANAGER("部门主管", "deptDirector"), NORMAL_EMPLOYEE("一般员工", "employee"), HR("行政",
			"administrative");

	private String name;
	private String code;

	private UsersRole(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
