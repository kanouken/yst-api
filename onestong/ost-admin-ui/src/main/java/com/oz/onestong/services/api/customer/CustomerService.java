//package com.oz.onestong.services.api.customer;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.oz.onestong.controller.api.base.BaseService;
//import com.oz.onestong.dao.customer.CustomerMapper;
//import com.oz.onestong.dao.customer.CustomerTagMapper;
//import com.oz.onestong.dao.resources.ResourceMapper;
//import com.oz.onestong.dao.user.UserCustomerTagMapper;
//import com.oz.onestong.model.customer.Customer;
//import com.oz.onestong.model.customer.CustomerExample;
//import com.oz.onestong.model.customer.CustomerTag;
//import com.oz.onestong.model.customer.CustomerTagExample;
//import com.oz.onestong.model.resources.Resource;
//import com.oz.onestong.model.user.User;
//import com.oz.onestong.tools.Constants;
//import com.oz.onestong.tools.ResourceHelper;
//import com.oz.onestong.tools.image.CompressPicDemo;
//@Service
//public class CustomerService extends BaseService {
//	@Autowired
//	private ResourceMapper resourceMapper;
//	@Autowired
//	private CustomerMapper  customerMapper;
//	@Autowired
//	private CustomerTagMapper customerTagMapper;
//	@Autowired
//	private UserCustomerTagMapper userCustomerTagMapper;
//	
//	@Transactional(readOnly = true)
//	public List<Map<String, Object>> findCustomersByTag(Integer userId,Integer tagId){
//		
//		
//		return this.userCustomerTagMapper.selectCustomersByTag(userId,tagId);
//	}
//	@Transactional(readOnly = true)
//	public List<Map<String, Object>> findCustomerTagsByUser(Integer userId){
//		
//		
//		return this.userCustomerTagMapper.selectTagsByUser(userId);
//	}
//	
//	
//	@Transactional(readOnly = true)
//	public List<Map<String, Object>> findCustomersByUser(Integer userId){
//		
//		
//		return this.userCustomerTagMapper.selectCustomersByUser(userId);
//	}
//	
//	
//	
//	/**
//	 * 添加客户标签 关联用户
//	 * @param tag
//	 * @param user
//	 * @param cIds  客户id
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void addTag(CustomerTag tag, User user, List<Integer> cIds) {
//		customerTagMapper.insertSelective(tag);
//		userCustomerTagMapper.insertUserAndTag(user, tag);
//		
//		if(CollectionUtils.isNotEmpty(cIds)){
//			//增加 客户 与标签关联
//			
//			this.userCustomerTagMapper.insertCustomerAndTag(tag.getCtId(), cIds);
//		}
//
//	}
//	/**
//	 * 
//	 * @param tagIds  标签 id 数组
//	 * @param user 用户 
//	 */
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void deleteTags(List<Integer> tagIds,User user) {
//		CustomerTagExample cte = new CustomerTagExample();
//		cte.createCriteria().andCtIdIn(tagIds);
//		CustomerTag  customerTag  = new  CustomerTag();
//		customerTag.setUpdateTime(new Date());
//		customerTag.setUpdator(user.getRealname());
//		customerTag.setValid(Constants.DATA_INVALID);
//		this.customerTagMapper.updateByExampleSelective(customerTag, cte);
//		//删除关联 
//		this.userCustomerTagMapper.deleteUserAndTag(user.getUserId(),tagIds );
//		this.userCustomerTagMapper.deleteCustomerAndTag(tagIds);
//	}
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void updateTag(CustomerTag tag,List<Integer> remove,List<Integer> add) {
//		
//		CustomerTagExample cte = new CustomerTagExample();
//		cte.createCriteria().andCtIdEqualTo(tag.getCtId());
//		
//		this.customerTagMapper.updateByExampleSelective(tag, cte);
//		
//		
//		if(CollectionUtils.isNotEmpty(remove))
//		this.userCustomerTagMapper.deleteCustomersByTag(tag.getCtId(), remove);	
//		
//		if(CollectionUtils.isNotEmpty(add))
//			this.userCustomerTagMapper.insertCustomerAndTag(tag.getCtId(), add);
//
//		
//		
//		
//	}
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public Map<String, Object> addCustomer(Customer c, User user, MultipartFile image) throws IOException {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		
//		String thumPic = "";
//		String fileName = "";
//		if(image.getSize() > 0 && !image.isEmpty()){
//			
//			fileName = ResourceHelper
//					.saveFile(image, c.getEmail());
//			
//			Resource r = new Resource();
//			r.setCreateTime(c.getCreateTime());
//			r.setUpdateTime(c.getCreateTime());
//			r.setCreator(user.getRealname());
//			r.setUpdator(user.getRealname());
//			r.setType(Constants.R_IMAGE);
//			r.setName(fileName);
//			//图片需要压缩 44*3 44*
//			thumPic  = new StringBuilder(fileName).insert(fileName.lastIndexOf("."), "_thum").toString();
//			CompressPicDemo compress = new CompressPicDemo();
//			compress.compressPic(Constants.FILE_SAVE_DIR, Constants.FILE_SAVE_DIR, fileName, thumPic, 44*3, 44*3, true);
//			// 小图 文件名
//			r.setName1(thumPic);
//			resourceMapper.insertSelective(r);
//			// event_files
//		}
//		if(StringUtils.isNotBlank(thumPic))
//		c.setPic(thumPic);
//		else
//		c.setPic(fileName);
//		this.customerMapper.insertSelective(c);
//		this.userCustomerTagMapper.insertUserAndCustomer(user.getUserId(), c.getCustomerId());
//		
//		resultMap.put("customoerId", c.getCustomerId());
//		resultMap.put("pic", c.getPic());
//		return resultMap;
//	}
//	
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void updateCustomer(Customer c) {
//		CustomerExample ce  = new CustomerExample();
//		ce.createCriteria().andCustomerIdEqualTo(c.getCustomerId());
//		this.customerMapper.updateByExampleSelective(c, ce);
//	}
//	
//	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
//	public void deleteCustomer(Customer c, List<Integer> customerIds) {
//		CustomerExample ce  = new CustomerExample();
//		ce.createCriteria().andCustomerIdIn(customerIds);
//		this.customerMapper.updateByExampleSelective(c, ce);
//	}
//
//}
