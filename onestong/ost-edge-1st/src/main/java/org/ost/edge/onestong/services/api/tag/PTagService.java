package org.ost.edge.onestong.services.api.tag;

import org.ost.edge.onestong.dao.tag.PTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *  项目标签 业务逻辑
 * @author xnq
 *
 */
@Service
public class PTagService {
	@Autowired
	private PTagMapper  pTagMapper;
	
	
}
