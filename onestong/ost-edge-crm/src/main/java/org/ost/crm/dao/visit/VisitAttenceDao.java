package org.ost.crm.dao.visit;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.crm.model.visit.VisitAttence;
import org.ost.entity.notification.visit.UnSignOutVisit;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VisitAttenceDao extends Mapper<VisitAttence> {
	VisitAttence selectAttence(@Param("visitId") Integer visitId, @Param("userId") Integer userId, @Param("schemaId") String schemaId);

	List<UnSignOutVisit> selectUnSignOut();

}
