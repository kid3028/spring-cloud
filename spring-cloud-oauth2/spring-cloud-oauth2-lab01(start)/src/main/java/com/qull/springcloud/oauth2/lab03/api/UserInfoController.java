package com.qull.springcloud.oauth2.lab03.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-11 下午4:05
 */
@Controller
public class UserInfoController {

    @GetMapping("/api/userinfo")
    public ResponseEntity<UserInfo> userInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        UserInfo userInfo = new UserInfo();
        userInfo.setName(username);
        userInfo.setEmail(username + "@spring.com");
        return ResponseEntity.ok(userInfo);
    }


}
