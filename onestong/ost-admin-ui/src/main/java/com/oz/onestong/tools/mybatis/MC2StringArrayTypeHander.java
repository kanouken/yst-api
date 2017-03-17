package com.oz.onestong.tools.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
/**
 * 用于实现报表数据的  ，分隔 尝试 解决直接通过代码 带来的 字符串丢失问题
 * @author xnq
 *
 */
@MappedTypes({String[].class})  
@MappedJdbcTypes({JdbcType.VARCHAR})  
public class MC2StringArrayTypeHander extends BaseTypeHandler<String[]> {

	@Override
	public String[] getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
	       return getStringArray(rs.getString(columnName));  
	}

	@Override
	public String[] getNullableResult(ResultSet rs, int index)
			throws SQLException {
		return getStringArray(rs.getString(index));
	}

	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
       return this.getStringArray(cs.getString(columnIndex));  

	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i ,
			String[] parameter, JdbcType arg3) throws SQLException {
		 StringBuffer result = new StringBuffer();  
	       for (String value : parameter)  
	           result.append(value).append(",");  
	       result.deleteCharAt(result.length()-1);  
	       ps.setString(i, result.toString());  
	}
	
	private String[] getStringArray(String columnValue) {  
	       if (columnValue == null)  
	           return null;  
	       return columnValue.split(",");  
	    }  

}
