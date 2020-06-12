package learn.rr.microservice.orderms.controller;

import learn.rr.microservice.orderms.model.Order;
import learn.rr.microservice.orderms.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid  @RequestBody Order order){
        Order r = orderService.createOrder(order);
        System.out.println("Created ORder ::: " + r);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex){
        List<String> errors = new ArrayList<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField() + ":"+error.getDefaultMessage());
        }

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
