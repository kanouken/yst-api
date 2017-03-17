package org.ost.entity.customer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.address.Address;
import org.ost.entity.customer.address.vo.AddressVo;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerUpdateDto;
import org.ost.entity.customer.org.CustomerOrg;
import org.ost.entity.customer.user.UserCustomers;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.report.dto.KeHuReportDto;
import org.ost.entity.user.dto.UserListDto;

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
			@Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm"),
			@Mapping(expression = "java( org.ost.entity.tools.JsonType.convertToMap(customer.getProperty()))", target = "properties") })
	CustomerDetailDto customerToCustomerDetailDto(Customer customer);

	Customer customerUpdateDtoToCustomer(CustomerUpdateDto updateDto);

	@Mapping(source = "detailAddress1", target = "detailAddress")
	AddressVo addressToAddressVo(Address addresses);

	List<AddressVo> addressToAddressVo(List<Address> addresses);

	@Mappings({ @Mapping(source = "organizeId", target = "id"), @Mapping(source = "organizeName", target = "name") })
	DepartmentListDto CustomerOrgToDepartMentListDto(CustomerOrg cos);

	List<DepartmentListDto> CustomerOrgToDepartMentListDto(List<CustomerOrg> cos);

	@Mappings({ @Mapping(source = "userId", target = "id"), @Mapping(source = "userName", target = "name"),
			@Mapping(source = "organizeId", target = "deptID"),
			@Mapping(source = "organizeName", target = "deptName") })
	UserListDto UserCustomerToUserListDto(UserCustomers ucs);

	List<UserListDto> UserCustomerToUserListDto(List<UserCustomers> ucs);

}
