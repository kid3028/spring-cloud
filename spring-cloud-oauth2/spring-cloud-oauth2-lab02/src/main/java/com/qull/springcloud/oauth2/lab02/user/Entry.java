package com.qull.springcloud.oauth2.lab02.user;

import lombok.Data;

/**
 * @Author kzh
 * @Description
 * @Date 19-4-9 下午3:17
 */
@Data
public class Entry {
    private String value;

    public Entry(String value) {
        this.value = value;
    }
}
