package learn.rr.microservice.orderms.repository;

import learn.rr.microservice.orderms.model.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;


public interface OrderRepository extends CassandraRepository<Order, UUID> {
}
