package com.qull.springcloud.oauth2.resourceserver.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定制AccessToken转换器，为添加额外信息在服务器端获取做准备
 * @Author kzh
 * @Description
 * @Date 19-4-12 下午4:12
 */
@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {

        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
}
