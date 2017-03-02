package org.ost.contacts.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.ost.contacts.model.ApprovalUsers;
import org.ost.contacts.model.ApprovalUsersExample;

public interface ApprovalUsersMapper {
    int countByExample(ApprovalUsersExample example);

    int deleteByExample(ApprovalUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalUsers record);

    int insertSelective(ApprovalUsers record);

    List<ApprovalUsers> selectByExample(ApprovalUsersExample example);

    ApprovalUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApprovalUsers record, @Param("example") ApprovalUsersExample example);

    int updateByExample(@Param("record") ApprovalUsers record, @Param("example") ApprovalUsersExample example);

    int updateByPrimaryKeySelective(ApprovalUsers record);

    int updateByPrimaryKey(ApprovalUsers record);
}