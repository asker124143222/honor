package com.wood.honor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xu.dm
 * @Date: 2021/2/10 19:53
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginInfo implements Serializable {
    private String username;
    private String password;
    @JsonDeserialize(using = DeptDeserializer.class)
    private Dept dept;
}
