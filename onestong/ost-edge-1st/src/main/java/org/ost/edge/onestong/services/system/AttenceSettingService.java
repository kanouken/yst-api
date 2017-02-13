package org.ost.edge.onestong.services.system;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.ost.edge.onestong.dao.system.location.AttenceLocationMapper;
import org.ost.edge.onestong.model.system.location.AttenceLocation;
import org.ost.edge.onestong.model.system.location.AttenceLocationExample;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttenceSettingService {
	@Autowired
	private AttenceLocationMapper attenceLocationMapper;

	/**
	 * 获取所处域的考勤地点
	 * 
	 * @param domainId
	 */
	@Transactional(readOnly = true)
	public AttenceLocation findAttenceLocationByDomain(Integer domainId) {

		AttenceLocationExample ale = new AttenceLocationExample();

		ale.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andDomainIdEqualTo(domainId);

		List<AttenceLocation> als = this.attenceLocationMapper
				.selectByExample(ale);
		if (CollectionUtils.isEmpty(als)) {
			return null;
		} else {
			return als.get(0);
		}

	}

	/**
	 * 更新或者添加 地点
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void saveOrUpdateLocation(AttenceLocation al) {
		AttenceLocation old = this
				.findAttenceLocationByDomain(al.getDomainId());
		if (null != old) {
			// 修改
			al.setLocationId(old.getLocationId());
			al.setCreateTime(old.getCreateTime());
			al.setCreator(old.getCreator());
			this.attenceLocationMapper.updateByPrimaryKeySelective(al);
		} else {
			// new
			this.attenceLocationMapper.insertSelective(al);

		}

	}
}
