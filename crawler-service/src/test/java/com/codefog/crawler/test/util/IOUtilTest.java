package com.codefog.crawler.test.util;

import com.codefog.crawler.util.IOUtil;

/**
 * io util test
 */
public class IOUtilTest {

    public static void main(String[] args) {
        System.out.println(IOUtil.toString(IOUtil.toInputStream("qwe123阿斯达", null), null));
    }

}
