package org.ost.edge.onestong.dao.department;

import org.ost.entity.org.department.Departments;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface DepartmentDao extends Mapper<Departments> {

}
