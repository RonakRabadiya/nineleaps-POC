package learn.rr.microservice.supplierms.connector.rest;

import learn.rr.microservice.supplierms.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductRestConnector {

    @Autowired
    private RestTemplate template;

    @Value("${ecom.productms.url}")
    private String baseUrl ;

    public List<Product> getAllSupplierForProducts(List<String> productIds){
        String url = baseUrl+"/products?products={products}";
        Map<String,Object> uriVariables = new HashMap<>();
        uriVariables.put("products",String.join(",",productIds));
        ResponseEntity<Product[]> resp = template.getForEntity(url,Product[].class,uriVariables);
        if(resp.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(Objects.requireNonNull(resp.getBody()));
        }else{
            throw new RuntimeException("Connection Error :: Response Code : " + resp.getStatusCode());
        }

    }

}

