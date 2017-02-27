package org.ost.crm.controller.contacts;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.contacts.ContactsService;
import org.ost.entity.contacts.dto.ContactsCreateDto;
import org.ost.entity.contacts.dto.ContactsDetailDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public OperateResult<ContactsDetailDto> queryContacts(@RequestHeader(value = "schemaID") String schemaID,
			@RequestAttribute(value = LOGIN_USER) Users users) {
		return null;
	}

}
