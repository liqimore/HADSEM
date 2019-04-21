package com.codefog.crawler.test;

import com.codefog.crawler.XxlCrawler;
import com.codefog.crawler.conf.XxlCrawlerConf;
import com.codefog.crawler.parser.PageParser;
import com.codefog.crawler.util.FileUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 爬虫示例02：爬取页面，下载Html文件
 *
 * @author xuxueli 2017-10-09 19:48:48
 */
public class XxlCrawlerTest02 {

    public static void main(String[] args) {

        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://gitee.com/xuxueli0323/projects?page=1")
                .setWhiteUrlRegexs("https://gitee\\.com/xuxueli0323/projects\\?page=\\d+")
                .setThreadCount(3)
                .setPageParser(new PageParser<Object>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, Object pageVo) {

                        // 文件信息
                        String htmlData = html.html();
                        String filePath = "/Users/xuxueli/Downloads/tmp";
                        String fileName = FileUtil.getFileNameByUrl(html.baseUri(), XxlCrawlerConf.CONTENT_TYPE_HTML);

                        // 下载Html文件
                        FileUtil.saveFile(htmlData, filePath, fileName);
                    }
                })
                .build();

        System.out.println("start");
        crawler.start(true);
        System.out.println("end");
    }

}
