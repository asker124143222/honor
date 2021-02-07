package com.wood.honor.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: xu.dm
 * @Date: 2021/1/15 17:06
 * @Version: 1.0
 * @Description: TODO
 **/
@Component
@Order(101)
public class Test2Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Test2Filter...init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Test2Filter 预处理");
        chain.doFilter(request, response);
        System.out.println("Test2Filter 后处理");
    }

    @Override
    public void destroy() {
        System.out.println("Test2Filter...destroy");
    }
}
