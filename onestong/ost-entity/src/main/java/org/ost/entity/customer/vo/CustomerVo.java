package org.ost.entity.customer.vo;

public class CustomerVo {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public CustomerVo() {
		super();
	}

	public CustomerVo(Integer id, String name) {
		this.id = id;
		this.name = name;
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

}
