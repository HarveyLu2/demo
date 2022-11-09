
package com.stori.sofa;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * 启动类
 * 
 * @author king
 * @date 2022/05/06 19:55
 **/
@Component
@SpringBootApplication(scanBasePackages = {"com.stori.sofa", "com.account", "com.bill", "com.stori.security", "com.stori.sso"},
    exclude = {RocketMQAutoConfiguration.class})
public class WebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplication.class);

    /**
     * main function
     * 
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
