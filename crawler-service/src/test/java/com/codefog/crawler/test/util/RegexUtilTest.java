package com.codefog.crawler.test.util;

import com.codefog.crawler.util.RegexUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * regex tool test
 */
public class RegexUtilTest {


    /**
     * regex match
     */
    @Test
    public void matchesTest(){
        String regex = "https://my\\.oschina\\.net/xuxueli/blog/\\d+";
        String url = "https://my.oschina.net/xuxueli/blog/690978";

        boolean ret = RegexUtil.matches(regex, url);
        Assert.assertTrue(ret);
    }

    /**
     * url格式校验
     */
    @Test
    public void isUrlTest(){
        String url = "http://www.baidu.com/";

        boolean ret = RegexUtil.isUrl(url);
        Assert.assertTrue(ret);
    }

}
