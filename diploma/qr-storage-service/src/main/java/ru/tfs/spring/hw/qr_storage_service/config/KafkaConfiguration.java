package ru.tfs.spring.hw.qr_storage_service.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.tfs.spring.hw.qr_storage_service.dto.VaccinationKafkaDto;
import ru.tfs.spring.hw.qr_storage_service.property.ApplicationProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public ConsumerFactory<Long, VaccinationKafkaDto> consumerFactory() {
        ApplicationProperties.KafkaConfig kafkaConfig = applicationProperties.getKafka();

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getGroupId());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfig.getEnableAutoCommit());
        return new DefaultKafkaConsumerFactory<>
                (config, new LongDeserializer(), new JsonDeserializer<>(VaccinationKafkaDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, VaccinationKafkaDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, VaccinationKafkaDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(false);
        return factory;
    }
}
