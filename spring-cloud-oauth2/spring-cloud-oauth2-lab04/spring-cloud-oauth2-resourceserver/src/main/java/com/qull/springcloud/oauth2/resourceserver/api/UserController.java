package com.qull.springcloud.oauth2.resourceserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-12 下午2:46
 */

@Controller
public class UserController {

    @Autowired
    private TokenStore tokenStore;

    // 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<UserInfo> getUserInfo() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = principal.toString() + "@spring.com";

        UserInfo userInfo = new UserInfo();
        userInfo.setName(principal.toString());
        userInfo.setEmail(email);

        System.out.println(getExtraInfo((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()));
        System.out.println(getExtralInfo2(SecurityContextHolder.getContext().getAuthentication()));
        return ResponseEntity.ok(userInfo);
    }

    /**
     * 读取token中的额外信息
     * @param authentication
     * @return
     */
    public Map<String, Object> getExtraInfo(OAuth2Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        return accessToken.getAdditionalInformation();
    }

    /**
     * 读取token中额外信息，建议使用这中方式
     * {@link UserController#getExtraInfo(OAuth2Authentication)} 方法需要从tokenValue中解析信息，实际使用时可能会比较麻烦，如果可以直接从OAuth2Authentication中获取，
     * 那么便可以很方便，使用{@link OAuth2AuthenticationDetails#decodedDetail}存储业务所需信息，取的时候便可以通过get方法直接调取。
     * 需要使用访问令牌转换器
     * @param auth
     * @return
     */
    public Map<String, Object> getExtralInfo2(Authentication auth) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        return (Map<String, Object>) details.getDecodedDetails();
    }
}
