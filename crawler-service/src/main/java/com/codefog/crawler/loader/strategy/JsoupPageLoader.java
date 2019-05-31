package com.codefog.crawler.loader.strategy;

import com.codefog.crawler.loader.PageLoader;
import com.codefog.crawler.model.PageRequest;
import com.codefog.crawler.util.JsoupUtil;
import org.jsoup.nodes.Document;

/**
 * jsoup page loader
 *
 */
public class JsoupPageLoader extends PageLoader {

    @Override
    public Document load(PageRequest pageRequest) {
        return JsoupUtil.load(pageRequest);
    }

}
