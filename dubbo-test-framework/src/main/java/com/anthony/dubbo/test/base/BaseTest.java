package com.anthony.dubbo.test.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author anthony
 * @date Jul 5, 2017
 * @updateTime 3:17:06 PM
 */
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})

@Configuration
public class BaseTest extends AbstractTestNGSpringContextTests{

}
