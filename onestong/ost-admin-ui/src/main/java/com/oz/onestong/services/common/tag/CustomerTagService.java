package com.oz.onestong.services.common.tag;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oz.onestong.dao.tag.CTagMapper;
import com.oz.onestong.dao.tag.TagEventMapper;
import com.oz.onestong.model.tag.CTag;
import com.oz.onestong.model.tag.CTagExample;
import com.oz.onestong.model.tag.CTagExample.Criteria;
import com.oz.onestong.tools.Constants;

@Service
public class CustomerTagService {
	@Autowired
	private CTagMapper cTagMapper;

	private @Autowired
	TagEventMapper tagEventMapper;

	@Transactional(readOnly = true)
	public int findCustomerTagCountByTag(CTag tag) {
		CTagExample cte = new CTagExample();
		Criteria c = cte.createCriteria();

		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(
				tag.getDomainId());
		if (StringUtils.isNotBlank(tag.getcTName())) {

			c.andCTNameLike("%" + tag.getcTName() + "%");
		}
		return this.cTagMapper.countByExample(cte);
	}

	@Transactional(readOnly = true)
	public List<CTag> findCustomerTagsByTag(CTag tag, RowBounds rb) {
		CTagExample cte = new CTagExample();
		Criteria c = cte.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(
				tag.getDomainId());
		if (StringUtils.isNotBlank(tag.getcTName())) {
			c.andCTNameLike("%" + tag.getcTName() + "%");
		}
		cte.setOrderByClause("update_time desc");

		if (null != rb)
			return this.cTagMapper.selectByExample(cte, rb);
		else
			return this.cTagMapper.selectByExample(cte);
	}

	@Transactional(readOnly = true)
	public int findCustomerRelatedEventByTag(CTag tag, String keyword) {
		if (StringUtils.isNotBlank(keyword)) {

			keyword = "%" + keyword + "%";
		}

		return this.tagEventMapper.selectCustomerTagRelatedEventCount(
				tag.getcTId(), keyword);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findCustomerRelatedEvents(CTag tag,
			String keyword, RowBounds rb) {
		if (StringUtils.isNotBlank(keyword)) {
			keyword = "%" + keyword + "%";
		}
		return this.tagEventMapper.selectCustomerTagRelatedEvent(tag.getcTId(),
				keyword, rb);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findCustomerRelatedEvents(CTag tag,
			String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			keyword = "%" + keyword + "%";
		}
		return this.tagEventMapper.selectCustomerTagRelatedEvent(tag.getcTId(), keyword);
	}

	public CTag findOneById(Integer tagId) {
		return this.cTagMapper.selectByPrimaryKey(tagId);
	}

}
