package org.common.tools.db;

/**
 * oracle 方言处理
 * @author xx
 *
 */
public class OracleDialect extends Dialect {
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
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		else if (offset == -10)
			pagingSelect.append("select count(1) as total from ( ");
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString + " and rownum_ > " + offsetPlaceholder);
		}
		else if (offset == -10) {
			pagingSelect.append(" ) ");
		}
		else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}
		if ((isForUpdate) && (offset != -10)) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}
}