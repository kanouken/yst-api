package org.ost.contacts.controllers;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.contacts.services.ContactsService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
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
@RequestMapping(value = "contacts")
public class ContactController extends Action {
	@Autowired
	private ContactsService contactService;

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public OperateResult<String> deleteContacts(@PathVariable(value = "id") Integer id, Users users) {
		return new OperateResult<String>(this.contactService.deleteContacts(id, users));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public OperateResult<ContactsDto> createContact(@RequestHeader(value = TENANT_ID, required = false) String schemaID,
			@RequestBody ContactsDto contactDto) {
		return new OperateResult<ContactsDto>(this.contactService.createContacts(contactDto));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public OperateResult<PageEntity<ContactsListDto>> contactList(@RequestHeader(value = "curPage") Integer curPage,
			@RequestHeader(value = "schemaID") String schemaID, @RequestHeader(value = "perPageSum") Integer perPageSum,
			@RequestParam(value = "email") String email, @RequestParam(value = "name") String name,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "customerID") Integer customerID) {
		return new OperateResult<PageEntity<ContactsListDto>>(
				this.contactService.queryContacts(schemaID, curPage, perPageSum, email, name, phone, customerID));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OperateResult<ContactsDto> queryDetail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID") String schemaID) {
		return new OperateResult<ContactsDto>(this.contactService.getContactsDetail(id, schemaID));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public OperateResult<ContactsDto> updateContact(@PathVariable(value = "id") Integer id,
			@RequestBody ContactsDto contactDto) {
		return new OperateResult<ContactsDto>(this.contactService.updateContacts(id, contactDto));
	}

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public OperateResult<String> updateContactProject(@RequestHeader(value = "schemaID") String schemaID,
			@RequestBody ProjectCreateOrUpdateDto dto) {
		return new OperateResult<String>(this.contactService.createOrUpdateProjectContacts(schemaID, dto));
	}

	@RequestMapping(value = "/project", method = RequestMethod.PUT)
	public OperateResult<String> updateProject(@RequestHeader(value = "schemaID") String schemaID,
			@RequestBody List<ContactsListDto> dtos) {
		return new OperateResult<String>(this.contactService.updateConactsProject(schemaID, dtos));
	}
}
