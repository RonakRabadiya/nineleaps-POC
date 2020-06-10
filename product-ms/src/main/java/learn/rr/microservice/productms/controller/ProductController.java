package learn.rr.microservice.productms.controller;

import learn.rr.microservice.productms.dto.ProductDto;
import learn.rr.microservice.productms.exception.BusinessException;
import learn.rr.microservice.productms.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductService productService;
    private UUID id;
    private ProductDto productDto;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name = "products",required = false, defaultValue = "") List<UUID> productIds) {
        if(productIds.isEmpty()) {
            return productService.getAllProducts();
        } else {
            return productService.getAllProductsByIds(productIds);
        }
    }

    @GetMapping("/{productid}")
    public ProductDto getProductById(@PathVariable("productid") UUID id) throws BusinessException {
        return productService.getProductByProductId(id);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) throws BusinessException {
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{productid}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productid") UUID id, @RequestBody ProductDto productDto) throws BusinessException {
        ProductDto product = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productid") UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
