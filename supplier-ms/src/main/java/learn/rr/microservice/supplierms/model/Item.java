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
public class Item {
    private UUID productId;
    private Integer quantity;
    private  Double price ;
}
