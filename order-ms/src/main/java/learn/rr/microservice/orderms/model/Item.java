package learn.rr.microservice.orderms.model;

import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@UserDefinedType("orderitem")
public class Item implements Serializable {

    @CassandraType(type = CassandraType.Name.UUID)
    @NotNull(message = "Product id required to place an order")
    private UUID productId;

    @CassandraType(type = CassandraType.Name.INT)
    @Min(value = 1,message = "product quantity should be positive")
    @EqualsAndHashCode.Exclude
    private int quantity;

    @CassandraType(type = CassandraType.Name.DECIMAL)
    @DecimalMin(value = "1.0",message ="Price should be an positive number" )
    @EqualsAndHashCode.Exclude
    private double price ;
}
