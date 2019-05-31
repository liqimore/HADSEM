package com.codefog.admin.api.controller;


import com.codefog.admin.bean.vo.SpringContextHolder;
import com.codefog.admin.dao.cache.TokenCache;
import com.codefog.admin.utils.HttpKit;
import com.codefog.admin.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

/**
 * 基础controller
 *
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 根据token获取用户id，如果不存在则抛出异常
     *
     * @param request
     * @return
     */
    public Long getIdUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        Long idUser = SpringContextHolder.getBean(TokenCache.class).get(token);
        if (idUser == null) {
            throw new RuntimeException("用户不存在");
        }
        return idUser;
    }

    /**
     * 获取客户端token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String getToken() {
        return HttpKit.getRequest().getHeader("Authorization");
    }

    /**
     * 获取客户端ip
     *
     * @param req
     * @return
     */
    public String getRealIp(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取前端传递过来的json字符串<br>
     *     如果前端使用axios的data方式传参则使用改方法接收参数
     * @return
     */
    public   String getjsonReq(   ) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(HttpKit.getRequest().getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);

            }
            br.close();
            if (sb.length() < 1) {return "";}
            String reqBody = URLDecoder.decode(sb.toString(), "UTF-8");
            reqBody = reqBody.substring(reqBody.indexOf("{"));
            return reqBody;

        } catch (IOException e) {

            logger.error("获取json参数错误！{}", e.getMessage());

            return "";

        }

    }
    public <T>T getFromJson(Class<T> klass){
        String jsonStr = getjsonReq();
        if(StringUtils.isEmpty(jsonStr)){
            return null;
        }
        JSONObject json = JSONObject.parseObject(jsonStr);
        return JSON.toJavaObject(json,klass);
    }

}
