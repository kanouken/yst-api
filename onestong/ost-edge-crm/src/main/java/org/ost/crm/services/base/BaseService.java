package org.ost.crm.services.base;

import java.util.List;
import java.util.Map;

import org.ost.crm.dao.project.common.CommonParamDao;
import org.ost.crm.model.common.CommonParams;
import org.ost.entity.tools.JsonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseService {
	@Autowired
	private CommonParamDao commonParamDao;

	// TODO cache
	@Transactional(readOnly = true)
	public List<CommonParams> getParams(String typeCode) {
		CommonParams cParams = new CommonParams();
		cParams.setSchemaId("0");
		cParams.setType(typeCode);
		List<CommonParams> comonParams = commonParamDao.select(cParams);
		return comonParams;
	}

	// TODO cache
	@Transactional(readOnly = true)
	public Map<String, Object> getParamJson(String typeCode, String schemaId) {
		Object val = commonParamDao.selectCommonParamJson(typeCode, schemaId);
		return JsonType.convertToMap(val);
	}
}
