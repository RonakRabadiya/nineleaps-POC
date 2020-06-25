package learn.rr.microservice.supplierms.service;

import learn.rr.microservice.supplierms.connector.rest.ProductRestConnector;
import learn.rr.microservice.supplierms.exception.BusinessException;
import learn.rr.microservice.supplierms.model.Item;
import learn.rr.microservice.supplierms.model.Order;
import learn.rr.microservice.supplierms.model.Product;
import learn.rr.microservice.supplierms.model.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationProcessorTest {
    @Mock
    private SupplierService supplierService;

    @Mock
    private ProductRestConnector connector;
    @Mock
    private MailSenderService mailSenderService;

    @Captor
    private ArgumentCaptor<String> methodParamCapture;

    @InjectMocks
    private NotificationProcessor notificationProcessor;

    @Test
    void processOrder() throws BusinessException {
        Order order = new Order();
        order.setCustomerAddress("Address");
        order.setCustomerEmail("cust-email@test.com");
        order.setCustomerName("Mock customer");
        order.setDate(LocalDate.now());
        order.setId(UUID.randomUUID());
        order.setItems(Collections.singleton(new Item(UUID.randomUUID(),10,100.00)));
        Supplier supplier = new Supplier(UUID.randomUUID(),"Mock Supplier","mockSup@test.com");
        when(connector.getAllSupplierForProducts(anyList())).thenReturn(Collections.singletonList(new Product(UUID.randomUUID(),
                UUID.randomUUID(), "Mock Product", "Mock Product description")));
        when(supplierService.getSupplierById(Mockito.any(UUID.class))).thenReturn(supplier);
        doNothing().when(mailSenderService).sendSampleMail(methodParamCapture.capture(),methodParamCapture.capture(),
                methodParamCapture.capture(),methodParamCapture.capture());
        notificationProcessor.processOrderCreated(order);
        List<String> captureValues = methodParamCapture.getAllValues();
        assertThat(captureValues).isNotNull();
        assertThat(captureValues).isNotEmpty();
        assertThat(captureValues.size()).isEqualTo(4);
        assertThat(captureValues.get(0)).isEqualTo("mockSup@test.com");
        assertThat(captureValues.get(1)).isEqualTo("");
        assertThat(captureValues.get(2)).isEqualTo("Order Received for Mock Product");
        assertThat(captureValues.get(3)).isEqualTo("You have received an order for product: Mock Product.");

    }
}
//[mockSup@test.com, , Order Received for Mock Product, You have received an order for product: Mock Product. ]