package com.codefog.admin.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages="com.codefog.admin.bean.entity")
@EnableJpaRepositories(basePackages= "com.codefog.admin.dao.system")
public class AppConfiguration {
}

