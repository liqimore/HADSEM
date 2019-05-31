package com.codefog.crawler.util;

/**
 * url tool
 *
 */
public class UrlUtil {

    /**
     * url格式校验
     */
    public static boolean isUrl(String url) {
        if (url!=null && url.trim().length()>0 && url.startsWith("http")) {
            return true;
        }
        return false;
    }

}
