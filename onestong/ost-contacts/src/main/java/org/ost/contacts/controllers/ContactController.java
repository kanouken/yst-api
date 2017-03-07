package org.ost.contacts.controllers;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.contacts.services.ContactsService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.tools.tree.ThisExpression;

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
	public OperateResult<PageEntity<ContactsListDto>> contactList(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "customerID", required = false) Integer customerID) {
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
			@RequestHeader(value = TENANT_ID) String schemaId, @RequestBody ContactsDto contactDto) {
		return new OperateResult<ContactsDto>(this.contactService.updateContacts(id, schemaId, contactDto));
	}

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public OperateResult<String> updateContactProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody ProjectContactsDto dto) {
		return new OperateResult<String>(this.contactService.createProjectContacts(schemaID, dto));
	}

	@RequestMapping(value = "/project", method = RequestMethod.PUT)
	public OperateResult<String> updateProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody ProjectContactsDto dtos) {
		return new OperateResult<String>(this.contactService.updateConactsProject(schemaID, dtos));
	}

	@GetMapping(value = "queryByProject")
	public OperateResult<List<ContactsListDto>> queryByProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "projectId") Integer projectId) {
		return new OperateResult<List<ContactsListDto>>(contactService.queryByProject(schemaID, projectId));
	}

}
