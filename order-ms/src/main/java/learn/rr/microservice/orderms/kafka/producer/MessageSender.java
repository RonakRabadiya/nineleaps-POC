package learn.rr.microservice.orderms.kafka.producer;

import learn.rr.microservice.orderms.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class MessageSender {

    @Autowired
    KafkaTemplate<String, Order> template ;

    public void send(String topic,Order payload){
        template.send(topic, payload);
    }
}
