package learn.rr.microservice.supplierms.service;

import learn.rr.microservice.supplierms.connector.rest.ProductRestConnector;
import learn.rr.microservice.supplierms.model.Order;
import learn.rr.microservice.supplierms.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationProcessor {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductRestConnector connector;

    @Autowired
    MailSenderService mailSenderService;

    public void processOrderCreated(Order order){
        List<String> ids = order.getItems().stream().map(item -> item.getProductId().toString()).collect(Collectors.toList());
        List<Product> products = connector.getAllSupplierForProducts(ids);
        String message = "You have received an order for product: #productName#. ";
        String subjectTemplate = "Order Received for #productName#";
        for(Product p  : products){
            try {
                String body = message.replaceAll("#productName#", p.getName());
                System.out.println("Body : " + body);
                String to = supplierService.getSupplierById(p.getSupplierId()).getEmail();
                System.out.println("to:"+to);
                String suject= subjectTemplate.replaceAll("#productName#", p.getName());
                System.out.println("subject"+suject);
                mailSenderService.sendSampleMail(to,"",suject,body);

            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("Order processing failed for supplier id:" + p.getSupplierId());
            }
        }
    }
}
