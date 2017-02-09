package org.ost.customers;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ost.customers.dao.customer.CustomerDao;
import org.ost.customers.dao.tag.TagDao;
import org.ost.customers.model.customer.Customer;
import org.ost.customers.model.customer.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
public class CustomerTest {
	@Autowired
	CustomerDao customerDao;

	@Autowired
	TagDao tagDao;

	@Test
	public void addCustomer() {
		Date now = this.tagDao.selectNow();
		System.out.println(now);
	}

}
