package com.codefog.admin.admin.core.util;

import com.codefog.admin.admin.config.properties.GunsProperties;
import com.codefog.admin.bean.vo.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     *
     * @author stylefeng
     * @Date 2017/5/23 22:34
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(GunsProperties.class).getKaptchaOpen();
    }
}