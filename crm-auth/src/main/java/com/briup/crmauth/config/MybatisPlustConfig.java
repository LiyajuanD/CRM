package com.briup.crmauth.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lining
 * @Date 2022/11/14
 */
@MapperScan("com.briup.crmauth.mapper")
@Configuration
public class MybatisPlustConfig {
    //添加分页拦截器bean对象...
    @Bean
    public MybatisPlusInterceptor interceptor(){
        //1.创建拦截器对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //2.添加分页拦截器 mysql limit语句
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
