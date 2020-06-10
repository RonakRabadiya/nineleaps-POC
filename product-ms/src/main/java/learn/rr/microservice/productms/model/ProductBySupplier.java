package learn.rr.microservice.productms.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("product_by_supplier")
public class ProductBySupplier {

    @PrimaryKey
    private UUID supplierId;
    private UUID id;
    private String name;
    private String description;
    private Double price;

    public ProductBySupplier(UUID supplierId, UUID id, String name, String description, Double price) {
        this.supplierId = supplierId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductBySupplier() {
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductBySupplier{" +
                "supplierId=" + supplierId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
