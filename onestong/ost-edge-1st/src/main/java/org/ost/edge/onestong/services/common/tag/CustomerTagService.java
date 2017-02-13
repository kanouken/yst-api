package org.ost.edge.onestong.services.common.tag;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.dao.tag.CTagMapper;
import org.ost.edge.onestong.dao.tag.TagEventMapper;
import org.ost.edge.onestong.model.tag.CTag;
import org.ost.edge.onestong.model.tag.CTagExample;
import org.ost.edge.onestong.model.tag.CTagExample.Criteria;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
