package com.itsq.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 *
 * @param null
 * @return
 * @author sq
 * @creed: mybatisplus配置分页
 * @date 2020/2/25 10:29
 */
@EnableTransactionManagement//开启事务管理器
@Configuration//配置类
public class MyBatisPlusConfig {
    /**
     * 分页插件配置
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}