package org.ost.contacts.services.visit;

import java.util.Date;
import java.util.List;

import org.ost.contacts.dao.VisitContactsDao;
import org.ost.contacts.services.BaseService;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.contacts.visit.VisitContacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactsVisitService extends BaseService {

	@Autowired
	VisitContactsDao visitContactsDao;

	/**
	 * 根据外访查询联系人
	 * 
	 * @param schemaID
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ContactsListDto> queryByVisit(String schemaID, Integer id) {
		return this.contactDao.selectByVisit(schemaID, id);
	}

	/**
	 * 
	 * @param schemaID
	 * @param visitContactsDto
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateVisitContacts(String schemaID, VisitContactsDto visitContactsDto) {
		// remove
		VisitContacts vContacts = new VisitContacts();
		vContacts.setVisitEventID(visitContactsDto.getVisitEventId());
		visitContactsDao.delete(vContacts);
		// add
		createVisitContacts(schemaID, visitContactsDto);
		return HttpStatus.OK.name();
	}

	/**
	 * 添加外访联系人
	 * 
	 * @param schemaID
	 * @param visitContactsDto
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String createVisitContacts(String schemaID, VisitContactsDto visitContactsDto) {
		visitContactsDto.getContactsIds().forEach(c -> {
			VisitContacts vc = new VisitContacts();
			vc.setContactID(c);
			vc.setCreateBy(visitContactsDto.getUserName());
			vc.setCreateId(Integer.parseInt(visitContactsDto.getUserId()));
			vc.setCreateTime(new Date());
			vc.setIsDelete(Short.parseShort("0"));
			vc.setSchemaId(schemaID);
			vc.setUpdateBy(visitContactsDto.getUserName());
			vc.setUpdateTime(vc.getCreateTime());
			vc.setVisitEventID(visitContactsDto.getVisitEventId());
			visitContactsDao.insertSelective(vc);
		});
		return HttpStatus.OK.name();
	}

	/**
	 * 根据外访记录查询联系人
	 * 
	 * @param schemaID
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<VisitContactsDto> queryByVisits(String schemaID, Integer[] ids) {
		return this.contactDao.selectByVisits(schemaID, ids);
	}

}
