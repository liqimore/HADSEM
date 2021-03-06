package com.codefog.crawler.test;

import com.codefog.crawler.XxlCrawler;
import com.codefog.crawler.annotation.PageFieldSelect;
import com.codefog.crawler.annotation.PageSelect;
import com.codefog.crawler.conf.XxlCrawlerConf;
import com.codefog.crawler.loader.strategy.HtmlUnitPageLoader;
import com.codefog.crawler.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爬虫示例07：JS渲染方式采集数据，"htmlUnit" 方案
 * (仅供学习测试使用，如有侵犯请联系删除； )
 *
 */
public class XxlCrawlerTest07 {
    private static Logger logger = LoggerFactory.getLogger(XxlCrawlerTest05.class);

    @PageSelect(cssQuery = "body")
    public static class PageVo {

        @PageFieldSelect(cssQuery = "#jd-price", selectType = XxlCrawlerConf.SelectType.TEXT)
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {

        // 构造爬虫
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://item.jd.com/12228194.html")
                .setAllowSpread(false)
                .setPageLoader(new HtmlUnitPageLoader())        // HtmlUnit 版本 PageLoader：支持 JS 渲染
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        if (pageVo.getData() != null) {
                            logger.info("商品价格（JS动态渲染方式获取）: {}", pageVo.getData());
                        } else {
                            logger.info("商品价格（JS动态渲染方式获取）: 获取失败");
                        }

                    }
                })
                .build();

        // 启动
        crawler.start(true);

    }

}
