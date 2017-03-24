package org.ost.customers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.CustomerProject;
import org.ost.entity.customer.org.CustomerOrg;
import org.ost.entity.customer.user.UserCustomers;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.report.dto.KeHuReportDto;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerDao extends Mapper<Customer> {

	List<Customer> selectCustomers(@Param("paramsMap") Map<String, String> params, @Param("customer") Customer customer,
			@Param("keyword") String keyword, RowBounds rb);

	int updateCustomerSelective(@Param("customer") Customer customer);

	Integer selectCustomerCount(@Param("params") Map<String, String> params, @Param("customer") Customer customer,
			@Param("keyword") String keyword);

	List<Customer> selectByIds(@Param("ids") Integer[] ids);

	@Delete("delete from tbl_contact_info where customerID = #{customerId}")
	void deleteContactInfo(@Param("customerId") Integer customerId);

	@Delete("delete from tbl_user_customer where customerID = #{customerId}")
	void deleteUserCustomers(@Param("customerId") Integer customerId);

	@Delete("delete from tbl_organize_customer where customerID = #{customerId}")
	void deleteCustomerOrg(@Param("customerId") Integer customerId);

	@Delete("delete from tbl_address where customerID = #{customerId}")
	void deleteCustomerAddress(@Param("customerId") Integer customerId);

	List<CustomerOrg> selectCustomerOrg(@Param("customerIds") int[] customerIds);

	List<UserCustomers> selectCustomerUsers(@Param("customerIds") int[] customerIds);

	CustomerVo selectByProject(@Param("schemaId") String schemaID, @Param("projectId") Integer projectId);

	@Update("update tbl_project_customer set customerID = #{customerID} ,updateBy = #{updateBy},updateTime=#{updateTime}  where projectID = #{projectID} and schemaID =#{schemaId} ")
	Integer updateCustomerProject(CustomerProject cProject);

	// 客户报表
	//获取记录(客户总数)
	Integer selectNewCount(@Param("params") Map<String, Object> params,@Param("schemaID")String schemaID);
	//获取列表
	List<KeHuReportDto> selectReportByParam(@Param("params") Map<String, Object> params,RowBounds rowBounds,@Param("schemaID")String schemaID);
	//获取图表信息
	List<KeHuReportDto> selectReportChart(@Param("params") Map<String, Object> params,@Param("schemaID")String schemaID);
	//统计列表
	Integer selectReportCount(@Param("params") Map<String, Object> params,@Param("schemaID")String schemaID);

	

	
}
