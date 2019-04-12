package com.qull.springcloud.oauth2.resourceserver.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-12 下午2:46
 */

@Controller
public class UserController {

    // 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<UserInfo> getUserInfo() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = principal.toString() + "@spring.com";

        UserInfo userInfo = new UserInfo();
        userInfo.setName(principal.toString());
        userInfo.setEmail(email);

        return ResponseEntity.ok(userInfo);
    }
}
