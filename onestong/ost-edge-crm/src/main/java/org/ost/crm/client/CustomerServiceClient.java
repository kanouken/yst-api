package org.ost.crm.client;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.common.tools.OperateResult;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerProjectDto;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.report.dto.KeHuReportDto;
import org.ost.entity.user.Users;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customerService")
public interface CustomerServiceClient extends BaseClient {

	@RequestMapping(value = "customer/", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<CustomerCreateVo> createCustomer(
			@RequestHeader(value = TENANT_ID, required = true) String schemaId, @RequestBody CustomerCreateVo customer);

	@RequestMapping(value = "customer/list/", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<PageEntity<CustomerListDto>> queryMember(
			@RequestHeader(value = TENANT_ID, required = true) String schemaId,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody Customer customer);

	@RequestMapping("customer/{id}/")
	public OperateResult<CustomerDetailDto> queryDetail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaId);

	@RequestMapping(value = "customer/{id}", method = RequestMethod.DELETE)
	public OperateResult<Integer> deleteCustomer(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaId, Users users);

	@RequestMapping(value = "customer/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public OperateResult<String> updateCustomer(@PathVariable(value = "id") Integer customerId,
			@RequestHeader(value = TENANT_ID) String schemaId, @RequestBody CustomerCreateVo updateDto);

	@RequestMapping(value = "/queryByContacts", method = RequestMethod.GET)
	public OperateResult<CustomerListDto> queryDetailByContacts(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "contactsId") Integer contactsId);

	@RequestMapping(value = "customer/queryByIds", method = RequestMethod.GET)
	public OperateResult<List<CustomerListDto>> queryByIds(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value = "ids") int[] ids);

	@Deprecated
	@RequestMapping(value = "customer/project", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<String> createCustomerProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody CustomerProjectDto dto);

	@Deprecated
	@RequestMapping(value = "customer/project", method = RequestMethod.PUT, consumes = "application/json")
	public OperateResult<String> updateCustomerProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody CustomerProjectDto dto);

	@Deprecated
	@RequestMapping(value = "customer/queryByProject", method = RequestMethod.GET)
	public OperateResult<CustomerVo> queryByProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "projectId") Integer projectId);

	@RequestMapping(value = "customer/kehuReportList", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<PageEntity<KeHuReportDto>> queryCustomersReport(
			@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody KeHuReportDto keHuReportDto);

	@RequestMapping(value = "customer/kehuReportChart", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<Object> reportChart(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody KeHuReportDto keHuReportDto);

	@RequestMapping(value = "customer/kehuReportCount", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<Object> queryReportCount(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody KeHuReportDto keHuReportDto);

	@RequestMapping(value = "customer/queryByUser", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<PageEntity<CustomerListDto>> queryCustomerByUser(
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody CustomerQueryDto customerQueryDto);

	@RequestMapping(value = "customer/user", method = RequestMethod.PUT, consumes = "application/json")
	public OperateResult<String> updateUser(@RequestHeader(value = ACCOUNT_NAME, required = true) String accountName,
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestBody List<CustomerListDto> customerListDtos);

	@RequestMapping(value = "customer/queryByDepartmentIds",method= RequestMethod.POST,consumes = "application/json")
	public OperateResult<PageEntity<CustomerListDto>> queryCustomerByDept(
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody CustomerQueryDto customerQueryDto);

}
