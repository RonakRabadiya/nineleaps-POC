package learn.rr.microservice.supplierms.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Supplier {

	@PrimaryKey
	private UUID id;
	private String name;
	private String email;

	public Supplier(UUID id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Supplier() {
		super();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	
}
