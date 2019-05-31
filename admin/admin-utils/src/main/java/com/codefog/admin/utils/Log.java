package com.codefog.admin.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    public static Logger get(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
