package org.ost.crm.services.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsCreateDto;
import org.ost.entity.contacts.dto.ContactsDetailDto;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContactsService {

	@Autowired
	private ContactsServiceClient contactsServiceClient;
	@Autowired
	private CustomerServiceClient customerServiceClient;

	/**
	 * 创建联系人
	 * 
	 * @param users
	 * @param contactsCreateDto
	 * @return
	 */
	public ContactsCreateDto createContacts(Users users, ContactsCreateDto contactsCreateDto) {
		ContactsDto contactsDto = ContactsEntityMapper.INSTANCE.contactsCreateDtoToContactsDto(contactsCreateDto);
		contactsDto.setSchemaId(users.getSchemaId());
		contactsDto.setCurrentUserName(users.getRealname());
		OperateResult<ContactsDto> result = this.contactsServiceClient.createContact(users.getSchemaId(), contactsDto);
		if (result.success()) {
			// success
		} else {
			throw new ApiException("联系人创建失败", result.getInnerException());
		}
		return contactsCreateDto;
	}

	public ContactsDetailDto queryDetail(Integer contactsId, Users users) {
		OperateResult<ContactsDto> result = this.contactsServiceClient.queryDetail(contactsId, users.getSchemaId());
		if (result.success()) {
			ContactsDto contactsDto = result.getData();
			ContactsDetailDto detailDto = ContactsEntityMapper.INSTANCE.contactsDtoToContactsDetailDto(contactsDto);
			if (contactsDto.getCustomer() != null) {
				OperateResult<CustomerDetailDto> result2 = this.customerServiceClient
						.queryDetail(contactsDto.getCustomer().getId(), users.getSchemaId());
				if (result2.success()) {
					detailDto.setCustomer(new CustomerVo(result2.getData().getId(), result2.getData().getName()));
				}
			}
			return detailDto;
		} else {
			throw new ApiException("获取联系人详细失败", result.getInnerException());
		}

	}

	/**
	 * 联系人列表
	 * 
	 * @param customerID
	 * @param keyword
	 * @param name
	 * @param phone
	 * @param users
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	public Map<String, Object> queryContacts(Integer customerID, String keyword, String name, String phone, Users users,
			Integer curPage, Integer perPageSum) {
		OperateResult<PageEntity<ContactsListDto>> result = contactsServiceClient.contactList(curPage,
				users.getSchemaId(), perPageSum, null, name, phone, customerID);
		List<ContactsListDto> records = new ArrayList<ContactsListDto>();
		if (result.success()) {
			records = result.getData().getObjects();
			if (CollectionUtils.isNotEmpty(result.getData().getObjects())) {
				int[] customerIds = result.getData().getObjects().stream().mapToInt(c -> c.getCustomerID()).toArray();
				OperateResult<List<CustomerListDto>> result2 = this.customerServiceClient
						.queryByIds(users.getSchemaId(), customerIds);
				if (result2.success()) {
					records.forEach(r -> {
						Optional<CustomerListDto> _rOptional = result2.getData().stream()
								.filter(c -> c.getId() == r.getCustomerID()).findFirst();
						if (_rOptional.isPresent()) {
							r.setCustomer(new CustomerVo(_rOptional.get().getId(), _rOptional.get().getName()));
						}
					});

				}
			}
			Page page = new Page();
			page.setCurPage(curPage);
			page.setTotalRecords(result.getData().getTotalRecord());
			return OperateResult.renderPage(page, result.getData().getObjects());
		} else {
			throw new ApiException("获取联系人列表失败", result.getInnerException());
		}

	}

	/**
	 * 更新联系人
	 * 
	 * @param contactsId
	 * @param users
	 * @param dto
	 * @return
	 */
	public String updateContacts(Integer contactsId, Users users, ContactsCreateDto dto) {
		ContactsDto contactsDto = ContactsEntityMapper.INSTANCE.contactsCreateDtoToContactsDto(dto);
		contactsDto.setCurrentUserName(users.getRealname());
		contactsDto.setSchemaId(users.getSchemaId());
		OperateResult<ContactsDto> result = contactsServiceClient.updateContact(contactsId, users.getSchemaId(),
				contactsDto);
		if (result.success()) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("更新联系人失败", result.getInnerException());
		}
	}

	/**
	 * 删除 联系人
	 * 
	 * @param contactsId
	 * @param users
	 * @return
	 */
	public String deleteContacts(Integer contactsId, Users users) {
		OperateResult<String> result = this.contactsServiceClient.deleteContacts(contactsId, users.getSchemaId(),
				users.getRealname());
		if (result.success()) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("删除联系人失败", result.getInnerException());
		}
	}

}
