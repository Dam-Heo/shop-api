package com.example.allrabackendassignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    /**
     * RestTemplate 인스턴스를 생성하는 Bean.
     * <p>
     * RestTemplate은 RESTful 웹 서비스와 작업하기 위한 유틸리티 클래스입니다.
     * 기본 HTTP 클라이언트를 추상화하여 더 편리하게 리소스와 작업할 수 있도록 합니다.
     * <p>
     * RestTemplate은 스레드 안전(thread-safe)하므로 여러 컴포넌트에 안전하게 주입할 수 있습니다.
     *
     * @return RestTemplate의 새 인스턴스.
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
