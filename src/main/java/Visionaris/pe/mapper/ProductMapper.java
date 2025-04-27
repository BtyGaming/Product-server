package Visionaris.pe.mapper;

import Visionaris.pe.dto.product.ProductCreateDTO;
import Visionaris.pe.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductCreateDTO toDto(Product product);

    Product toEntity(ProductCreateDTO productCreateDTO);

    void updateFromDto(ProductCreateDTO dto, @MappingTarget Product entity);
}
