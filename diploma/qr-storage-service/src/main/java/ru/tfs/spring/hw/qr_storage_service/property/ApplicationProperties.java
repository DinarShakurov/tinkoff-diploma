package ru.tfs.spring.hw.qr_storage_service.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qr-storage-service.config")
@Data
public class ApplicationProperties {

    private KafkaConfig kafka = new KafkaConfig();

    @Data
    public static class KafkaConfig {
        private String server;
        private String groupId;
        private Boolean enableAutoCommit;
        private String generateQrCodeTopic;
        private Integer generateQrCodeTopicNumPartitions;
        private Short generateQrCodeTopicReplicationFactor;

        private String acksConfig;
    }
}
