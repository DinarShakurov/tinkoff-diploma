package ru.tfs.spring.hw.medical_service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.tfs.spring.hw.medical_service.dto.VaccinationKafkaDto;
import ru.tfs.spring.hw.medical_service.property.ApplicationProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class KafkaConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        ApplicationProperties.KafkaConfig kafka = applicationProperties.getKafka();

        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getServer());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic generateQrCodeTopic() {
        ApplicationProperties.KafkaConfig kafka = applicationProperties.getKafka();
        return new NewTopic(kafka.getGenerateQrCodeTopic(), kafka.getGenerateQrCodeTopicNumPartitions(),
                kafka.getGenerateQrCodeTopicReplicationFactor());
    }

    @Bean
    public ProducerFactory<Long, VaccinationKafkaDto> producerFactory() {
        ApplicationProperties.KafkaConfig kafka = applicationProperties.getKafka();

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getServer());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        configProps.put(ProducerConfig.ACKS_CONFIG, kafka.getAcksConfig());

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<Long, VaccinationKafkaDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory()) {{
            setDefaultTopic(applicationProperties.getKafka().getGenerateQrCodeTopic());
        }};
    }
}
