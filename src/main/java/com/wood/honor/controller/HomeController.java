package com.wood.honor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wood.honor.model.AuthToken;
import com.wood.honor.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xu.dm
 * @Date: 2020/12/4 13:48
 * @Version: 1.0
 * @Description: TODO
 **/
@Slf4j
@Controller
public class HomeController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Tika tika;

    @RequestMapping("/parseFile")
    @ResponseBody
    public String parseFile(@RequestParam(required = false) String fileName) throws IOException, TikaException {
        Path path;
        if(fileName == null || fileName.length()==0) {
            path = Paths.get("D:", "temp", "项目经理任命书-测试0003-20231101.xlsx");
        } else {
            path = Paths.get("D:", "temp", fileName);
        }

        File file = path.toFile();
        System.out.println(file.getAbsolutePath());
        String data = tika.parseToString(file);
        System.out.println(data);
        return data;
    }

    @RequestMapping("/")
    public String homePage() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String token) {

        return "login";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @ResponseBody
    @GetMapping("/auth")
    public AuthToken getAuth() {
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token(UUID.randomUUID().toString().replace("-",""));
        authToken.setExpires_in(1919191);
        authToken.setJti(String.valueOf(UUID.randomUUID().getLeastSignificantBits()));
        authToken.setScope("全部");
        return authToken;
    }

    @ResponseBody
    @PostMapping("/auth")
    public void sendAuth(@RequestBody AuthToken authToken) {
        System.out.println(authToken);
    }

    @ResponseBody
    @PutMapping("/auth")
    public AuthToken postAuth(@RequestBody AuthToken authToken) {
        System.out.println(authToken);
        return authToken;
    }

    @ResponseBody
    @PostMapping("/login2")
    public LoginInfo login2(LoginInfo loginInfo){
        System.out.println(loginInfo);
        return loginInfo;
    }

    @ResponseBody
    @PostMapping("/login3")
    public LoginInfo login3(@RequestBody LoginInfo loginInfo){
        System.out.println(loginInfo);
        return loginInfo;
    }

    @ResponseBody
    @RequestMapping("/ok")
    public String okPage(@RequestParam(required = false) String token) {
        if (!StringUtils.isEmpty(token)) {
            String accessToken = stringRedisTemplate.boundValueOps("token:ak:" + token).get();
            return "ok:" + accessToken;
        }
        return "ok page";
    }

    @ResponseBody
    @GetMapping("/getState")
    public String getState() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @ResponseBody
    @PostMapping("/getState")
    public String postState(@RequestBody AuthToken authToken) {
        return authToken.toString();
    }

    @ResponseBody
    @GetMapping("/callback")
    public void getAuthorization(@RequestParam String code, @RequestParam(required = false) String state,
                                 HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(code) || code.equals("-1")) {
            response.sendRedirect("/login");
            return;
        }
        String clientId = "wood_app";
        String clientSecret = "123456";

        String url = "http://localhost:9000/auth/oauth2/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        // 按照Spring Security的OAuth2标准，在获取令牌前需要对客户端做http basic 认证
        headers.add("Authorization", this.getHttpBasic(clientId, clientSecret));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        Map map = responseEntity.getBody();
        if (map == null || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null) {
            //申请令牌失败
            throw new RuntimeException("申请令牌失败");
        }
        // 封装令牌数据
        ObjectMapper mapper = new ObjectMapper();
        AuthToken authToken;
        try {
            String jwtTokenString = mapper.writeValueAsString(map);
            authToken = mapper.readValue(jwtTokenString, AuthToken.class);
        } catch (JsonProcessingException e) {
            log.error("令牌序列化失败：" + e.getMessage(), map);
            throw new RuntimeException("申请令牌失败：令牌序列化失败");
        }
        System.out.println(authToken);
        response.sendRedirect("/ok?token=" + authToken.getJti());

    }

    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic " + new String(encode);
    }
}
