package org.ost.entity.customer.address.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.customer.address.Address;
import org.ost.entity.customer.address.vo.AddressVo;
@Mapper
public interface AddressEntityMapper {
	AddressEntityMapper INSTANCE = Mappers.getMapper(AddressEntityMapper.class);

	@Mappings({ @Mapping(source = "city", target = "city"), @Mapping(source = "province", target = "province"),
			@Mapping(source = "district", target = "district"), @Mapping(source = "lat", target = "lat"),
			@Mapping(source = "lng", target = "lng"), @Mapping(source = "detailAddress", target = "detailAddress1"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())")

	})
	Address addressInfoToAddress(AddressVo vo);

	List<Address> addressesInfoToAddresses(List<AddressVo> vo);

}
