package org.ost.edge.onestong.dao.event.approval;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ost.entity.event.approval.ApprovalEvent;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApprovalDao extends Mapper<ApprovalEvent> {

	@Select("select ae.ae_id aeId,ae.approval_type approvalType,ae.approval_tip approvalTip,ae.state from approval_events ae left join approval_users au  on ae.ae_id = au.approval_event_id where au.is_delete  = 0 and au.user_id = #{userId} order by ae.create_time desc")
	List<ApprovalEvent> selectTodoByUserId(@Param("userId") Integer userId);
}
