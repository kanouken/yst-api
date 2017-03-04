package org.ost.edge.onestong.user;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	@Autowired
	private UsersService userService;

	// @Autowired
	// private UserDao userDao;

	/**
	 * 同事通讯录
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryAllContacts() throws Exception {
		User u = new User();
		u.setDomainId(1);
		this.userService.queryAllContacts(u);
	}

	@Test
	public void test() {
		User u = new User();
		u.setDomainId(1);
		// List<User> users = this.userDao.select(u);
		// UserEntityMapper.INSTANCE.UserToUserListDto(users.get(0));
	}

	public static void main(String[] args) {
		System.out.println(
				Jwts.builder().setSubject("1st").claim("userId", 10).claim("realName", "admin")
						.claim("email","xxx@xx.com").claim("deptId", 1)
						.claim("schemaId", "xx").setIssuedAt(new Date())
						.signWith(SignatureAlgorithm.HS256, "1stapp").compact());

	}
}
