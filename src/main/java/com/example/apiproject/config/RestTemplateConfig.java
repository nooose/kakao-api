package com.example.apiproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // TODO: feignClient 또는 webClient 로 변경해보기
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
