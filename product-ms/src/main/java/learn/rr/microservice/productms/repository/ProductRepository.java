package learn.rr.microservice.productms.repository;

import learn.rr.microservice.productms.model.Product;
import learn.rr.microservice.productms.model.ProductPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends CassandraRepository<Product, ProductPrimaryKey> {




    Optional<Product> findByKeyId(UUID id);

    List<Product> findAllByKeyIdIn(List<UUID> ids);
}
