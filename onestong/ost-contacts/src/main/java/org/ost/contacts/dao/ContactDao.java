package org.ost.contacts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.contacts.Contacts;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactDao extends Mapper<Contacts> {

	public static final String SELECT_COUNT = "select  count( DISTINCT t.id) from ( "
			+ "select tc.id, tc.firstName,tc.lastName,tci.type,tci.val from tbl_contact tc left join tbl_contact_info  tci "
			+ "on (tc.id,tc.schemaID) = (tci.contactID ,tci.schemaID) ) t where "
			+ "INSTR(CONCAT(t.firstName,t.lastName),#{name}) >0 "
			+ "or  INSTR(t.val,#{email}) or instr(t.val,#{phone}) >0 ";

	@Select(SELECT_COUNT)
	Integer selectCountContacts(@Param("name") String name, @Param("phone") String phone, @Param("email") String email);

	List<ContactsListDto> selectContacts(@Param("name") String name, @Param("phone") String phone,
			@Param("email") String email, RowBounds rb);

}
