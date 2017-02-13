package org.ost.edge.onestong.services.web.account;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.ost.edge.onestong.dao.account.AccountMapper;
import org.ost.edge.onestong.dao.user.UserMapper;
import org.ost.edge.onestong.model.account.Account;
import org.ost.edge.onestong.model.account.AccountExample;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
	@Autowired
	private AccountMapper  accountMapper;
	
	@Autowired
	private UserMapper userMapper;
	@Transactional(readOnly = true)
	public boolean isTokenValid(String token){
		AccountExample ae  = new  AccountExample();
		ae.createCriteria().andTokenEqualTo(token);
		Integer count  = this.accountMapper.countByExample(ae);
		
		if(0 < count){
			return true;
		}else{
			
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findHxNameByNickName(String nickName){
	 return 	this.userMapper.selectAccountByUserRealName("%"+nickName+"%");
	}
	
	@Transactional(readOnly = true)
	public Account findOneByAccountLoginName(String loginName){
		AccountExample ae  = new  AccountExample();
		ae.createCriteria().andLoginNameEqualTo(loginName).andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL);
		
		List<Account> accounts = this.accountMapper.selectByExample(ae);
		if(CollectionUtils.isNotEmpty(accounts)){
			return accounts.get(0);
		}else{
			
			return null;
		}
		
		
	}

	@Transactional(readOnly = true)
	public Account findOneById(Integer  id){
		
		return this.accountMapper.selectByPrimaryKey(id);
		
	}

	
}
