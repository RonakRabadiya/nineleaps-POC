package learn.rr.microservice.productms.service;

import learn.rr.microservice.productms.dto.ProductDto;
import learn.rr.microservice.productms.exception.BusinessException;
import learn.rr.microservice.productms.exception.ErrorCode;
import learn.rr.microservice.productms.mapper.ProductMapper;
import learn.rr.microservice.productms.model.Product;
import learn.rr.microservice.productms.model.ProductBySupplier;
import learn.rr.microservice.productms.repository.ProductBySupplierRepository;
import learn.rr.microservice.productms.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private ProductBySupplierRepository productBySupplierRepository;

    public List<ProductDto> getAllProducts() {
        List<Product>  productSlices = productRepository.findAll() ;
        return productMapper.productsToProductDtos(productSlices);
    }

    public List<ProductDto> getAllProductsByIds(List<UUID> productIds) {
        List<Product> products = productRepository.findAllByKeyIdIn(productIds);
        return productMapper.productsToProductDtos(products);
    }


    public ProductDto getProductByProductId(UUID id) throws BusinessException {
        Optional<Product> product = productRepository.findByKeyId(id);
        if (!product.isPresent()) {
            throw new BusinessException(ErrorCode.ERROR_CODE_PRODUCT_NOT_FOUND,"product not found");
        }
        return productMapper.productToProductDto(product.get());
    }

    public ProductDto createProduct(ProductDto productDto) throws BusinessException {
        boolean isSupplierRegistered = checkForProductSupplierAlreadyRegistered(productDto.getSupplierId());
        if(isSupplierRegistered){
            throw new BusinessException(ErrorCode.ERROR_CODE_SUPPLIER_ALREAD_REGISTERED,"Supplier is already registered with some other product");
        }
        Product product = productMapper.productDtoToProduct(productDto);
        product.getKey().setId(UUID.randomUUID());
        Product savedProduct = productRepository.save(product);
        saveProductEntities(productDto);
        return productMapper.productToProductDto(savedProduct);
    }

    private boolean checkForProductSupplierAlreadyRegistered(UUID supplierId) {
        return productBySupplierRepository.existsById(supplierId);
    }

    private void saveProductEntities(ProductDto productDto) {
        ProductBySupplier productBySupplier = productMapper.productDtoToProductBySupplier(productDto);
        System.out.println("productBySupplier"+productBySupplier);
        productBySupplierRepository.save(productBySupplier);
    }

    public ProductDto updateProduct(UUID id, ProductDto productDto) throws BusinessException {
        Optional<Product> savedProduct = productRepository.findByKeyId(id);
        if (!savedProduct.isPresent()) {
            throw new BusinessException(ErrorCode.ERROR_CODE_PRODUCT_NOT_FOUND,"product not found");
        }
        if(!savedProduct.get().getKey().getSupplierId().equals(productDto.getSupplierId())){
            throw new BusinessException(ErrorCode.BUSINESS_ERROR,"You Can not change supplier id. Please delete product and register again with different supplier");
        }
        productDto.setId(id);
        Product product = productMapper.productDtoToProduct(productDto);
        product = productRepository.save(product);
        updateProductEntities(savedProduct.get().getKey().getSupplierId(),productDto);
        return productMapper.productToProductDto(product);
    }

    private void updateProductEntities(UUID supplierId, ProductDto productDto) {
        productBySupplierRepository.deleteById(supplierId);
        ProductBySupplier productBySupplier = productMapper.productDtoToProductBySupplier(productDto);
        System.out.println(productBySupplier);

        productBySupplierRepository.save(productBySupplier);
    }


    public void deleteProduct(UUID id) {
        Optional<Product> product = productRepository.findByKeyId(id);
        product.ifPresent(value -> productBySupplierRepository.deleteById(value.getKey().getSupplierId()));
        product.ifPresent(value -> productRepository.delete(value));
    }
}
