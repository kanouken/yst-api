package org.ost.crm.dao.visit;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.crm.model.visit.Visit;
import org.ost.crm.model.visit.VisitApprover;
import org.ost.crm.model.visit.VisitProject;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.crm.model.visit.dto.VisitListDto;
import org.ost.entity.customer.Customer;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VisitDao extends Mapper<Visit> {

	void insertVisitProject(@Param("vps") List<VisitProject> vps);

	void insertVisitSupporter(@Param("vps") List<VisitSupporter> vps);

	void insertVisitApprover(@Param("vps") List<VisitApprover> vps);

	void deleteVisitProject(@Param("visitId") Integer id);

	List<VisitListDto> selectByCustomer(@Param("customer") Customer customer, @Param("params") Map<String, Object> params, RowBounds rBounds);

	Integer selectCountByCustomer(@Param("customer") Customer customer, @Param("params") Map<String, Object> params);

	List<Map<String,Object>> selectMyVisit(Map<String,Object> params, RowBounds rBounds);
	
}
