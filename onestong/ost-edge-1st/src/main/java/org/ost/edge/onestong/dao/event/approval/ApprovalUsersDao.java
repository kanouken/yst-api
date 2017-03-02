package org.ost.edge.onestong.dao.event.approval;

import org.ost.entity.event.approval.ApprovalUsers;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApprovalUsersDao extends Mapper<ApprovalUsers> {

}
