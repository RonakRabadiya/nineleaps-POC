package learn.rr.microservice.productms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private UUID id ;
    @NotNull(message = "supplier id should not be null or empty")
    private UUID supplierId;
    @NotBlank(message = "product name is required")
    private String name;
    @NotBlank(message = "prodcut description is required")
    private String description;
    @NotNull(message = "product price is required")
    @Min(value = 1,message = "product price should be positive value")
    private Double price;
}
