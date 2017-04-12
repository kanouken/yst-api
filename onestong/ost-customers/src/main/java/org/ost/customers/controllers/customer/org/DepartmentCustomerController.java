package org.ost.customers.controllers.customer.org;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.customers.controllers.customer.Action;
import org.ost.customers.services.org.DepartmentCustomerService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class DepartmentCustomerController extends Action {
	@Autowired
	DepartmentCustomerService deptCustomerService;

	/**
	 * 按部门id查询
	 * 
	 * @param schemaID
	 * @param curPage
	 * @param perPageSum
	 * @param customerQueryDto
	 * @return
	 */
	@PostMapping(value = "queryByDepartmentIds")
	public OperateResult<PageEntity<CustomerListDto>> queryMemberByDept(
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody CustomerQueryDto customerQueryDto) {
		customerQueryDto.setSchemaId(schemaID);
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<PageEntity<CustomerListDto>>(
				deptCustomerService.queryCustomerByDeptIds(customerQueryDto, page));
	}

}
