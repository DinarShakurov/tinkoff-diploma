package ru.tfs.spring.hw.medical_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.tfs.spring.hw.medical_service.property.ApplicationProperties;

@SpringBootApplication
@EnableFeignClients(basePackages = "ru.tfs.spring.hw.medical_service.feign")
@EnableConfigurationProperties(ApplicationProperties.class)
public class MedicalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalServiceApplication.class, args);
    }
}
