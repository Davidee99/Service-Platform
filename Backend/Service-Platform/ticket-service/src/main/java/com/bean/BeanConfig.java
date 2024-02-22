package com.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utils.AppConstants;

@Configuration
public class BeanConfig 
{
    @Bean
    AppConstants appConstants() { return new AppConstants(); }
}
