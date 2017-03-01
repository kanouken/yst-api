package org.ost.edge.onestong.department;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.web.department.DepartmentService;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DepartmentTest {
	@Autowired
	private DepartmentService deptService;
	@Test
	public void testQueryAllDepts(){
		User u = new User();
		u.setUserId(1);
		Object result = this.deptService.queryAllDepts(u);
		if(result != null){
			List<DepartmentListDto> ds = (List<DepartmentListDto>) result;
		}
	}
	
	
}
