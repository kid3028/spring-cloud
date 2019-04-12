package com.qull.springcloud.oauth2.lab02.oauth;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午4:11
 */
@Data
public class OAuth2Token {
    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "token_type")
    private String tokenType;

    @JSONField(name = "expires_in")
    private String expiresIn;

    @JSONField(name = "refresh_token")
    private String refreshToken;

}
