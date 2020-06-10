package learn.rr.microservice.productms.mapper;

import learn.rr.microservice.productms.dto.ProductDto;
import learn.rr.microservice.productms.model.Product;
import learn.rr.microservice.productms.model.ProductBySupplier;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper  {
ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id",source = "key.id")
    @Mapping(target = "supplierId",source = "key.supplierId")
    ProductDto productToProductDto(Product product);

    List<ProductDto> productsToProductDtos(List<Product> products);

    @InheritInverseConfiguration(name = "productToProductDto")
    Product productDtoToProduct(ProductDto productDto);

    ProductBySupplier productDtoToProductBySupplier(ProductDto productDto);

}
