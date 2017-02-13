package org.ost.edge.onestong.controller.api.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.customer.Customer;
import org.ost.edge.onestong.model.customer.CustomerTag;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.customer.CustomerService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
/**
 * 客户 crud 客户标签 crud
 * @author mac
 *
 */
@Controller
@RequestMapping("/api/customer")
public class CustomerDataApi extends Action {

	@Autowired
	private UsersService usersService;
	@Autowired
	private CustomerService customerService;
	/**
	 * 根据 分组 查询 用户客户
	 * @param userId
	 * @param token
	 * @param groupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="{userId}/{groupId}/members/{token}")
	public Object findCustomersByGroup(@PathVariable("userId") Integer userId,@PathVariable("token") String token,
			@PathVariable("groupId") Integer groupId){
		
		
		OperateResult op = new OperateResult();
		try {
			
			List<Map<String, Object>> tags =  this.customerService.findCustomersByTag(userId, groupId);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("find customer by group    success!");
			op.setData(tags);
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {

				logger.error("按分组获取客户 失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}

	
	
	@ResponseBody
	@RequestMapping(value="all/{userId}/{token}")
	public Object findCustomersByUser(@PathVariable("userId") Integer userId,@PathVariable("token") String token){
		
		
		OperateResult op = new OperateResult();
		try {
			
			List<Map<String, Object>> tags =  this.customerService.findCustomersByUser(userId);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("find customer group   success!");
			op.setData(tags);
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {

				logger.error("获取用户 客户 失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}
	
	@ResponseBody
	@RequestMapping(value="group/{userId}/{token}")
	public Object findCustomerGroup(@PathVariable("userId") Integer userId,@PathVariable("token") String token){
		
		
		OperateResult op = new OperateResult();
		try {
			
			List<Map<String, Object>> tags =  this.customerService.findCustomerTagsByUser(userId);
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("find customer group   success!");
			op.setData(tags);
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {

				logger.error("获取客户分组失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}
	
	
	/**
	 * 更新客户信息 或者 设为 共有客户 {"userId":"3","customerId":"3","customerName":"test","customerCompany":"湖北武汉XX公司","isPublic":"1"}
	 */
	@ResponseBody
	@RequestMapping(value="update/{token}",method=RequestMethod.POST)
	public Object updateCustomer(@PathVariable("token") String token,@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		try {
			Object userId = entity.get("userId");
			Object customerId = entity.get("customerId");
			Object customerName = entity.get("customerName");
			Object customerCompany = entity.get("customerCompany");
			Object isPublic = entity.get("isPublic");
			Customer  c  = new  Customer();
			if(null == userId){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  must be provided !");
				return op;
			}else if(null == customerId){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customerId  must be provided !");
				return op;
			}
			User user = usersService.findOneById(Integer.valueOf(userId.toString().trim()));
			if(null == user){
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			c.setCustomerId(Integer.valueOf(customerId.toString().trim()));
			c.setName(customerName!= null?customerName.toString().trim():null);
			c.setCompany(customerCompany!= null?customerCompany.toString().trim():null);
			c.setUpdateTime(new Date());
			c.setUpdator(user.getRealname());
			c.setIsPublic(Byte.valueOf(isPublic.toString().trim()));
			this.customerService.updateCustomer(c);
			
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("customer update success!");
			
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {

				logger.error("客户更新失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}
	/**
	 * 添加客户   加入客户头像
	 * @param token  {"userId":"3","customerName":"test001","customerCompany":"湖北武汉xx公司","customerPhone":"12324234354345"}
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add/{token}",method=RequestMethod.POST)
	public Object add(@PathVariable("token") String token,
			@RequestParam("entity") String entity,@RequestParam(required = false) MultipartFile image) {
		OperateResult op = new OperateResult();
		
		JSONObject requestAtt = JSONObject.fromObject(entity);
		Object  userId  = requestAtt.get("userId");
		Object  customerName  = requestAtt.get("customerName");
		Object  customerCompany  = requestAtt.get("customerCompany");
		Object  customerPhone  = requestAtt.get("customerPhone");
		Object email   =  requestAtt.get("email");
		Object  qq  = requestAtt.get("qq");
		Object  tel = requestAtt.get("tel");
		Object  department = requestAtt.get("department");
		Object  position= requestAtt.get("position");
		
		
		try {
			if(null == userId){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  must be provided !");
				return op;
			}else if(null == customerName){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'name  must be provided !");
				return op;
			}else if(null == customerCompany){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'company  must be provided !");
				return op;
			}else if(null == customerPhone){
				
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'contact phone  must be provided !");
				return op;
			}else if(null == qq){
				
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'qq phone  must be provided !");
				return op;
			}else if(null == tel){
				
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'contact tel  must be provided !");
				return op;
			}else if(null == department){
				
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'department  must be provided !");
				return op;
			}else if(null == position){
				
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'position  must be provided !");
				return op;
			}
			
			User  user =  usersService.findOneById(Integer.valueOf(userId.toString().trim()));
			if(null == user){
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			Customer  c = new Customer();
			Date  currentTime = new Date();
			c.setEmail(email==null?null:email.toString());
			c.setName(customerName.toString());
			c.setCompany(customerCompany.toString());
			c.setCreateTime(currentTime);
			c.setUpdateTime(currentTime);
			c.setCreator(user.getRealname());
			c.setUpdator(user.getRealname());
			c.setPhone(customerPhone.toString());
			c.setQq(qq.toString());
			c.setTel(tel.toString());
			c.setDepartment(department.toString());
			c.setPosition(position.toString());
			Map<String, Object> resultEntity = this.customerService.addCustomer(c,user,image);
			
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("customer add success!");
			op.setData(resultEntity);
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {

				logger.error("客户 添加 失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}

	
	/**
	 * 删除客户   {"userId":"3","customerIds":[1,2,3]}
	 * @param token
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "delete/{token}",method = RequestMethod.POST)
	public Object delete(@PathVariable("token") String token,@RequestBody Map<String, Object> entity) {
		OperateResult op = new OperateResult();
		try {
			Object userId = entity.get("userId");
			Object customerIds = entity.get("customerIds");
			if(null == userId){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  must be provided !");
				return op;
			}else if(null == customerIds||((List<Integer>) customerIds).size() < 0){
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("customer'id  must be array !");
				return op;
			}
			
			User  user =  usersService.findOneById(Integer.valueOf(userId.toString().trim()));
			if(null == user){
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			Customer  c = new Customer();
			Date  currentTime = new Date();
			c.setValid(Constants.DATA_INVALID);
			c.setUpdateTime(currentTime);
			c.setUpdator(user.getRealname());
			this.customerService.deleteCustomer(c,(List<Integer>)customerIds);
			
			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("customer delete success!");
			
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {

				logger.error("客户 删除 失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;
	}

	/**
	 * 查询自己的客户
	 */
	public void findAll() {
		// TODO Auto-generated method stub

	}

	/**
	 * 添加客户标签 顺便添加 客户（如果有） curl -H "Content-type:application/json" -POST -d
	 * '{"userId":3,"tagName":"闹眼子" , "customerIds":[1,5]}'
	 * "http://localhost:8080/ONESTONG/api/customer/tag/add/asfafasfafafafa"
	 * 
	 * @param tag
	 *            标签
	 * @param user
	 *            用户
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "tag/add/{token}", method = RequestMethod.POST)
	public Object addTag(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {
		// TODO token 检查
		OperateResult op = new OperateResult();
		try {
			Object userId = entity.get("userId");
			Object tagName = entity.get("tagName");
			Object customerIds = entity.get("customerIds");
			List<Integer> cIds = new ArrayList<Integer>();
			if (null == userId) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  must be provided!");
				return op;
			} else if (null == tagName || "".equals(tagName.toString().trim())) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("tag name must be provided!");
				return op;
			}

			if (null != customerIds
					&& 0 < (cIds = (List<Integer>) customerIds).size()) {
				// TODO 客户 id 的合法性校验
			} else {

				cIds = null;
			}

			User user = new User();
			user = usersService.findOneById(Integer.valueOf(userId.toString()
					.trim()));
			Date currentTime = new Date();
			CustomerTag tag = new CustomerTag();
			tag.setCreateTime(currentTime);
			tag.setName(tagName.toString());
			tag.setCreator(user.getRealname());
			tag.setUpdator(user.getRealname());
			tag.setUpdateTime(currentTime);
			this.customerService.addTag(tag, user, cIds);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("add customer's tag success!");
			op.setData(tag);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("客户标签 添加 失败 -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}

		return op;
	}

	/**
	 * curl -X POST -H "Content-Type: application/json" -d {userId=3, tagIds=[8,
	 * 100]} -i
	 * "http://localhost:8080/ONESTONG/api/customer/tag/delete/asfafasfafafafa
	 */
	/**
	 * 删除(批量删除)客户标签 {"name":"tst","ids":[1,2]}
	 * 
	 * @param tag
	 *            标签
	 * @param user
	 *            用户
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "tag/delete/{token}", method = RequestMethod.POST)
	public Object deleteTag(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {

		User user = null;
		// TODO token 检查
		OperateResult op = new OperateResult();
		try {

			Integer userId = Integer.valueOf(entity.get("userId").toString()
					.trim());
			List<Integer> tagIds = (List<Integer>) entity.get("tagIds");

			if (null == userId) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  must be privoed!");
				return op;
			} else if (null == tagIds) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("tagIds  must be privoed!");
				return op;
			}

			user = usersService.findOneById(userId);

			this.customerService.deleteTags(tagIds, user);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("delete customer tag success");

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("客户标签 删除失败  -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}

		return op;
	}

	public void findAllAndGroupByTag() {
		// TODO Auto-generated method stub

	}

	/**
	 * 修改标签名称 移除 标签内成员 添加标签内成员 {"userId":"3","tagName":"newName",
	 * "tagId":"7","remove":[1,2,3],"add":[4,5,6]}
	 * 
	 * @param token
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "tag/update/{token}", method = RequestMethod.POST)
	public Object updateTag(@PathVariable("token") String token,
			@RequestBody Map<String, Object> entity) {

		User user = null;
		// TODO token 检查
		OperateResult op = new OperateResult();
		try {

			Object userId = entity.get("userId");
			Object tagId = entity.get("tagId");
			Object newName = entity.get("tagName");
			List<Integer> remove = entity.get("remove") != null
					&& 0 < ((List<Integer>) entity.get("remove")).size() ? (List<Integer>) entity
					.get("remove") : null;
			List<Integer> add = entity.get("add") != null
					&& 0 < ((List<Integer>) entity.get("add")).size() ? (List<Integer>) entity
					.get("add") : null;

			if (null == userId) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("userId  must be provided!");
				return op;
			} else if (null == tagId) {
				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("tagId  must be provided!");
				return op;
			}

			user = usersService.findOneById(Integer.valueOf(userId.toString()
					.trim()));

			if (null == user) {
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			Date currentTime = new Date();
			CustomerTag tag = new CustomerTag();
			tag.setCtId(Integer.valueOf(tagId.toString().trim()));
			tag.setName(newName == null ? null : newName.toString().trim());
			tag.setUpdateTime(currentTime);
			tag.setUpdator(user.getRealname());
			this.customerService.updateTag(tag, remove, add);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("update customer tag success");

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("客户标签 更新失败  -", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);

		}

		return op;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("name", "tst");
		List<Integer> tmp = new ArrayList<Integer>();

		tmp.add(1);
		tmp.add(2);

		json.put("ids", tmp);
		System.out.println(json.toString());
	}
}
