package com.codefog.crawler.annotation;

import java.lang.annotation.*;

/**
 * page vo annotation
 *
 * 页面数据对象 注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface PageSelect {

    /**
     * CSS-like query, like "#body"
     *
     * CSS选择器, 如 "#body"
     *
     * @return String
     */
    public String cssQuery() default "";

}
