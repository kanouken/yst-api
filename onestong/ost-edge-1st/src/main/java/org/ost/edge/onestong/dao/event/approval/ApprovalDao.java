package org.ost.edge.onestong.dao.event.approval;

import org.ost.entity.event.approval.ApprovalEvent;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApprovalDao extends Mapper<ApprovalEvent> {

}
