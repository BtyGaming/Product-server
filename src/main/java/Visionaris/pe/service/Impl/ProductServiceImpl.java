package Visionaris.pe.service.Impl;



import Visionaris.pe.dto.product.ProductCreateDTO;
import Visionaris.pe.entity.Category;
import Visionaris.pe.entity.Image;
import Visionaris.pe.entity.Product;
import Visionaris.pe.mapper.ProductMapper;
import Visionaris.pe.repository.CategoryRepository;
import Visionaris.pe.repository.ProductRepository;
import Visionaris.pe.service.ImageService;
import Visionaris.pe.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final ProductMapper productMapper;

    @Override
    public Product save(ProductCreateDTO dto, MultipartFile file) throws IOException {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Image image = imageService.uploadImage(file);
        Product product = productMapper.toEntity(dto);

        product.setCategory(category);
        product.setImage(image);

        return  productRepository.save(product);
    }

    @Override
    public Product updateImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        imageService.deleteImage(product.getImage());

        Image newImage = imageService.uploadImage(file);
        product.setImage(newImage);

        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(Product product) throws IOException {
        if(product.getImage() != null) {
            imageService.deleteImage(product.getImage());
        }
        productRepository.deleteById(product.getId());
    }

    @Override
    public Product update(
            Long id,
            ProductCreateDTO dto,
            MultipartFile file
    ) throws IOException {
        // 1. Recuperar el producto existente o lanzar excepción
        Product existing = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado con id " + id)
                );  // :contentReference[oaicite:6]{index=6}

        // 2. Mapear campos del DTO sobre la entidad existente
        productMapper.updateFromDto(dto, existing);  // :contentReference[oaicite:7]{index=7}

        // 3. Gestionar la imagen (eliminar anterior, subir nueva)
        if (file != null && !file.isEmpty()) {
            if (existing.getImage() != null) {
                imageService.deleteImage(existing.getImage());  // :contentReference[oaicite:8]{index=8}
            }
            Image newImage = imageService.uploadImage(file);
            existing.setImage(newImage);
        }

        // 4. Asignar categoría actualizada
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada con id " + dto.getCategoryId())
                );  // :contentReference[oaicite:9]{index=9}
        existing.setCategory(category);

        // 5. Persistir cambios en la base de datos
        return productRepository.save(existing);  // :contentReference[oaicite:10]{index=10}
    }

}

