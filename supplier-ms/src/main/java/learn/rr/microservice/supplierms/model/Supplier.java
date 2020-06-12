package learn.rr.microservice.supplierms.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
public class Supplier {

	@PrimaryKey
	private UUID id;
	private String name;
	private String email;
}
