package com.wood.honor.config;

import com.wood.honor.model.Dept;
import com.wood.honor.model.LoginInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * @Author: xu.dm
 * @Date: 2021/2/10 9:10
 * @Description:
 * 与Bean方式二选一可以注册CustomAuthMessageConverter
 *     @Bean
 *     public CustomAuthMessageConverter customAuthMessageConverter() {
 *         return new CustomAuthMessageConverter();
 *     }
 */
@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new CustomAuthMessageConverter());
//    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Dept>() {
            @Override
            public Dept convert(String source) {
                if(!StringUtils.isEmpty(source)){
                    String[] split = source.split(":");
                    Dept dept = new Dept();
                    dept.setId(split[0]);
                    dept.setDeptName(split[1]);
                    return dept;
                }
                return null;
            }
        });
    }
}
