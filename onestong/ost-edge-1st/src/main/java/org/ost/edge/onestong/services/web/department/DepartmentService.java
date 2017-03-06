package org.ost.edge.onestong.services.web.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.dao.department.DepartmentDao;
import org.ost.edge.onestong.dao.department.DepartmentMapper;
import org.ost.edge.onestong.model.department.Department;
import org.ost.edge.onestong.model.department.DepartmentExample;
import org.ost.edge.onestong.model.department.DepartmentExample.Criteria;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.ost.entity.org.department.Departments;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.org.department.mapper.DepartmentEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门管理的业务逻辑 一般逻辑
 * 
 * @author mac
 *
 */
@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private DepartmentDao deptDao;

	@Autowired
	private UsersService userService;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void addDepartment(Department department) {

		this.departmentMapper.insertSelective(department);

	}

	@Transactional(readOnly = true)
	public List<Department> findAllTopDepartments(Integer domainId) {
		DepartmentExample de = new DepartmentExample();
		de.createCriteria().andValidEqualTo(Constants.DATA_VALID).andIsLeafEqualTo(Constants.IS_NOT_LEAF)
				.andDomainIdEqualTo(domainId);
		de.setOrderByClause("update_time asc");
		return this.departmentMapper.selectByExample(de);

	}

	public List<Map<String, Object>> findAllDepartmentsAndParentNameFilterByDomain(Integer domainId) {

		return this.departmentMapper.selectDepartmentsAndParentNameByDomainId(domainId);

	}

	/**
	 * 供添加用户时的部门设置用
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Department> findAllDepartments(Department dept) {
		DepartmentExample de = new DepartmentExample();
		de.createCriteria().andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(dept.getDomainId());
		de.setOrderByClause("update_time desc");
		return this.departmentMapper.selectByExample(de);

	}

	@Transactional(readOnly = true)
	public int findAllDepartmentsCount(Department dept) {
		DepartmentExample de = new DepartmentExample();
		Criteria c = de.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(dept.getDomainId());

		if (StringUtils.isNotBlank(dept.getName())) {

			c.andNameLike("%" + dept.getName() + "%");
		}

		return this.departmentMapper.countByExample(de);
	}

	@Transactional(readOnly = true)
	public Object findAllDepartments(Department dept, RowBounds rb) {
		DepartmentExample de = new DepartmentExample();
		Criteria c = de.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(dept.getDomainId());
		de.setOrderByClause("update_time desc");
		if (StringUtils.isNotBlank(dept.getName())) {

			c.andNameLike("%" + dept.getName() + "%");
		}
		return this.departmentMapper.selectByExample(de, rb);
	}

	@Transactional(readOnly = true)
	public Department findOne(Integer deptId) {
		DepartmentExample de = new DepartmentExample();
		de.createCriteria().andValidEqualTo(Constants.DATA_VALID).andDeptIdEqualTo(deptId);

		List<Department> depts = this.departmentMapper.selectByExample(de);
		if (CollectionUtils.isNotEmpty(depts)) {

			return depts.get(0);
		} else {

			return null;
		}

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateDepartment(Department department) {

		DepartmentExample de = new DepartmentExample();
		de.createCriteria().andDeptIdEqualTo(department.getDeptId());

		this.departmentMapper.updateByExampleSelective(department, de);

	}

	/**
	 * 第一级部门列表
	 */
	@Transactional(readOnly = true)
	public List<Department> findAllLeve1Departments(Integer domainId) {
		DepartmentExample de = new DepartmentExample();
		de.createCriteria().andParentIdEqualTo(0).andDomainIdEqualTo(domainId);

		return this.departmentMapper.selectByExample(de);
	}

	/**
	 * 
	 * @param parentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Object findAllChildorMembersByParentId(Integer parentId) {
		Map<String, Object> directorAndSubDepartments = new HashMap<String, Object>();
		DepartmentExample de = new DepartmentExample();
		de.createCriteria().andValidEqualTo(Constants.DATA_VALID).andDeptIdEqualTo(parentId);
		List<Department> tmps = this.departmentMapper.selectByExample(de);
		Department tmp = null;
		if (CollectionUtils.isNotEmpty(tmps)) {
			tmp = tmps.get(0);

			if (tmp.getIsLeaf() == Constants.IS_LEAF) {

				List<Map<String, Object>> users = this.userService.findAllUsersByDepartId(tmp.getDeptId());
				directorAndSubDepartments.put("users", users);
				directorAndSubDepartments.put("subDepts", null);

				return directorAndSubDepartments;
			} else {

				// 返回二级部门列表

				de.clear();
				de.createCriteria().andValidEqualTo(Constants.DATA_VALID).andParentIdEqualTo(parentId);

				List<Department> subs = this.departmentMapper.selectByExample(de);
				directorAndSubDepartments.put("subDepts", subs);
				List<Map<String, Object>> users = this.userService.findAllUsersByDepartId(parentId);
				directorAndSubDepartments.put("users", users);
				return directorAndSubDepartments;
			}
		} else {

			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<DepartmentListDto>  queryAllDepts(User currentUser) {
		Departments dept = new Departments();
		dept.setDomainId(currentUser.getDomainId());
		dept.setValid(Byte.parseByte("0"));
		List<Departments> depts = this.deptDao.select(dept).stream().sorted((d1, d2) -> {
			return d1.getDeptId().compareTo(d2.getDeptId());
		}).collect(Collectors.toList());
		return DepartmentEntityMapper.INSTANCE.departmentsToDepartmentListDtos(depts);
	}

}
