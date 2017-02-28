package org.ost.crm.controller.contacts;

import javax.servlet.http.HttpServletRequest;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.contacts.ContactsService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsCreateDto;
import org.ost.entity.contacts.dto.ContactsDetailDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "contacts")
public class ContactsController extends Action {

	@Autowired
	private ContactsService contactsService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public OperateResult<ContactsCreateDto> createContacts(@RequestAttribute(value = LOGIN_USER) Users users,
			ContactsCreateDto contactsDto) {
		return new OperateResult<ContactsCreateDto>(this.contactsService.createContacts(users, contactsDto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OperateResult<ContactsDetailDto> queryDetail(@PathVariable(value = "id") Integer contactsId,
			@RequestAttribute(value = LOGIN_USER) Users users) {
		return new OperateResult<ContactsDetailDto>(this.contactsService.queryDetail(contactsId, users));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public OperateResult<PageEntity<ContactsListDto>> queryContacts(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestHeader(value = "schemaID") String schemaID, @RequestAttribute(value = LOGIN_USER) Users users,
			@RequestParam(value = "customerID") Integer customerID, @RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "name") String name, @RequestParam(value = "phone") String phone) {
		return new OperateResult<PageEntity<ContactsListDto>>(this.contactsService.queryContacts(schemaID, customerID,
				keyword, name, phone, users, curPage, perPageSum));
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public OperateResult<ContactsCreateDto> updateContacts(@PathVariable(value = "id") Integer contactsId,
			@RequestAttribute(value = LOGIN_USER) Users users,@RequestBody ContactsCreateDto dto ) {
		return new OperateResult<ContactsCreateDto>(this.contactsService.updateContacts(contactsId,users,dto));
	}
}
