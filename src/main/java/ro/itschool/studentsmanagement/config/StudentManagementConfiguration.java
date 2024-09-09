package ro.itschool.studentsmanagement.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class StudentManagementConfiguration {

    // This is the configuration for the first external service
    @Bean
    public RestTemplate firstExternalService() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        restTemplateBuilder.basicAuthentication("user", "password");
        restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10));

        return restTemplateBuilder.rootUri("http://localhost:8080/api/v1").build();
    }

    // This is the configuration for the second external service
    @Bean
    public RestTemplate secondExternalService() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10));
        restTemplateBuilder.setReadTimeout(Duration.ofSeconds(10));
        restTemplateBuilder.basicAuthentication("user", "password");
        restTemplateBuilder.defaultHeader("header", "value");

        return restTemplateBuilder.build();
    }
}
