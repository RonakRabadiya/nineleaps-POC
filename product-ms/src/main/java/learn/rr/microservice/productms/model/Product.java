package learn.rr.microservice.productms.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Product {

    @PrimaryKey
    private ProductPrimaryKey key;
    private String name;
    private String description;
    private Double price;

    public Product(ProductPrimaryKey key, String name, String description, Double price) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public ProductPrimaryKey getKey() {
        return key;
    }

    public void setKey(ProductPrimaryKey key) {
        this.key = key;
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
        return "Product{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
