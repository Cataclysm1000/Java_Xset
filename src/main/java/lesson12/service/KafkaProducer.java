package lesson12.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lesson12.model.KafkaMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(String urlRequest, Object messageObject) {
        try {
            KafkaMessage kafkaMessage = new KafkaMessage(urlRequest, messageObject);

            String kafkaPayload = objectMapper.writeValueAsString(kafkaMessage);

            kafkaTemplate.send(topicName, kafkaPayload);

            return "Запись по " + urlRequest + " успешно добавлена в Kafka";

        } catch (JsonProcessingException e) {
            return "Ошибка JSON: " + e.getMessage();
        }
    }
}
