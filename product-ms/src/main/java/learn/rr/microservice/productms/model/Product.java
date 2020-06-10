package learn.rr.microservice.productms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
public class Product {
    @PrimaryKey
    private ProductPrimaryKey key;
    private String name;
    private String description;
    private Double price;
}
