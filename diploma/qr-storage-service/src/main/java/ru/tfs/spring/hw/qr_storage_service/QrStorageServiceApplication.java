package ru.tfs.spring.hw.qr_storage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.tfs.spring.hw.qr_storage_service.property.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableJpaAuditing
public class QrStorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrStorageServiceApplication.class, args);
    }
}