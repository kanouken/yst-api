package org.ost.auth.dao;

import org.ost.entity.user.Users;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserDao extends Mapper<Users> {

}
