package com.codefog.crawler.parser;

import com.codefog.crawler.model.PageRequest;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * page parser
 * @param <T>   PageVo
 */
public abstract class PageParser<T> {

    /**
     * pre parse page, before page load
     *
     * @param pageRequest  page request params
     */
    public void preParse(PageRequest pageRequest) {
        // TODO
    }

    /**
     * parse pageVo
     *
     * @param html              page html data
     * @param pageVoElement     pageVo html data
     * @param pageVo            pageVo object
     */
    public abstract void parse(Document html, Element pageVoElement, T pageVo);

}
