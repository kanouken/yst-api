package org.ost.crm.client;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "contactsService")
public interface ContactsServiceClient extends BaseClient {

	@RequestMapping(value = "contacts/{id}", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<String> deleteContacts(@PathVariable(value = "id") Integer id,
			@RequestParam(value = TENANT_ID) String schemaId, @RequestParam(value = "realName") String realName

	);

	@RequestMapping(value = "contacts/", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<ContactsDto> createContact(@RequestHeader(value = TENANT_ID, required = false) String schemaID,
			@RequestBody ContactsDto contactDto);

	@RequestMapping(value = "contacts/{id}/", method = RequestMethod.PUT, consumes = "application/json")
	public OperateResult<ContactsDto> updateContact(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID) String schemaId, @RequestBody ContactsDto contactDto);

	@RequestMapping(value = "contacts/{id}/", method = RequestMethod.GET)
	public OperateResult<ContactsDto> queryDetail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaID);

	@RequestMapping(value = "contacts/list", method = RequestMethod.GET)
	public OperateResult<PageEntity<ContactsListDto>> contactList(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "customerID", required = false) Integer customerID);

	@RequestMapping(value = "contacts/project", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<String> updateContactProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody ProjectContactsDto dto);

	@RequestMapping(value = "contacts/project", method = RequestMethod.PUT, consumes = "application/json")
	public OperateResult<String> updateProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody ProjectContactsDto dto);

	@RequestMapping(value = "contacts/queryByProject", method = RequestMethod.GET)
	public OperateResult<List<ContactsListDto>> queryByProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "projectId") Integer projectId);

	/**
	 * 新增外访联系人
	 * 
	 * @param schemaID
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "contacts/visit", method = RequestMethod.POST, consumes = "application/json")
	public OperateResult<String> createVisitContacts(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody VisitContactsDto visitContactsDto);

	@RequestMapping(value = "contacts/visit", method = RequestMethod.PUT, consumes = "application/json")
	public OperateResult<String> updateVisitContacts(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody VisitContactsDto visitContactsDto);

	@RequestMapping(value = "contacts/queryByVisit", method = RequestMethod.GET)
	public OperateResult<List<ContactsListDto>> queryByVisit(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "visitId") Integer id);

}
