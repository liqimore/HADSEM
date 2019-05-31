package com.codefog.crawler.test.util;

import com.codefog.crawler.util.UrlUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * url tool test
 *
 */
public class UrlUtilTest {

    /**
     * url格式校验
     */
    @Test
    public void isUrlTest(){
        String url = "http://www.baidu.com/";

        boolean ret = UrlUtil.isUrl(url);
        Assert.assertTrue(ret);
    }

}
