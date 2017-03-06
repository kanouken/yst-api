package org.ost.contacts.tools;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
/**
 * @author xnq
 *
 */
@MappedTypes({List.class})  
@MappedJdbcTypes({JdbcType.VARCHAR})  
public class MC2StringArrayTypeHander extends BaseTypeHandler<List<String>> {

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
	       return getStringArray(rs.getString(columnName));  
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int index)
			throws SQLException {
		return getStringArray(rs.getString(index));
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
       return this.getStringArray(cs.getString(columnIndex));  

	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i ,
			List<String> parameter, JdbcType arg3) throws SQLException {
		 StringBuffer result = new StringBuffer();  
	       for (String value : parameter)  
	           result.append(value).append(",");  
	       result.deleteCharAt(result.length()-1);  
	       ps.setString(i, result.toString());  
	}
	
	private List<String> getStringArray(String columnValue) {  
	       if (columnValue == null)  
	           return null;  
	       return Arrays.asList(columnValue.split(","));  
	    }  

}
