package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilerConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/api/profile/*");
        bean.addUrlPatterns("/api/address/*");
        bean.addUrlPatterns("/api/suppliers/*");
        bean.addUrlPatterns("/api/dosage/*");
        bean.addUrlPatterns("/api/treatment/*");
        bean.addUrlPatterns("/api/prescription/*");
        bean.addUrlPatterns("/api/form/*");
        bean.addUrlPatterns("/api/medicine/*");
        return bean;
    }

}
