package com.codefog.admin.api.interceptor;

import com.codefog.admin.bean.vo.SpringContextHolder;
import com.codefog.admin.dao.cache.TokenCache;
import com.codefog.admin.utils.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全校验拦截器<br>
 * 判断非登录请求,将其定位置登录页面
 * @version 2018-07-24
 *
 * @author eniluzt
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private Logger logger = Log.get(this.getClass());
    @Autowired
    private TokenCache tokenCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("requestURL:{},method:{}" , request.getRequestURL().toString(),request.getMethod());

        //如果用户是非登录用户，则拒绝用户请求
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }
        String token = request.getHeader("Authorization");
        logger.debug("token:{}",token);
        if (token == null) {
            this.printResponse(httpServletResponse);
            return false;
        }

        if (SpringContextHolder.getBean(TokenCache.class).get(token) == null) {
            this.printResponse(httpServletResponse);
            return false;
        }
        return true;
    }

    private void printResponse(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(401);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.debug("done requestURL:" + httpServletRequest.getRequestURL().toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}