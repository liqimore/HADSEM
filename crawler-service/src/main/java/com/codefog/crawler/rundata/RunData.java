package com.codefog.crawler.rundata;

/**
 * run data
 *
 */
public abstract class RunData {

    /**
     * add link
     *
     * @param link
     * @return boolean
     */
    public abstract boolean addUrl(String link);

    /**
     * get link, remove from unVisitedUrlQueue and add to visitedUrlSet
     *
     * @return String
     */
    public abstract String getUrl();

    /**
     * get url num
     *
     * @return int
     */
    public abstract int getUrlNum();

}
