package learn.rr.microservice.orderms.model;

import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@UserDefinedType("orderitem")
public class Item implements Serializable {

    @CassandraType(type = CassandraType.Name.UUID)
    private UUID productId;
    @CassandraType(type = CassandraType.Name.INT)
    private int quantity;
    @CassandraType(type = CassandraType.Name.DECIMAL)
    private double price ;

    public Item() {
    }

    public Item(UUID productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return productId.equals(item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
