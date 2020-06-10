package learn.rr.microservice.supplierms.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import learn.rr.microservice.supplierms.model.Supplier;

@Repository
public interface SupplierRepository extends CassandraRepository<Supplier, UUID>{

}
