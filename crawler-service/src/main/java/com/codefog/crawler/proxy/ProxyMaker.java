package com.codefog.crawler.proxy;

import java.net.Proxy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * proxy macker
 *
 */
public abstract class ProxyMaker {

    protected List<Proxy> proxyList = new CopyOnWriteArrayList<Proxy>();            // 请求代理池，对抗反采集策略规则WAF

    public ProxyMaker addProxy(Proxy proxy) {
        this.proxyList.add(proxy);
        return this;
    }

    public ProxyMaker addProxyList(List<Proxy> proxyList) {
        this.proxyList.addAll(proxyList);
        return this;
    }

    public ProxyMaker clear() {
        this.proxyList.clear();
        return this;
    }

    /**
     * make proxy
     *
     * @return Proxy
     */
    public abstract Proxy make();

}
