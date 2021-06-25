package com.mongant.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan(basePackages = {"com.mongant.controller", "com.mongant.advice"})
public class WebConfig {
}
