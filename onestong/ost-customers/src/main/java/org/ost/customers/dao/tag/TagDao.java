package org.ost.customers.dao.tag;

import java.util.Date;

import org.ost.customers.model.customer.tag.Tag;
import org.springframework.stereotype.Repository;


import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TagDao extends Mapper<Tag> {
	
	
	Date selectNow();
}
