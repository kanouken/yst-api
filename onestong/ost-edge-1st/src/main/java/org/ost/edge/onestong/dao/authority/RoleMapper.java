package org.ost.edge.onestong.dao.authority;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.authority.RoleExample;

public interface RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int countByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int deleteByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int deleteByPrimaryKey(Integer roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int insert(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int insertSelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    List<Role> selectByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    Role selectByPrimaryKey(Integer roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbggenerated Fri Jan 09 16:44:02 CST 2015
     */
    int updateByPrimaryKey(Role record);
}