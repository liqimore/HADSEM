package com.codefog.crawler.parser.strategy;

import com.codefog.crawler.parser.PageParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * non page parser
 */
public abstract class NonPageParser extends PageParser {

    @Override
    public void parse(Document html, Element pageVoElement, Object pageVo) {
        // TODO，not parse page, output page source
    }

    /**
     * @param url
     * @param pageSource
     */
    public abstract void parse(String url, String pageSource);

}
