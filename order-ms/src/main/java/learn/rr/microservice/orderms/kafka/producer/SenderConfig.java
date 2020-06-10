package learn.rr.microservice.orderms.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import learn.rr.microservice.orderms.model.Order;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class SenderConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }

    @Bean
    public ProducerFactory<String, Order> producerFactory(){
        return  new DefaultKafkaProducerFactory<>(producerConfig(),
            new StringSerializer(),new JsonSerializer<Order>().forKeys().noTypeInfo()
        );
    }

    @Bean
    public KafkaTemplate<String,Order> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public MessageSender sender(){
        return new MessageSender();
    }
}
