package com.redis.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName:MyFilter
 * @Despriction: 过滤器
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/18  11:24
 * @Version1.0
 **/

@Slf4j
@Order(1)
@Component
@WebFilter(filterName = "myFilter1", urlPatterns = "/*")
@ConditionalOnProperty(prefix = "filter.myFilter1", name = "enabled", havingValue = "true", matchIfMissing = false)
public class MyFilter1 implements Filter {

    /**
     * 过滤器初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter1 init............");
    }

    /**
     * 执行过滤器里面的过滤方法
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("MyFilter1 doFilter.........before");
        /*HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        log.info("--------------------->过滤器1：请求地址" + requestURI);*/
        chain.doFilter(request, response);
        log.info("MyFilter1 doFilter.........after");

        //当访问的路径上不包含info时进行转发到/fail路径的接口上进行响应
        /*if (!requestURI.contains("info")) {
            request.getRequestDispatcher("/failed").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }*/

    }

    /**
     * 过滤器销毁方法
     *
     */
    @Override
    public void destroy() {
        log.info("MyFilter1 destroy..........");
    }
}
