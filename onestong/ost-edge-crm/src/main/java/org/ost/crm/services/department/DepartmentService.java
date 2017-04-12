package org.ost.crm.services.department;

import java.util.List;

import org.ost.crm.dao.department.DepartmentDao;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.org.department.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService extends BaseService {
	@Autowired
	private DepartmentDao departmentDao;
	/**
	 * 
	 * @param dept root 
	 * @return root 下所有部门
	 */
	@Transactional(readOnly = true)
	public List<Departments> queryDepartmentRecursion(Departments dept) {
		List<Departments> departments = departmentDao.selectByDept(dept);
		return departments;
	}

}
