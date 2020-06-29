package learn.rr.microservice.supplierms.it;

import learn.rr.microservice.supplierms.connector.rest.ProductRestConnector;
import learn.rr.microservice.supplierms.model.Supplier;
import learn.rr.microservice.supplierms.service.MailSenderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class SupplierIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRestConnector productRestConnector;

    @MockBean
    private MailSenderService mailSenderService;

    @Test
    @Order(1)
    void getAllSupplier() throws Exception {
        mockMvc.perform(get("/")
                .header("API-VERSION", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    @Order(2)
    void getSupplierById() throws Exception {
        mockMvc.perform(get("/{id}", "9128f749-3fab-4ed0-837e-318891b70591")
                .header("API-VERSION", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id", is("9128f749-3fab-4ed0-837e-318891b70591")))
                .andExpect(jsonPath("$.name", is("ronak1")))
                .andExpect(jsonPath("$.email", is("ronak1@gmail.com")));
    }

    @Test
    @Order(3)
    void createSupplier() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("New Create Supplier");
        supplier.setEmail("supp@gmail.com");
        mockMvc.perform(post("/")
                .header("API-VERSION", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJson(supplier)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void updateSupplier() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("update Supplier");
        supplier.setEmail("supp1@gmail.com");
        mockMvc.perform(put("/{id}", "9128f749-3fab-4ed0-837e-318891b70592")
                .header("API-VERSION", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJson(supplier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id", is("9128f749-3fab-4ed0-837e-318891b70592")))
                .andExpect(jsonPath("$.name", is("update Supplier")))
                .andExpect(jsonPath("$.email", is("supp1@gmail.com")));
    }

    @Test
    @Order(5)
    void deleteSupplier() throws Exception {
        mockMvc.perform(delete("/{id}", "9128f749-3fab-4ed0-837e-318891b70593")
                .header("API-VERSION", "1"))
                .andExpect(status().isOk());
    }
}
