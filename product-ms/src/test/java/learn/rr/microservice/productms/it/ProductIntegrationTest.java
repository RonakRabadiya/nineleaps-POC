package learn.rr.microservice.productms.it;

import learn.rr.microservice.productms.dto.ProductDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class ProductIntegrationTest extends AbstractIntegratinTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/")
                .header("API-VERSION", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @Order(2)
    void getProductById() throws Exception {
        mockMvc.perform(get("/{id}", "9028f749-3fab-4ed0-837e-318891b70591")
                .header("API-VERSION", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.is("9028f749-3fab-4ed0-837e-318891b70591")))
                .andExpect(jsonPath("$.supplierId", Matchers.is("9128f749-3fab-4ed0-837e-318891b70591")))
                .andExpect(jsonPath("$.name", Matchers.is("product-1")))
                .andExpect(jsonPath("$.description", Matchers.is("this is product 1")))
                .andExpect(jsonPath("$.price", Matchers.is(100.00)));
    }

    @Test
    @Order(3)
    void createProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setSupplierId(UUID.randomUUID());
        productDto.setDescription("Mock Runtime Add product");
        productDto.setName("Runtime product");
        productDto.setPrice(1000.00);

        mockMvc.perform(post("/")
                .header("API-VERSION", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJson(productDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void updateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setSupplierId(UUID.fromString("9128f749-3fab-4ed0-837e-318891b70591"));
        productDto.setDescription("Updated Product");
        productDto.setName("Product-001");
        productDto.setPrice(1000.00);

        mockMvc.perform(put("/{id}", "9028f749-3fab-4ed0-837e-318891b70591")
                .header("API-VERSION", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJson(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.is("9028f749-3fab-4ed0-837e-318891b70591")))
                .andExpect(jsonPath("$.supplierId", Matchers.is("9128f749-3fab-4ed0-837e-318891b70591")))
                .andExpect(jsonPath("$.name", Matchers.is("Product-001")))
                .andExpect(jsonPath("$.description", Matchers.is("Updated Product")))
                .andExpect(jsonPath("$.price", Matchers.is(1000.00)));
    }

    @Test
    @Order(5)
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/{id}", "9028f749-3fab-4ed0-837e-318891b70591")
                .header("API-VERSION", "1"))
                .andExpect(status().isOk());
    }

}
