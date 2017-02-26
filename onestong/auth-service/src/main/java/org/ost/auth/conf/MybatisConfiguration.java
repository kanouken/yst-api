package org.ost.auth.conf;

import org.common.tools.db.DatabaseDialect;
import org.common.tools.db.DiclectResultSetHandlerInterceptor;
import org.common.tools.db.DiclectStatementHandlerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MyBatis 配置
 *
 *
 */
@Configuration
public class MybatisConfiguration  {
    @Bean
    public DiclectStatementHandlerInterceptor  statemetHandler() {
    	DiclectStatementHandlerInterceptor i =   new DiclectStatementHandlerInterceptor();
    	i.setDialect(DatabaseDialect.mysql);
    	return i;
    }
    @Bean
    public DiclectResultSetHandlerInterceptor  resultSetHandler() {
        return new DiclectResultSetHandlerInterceptor();
    }

}