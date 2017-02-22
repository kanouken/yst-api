package org.ost.customers;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.customers.dao.CustomerDao;
import org.ost.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {
	@Autowired
	CustomerDao customerDao;

	@Test
	public void testQuery() throws Exception {
		// List<Customer> customers = this.customerDao.selectAll();
		// customers.stream().forEach(c -> {
		// try {
		// byte[] b = (byte[]) c.getProperty();
		// String result = new String(b, "utf-8");
		// System.out.println(result);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// System.out.println(c.getProperty().toString());
		// });
		Map<String, String> params = new HashMap<>();
		params.put("ost_user_name", "test");
		params.put("ost_tenatid", "1");
		params.put("exName", "x");
//		List<Customer> customer = this.customerDao.selectCustomers(params, "1");
		
//		System.out.println(customer.size());

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
