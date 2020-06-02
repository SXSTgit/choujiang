package com.itsq.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @param null
 * @return 
 * @author sq
 * @creed: 阿里连接池后台 http://localhost:9002/druid/index.html 端口号记得换
 * @date 2020/2/25 10:29
 */
@Configuration
public class DruidConfig {

    /*
       将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
       绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
       @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
       前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    //后台监控页面 相当于web.xml,ServletRegistrationBean
    //访问页面http://localhost:9002/druid/index.html
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        //后台页面需要配置账号密码
        Map<String,String> initParameters = new HashMap<>();
        //增加配置 key值固定写法 账号密码
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword","admin");
        //允许谁可以访问 如果值为空则代表所有人可以访问
        //initParameters.put("allow", "localhost");
        //禁止谁能访问
        //initParameters.put("sunqi", "127.0.0.1");
        bean.setInitParameters(initParameters);
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParameters = new HashMap<>();
        //exclusions代表这些东西不进行统计 具体参数进WebStatFilter源码中看
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }


}