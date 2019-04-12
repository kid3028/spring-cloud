package com.qull.springcloud.oauth2.lab02.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午3:59
 */
@Service
public class AuthorizationCodeTokenService {

    @Autowired
    private AuthorizationCodeConfiguration authorizationCodeConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    public String getAuthorizationEndpoint() {
        String endpoint = "http://localhost:8080/oauth/authorize";

        Map<String, String> authParam = new HashMap<>();
        authParam.put("client_id", "uService");
        authParam.put("response_type", "code");
        authParam.put("redirect_uri", getEncodedUrl("http://localhost:9090/callback"));
        authParam.put("scope", getEncodedUrl("read"));
        return buildUrl(endpoint, authParam);
    }

    private String getEncodedUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUrl(String endpoint, Map<String, String> parameters) {
        List<String> params = new ArrayList<>(parameters.size());
        parameters.forEach((name, value) -> params.add(name + "=" + value));
        return endpoint + "?" + params.parallelStream().collect(Collectors.joining("&"));
    }

    public OAuth2Token getToken(String authorizationCode) {
        String authBase64 = authorizationCodeConfiguration.encodeCredentials("uService", "uService");
        RequestEntity<MultiValueMap<String, String>> requestEntity =
                new RequestEntity<>(authorizationCodeConfiguration.getBody(authorizationCode),
                    authorizationCodeConfiguration.getHeader(authBase64),
                    HttpMethod.POST,
                    URI.create("http://localhost:8080/oauth/token"));
        ResponseEntity<OAuth2Token> responseEntity = restTemplate.exchange(requestEntity, OAuth2Token.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        throw new RuntimeException("error trying to retrieve access token");

    }
}
