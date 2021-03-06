package com.oz.onestong.services.system;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oz.onestong.dao.system.worktime.WorktimeMapper;
import com.oz.onestong.model.system.worktime.Worktime;
import com.oz.onestong.model.system.worktime.WorktimeExample;
import com.oz.onestong.tools.Constants;

@Service
public class WorktimeService {
	@Autowired
	private WorktimeMapper worktimeMapper;

	/**
	 * 获取所处域的工作时间
	 * 
	 * @param domainId
	 */
	@Transactional(readOnly = true)
	public Worktime findWorktimeByDomain(Integer domainId) {

		WorktimeExample we = new WorktimeExample();

		we.createCriteria().andValidEqualTo(Constants.DATA_VALID)
				.andDomainIdEqualTo(domainId);

		List<Worktime> wes = this.worktimeMapper.selectByExample(we);
		if (CollectionUtils.isEmpty(wes)) {
			return null;
		} else {
			return wes.get(0);
		}

	}

	/**
	 * 更新工作时间
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateWorktime(Worktime wt) {
		this.worktimeMapper.updateByPrimaryKeySelective(wt);
	}

}
