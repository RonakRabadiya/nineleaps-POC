package learn.rr.microservice.orderms.it;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import learn.rr.microservice.orderms.model.Item;
import learn.rr.microservice.orderms.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class OrderIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void orderCreated() throws Exception {
        Order order = new Order();
        order.setCustomerName("Mock Customer");
        order.setCustomerAddress("Mock address");
        order.setCustomerEmail("test@gamil.com");
        order.setItems(Collections.singleton(new Item(UUID.fromString("9028f749-3fab-4ed0-837e-318891b70591"), 10, 100)));

        mockMvc.perform(post("/")
                .header("API-VERSION", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJson(order)))
                .andExpect(status().isCreated());

        //kafka message validation




        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);



        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.topic()).isNotNull();
        assertThat(singleRecord.topic()).isEqualTo("ORDER_CREATED_TOPIC");
        assertThat(singleRecord.value()).isNotNull();
    }
}
