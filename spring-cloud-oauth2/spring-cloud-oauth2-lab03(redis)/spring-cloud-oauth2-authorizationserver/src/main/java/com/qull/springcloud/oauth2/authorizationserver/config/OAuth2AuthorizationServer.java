package com.qull.springcloud.oauth2.authorizationserver.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-12 下午2:09
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyFactory.getKeyPair("mytest"));

        return converter;
    }

    /**
     * Handling error: NestedServletException, Handler dispatch failed; nested exception is java.lang.NoSuchMethodError: org.springframework.data.redis.connection.RedisConnection.set([B[B)V
     * spring-data-redis 2.0版本中set(String,String)被弃用了，要使用RedisConnection.stringCommands().set(…)，所有我自定义一个RedisTokenStore，代码和RedisTokenStore一样，
     * 只是把所有conn.set(…)都换成conn..stringCommands().set(…)，测试后方法可行。
     *            conn.stringCommands().set(accessKey, serializedAccessToken);
     * @return
     */
    @Bean
    public TokenStore customRedisTokenStore() {
        return new CustomRedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // 此处很重要，必须讲accessTokenConverter也添加进来，否则token将会变为随机字符串
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter() ));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(customRedisTokenStore())
                .accessTokenConverter(accessTokenConverter())
                // 添加上enhancer
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("qull")
                .secret(passwordEncoder.encode("qull"))
                .redirectUris("http://www.baidu.com")
                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
                .scopes("read", "write");
    }
}
