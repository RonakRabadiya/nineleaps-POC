package learn.rr.microservice.productms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@PrimaryKeyClass
public class ProductPrimaryKey {

    @PrimaryKeyColumn(name = "id",ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @PrimaryKeyColumn(name = "supplierid",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
    private UUID supplierId;
}
