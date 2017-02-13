package org.ost.edge.onestong.services.admin.domain;

import java.util.Date;

import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.dao.domain.DomainOrderDetailMapper;
import org.ost.edge.onestong.dao.domain.DomainOrderMapper;
import org.ost.edge.onestong.dao.user.UserMapper;
import org.ost.edge.onestong.model.domain.DomainOrder;
import org.ost.edge.onestong.model.user.UserExample;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DomainOrderService {
	@Autowired
	private DomainOrderMapper  domainOrderMapper;
	
	@Autowired
	private DomainOrderDetailMapper  detailMapper;
	@Autowired
	private UserMapper  userMapper;
	
	@Transactional(readOnly = true)
	public DomainOrder findDomainOrderByDomainId(Integer domainId){
		
		return this.domainOrderMapper.selectByDomainId(domainId);
	}
	
	@Transactional(readOnly = true)
	public DomainOrder findDomainOrderByDomainId(Integer domainId,RowBounds rb){
		
		return this.domainOrderMapper.selectByDomainId(domainId,rb);
	}


	@Transactional(readOnly = true)
	public int findDomainOrderDetailsByOrderId(Integer orderId){
		
		return this.detailMapper.countByDomainOrderId(orderId);
	}
	@Transactional(readOnly = true)
	public boolean isValidActive(Integer domainId){
		
		DomainOrder domainOrder = this.findDomainOrderByDomainId(domainId);
		boolean flag = false;
//		UserExample ue = new UserExample();
//		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL).andDomainIdEqualTo(domain.getDomainId());
//		
		//int count = this.userMapper.countByExample(ue);
		
		Date current = new Date();
		
		if(current.after(domainOrder.getStartTime())&& current.before(domainOrder.getEndTime())){
			
			flag  = true;
			
		}
		return flag;
		
	}
	
	
	@Transactional(readOnly = true)
	public boolean isValidActiveCodeNumValid(Integer domainId){
		
		DomainOrder domainOrder = this.findDomainOrderByDomainId(domainId);
		boolean flag = true;
		UserExample ue = new UserExample();
		ue.createCriteria().andValidEqualTo(Constants.DATA_VALID).andStateEqualTo(Constants.USER_NORMAL).andDomainIdEqualTo(domainId);
		
		int count = this.userMapper.countByExample(ue);
		
		if(count >= domainOrder.getDoSum()){
			
			flag = false;
		}
		
		
		return flag;
		
	}
	

}
