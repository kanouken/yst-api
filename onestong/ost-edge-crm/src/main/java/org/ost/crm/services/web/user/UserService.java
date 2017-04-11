package org.ost.crm.services.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.md5.Md5Util;
import org.common.tools.spring.UniqueTokenGenerator;
import org.ost.crm.dao.web.user.UserDao;
import org.ost.crm.model.web.user.AccountDto;
import org.ost.crm.model.web.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public OperateResult queryLogin(String email, String password) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDto userDto = this.userDao.selectByEmail(email);
		OperateResult operateResult = new OperateResult();
		if (userDto == null) {
			operateResult.setStatusCode("500");
			operateResult.setDescription("未知用户!");
			operateResult.setData(null);
			return operateResult;
		}
		AccountDto accountDto = new AccountDto();
		accountDto.setLoginName(userDto.getEmail());
		accountDto.setLoginPassword(password);
		if (!accountDto.getLoginPassword().equals(password)) {
			operateResult.setStatusCode("500");
			operateResult.setDescription("密码不正确！");
			operateResult.setData(null);
			return operateResult;
		}
		// 更新token
		String token = UniqueTokenGenerator.generateUniqueToken();
		accountDto.setToken(token);
		userDto = this.userDao.selectLogin(email, password);
		operateResult.setDescription("login success");
		resultMap.put("userId", userDto.getUserId());
		resultMap.put("realName", userDto.getRealName());
		resultMap.put("email", userDto.getEmail());
		resultMap.put("deptId", userDto.getDeptId());
		resultMap.put("deptName", userDto.getDeptName());
		resultMap.put("schemaId", userDto.getDomainId());
		resultMap.put("isDirector", userDto.getIsDirector());
		
		resultMap.put("token",
				Jwts.builder().setSubject("1st").claim("userId", userDto.getUserId())
						.claim("realName", userDto.getRealName()).claim("email", userDto.getEmail())
						.claim("deptId", userDto.getDeptId()).claim("deptName", userDto.getDeptName())
						.claim("schemaId", userDto.getDomainId()).claim("isDirector", userDto.getIsDirector())
						.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "1stapp").compact());

		operateResult.setData(resultMap);
		return operateResult;
	}

}
