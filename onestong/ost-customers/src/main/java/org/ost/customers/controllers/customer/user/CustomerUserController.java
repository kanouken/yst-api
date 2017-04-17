package org.ost.customers.controllers.customer.user;

import java.util.List;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.customers.controllers.customer.Action;
import org.ost.customers.services.user.UserCustomerService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerUserController extends Action {

	@Autowired
	private UserCustomerService userCustomerService;

	@PostMapping(value = "queryByUser")
	public OperateResult<PageEntity<CustomerListDto>> queryCustomerByUser(
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody CustomerQueryDto customerQueryDto) {
		customerQueryDto.setSchemaId(schemaID);
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<PageEntity<CustomerListDto>>(
				userCustomerService.queryCustomerByUser(customerQueryDto, page));
	}

	@PutMapping(value = "user")
	public OperateResult<String> updateUser(
			@RequestHeader(value=ACCOUNT_NAME,required = true) String accountName,
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestBody List<CustomerListDto> customerListDtos) {
		
		return new OperateResult<String>(userCustomerService.updateUser(accountName,schemaID,customerListDtos));
	}
}
