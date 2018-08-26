package com.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加对jsp的支持
     *
     * @return
     */
    @Bean
    public ViewResolver getJspViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/");
        internalResourceViewResolver.setSuffix(".html");
        internalResourceViewResolver.setOrder(1);
        return internalResourceViewResolver;
    }

    @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setPrefix("/");
        freeMarkerViewResolver.setSuffix(".html");
        freeMarkerViewResolver.setRequestContextAttribute("request");
        freeMarkerViewResolver.setOrder(0);
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        return freeMarkerViewResolver;
    }

    @Bean
    public FreeMarkerConfig freeMarkerConfig() throws Exception {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:/templates");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setConfiguration(factory.createConfiguration());
        return config;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
       /* registry.addResourceHandler("/img/**")
                 .addResourceLocations("classpath:/");
        registry.addResourceHandler("/static/img/**")
                .addResourceLocations("classpath:/static/");*/
    }
}
