package com.wood.honor.config;

import com.wood.honor.model.AuthToken;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2021/2/10 9:14
 * @Description:
 */
public class CustomAuthMessageConverter implements HttpMessageConverter<AuthToken> {
    private static String AUTH_MEDIA = "application/x-auth";

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(AuthToken.class);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        //  && mediaType.includes(MediaType.parseMediaType(AUTH_MEDIA))
        return clazz.isAssignableFrom(AuthToken.class);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes(AUTH_MEDIA);
    }

    @Override
    public AuthToken read(Class<? extends AuthToken> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream inputStream = inputMessage.getBody();
        String inputString = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        String[] split = inputString.split(":");
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token(split[0]);
        authToken.setScope(split[1]);
        return authToken;
    }

    @Override
    public void write(AuthToken authToken, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String authString = authToken.getAccess_token() + ":" + authToken.getExpires_in() + ":" + authToken.getScope();
        OutputStream outputStream = outputMessage.getBody();
        outputStream.write(authString.getBytes("utf-8"));
    }
}
