package com.oz.onestong.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.oz.onestong.model.customer.CustomerTag;
import com.oz.onestong.model.user.User;

/**
 * 用户 客户  客户标签 
 * @author xnq
 *
 */
public interface UserCustomerTagMapper {
	
	
	
	
	List<Map<String, Object>> selectCustomersByUser(@Param("userId") Integer userId);
	
	
	List<Map<String, Object>> selectTagsByUser(@Param("userId") Integer userId);
	
	
	void insertUserAndTag(@Param("user") User user,@Param("tag") CustomerTag tag );
	
	
	void insertCustomerAndTag(@Param("tagId") Integer tagId, @Param("customerIds") List<Integer> customerIds );
	/**
	 * 删除 与 tagId 关联的 客户 
	 * @param tagId
	 */
	void deleteCustomerAndTag(@Param("tagIds") List<Integer> tagIds);
	
	/**
	 * 删除 userId 下 的 tag关联 tag可以有多个
	 * @param tagId
	 */
	void deleteUserAndTag(@Param("userId")Integer userId,@Param("tagIds") List<Integer> tagIds);
	
	void deleteCustomersByTag(@Param("tagId") Integer tagId,@Param("customerIds") List<Integer> customerIds );
	
	
	
	void insertUserAndCustomer(@Param("userId")Integer userId,@Param("customerId") Integer customerId);


	List<Map<String, Object>> selectCustomersByTag(@Param("userId")  Integer userId,@Param("tagId") Integer tagId);
}
