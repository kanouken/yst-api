package org.ost.crm.dao.project.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ost.crm.model.common.CommonParams;

import tk.mybatis.mapper.common.Mapper;

public interface CommonParamDao extends Mapper<CommonParams> {

	@Select("select val from tbl_common_json where key =#{type} and schemaID =#{schemaID} ")
	Object selectCommonParamJson(@Param("type") String type, @Param("schemaID") String schemaID);
}
