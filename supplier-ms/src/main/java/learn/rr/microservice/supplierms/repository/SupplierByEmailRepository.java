package learn.rr.microservice.supplierms.repository;

import learn.rr.microservice.supplierms.model.SupplierByEmail;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierByEmailRepository extends CassandraRepository<SupplierByEmail,String> {
}
