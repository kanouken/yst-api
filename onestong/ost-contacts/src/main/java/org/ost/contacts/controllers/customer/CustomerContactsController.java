package org.ost.contacts.controllers.customer;

import org.common.tools.OperateResult;
import org.ost.contacts.controllers.Action;
import org.ost.contacts.services.customer.CustomerContactsService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value="contacts")
public class CustomerContactsController extends Action {
	@Autowired
	private CustomerContactsService  customerContactsService;
	
	@RequestMapping(value = "/queryByCustomer", method = RequestMethod.GET)
	public OperateResult<PageEntity<ContactsListDto>> contactList(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value= "keyword",required =false) String keyword,
			@RequestParam(value = "customerIds", required = false) String customerIds) {
		return new OperateResult<PageEntity<ContactsListDto>>(
				this.customerContactsService.queryContactsByCustomer(schemaID, curPage, perPageSum, email, name, phone,keyword, customerIds));
	}

	
}
