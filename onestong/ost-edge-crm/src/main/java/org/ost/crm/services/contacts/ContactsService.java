package org.ost.crm.services.contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.bouncycastle.jcajce.provider.asymmetric.RSA;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.ContactsServiceClient;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsCreateDto;
import org.ost.entity.contacts.dto.ContactsDetailDto;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerUpdateDto;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactsService {

	@Autowired
	private ContactsServiceClient contactsServiceClient;
	@Autowired
	private CustomerServiceClient customerServiceClient;

	public ContactsCreateDto createContacts(Users users, ContactsCreateDto contactsCreateDto) {
		ContactsDto contactsDto = ContactsEntityMapper.INSTANCE.contactsCreateDtoToContactsDto(contactsCreateDto);
		contactsDto.setSchemaId(users.getSchemaId());
		contactsDto.setCurrentUserName(users.getRealname());
		OperateResult<ContactsDto> result = this.contactsServiceClient.createContact(users.getSchemaId(), contactsDto);
		if (result.getData() != null) {
			// success
			// 更新 客户 联系人关系
			CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();
			customerUpdateDto.setUpdateBy(users.getRealname());
			customerUpdateDto.setId(contactsCreateDto.getCustomer().getId());
			List<ContactsDto> contactsDtos = new ArrayList<ContactsDto>();
			contactsDtos.add(contactsDto);
			customerUpdateDto.setContacts(contactsDtos);
			OperateResult<Integer> result2 = this.customerServiceClient
					.updateCustomer(contactsCreateDto.getCustomer().getId(), customerUpdateDto);
			if (result2.getData() == null) {
				// TODO transcation
				throw new ApiException("联系人创建失败", result2.getInnerException());
			}
		} else {
			throw new ApiException("联系人创建失败", result.getInnerException());
		}
		return contactsCreateDto;
	}

	public ContactsDetailDto queryDetail(Integer contactsId, Users users) {
		OperateResult<ContactsDto> result = this.contactsServiceClient.queryDetail(contactsId, users.getSchemaId());
		ContactsDto contactsDto = result.getData();
		ContactsDetailDto detailDto = ContactsEntityMapper.INSTANCE.contactsDtoToContactsDetailDto(contactsDto);
		detailDto.setCustomer(
				this.customerServiceClient.queryDetailByContacts(users.getSchemaId(), contactsId).getData());
		return detailDto;
	}

	public PageEntity<ContactsListDto> queryContacts(String schemaID, Integer customerID, String keyword, String name,
			String phone, Users users, Integer curPage, Integer perPageSum) {
		OperateResult<PageEntity<ContactsListDto>> result = contactsServiceClient.queryContacts(curPage, schemaID,
				perPageSum, null, name, phone, customerID);
		OperateResult<List<CustomerListDto>> result2 = this.customerServiceClient.queryByIds(schemaID,
				result.getData().getObjects().stream().mapToInt(d -> d.getCustomerID()).toArray());

		result.getData().getObjects().forEach(contactsListDto -> {
			Optional<CustomerListDto> _tmp = result2.getData().stream()
					.filter(customer -> customer.getId().equals(contactsListDto.getCustomerID())).findFirst();
			if (_tmp.isPresent()) {
				contactsListDto.setCustomer(new CustomerVo(_tmp.get().getId(), _tmp.get().getName()));
			}
		});

		return result.getData();
	}

}
