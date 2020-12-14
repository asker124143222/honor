package com.wood.honor.config.App;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: xu.dm
 * @Date: 2020/12/4 14:20
 * @Version: 1.0
 * @Description: TODO
 **/
@Configuration
public class config {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
