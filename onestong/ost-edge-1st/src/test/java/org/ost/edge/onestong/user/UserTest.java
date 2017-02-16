package org.ost.edge.onestong.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.edge.onestong.dao.user.UserDao;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.entity.user.Users;
import org.ost.entity.user.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	@Autowired
	private UsersService userService;

	@Autowired
	private UserDao userDao;

	/**
	 * 同事通讯录
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryAllContacts() throws Exception {
		Users u = new Users();
		u.settId(1000);
		this.userService.queryAllContacts(u);
	}

	@Test
	public void test() {
		Users u = new Users();
		u.settId(1);
		List<Users> users = this.userDao.select(u);
		UserEntityMapper.INSTANCE.UserToUserListDto(users.get(0));
	}
}
