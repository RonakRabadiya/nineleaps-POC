package learn.rr.microservice.orderms.service;

import learn.rr.microservice.orderms.kafka.producer.MessageSender;
import learn.rr.microservice.orderms.model.Item;
import learn.rr.microservice.orderms.model.Order;
import learn.rr.microservice.orderms.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MessageSender messageSender;
    @InjectMocks
    private OrderService orderService;

    @Captor
    private ArgumentCaptor<Order> saveOrderArgsCaptor;

    @Captor
    private ArgumentCaptor<String> stringParamArgumentCaptor;

    @BeforeEach
    void setup() {

        ReflectionTestUtils.setField(orderService, "ORDER_CREATED_TOPIC", "ORDER_CREATED");
    }

    @Test
    void createOrder() {
        Order order = getMockOrder();
        order.setTotal(0);
        when(orderRepository.save(saveOrderArgsCaptor.capture())).thenReturn(getMockOrder());
        doNothing().when(messageSender).send(stringParamArgumentCaptor.capture(), Mockito.any(Order.class));
        orderService.createOrder(order);
        double total = saveOrderArgsCaptor.getValue().getTotal();
        assertThat(total).isEqualTo(1000.00);
        assertThat(stringParamArgumentCaptor.getValue()).isEqualTo("ORDER_CREATED");

    }


    private Order getMockOrder() {
        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setTotal(1000);
        order.setId(UUID.randomUUID());
        order.setCustomerAddress("Mock cust address");
        order.setCustomerEmail("mock-custemail@test.com");
        order.setCustomerName("Mock customer");
        order.setItems(Collections.singleton(new Item(UUID.randomUUID(), 100, 10)));
        return order;
    }
}
