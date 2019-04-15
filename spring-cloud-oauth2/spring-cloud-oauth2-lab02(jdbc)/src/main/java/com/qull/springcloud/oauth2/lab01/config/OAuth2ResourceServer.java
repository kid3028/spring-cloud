package com.qull.springcloud.oauth2.lab01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 用于保护OAuth2要开放的资源，同时主要作用于client端以及token的认证(Bearer Auth)，由于后面OAuth2服务端后续还需要提供用户信息，所以也是一个Resource Server,
 * 默认拦截所有的请求，也可以通过重写方法自定义自己想要拦截的资源的URL地址
 * @Author kzh
 * @Description
 * @Date 19-4-11 下午3:01
 */
//资源服务配置
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                .antMatchers("/api/**");
    }

}

