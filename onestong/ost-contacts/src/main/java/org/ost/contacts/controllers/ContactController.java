package org.ost.contacts.controllers;

import org.common.tools.OperateResult;
import org.ost.contacts.services.ContactsService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
	@Autowired
	private ContactsService contactService;

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public OperateResult<Integer> deleteContacts(@PathVariable(value = "id") Integer id, Users users) {
		return new OperateResult<Integer>(this.contactService.deleteContacts(id, users));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public OperateResult<ContactsDto> createContact(
			@RequestHeader(value = "schemaID", required = false) String schemaID, @RequestBody ContactsDto contactDto) {
		return new OperateResult<ContactsDto>(this.contactService.createContacts(contactDto));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public OperateResult<PageEntity<ContactsListDto>> contactList(@RequestHeader(value = "curPage") Integer curPage,
			@RequestHeader(value = "tenantId") String tenantId, @RequestHeader(value = "perPageSum") Integer perPageSum,
			@RequestParam(value = "email") String email, @RequestParam(value = "name") String name,
			@RequestParam(value = "phone") String phone) {
		return new OperateResult<PageEntity<ContactsListDto>>(
				this.contactService.queryContacts(tenantId, curPage, perPageSum, email, name, phone));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OperateResult<ContactsDto> queryDetail(@PathVariable(value = "id") Integer id, String schemaId) {
		return new OperateResult<ContactsDto>(this.contactService.getContactsDetail(id, schemaId));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public OperateResult<ContactsDto> updateContact(@PathVariable(value = "id") Integer id,
			@RequestBody ContactsDto contactDto) {
		return new OperateResult<ContactsDto>(this.contactService.updateContacts(id, contactDto));
	}
}
