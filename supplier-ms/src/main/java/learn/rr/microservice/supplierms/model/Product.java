package learn.rr.microservice.supplierms.model;

import java.util.UUID;

public class Product {
    private UUID id ;
    private UUID supplierId;
    private String name;
    private String description;
//    private Double price;

    public Product(UUID id, UUID supplierId, String name, String description) {
        this.id = id;
        this.supplierId = supplierId;
        this.name = name;
        this.description = description;
    }

    public Product() {
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", supplierId=" + supplierId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
