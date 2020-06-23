package learn.rr.microservice.productms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import learn.rr.microservice.productms.dto.ProductDto;
import learn.rr.microservice.productms.exception.BusinessException;
import learn.rr.microservice.productms.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Api(tags = "product microservice",value = "Product Controller", description = "Controller for all product related operations")
@RestController
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductService productService;
    private UUID id;
    private ProductDto productDto;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "retrive all products or filter products based on query input")
    @ApiParam(name = "products", value = "list of product id to be fetched")
    @GetMapping(headers = "API-VERSION=1")
    public List<ProductDto> getAllProducts(@RequestParam(name = "products",required = false, defaultValue = "") List<UUID> productIds) {
        if(productIds.isEmpty()) {
            return productService.getAllProducts();
        } else {
            return productService.getAllProductsByIds(productIds);
        }
    }

    @ApiOperation("retrive product by product id")
    @GetMapping(value = "/{productid}",headers = "API-VERSION=1")
    public ProductDto getProductById(@PathVariable("productid") UUID id) throws BusinessException {
        return productService.getProductByProductId(id);
    }

    @ApiOperation("Create product")
    @PostMapping(headers = "API-VERSION=1")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductDto productDto) throws BusinessException {
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Update product by id")
    @PutMapping(value = "/{productid}",headers = "API-VERSION=1")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productid") @NotNull UUID id, @Valid @RequestBody ProductDto productDto) throws BusinessException {
        ProductDto product = productService.updateProduct(id, productDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation("Delete product")
    @DeleteMapping(value = "/{productid}" ,headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productid") @NotNull UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
