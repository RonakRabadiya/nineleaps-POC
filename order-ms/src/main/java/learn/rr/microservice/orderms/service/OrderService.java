package learn.rr.microservice.orderms.service;

import learn.rr.microservice.orderms.kafka.producer.MessageSender;
import learn.rr.microservice.orderms.model.Order;
import learn.rr.microservice.orderms.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.kafka.topic.order.created}")
    private String ORDER_CREATED_TOPIC;

    private MessageSender sender;

    private OrderRepository orderRepository;

    public OrderService(MessageSender sender, OrderRepository orderRepository) {
        this.sender = sender;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(@RequestBody Order order){
        order.setId(UUID.randomUUID());
        double total =order.getItems().stream().mapToDouble(item -> item.getPrice()*item.getQuantity()).sum();
        order.setTotal(total);
        order.setDate(LocalDate.now());
        Order savedOrder =  orderRepository.save(order);
        logger.info("Order Saved :: " + savedOrder);
        sender.send(ORDER_CREATED_TOPIC,savedOrder);
        return savedOrder;
    }

}
