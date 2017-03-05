package org.ost.entity.customer.contacts.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.customer.contacts.ContactsInfo;
import org.ost.entity.customer.contacts.vo.ContactInfoVo;

@Mapper
public interface ContactInfoEntityMapper {
	ContactInfoEntityMapper INSTANCE = Mappers.getMapper(ContactInfoEntityMapper.class);

	@Mappings({ @Mapping(source = "type", target = "type"), @Mapping(source = "typeName", target = "custName"),
			@Mapping(source = "val", target = "val"), @Mapping(target = "isDelete", constant = "0"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())")

	})
	ContactsInfo contactInfoVoToContactInfo(ContactInfoVo vo);

	List<ContactsInfo> contactInfosVoToContactInfos(List<ContactInfoVo> vo);

	@InheritInverseConfiguration
	ContactInfoVo contactsInfoToContactsInfoVo(ContactsInfo infos);

	List<ContactInfoVo> contactsInfoToContactsInfoVo(List<ContactsInfo> infos);
}
