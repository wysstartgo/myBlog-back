package com.ambition.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.github.dreamroute.locker.interceptor.OptimisticLocker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName MyBatisPlusConfig
 * @Author
 * @Date 2019/1/10 18:02
 * @Mail
 * @Description Mybatis-Plus配置
 * @Version 1.0
 **/

@EnableTransactionManagement
@Configuration
@MapperScan("com.ambition.business.mapper")
public class MyBatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }


    /**
     * myibatis乐观锁配置
     * @return
     */
    @Bean
    public OptimisticLocker locker() {
        OptimisticLocker locker = new OptimisticLocker();
        // 不设置versionColumn，默认为version
        Properties props = new Properties();
        props.setProperty("versionColumn", "version");
        locker.setProperties(props);
        return locker;
    }


}
