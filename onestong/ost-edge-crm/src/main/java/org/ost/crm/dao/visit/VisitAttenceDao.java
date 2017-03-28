package org.ost.crm.dao.visit;

import org.apache.ibatis.annotations.Param;
import org.ost.crm.model.visit.VisitAttence;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VisitAttenceDao extends Mapper<VisitAttence> {
	VisitAttence selectAttence(@Param("visitId") Integer visitId, @Param("userId") Integer userId, @Param("schemaId") String schemaId);

}
