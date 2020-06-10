package learn.rr.microservice.orderms.controller;

import learn.rr.microservice.orderms.model.Order;
import learn.rr.microservice.orderms.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody Order order){
        Order r = orderService.createOrder(order);
        System.out.println("Created ORder ::: " + r);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
