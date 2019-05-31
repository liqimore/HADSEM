package com.codefog.crawler.test.util;

import com.codefog.crawler.util.ProxyIpUtil;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * proxy ip util test
 *
 */
public class ProxyIpUtilTest {

    public static void main(String[] args) {
        int code = ProxyIpUtil.checkProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("---", 80)), null);
        System.out.println(code);
    }

}
