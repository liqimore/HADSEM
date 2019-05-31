package com.codefog.admin.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Name: DabConfiguration<br>
 */
@Configuration
@EnableJpaRepositories("com.codefog.admin.dao")
public class DaoConfiguration {
}
