package org.ost.contacts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.contacts.CustomerContacts;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerContactsDao extends Mapper<CustomerContacts> {

	Integer selectCountContacts(@Param("name") String name, @Param("phone") String phone, @Param("keyword") String keyword, @Param("email") String email, @Param("customerIds") int[] _customerIds);

	List<ContactsListDto> selectContacts(@Param("name") String name, @Param("phone") String phone, @Param("keyword") String keyword, @Param("email") String email, @Param("customerIds") int[] _customerIds,
			RowBounds rb);

}
