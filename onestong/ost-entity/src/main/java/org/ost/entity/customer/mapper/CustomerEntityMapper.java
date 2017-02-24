package org.ost.entity.customer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;

@Mapper
public interface CustomerEntityMapper {
	CustomerEntityMapper INSTANCE = Mappers.getMapper(CustomerEntityMapper.class);

	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "name", target = "name"),
			@Mapping(expression = "java( org.ost.entity.tools.JsonType.convertToMap(customer.getProperty()))", target = "properties"),
			@Mapping(source = "py", target = "py"), @Mapping(source = "szm", target = "szm") })
	CustomerListDto customerToCustomerListDto(Customer customer);

	List<CustomerListDto> customersToCustomerListDtos(List<Customer> customers);

	@Mappings({ @Mapping(source = "id", target = "id"), @Mapping(source = "name", target = "name"),
			@Mapping(source = "py", target = "py"), @Mapping(source = "szm", target = "szm"),
			@Mapping(source = "createTime", target = "createTimeStr", dateFormat = "yyyy-MM-dd HH:mm"),
			@Mapping(expression = "java( org.ost.entity.tools.JsonType.convertToMap(customer.getProperty()))", target = "properties") })
	CustomerDetailDto customerToCustomerDetailDto(Customer customer);

}
