package com.oz.onestong.services.admin.domain;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.md5.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oz.onestong.dao.domain.DomainMapper;
import com.oz.onestong.dao.domain.DomainOrderDetailMapper;
import com.oz.onestong.dao.domain.DomainOrderMapper;
import com.oz.onestong.dao.system.worktime.WorktimeMapper;
import com.oz.onestong.model.domain.Domain;
import com.oz.onestong.model.domain.DomainExample;
import com.oz.onestong.model.domain.DomainExample.Criteria;
import com.oz.onestong.model.domain.DomainOrder;
import com.oz.onestong.model.domain.DomainOrderDetail;
import com.oz.onestong.model.system.worktime.Worktime;
import com.oz.onestong.tools.Constants;
import com.oz.onestong.tools.mail.MailHelper;
import com.oz.onestong.tools.mail.MailSenderInfo;

@Service
public class DomainService {
	
	@Autowired
	private WorktimeMapper  worktimeMapper;
	@Autowired
	private DomainMapper domainMapper;
	@Autowired
	private DomainOrderDetailMapper  domainOrderDetailMapper;
	@Autowired
	private DomainOrderMapper domainOrderMapper;
	@Transactional(readOnly = true)
	public int findAllDomainUsersCount(Domain domain) {
		DomainExample de = new DomainExample();
		Criteria c = de.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID);

		if (StringUtils.isNotBlank(domain.getDomainName())) {

			c.andDomainNameLike("%" + domain.getDomainName() + "%");
		}

		return this.domainMapper.countByExample(de);

	}

	@Transactional(readOnly = true)
	public List<Domain> findAllDomainUsers(Domain domain, RowBounds rb) {
		DomainExample de = new DomainExample();
		Criteria c = de.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID);

		if (StringUtils.isNotBlank(domain.getDomainName())) {

			c.andDomainNameLike("%" + domain.getDomainName() + "%");
		}
		de.setOrderByClause("update_time desc");

		return domainMapper.selectByExample(de, rb);

	}

	@Transactional(readOnly = true)
	public Domain findOneByLoginName(String loginName) {
		DomainExample de = new DomainExample();
		de.createCriteria().andLoginNameEqualTo(loginName)
				.andValidEqualTo(Constants.DATA_VALID);

		List<Domain> domains = domainMapper.selectByExample(de);
		if (CollectionUtils.isNotEmpty(domains)) {
			return domains.get(0);
		} else {

			return null;
		}

	}

	@Transactional(readOnly = true)
	public Domain findOneById(Integer domainId) {

		return this.domainMapper.selectByPrimaryKey(domainId);

	}
	
	/**
	 * 添加 域 使用者 并 第一次初始化 激活码 添加工作时间设定
	 * @throws NoSuchAlgorithmException 
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void addDomainUserAndPreOrder(Domain domain,DomainOrder order,Worktime wt) throws NoSuchAlgorithmException{
		String password = domain.getLoginPassword();
		domain.setLoginPassword(Md5Util.md5(domain.getLoginPassword()));
		this.domainMapper.insertSelective(domain);
		order.setDomainId(domain.getDomainId());
		domainOrderMapper.insertSelective(order);
		//工作时间设定
		wt.setDomainId(domain.getDomainId());
		this.worktimeMapper.insertSelective(wt);
		//发送邮件
		 MailSenderInfo mailInfo = new MailSenderInfo();
	     mailInfo.setMailServerHost("mx.mc2.cn");
	     mailInfo.setMailServerPort("587");
	     mailInfo.setValidate(true);
	     mailInfo.setUserName("noreply_onestong@mc2.cn");
	     mailInfo.setPassword("Noreply2014WH");
	     mailInfo.setFromAddress("noreply_onestong@mc2.cn");
	     mailInfo.setToAddress(domain.getEmail());
	     mailInfo.setSubject("onestong系统管理员");

	     DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	     MailHelper sms = new MailHelper();
	     sms.setMailInfo(mailInfo);
	     String contentString=sms.GetSendMsg(domain.getDomainName(), domain.getLoginName(), password, order.getDoSum(), df.format(order.getStartTime()), df.format(order.getEndTime()));
	     mailInfo.setContent(contentString);
	     sms.start();

		
	}
	
	
	
	/**
	 * 修改域使用者信息 
	 * @throws NoSuchAlgorithmException 
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateDomainUserAndPreOrder(Domain domain,DomainOrder order) throws NoSuchAlgorithmException{
	
		
		if(StringUtils.isNotBlank(domain.getLoginPassword())){
			domain.setLoginPassword(Md5Util.md5(domain.getLoginPassword()));
		}else{
			
			domain.setLoginPassword(null);
		}
		this.domainMapper.updateByPrimaryKeySelective(domain);
		//order.setDomainId(domain.getDomainId());
		domainOrderMapper.updateByPrimaryKeySelective(order);
		//发送邮件
		 
		
	}
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void addActiveCode(DomainOrder order,DomainOrderDetail dod) {
		//添加 一条 详情 记录 
		dod.setOrderId(order.getDoId());
		this.domainOrderDetailMapper.insertSelective(dod);
		//总记录数 加
		
		order.setDoSum(order.getDoSum()+dod.getOdSum());
		this.domainOrderMapper.updateByPrimaryKeySelective(order);
	}

}
