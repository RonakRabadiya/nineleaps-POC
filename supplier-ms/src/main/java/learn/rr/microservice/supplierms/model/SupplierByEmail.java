package learn.rr.microservice.supplierms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("supplier_by_email")
public class SupplierByEmail {
    @PrimaryKey
    private String email;
    private UUID id;
    private String name;
}
