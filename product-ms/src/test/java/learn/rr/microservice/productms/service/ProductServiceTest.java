package learn.rr.microservice.productms.service;

import learn.rr.microservice.productms.dto.ProductDto;
import learn.rr.microservice.productms.exception.BusinessException;
import learn.rr.microservice.productms.mapper.ProductMapper;
import learn.rr.microservice.productms.model.Product;
import learn.rr.microservice.productms.model.ProductBySupplier;
import learn.rr.microservice.productms.model.ProductPrimaryKey;
import learn.rr.microservice.productms.repository.ProductBySupplierRepository;
import learn.rr.microservice.productms.repository.ProductRepository;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductBySupplierRepository productBySupplierRepository;
    @Spy
    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProduct() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(getMockProduct()));
        List<ProductDto> response = productService.getAllProducts();
        assertThat(response).hasSize(1);
        assertProduct(response.get(0));
    }

    @Test
    void getAllProductByListOfId() {
        when(productRepository.findAllByKeyIdIn(anyList())).thenReturn(Collections.singletonList(getMockProduct()));
        List<ProductDto> response = productService.getAllProductsByIds(Arrays.asList(UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a51")));
        assertThat(response).hasSize(1);
        assertProduct(response.get(0));
    }

    @Test
    void getProductByProductId() throws BusinessException {
        when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.of(getMockProduct()));
        ProductDto response = productService.getProductByProductId(UUID.fromString("ce9c1524-1fb3-48e2-b717" +
                "-8f7bfae36a51"));
        assertProduct(response);
    }

    @Test
    void getProductByProductIdWithProductNotFoundForGivenId() throws BusinessException {
        when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.empty());
        Throwable ex =catchThrowable(() -> {
            productService.getProductByProductId(UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a51"));});
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(ex).hasMessage("product not found");
        assertThat(((BusinessException)ex).getErrorCode()).isEqualTo("PRODUCT_NOT_FOUND");
    }

    @Test
    @DisplayName("Create Product using supplier having already product registered")
    void createProductSupplierAlreadyRegistered(){
        when(productBySupplierRepository.existsById(any(UUID.class))).thenReturn(true);
        ProductDto request = getMockProductDto();
        request.setId(null);
        Throwable ex =catchThrowable(() -> {productService.createProduct(request);});
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(ex).hasMessage("Supplier is already registered with some other product");
        assertThat(((BusinessException)ex).getErrorCode()).isEqualTo("SUPPLIER_ALREAD_REGISTERED");
    }

    @Test
    @DisplayName("Create Product success")
    void createProduct() throws BusinessException {
        when(productBySupplierRepository.existsById(any(UUID.class))).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(getMockProduct());
        when(productBySupplierRepository.save(any(ProductBySupplier.class))).thenReturn(new ProductBySupplier());
        ProductDto request = getMockProductDto();
        ProductDto response = productService.createProduct(request);
        assertProduct(response);
    }

    @Test
    @DisplayName("Update product : product is not registered yet")
    void updateProductWithProductNotFound() throws BusinessException {
       when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.empty());
       ProductDto productDto = getMockProductDto();
       UUID id  = productDto.getId();
        Throwable ex =catchThrowable(() -> {productService.updateProduct(id,productDto);});
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(ex).hasMessage("product not found");
        assertThat(((BusinessException)ex).getErrorCode()).isEqualTo("PRODUCT_NOT_FOUND");
    }

    @Test
    @DisplayName("Update product : change supplier id")
    void updateProductWithSupplierUpdate() throws BusinessException {
        when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.of(getMockProduct()));
        ProductDto productDto = getMockProductDto();
        productDto.setSupplierId(UUID.randomUUID());
        UUID id  = productDto.getId();
        Throwable ex =catchThrowable(() -> {productService.updateProduct(id,productDto);});
        assertThat(ex).isInstanceOf(BusinessException.class);
        assertThat(ex).hasMessage("You Can not change supplier id. Please delete product and register again with different supplier");
        assertThat(((BusinessException)ex).getErrorCode()).isEqualTo("BUSINESS_ERROR");
    }

    @Test
    @DisplayName("Update product : product is not registered yet")
    void updateProductsuccess() throws BusinessException {
        when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.of(getMockProduct()));
        when(productRepository.save(any(Product.class))).thenReturn(getMockProduct());
        when(productBySupplierRepository.save(any(ProductBySupplier.class))).thenReturn(new ProductBySupplier());
        ProductDto productDto = getMockProductDto();
        UUID id  = productDto.getId();
        ProductDto response = productService.updateProduct(id,productDto);
        assertProduct(response);
    }

    @Test
    void deleteProductWithProductNotExists(){
        when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.empty());
//        doNothing().when(productRepository).delete(any(Product.class));
//        doNothing().when(productBySupplierRepository).deleteById(any(UUID.class));
        productService.deleteProduct(getMockProductDto().getId());
        verify(productRepository,times(1)).findByKeyId(any(UUID.class));
        verify(productRepository,never()).delete(any(Product.class));
        verify(productBySupplierRepository,never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteProductSuccess(){
        when(productRepository.findByKeyId(any(UUID.class))).thenReturn(Optional.of(getMockProduct()));
        doNothing().when(productRepository).delete(any(Product.class));
        doNothing().when(productBySupplierRepository).deleteById(any(UUID.class));
        productService.deleteProduct(getMockProductDto().getId());
        verify(productRepository,times(1)).findByKeyId(any(UUID.class));
        verify(productRepository,times(1)).delete(any(Product.class));
        verify(productBySupplierRepository,times(1)).deleteById(any(UUID.class));
    }

    private void assertProduct(ProductDto productDto) {
        assertThat(productDto).isNotNull();
        assertThat(productDto.getId()).isEqualTo(getMockProduct().getKey().getId());
        assertThat(productDto.getSupplierId()).isEqualTo(getMockProduct().getKey().getSupplierId());
        assertThat(productDto.getName()).isEqualTo(getMockProduct().getName());
        assertThat(productDto.getDescription()).isEqualTo(getMockProduct().getDescription());
        assertThat(productDto.getPrice()).isEqualTo(getMockProduct().getPrice());
    }


    private Product getMockProduct() {
        Product product = new Product();
        product.setKey(new ProductPrimaryKey(UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a51"),
                UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a52")));
        product.setDescription("Description Mock Product");
        product.setName("Mock Product");
        product.setPrice(1000.00);
        return product;
    }

    private ProductDto getMockProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a51"));
        productDto.setSupplierId(UUID.fromString("ce9c1524-1fb3-48e2-b717-8f7bfae36a52"));
        productDto.setDescription("Description Mock Product");
        productDto.setName("Mock Product");
        productDto.setPrice(1000.00);
        return productDto;
    }

}
