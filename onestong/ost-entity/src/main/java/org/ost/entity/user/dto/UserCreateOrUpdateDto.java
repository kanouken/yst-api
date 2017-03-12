package org.ost.entity.user.dto;

public class UserCreateOrUpdateDto {
	private Integer id;

	private String name;

	private String deptName;
	private Integer deptID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

}
