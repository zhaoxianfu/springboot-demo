package com.springboot.demo.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @ClassName:MyLocaleResolver
 * @Despriction: 自定义国际化语言解析器, 一般情况会有AcceptHeaderLocaleResolver, SessionLocaleResolver, CookieLocaleResolver
 * 我们这里使用自定义的的语言解析器进行设置语种
 * @Author:zhaoxianfu
 * @Date:Created 2019/4/27  19:13
 * @Version1.0
 **/

@Slf4j
@Component("localeResolver")               //必须要为localeResolver这个名称
public class MyLocaleResolver implements LocaleResolver {

    private static final String I18N_LANGUAGE = "lang";
    private static final String I18N_LANGUAGE_SESSION = "lang_session";

    /**
     * 国际化语种的解析并设置---返回的是设置的语种信息,比如zh_CN或者en_US
     *
     * @param request
     * @return
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        String i18nLanguage = request.getParameter(I18N_LANGUAGE);
        //获取本机器的默认语种
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(i18nLanguage)) {
            String[] language = i18nLanguage.split("_");
            //获取参数传递的语种
            locale = new Locale(language[0], language[1]);

            //将国际化语言保存到session,可以选择保存到Redis中
            HttpSession session = request.getSession();
            session.setAttribute(I18N_LANGUAGE_SESSION, locale);
        } else {
            //如果没有带国际化参数,则判断session有没有保存,有保存,则使用保存的,也就是之前设置的,避免之后的请求不带国际化参数造成语言显示不对
            HttpSession session = request.getSession();
            Locale localeInSession = (Locale) session.getAttribute(I18N_LANGUAGE_SESSION);
            if (null != localeInSession) {
                locale = localeInSession;
            }
        }
        return locale;
    }

    /**
     * 这个方法应该是直接设置语种的
     *
     * @param request
     * @param response
     * @param locale
     */
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
