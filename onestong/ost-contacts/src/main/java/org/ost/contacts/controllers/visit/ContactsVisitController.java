package org.ost.contacts.controllers.visit;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.contacts.controllers.Action;
import org.ost.contacts.services.visit.ContactsVisitService;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "contacts")
public class ContactsVisitController extends Action {
	@Autowired
	private ContactsVisitService contactsVisitService;

	/**
	 * 根据外访记录查询联系人
	 * 
	 * @param schemaID
	 * @param id
	 * @return
	 */
	@GetMapping(value = "queryByVisit")
	public OperateResult<List<ContactsListDto>> queryByVisit(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "visitId") Integer id) {
		return new OperateResult<List<ContactsListDto>>(contactsVisitService.queryByVisit(schemaID, id));
	}

	/**
	 * 根据外访记录查询联系人
	 * 
	 * @param schemaID
	 * @param id
	 * @return
	 */
	@GetMapping(value = "queryByVisits")
	public OperateResult<List<VisitContactsDto>> queryByVisits(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "visitId") Integer[] ids) {
		return new OperateResult<List<VisitContactsDto>>(contactsVisitService.queryByVisits(schemaID, ids));
	}

	/**
	 * 新增外访联系人
	 * 
	 * @param schemaID
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "visit", method = RequestMethod.POST)
	public OperateResult<String> addVisitContacts(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody VisitContactsDto visitContactsDto) {
		return new OperateResult<String>(contactsVisitService.createVisitContacts(schemaID, visitContactsDto));
	}

	/**
	 * 修改外访联系人
	 * 
	 * @param schemaID
	 * @param dtos
	 * @return
	 */
	@RequestMapping(value = "visit", method = RequestMethod.PUT)
	public OperateResult<String> updateVisitContacts(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody VisitContactsDto visitContactsDto) {
		return new OperateResult<String>(this.contactsVisitService.updateVisitContacts(schemaID, visitContactsDto));
	}

}
