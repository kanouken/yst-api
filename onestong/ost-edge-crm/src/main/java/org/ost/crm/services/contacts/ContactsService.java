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
import org.ost.crm.services.department.DepartmentService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsCreateDto;
import org.ost.entity.contacts.dto.ContactsDetailDto;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.org.department.Departments;
import org.ost.entity.user.Users;
import org.ost.entity.user.UsersRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import sun.tools.tree.ThisExpression;

@Service
public class ContactsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ContactsServiceClient contactsServiceClient;
	@Autowired
	private CustomerServiceClient customerServiceClient;

	@Autowired
	private DepartmentService departmentService;

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
	 * FIXME 无分页
	 * 
	 * @param visitId
	 * @param currentUser
	 * @return
	 */
	private Map<String, Object> queryContactsByVisit(Integer visitId, Users currentUser) {
		OperateResult<List<ContactsListDto>> result = contactsServiceClient.queryByVisit(currentUser.getSchemaId(),
				visitId);
		List<ContactsListDto> records = new ArrayList<ContactsListDto>();
		if (result.success()) {
			records = result.getData();
			if (CollectionUtils.isNotEmpty(result.getData())) {
				int[] customerIds = result.getData().stream().mapToInt(c -> c.getCustomerID()).toArray();
				OperateResult<List<CustomerListDto>> result2 = this.customerServiceClient
						.queryByIds(currentUser.getSchemaId(), customerIds);
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
			page.setCurPage(1);
			page.setTotalRecords(result.getData().size());
			return OperateResult.renderPage(page, result.getData());
		} else {
			throw new ApiException("获取联系人列表失败", result.getInnerException());
		}
	}

	/**
	 * @see #queryContactsByCustomer(Integer, Integer, String, String, String,
	 *      Users, Integer, Integer)
	 * @param customerID
	 * @param customerId
	 * @param keyword
	 * @param name
	 * @param phone
	 * @param users
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	@Deprecated
	public Map<String, Object> queryContacts(Integer visitId, Integer customerID, String keyword, String name,
			String phone, Users users, Integer curPage, Integer perPageSum) {
		logger.info("调用参数 keyword " + keyword + "-name " + name);
		if (visitId != null) {
			return this.queryContactsByVisit(visitId, users);
		}

		OperateResult<PageEntity<ContactsListDto>> result = contactsServiceClient.contactList(curPage,
				users.getSchemaId(), perPageSum, null, name, phone, keyword, customerID);
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
	 * 联系人列表 FIXME YSTCRM-280 1. 普通员工-联系人列表只能显示归属自己客户下的联系人。 2.
	 * 部门主管可以查看本部门所有客户，以及下级部门所有客户下的联系人。
	 * 
	 * @param visitId
	 * @param customerID
	 * @param keyword
	 * @param name
	 * @param phone
	 * @param users
	 * @param curPage
	 * @param perPageSum
	 * @return
	 */
	public Map<String, Object> queryContactsUserScoped(Integer visitId, Integer customerID, String keyword, String name,
			String phone, Users current, Integer curPage, Integer perPageSum) {
		logger.info("调用参数 keyword " + keyword + "-name " + name);
		if (visitId != null) {
			return this.queryContactsByVisit(visitId, current);
		}

		Boolean isDirector = false;
		if (current.getRole() != null) {
			if (current.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER.getCode())) {
				isDirector = true;
			}
		}
		OperateResult<PageEntity<CustomerListDto>> customerResult = null;
		OperateResult<PageEntity<ContactsListDto>> contactsResult = null;
		CustomerQueryDto queryDto = new CustomerQueryDto();
		List<ContactsListDto> records = new ArrayList<ContactsListDto>();
		Integer totalRecords = 0;
		if (isDirector) {
			// 部门主管可以查看本部门所有客户，以及下级部门所有客户下的联系人。
			Departments dept = new Departments();
			dept.setDeptId(current.getDeptId());
			List<Departments> deptsDepartments = departmentService.queryDepartmentRecursion(dept);
			if (CollectionUtils.isNotEmpty(deptsDepartments)) {
				List<Integer> deptIds = deptsDepartments.stream().map(d -> d.getDeptId()).collect(Collectors.toList());
				queryDto.setDeptIds(deptIds);
			} else {
				throw new ApiException("参数错误", "" + current.getUserId());
			}
			customerResult = this.customerServiceClient.queryCustomerByDept(current.getSchemaId(), 1, Integer.MAX_VALUE,
					queryDto);
		} else {
			// 普通员工-联系人列表只能显示归属自己客户下的联系人
			queryDto.setUserId(current.getUserId());
			customerResult = this.customerServiceClient.queryCustomerByUser(current.getSchemaId(), 1, Integer.MAX_VALUE,
					queryDto);
		}
		if (customerResult.success()) {
			if (CollectionUtils.isNotEmpty(customerResult.getData().getObjects())) {
				String customerIds = customerResult.getData().getObjects().stream().map(c -> String.valueOf(c.getId()))
						.collect(Collectors.joining(","));
				contactsResult = contactsServiceClient.queryByCustomer(curPage, current.getSchemaId(), perPageSum, null,
						name, phone, keyword, customerIds);
				List<CustomerListDto> customers = customerResult.getData().getObjects();
				if (contactsResult.success()) {
					records = contactsResult.getData().getObjects();
					totalRecords = contactsResult.getData().getTotalRecord();
					if (CollectionUtils.isNotEmpty(contactsResult.getData().getObjects())) {
						records.forEach(contact -> {
							Optional<CustomerListDto> _rOptional = customers.stream()
									.filter(c -> c.getId() == contact.getCustomerID()).findFirst();
							if (_rOptional.isPresent()) {
								contact.setCustomer(
										new CustomerVo(_rOptional.get().getId(), _rOptional.get().getName()));
							}
						});
					}

				} else {
					throw new ApiException("获取联系人列表失败", contactsResult.getInnerException());
				}
			}
		} else {
			throw new ApiException("获取联系人列表失败", customerResult.getInnerException());
		}
		Page page = new Page();
		page.setCurPage(curPage);
		page.setTotalRecords(totalRecords);
		return OperateResult.renderPage(page,records);
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
