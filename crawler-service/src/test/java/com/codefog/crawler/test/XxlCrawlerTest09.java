package com.codefog.crawler.test;

import com.codefog.crawler.XxlCrawler;
import com.codefog.crawler.parser.strategy.NonPageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爬虫示例09：采集非Web页面，如JSON接口等，直接输出响应数据
 *
 */
public class XxlCrawlerTest09 {
    private static Logger logger = LoggerFactory.getLogger(XxlCrawlerTest05.class);

    public static void main(String[] args) {

        // 构造爬虫
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("http://news.baidu.com/widget?id=LocalNews&ajax=json")
                .setPageParser(new NonPageParser() {
                    @Override
                    public void parse(String url, String pageSource) {
                        System.out.println(url + ": " + pageSource);
                    }
                })
                .build();

        // 启动
        crawler.start(true);

    }

}
