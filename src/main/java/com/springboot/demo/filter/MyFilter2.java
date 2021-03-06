package com.springboot.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName:MyFilter2
 * @Despriction: 过滤器2
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/18  11:40
 * @Version1.0
 **/

@Slf4j
@Order(2)
@Component
@WebFilter(filterName = "myFilter2", urlPatterns = "/*")
@ConditionalOnProperty(prefix = "filter.myFilter2", name = "enabled", havingValue = "true", matchIfMissing = false)
public class MyFilter2 implements Filter {
    /**
     * 过滤器初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter2 init............");
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
        log.info("MyFilter2 doFilter.........before");
       /* HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        log.info("--------------------->过滤器2：请求地址" + requestURI);*/
        chain.doFilter(request, response);
        log.info("MyFilter2 doFilter.........after");

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
        log.info("MyFilter2 destroy..........");
    }
}
