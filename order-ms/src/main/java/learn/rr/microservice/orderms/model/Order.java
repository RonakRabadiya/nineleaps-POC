package learn.rr.microservice.orderms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
public class Order {

    @PrimaryKey
    private UUID id;

    private LocalDate date;

    @Column("customer_name")
    @NotBlank(message = "Customer name required")
    private String customerName;

    @Column("customer_email")
    @NotBlank(message = "Customer email required")
    @Email(regexp = ".+@.+\\..+",message = "Not a valid email address")
    private String customerEmail;

    @Column("customer_address")
    @NotBlank(message = "Customer address required")
    private String customerAddress;

    @Column("items")
    @NotEmpty
    @Valid
    private Set<Item> items;
    private double total;
}
