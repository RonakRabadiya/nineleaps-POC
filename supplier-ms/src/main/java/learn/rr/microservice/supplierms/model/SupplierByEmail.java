package learn.rr.microservice.supplierms.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("supplier_by_email")
public class SupplierByEmail {
    @PrimaryKey
    private String email;
    private UUID id;
    private String name;

    public SupplierByEmail(String email, UUID id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public SupplierByEmail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


}
