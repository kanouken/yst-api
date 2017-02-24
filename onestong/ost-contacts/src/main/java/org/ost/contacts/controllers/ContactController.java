package org.ost.contacts.controllers;

import org.ost.contacts.services.ContactsService;
import org.ost.entity.contacts.dto.ContactsDto;
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

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createContact(@RequestBody ContactsDto contactDto) {
		this.contactService.createContacts(contactDto);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object contactList(@RequestParam(value = "contactId") Integer contactId,
			@RequestHeader(value = "curPage") Integer curPage,
			@RequestHeader(value = "perPageSum") Integer perPageSum) {
		return this.contactService.queryContacts(contactId, curPage, perPageSum);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void queryDetail(@PathVariable(value = "id") Integer id, String tenantId) {
		this.contactService.getContactsDetail(id,tenantId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateContact(@PathVariable(value = "id") Integer id, @RequestBody ContactsDto contactDto) {
		this.contactService.updateContacts(id, contactDto);
	}
}
