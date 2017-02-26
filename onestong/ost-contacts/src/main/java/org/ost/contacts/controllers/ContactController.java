package org.ost.contacts.controllers;

import org.ost.contacts.services.ContactsService;
import org.ost.entity.contacts.dto.ContactsDto;
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

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void createContact(@PathVariable(value = "id") Integer id, Users users) {
		this.contactService.deleteContacts(id, users);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createContact(@RequestBody ContactsDto contactDto) {
		this.contactService.createContacts(contactDto);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object contactList(@RequestHeader(value = "curPage") Integer curPage,
			@RequestHeader(value = "tenantId") String tenantId,
			@RequestHeader(value = "perPageSum") Integer perPageSum, @RequestParam(value = "email") String email,
			@RequestParam(value = "name") String name, @RequestParam(value = "phone") String phone) {
		return this.contactService.queryContacts( tenantId, curPage, perPageSum,email,name,phone);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void queryDetail(@PathVariable(value = "id") Integer id, String tenantId) {
		this.contactService.getContactsDetail(id, tenantId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateContact(@PathVariable(value = "id") Integer id, @RequestBody ContactsDto contactDto) {
		this.contactService.updateContacts(id, contactDto);
	}
}
