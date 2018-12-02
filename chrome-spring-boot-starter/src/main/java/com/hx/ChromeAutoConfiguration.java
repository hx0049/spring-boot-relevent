package com.hx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ChromeProperty.class)
@ConditionalOnClass({ChromeRunner.class})
@ConditionalOnProperty(prefix = "chrome",value = "enabled",matchIfMissing = true)
public class ChromeAutoConfiguration {

    @Autowired
    private ChromeProperty chromeProperty;

    @Bean
    public ChromeRunner chromeRunner(){
        ChromeRunner runner = new ChromeRunner();
        runner.init(chromeProperty);
        return  runner;
    }
}
