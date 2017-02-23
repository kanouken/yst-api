package org.ost.customers;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.customers.dao.CustomerDao;
import org.ost.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {
	@Autowired
	CustomerDao customerDao;

	@Test
	public void testQuery() throws Exception {
		Customer customer = new Customer();
		customer.setIsDelete(Short.valueOf("0"));
		customer.setId(1);
		 this.customerDao.selectOne(customer);
		
	}

	@Test
	public void testInsert() throws Exception {
		Customer customer = new Customer();
		customer.setCreateBy("admin");
		customer.setUpdateBy("admin");
		customer.setName("test1");
		customer.setTenantId("xxxx");
		customer.setCreateTime(new Date());
		customer.setUpdateTime(new Date());
		customer.setPy("xxx");
		customer.setSzm("xxx");
		this.customerDao.insert(customer);
	}
}
