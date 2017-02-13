package org.ost.edge.onestong.services.common.tag;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.dao.tag.PTagMapper;
import org.ost.edge.onestong.dao.tag.TagEventMapper;
import org.ost.edge.onestong.model.tag.PTag;
import org.ost.edge.onestong.model.tag.PTagExample;
import org.ost.edge.onestong.model.tag.PTagExample.Criteria;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectTagService {
	@Autowired
	private PTagMapper pTagMapper;

	private @Autowired
	TagEventMapper tagEventMapper;

	@Transactional(readOnly = true)
	public int findProjectTagCountByTag(PTag tag) {
		PTagExample pte = new PTagExample();

		Criteria c = pte.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(
				tag.getDomainId());
		if (StringUtils.isNotBlank(tag.getpTName())) {

			c.andPTNameLike("%" + tag.getpTName() + "%");
		}
		return this.pTagMapper.countByExample(pte);
	}

	@Transactional(readOnly = true)
	public List<PTag> findProjectTagsByTag(PTag tag, RowBounds rb) {
		PTagExample pte = new PTagExample();
		Criteria c = pte.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(
				tag.getDomainId());
		if (StringUtils.isNotBlank(tag.getpTName())) {
			c.andPTNameLike("%" + tag.getpTName() + "%");
		}
		pte.setOrderByClause("update_time desc");

		if (null != rb)
			return this.pTagMapper.selectByExample(pte, rb);
		else
			return this.pTagMapper.selectByExample(pte);
	}

	@Transactional(readOnly = true)
	public int findProjectsRelatedEventByTag(PTag tag, String keyword) {
		if (StringUtils.isNotBlank(keyword)) {

			keyword = "%" + keyword + "%";
		}

		return this.tagEventMapper.selectProjectTagRelatedEventCount(
				tag.getpTId(), keyword);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findProjectRelatedEvents(PTag tag,
			String keyword, RowBounds rb) {
		if (StringUtils.isNotBlank(keyword)) {
			keyword = "%" + keyword + "%";
		}
		return this.tagEventMapper.selectProjectTagRelatedEvent(tag.getpTId(),
				keyword, rb);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findProjectRelatedEvents(PTag tag,
			String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			keyword = "%" + keyword + "%";
		}
		return this.tagEventMapper.selectProjectTagRelatedEvent(tag.getpTId(),
				keyword);
	}

	public PTag findOneById(Integer tagId) {
		return this.pTagMapper.selectByPrimaryKey(tagId);
	}

}
