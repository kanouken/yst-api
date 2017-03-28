package org.ost.crm.dao.project.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ost.crm.model.common.CommonParams;

import tk.mybatis.mapper.common.Mapper;

public interface CommonParamDao extends Mapper<CommonParams> {

	@Select("select paramVal from tbl_common_json where type =#{type} and schemaID =#{schemaID} ")
	Object selectCommonParamJson(@Param("type") String type, @Param("schemaID") String schemaID);

	@Select("select paramVal \"val\",paramKey \"key\",type from tbl_common_param where instr(type,#{type}) > 0 and schemaID = #{schemaId}")
	List<CommonParams> selectByTypeCode(CommonParams cParams);
}
