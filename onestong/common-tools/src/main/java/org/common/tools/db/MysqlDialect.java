package org.common.tools.db;

/**
 * mysql 方言处理
 * @author xx
 *
 */
public class MysqlDialect extends Dialect {
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0)
			//pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
			pagingSelect.append("select * from(");
		else if (offset == -10)
			pagingSelect.append("select count(1) as total from ( ");
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
		
			pagingSelect.append(") as t limit "+offset+","+limit);
		}
		else if (offset == -10) {
			pagingSelect.append(" ) as total_table");
		}
		else {
			pagingSelect.append(")as t limit 0,"+limitPlaceholder);
		}
		if ((isForUpdate) && (offset != -10)) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}
}