package learn.rr.microservice.productms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("product_by_supplier")
public class ProductBySupplier {

    @PrimaryKey
    private UUID supplierId;
    private UUID id;
    private String name;
    private String description;
    private Double price;
}
