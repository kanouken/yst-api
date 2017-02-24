package org.ost.entity.contacts.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.contacts.Contacts;
import org.ost.entity.contacts.address.ContactsAddress;
import org.ost.entity.contacts.address.dto.ContactsAddressDto;
import org.ost.entity.contacts.contactsinfo.ContactsInfo;
import org.ost.entity.contacts.contactsinfo.dto.ContactsInfoDto;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.file.ContactsFile;
import org.ost.entity.contacts.file.dto.ContactsFileDto;

@Mapper
public interface ContactsEntityMapper {
	ContactsEntityMapper INSTANCE = Mappers.getMapper(ContactsEntityMapper.class);

	
	ContactsDto contactsToContactsDto(Contacts contacts);
	
	
	@Mappings({

			@Mapping(source = "dto.province", target = "province"), @Mapping(source = "dto.city", target = "city"),
			@Mapping(source = "dto.district", target = "district"), @Mapping(source = "dto.lat", target = "lat"),
			@Mapping(source = "dto.lng", target = "lng"),
			@Mapping(source = "dto.detailAddress1", target = "detailAddress1"),
			@Mapping(source = "dto.detailAddress2", target = "detailAddress2"),
			@Mapping(source = "dto.detailAddress3", target = "detailAddress3"),
			@Mapping(source = "contacts.tenantId", target = "tenantId"),
			@Mapping(source = "contacts.currentUserName", target = "createBy"),
			@Mapping(source = "contacts.currentUserName", target = "updateBy"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "isDelete", constant = "0")

	})
	ContactsAddress contactsAddressDtoToContactsAddress(ContactsAddressDto dto, ContactsDto contacts);

	@Mappings({

			@Mapping(source = "dto.type", target = "type"), @Mapping(source = "dto.custName", target = "custName"),
			@Mapping(source = "dto.val", target = "val"), @Mapping(source = "contacts.tenantId", target = "tenantId"),
			@Mapping(source = "contacts.currentUserName", target = "createBy"),
			@Mapping(source = "contacts.currentUserName", target = "updateBy"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "isDelete", constant = "0")

	})
	ContactsInfo contactsInfoDtoToContactsInfo(ContactsInfoDto dto, ContactsDto contacts);

	@Mappings({

			@Mapping(source = "dto.type", target = "type"), @Mapping(source = "dto.val", target = "val"),
			@Mapping(source = "contacts.tenantId", target = "tenantId"),
			@Mapping(source = "contacts.currentUserName", target = "createBy"),
			@Mapping(source = "contacts.currentUserName", target = "updateBy"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "isDelete", constant = "0") })
	ContactsFile contactsFileDtoToContactsFile(ContactsFileDto dto, ContactsDto contacts);

	ContactsFileDto contactsFileToContactsFileDto(ContactsFile cFile);

	List<ContactsFileDto> contactsFileToContactsFileDto(List<ContactsFile> cFile);

	ContactsAddressDto contactsAddressToContactsAddressDto(ContactsAddress cAddress);

	List<ContactsAddressDto> contactsAddressToContactsAddressDto(List<ContactsAddress> cAddress);

	ContactsInfoDto contactsInfoToContactsInfoDto(ContactsInfo cinfos);

	List<ContactsInfoDto> contactsInfoToContactsInfoDto(List<ContactsInfo> cFile);

}
