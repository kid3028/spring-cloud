package com.qull.springcloud.oauth2.lab02.user;

import com.alibaba.fastjson.JSON;
import com.qull.springcloud.oauth2.lab02.oauth.AuthorizationCodeTokenService;
import com.qull.springcloud.oauth2.lab02.oauth.OAuth2Token;
import com.qull.springcloud.oauth2.lab02.security.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午3:25
 */
@Controller
public class MainController {

    @Autowired
    private AuthorizationCodeTokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home index";
    }

    @GetMapping("/callback")
    public String callback(String code, String state) {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        OAuth2Token token = tokenService.getToken(code);
        clientUser.setAccessToken(token.getAccessToken());
        clientUser.setAccessTokenValidity(Instant.now().plusSeconds(1800));
        userRepository.save(clientUser);

        return "redirect:/mainPage";
    }

    @GetMapping("/mainPage")
    public String mainPage() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();
        if(clientUser.getAccessToken() == null) {
            String authorizationEndpoint = tokenService.getAuthorizationEndpoint();
            return "redirect:" + authorizationEndpoint;
        }

        clientUser.setEntries(Arrays.asList(new Entry("entry 1"), new Entry("entry 2")));
        UserInfo userInfo = tryToGetUserInfo(clientUser.getAccessToken());
        Map<String, Object> result = new HashMap<>();
        result.put("clientUser", clientUser);
        result.put("userInfo", userInfo);
        return JSON.toJSONString(result);

    }

    public UserInfo tryToGetUserInfo(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = "http://localhost:8080/api/userInfo";

        RequestEntity<Object> requestEntity =
                new RequestEntity<>(headers, HttpMethod.GET, URI.create(endpoint));
        ResponseEntity<UserInfo> userInfo = restTemplate.exchange(requestEntity, UserInfo.class);
        if(userInfo.getStatusCode().is2xxSuccessful()) {
            return userInfo.getBody();
        }else {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }

    @GetMapping("/register")
    public String register(String username, String password) {
        ClientUser clientUser = new ClientUser();
        clientUser.setUsername(username);
        clientUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(clientUser);
        return "注册成功";
    }

}
