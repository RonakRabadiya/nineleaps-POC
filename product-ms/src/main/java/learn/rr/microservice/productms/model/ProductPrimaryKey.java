package learn.rr.microservice.productms.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@PrimaryKeyClass
public class ProductPrimaryKey {

    @PrimaryKeyColumn(name = "id",ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @PrimaryKeyColumn(name = "supplierid",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
    private UUID supplierId;

    public ProductPrimaryKey() {
    }

    public ProductPrimaryKey(UUID id, UUID supplierId) {
        this.id = id;
        this.supplierId = supplierId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "ProductPrimaryKey{" +
                "id=" + id +
                ", supplierId=" + supplierId +
                '}';
    }
}
