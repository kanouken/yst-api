package org.ost.edge.onestong.services.admin;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.md5.Md5Util;
import org.ost.edge.onestong.dao.admin.AdminMapper;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Transactional(readOnly = true)
	public OperateResult login(String loginName, String password)
			throws NoSuchAlgorithmException {

		OperateResult op = new OperateResult();

		Map<String, Object> admin = this.adminMapper
				.selectOneByLoginName(loginName);
		if (null == admin) {

			op.setStatusCode(Constants.USER_NOT_FOUND);
			op.setDescription("user was not foud!");
			return op;

		} else {

			if (!Md5Util.md5(password).equals(
					admin.get("login_password").toString().trim())) {

				op.setStatusCode(Constants.PASSWORD_INCORRECT);
				op.setDescription("password was not correct!");
				return op;

			} else {

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("login success!");
				op.setData(admin);
				return op;

			}

		}

	}
}
