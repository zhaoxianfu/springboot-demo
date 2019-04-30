package com.springboot.demo.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @ClassName:DruidStatFilter
 * @Despriction:
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/30  14:27
 * @Version1.0
 **/

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源,这些路径不拦截
        }
)
public class DruidStatFilter extends WebStatFilter {

}
