package learn.rr.microservice.supplierms.kafka.consumer;

import learn.rr.microservice.supplierms.model.Order;
import learn.rr.microservice.supplierms.service.MailSenderService;
import learn.rr.microservice.supplierms.service.NotificationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class MessageReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    private NotificationProcessor notificationProcessor;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @KafkaListener(topics = "${spring.kafka.topic.order.created}",groupId = "${spring.kafka.consumer.client-id}}" )
    public void receive(Order order){
        LOGGER.info("message received ... message received: {} ",order);
        notificationProcessor.processOrderCreated(order);
        countDownLatch.countDown();
    }
}
