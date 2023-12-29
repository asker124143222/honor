package com.wood.honor.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Author: xu.dm
 * @Date: 2021/2/10 20:31
 * @Description:
 */
public class DeptDeserializer extends JsonDeserializer<Dept> {
    @Override
    public Dept deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();
        if (!StringUtils.isEmpty(text)) {
            String[] split = text.split(":");
            Dept dept = new Dept();
            dept.setId(split[0]);
            dept.setDeptName(split[1]);
            return dept;
        }
        return null;
    }
}
