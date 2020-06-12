package learn.rr.microservice.supplierms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order  {
    private UUID id;
    private LocalDate date;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private Set<Item> items;
    private Double total;

}
