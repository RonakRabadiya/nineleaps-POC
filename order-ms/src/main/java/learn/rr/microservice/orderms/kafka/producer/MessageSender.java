package learn.rr.microservice.orderms.kafka.producer;

import learn.rr.microservice.orderms.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class MessageSender {

    @Autowired
    KafkaTemplate<String, Order> template;

    public void send(String topic, Order payload) {
        log.info("sending message with topic={} and payload={}", topic, payload);
        template.send(topic, payload);
    }
}
