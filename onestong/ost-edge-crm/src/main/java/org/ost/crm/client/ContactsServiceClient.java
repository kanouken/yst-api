package org.ost.crm.client;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "contactsService")
public interface ContactsServiceClient extends BaseClient {

	@RequestMapping(value = "contacts/{id}", method = RequestMethod.POST)
	public OperateResult<String> deleteContacts(@PathVariable(value = "id") Integer id,
			@RequestParam(value = TENANT_ID) String schemaId, @RequestParam(value = "realName") String realName

	);

	@RequestMapping(value = "contacts/", method = RequestMethod.POST)
	public OperateResult<ContactsDto> createContact(@RequestHeader(value = TENANT_ID, required = false) String schemaID,
			@RequestBody ContactsDto contactDto);

	@RequestMapping(value = "contacts/{id}/", method = RequestMethod.PUT)
	public OperateResult<ContactsDto> updateContact(@PathVariable(value = "id") Integer id,
			@RequestBody ContactsDto contactDto);

	@RequestMapping(value = "contacts/{id}/", method = RequestMethod.GET)
	public OperateResult<ContactsDto> queryDetail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaID);

	@RequestMapping(value = "contacts/list/", method = RequestMethod.GET)
	public OperateResult<PageEntity<ContactsListDto>> queryContacts(@RequestHeader(value = "curPage") Integer curPage,
			@RequestHeader(value = "tenantId") String tenantId, @RequestHeader(value = "perPageSum") Integer perPageSum,
			@RequestParam(value = "email") String email, @RequestParam(value = "name") String name,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "customerID") Integer customerID);

	@RequestMapping(value = "contacts/project", method = RequestMethod.POST)
	public OperateResult<String> updateContactProject(@RequestHeader(value = "schemaID") String schemaID,
			@RequestBody ProjectCreateOrUpdateDto dto);

	@RequestMapping(value = "contacts/project", method = RequestMethod.PUT)
	public OperateResult<String> updateProject(@RequestHeader(value = "schemaID") String schemaID,
			@RequestBody List<ContactsListDto> dtos);
}
