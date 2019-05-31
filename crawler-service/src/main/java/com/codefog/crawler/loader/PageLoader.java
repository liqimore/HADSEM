package com.codefog.crawler.loader;

import com.codefog.crawler.model.PageRequest;
import org.jsoup.nodes.Document;

/**
 * page loader
 *
 */
public abstract class PageLoader {

    /**
     * load page
     *
     * @param pageRequest
     * @return Document
     */
    public abstract Document load(PageRequest pageRequest);

}
