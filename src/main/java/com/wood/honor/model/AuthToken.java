package com.wood.honor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xu.dm
 * @Date: 2020/12/14 14:30
 * @Version: 1.0
 * @Description: TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken implements Serializable {
    private String access_token;
    private String refresh_token;
    // jwt短令牌，是一个uuid，也是令牌id
    private String jti;
    private String scope;
    // 有效时间（秒）
    private Integer expires_in;
    private String token_type;
}