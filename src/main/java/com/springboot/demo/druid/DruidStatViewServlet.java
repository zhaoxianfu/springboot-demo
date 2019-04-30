package com.springboot.demo.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @ClassName:DruidStatViewServlet
 * @Despriction: Druid的Servlet与拦截器(用于查看Druid监控)
 * 这两个类好像由开发团队提供了样例，所以直接使用网上资源，只需修改为自己想要的参数即可
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/30  14:22
 * @Version1.0
 **/

@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1"),// IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name = "deny", value = "192.168.1.73"),// IP黑名单 (存在共同时,deny优先于allow)
                @WebInitParam(name = "loginUsername", value = "root"),// 用户名
                @WebInitParam(name = "loginPassword", value = "123456"),// 密码
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的"Reset All"功能
        })
public class DruidStatViewServlet extends StatViewServlet {

}
