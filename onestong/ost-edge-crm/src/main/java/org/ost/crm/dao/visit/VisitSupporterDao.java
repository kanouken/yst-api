package org.ost.crm.dao.visit;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.entity.user.dto.UserListDto;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VisitSupporterDao extends Mapper<VisitSupporter> {

	List<UserListDto> selectByVisitId(@Param("schemaId") String schemaId, @Param("id") Integer id ,@Param("role") Integer role);
	

}
