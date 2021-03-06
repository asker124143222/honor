package com.wood.honor.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: xu.dm
 * @Date: 2021/1/15 16:58
 * @Version: 1.0
 * @Description: TODO
 **/
@Component
@Order(100)
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TestFilter...init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("TestFilter 预处理");
        chain.doFilter(request, response);
        System.out.println("TestFilter 后处理");
    }

    @Override
    public void destroy() {
        System.out.println("TestFilter...destroy");
    }
}
