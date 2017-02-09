package org.common.tools.db;

import java.util.Properties;

import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;

/**
 * 目前我手动修改了 此处的代码已适应 不同的数据库 用于拦截 mybatis 的默认分页
 * 
 * @author xx
 *
 */
@SuppressWarnings("all")
@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = {
		java.sql.Connection.class, java.lang.Integer.class }) })
public class DiclectStatementHandlerInterceptor implements Interceptor {
	/**
	 * specify the target dialect
	 */
	private DatabaseDialect dialect;

	public void setDialect(DatabaseDialect dialect) {
		this.dialect = dialect;
	}

	public Object intercept(Invocation invocation) throws Throwable {
		// System.out.println("DiclectStatementHandlerInterceptor>>>>start:");
		RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler handler = (StatementHandler) ReflectUtil.getFieldValue(statement, "delegate");

		if ((handler instanceof PreparedStatementHandler)) {
			PreparedStatementHandler prehandler = (PreparedStatementHandler) ReflectUtil.getFieldValue(statement,
					"delegate");

			RowBounds rowBounds = (RowBounds) ReflectUtil.getFieldValue(prehandler, "rowBounds");
			if ((rowBounds.getLimit() > 0) && (rowBounds.getLimit() < 2147483647)) {
				BoundSql boundSql = statement.getBoundSql();
				BoundSql newBoundSql = statement.getBoundSql();
				String sql = boundSql.getSql();
				Dialect dialect = null;
				if(this.dialect == null){
					throw new RuntimeException("no database dialect choosed");
				}
				if(this.dialect.equals(DatabaseDialect.mysql)){
					dialect = new MysqlDialect();
				}else if(this.dialect.equals(DatabaseDialect.oracle)){
					dialect = new OracleDialect();
				}else{
					throw new RuntimeException("no database dialect choosed");
				}
				sql = dialect.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());

				ReflectUtil.setFieldValue(boundSql, "sql", sql);

				for (ParameterMapping mapping : boundSql.getParameterMappings()) {
					String prop = mapping.getProperty();
					if (boundSql.hasAdditionalParameter(prop)) {
						newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
					}
				}
				boundSql = newBoundSql;
			}

		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}
}
