package learn.rr.microservice.productms.repository;

import learn.rr.microservice.productms.model.ProductBySupplier;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductBySupplierRepository extends CassandraRepository<ProductBySupplier, UUID> {

}

