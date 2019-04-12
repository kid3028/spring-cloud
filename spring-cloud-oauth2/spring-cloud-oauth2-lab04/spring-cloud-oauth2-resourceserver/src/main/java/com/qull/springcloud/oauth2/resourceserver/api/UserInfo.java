package com.qull.springcloud.oauth2.resourceserver.api;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-12 下午2:45
 */
public class UserInfo {

    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
