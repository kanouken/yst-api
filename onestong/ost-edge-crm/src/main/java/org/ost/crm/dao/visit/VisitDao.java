package org.ost.crm.dao.visit;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.crm.model.visit.Visit;
import org.ost.crm.model.visit.VisitApprover;
import org.ost.crm.model.visit.VisitProject;
import org.ost.crm.model.visit.VisitSupporter;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VisitDao extends Mapper<Visit> {

	void insertVisitProject(@Param("vps") List<VisitProject> vps);

	void insertVisitSupporter(@Param("vps") List<VisitSupporter> vps);

	void insertVisitApprover(@Param("vps") List<VisitApprover> vps);

}
