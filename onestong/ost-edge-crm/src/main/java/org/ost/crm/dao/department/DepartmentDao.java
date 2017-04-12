package org.ost.crm.dao.department;

import java.util.List;

import org.ost.entity.org.department.Departments;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface DepartmentDao extends Mapper<Departments>{

	List<Departments> selectByDept(Departments dept);
	
	
	
}
