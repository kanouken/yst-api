package com.oz.onestong.services.api.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oz.onestong.dao.tag.PTagMapper;
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
