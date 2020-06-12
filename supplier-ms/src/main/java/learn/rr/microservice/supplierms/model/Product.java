package learn.rr.microservice.supplierms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private UUID id ;
    private UUID supplierId;
    private String name;
    private String description;
//    private Double price;


}
