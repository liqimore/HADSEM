package com.codefog.admin.dao;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Name: ApplicationStartTest<br>
 * User: enilu<br>
 * Date: 2018/1/24<br>
 * Time: 16:26<br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfiguration.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.codefog.admin")
@TestPropertySource(locations = {"classpath:application-test.properties"})
@EnableCaching
public class BaseApplicationStartTest {
    protected final Logger log= LoggerFactory.getLogger(getClass());



}
