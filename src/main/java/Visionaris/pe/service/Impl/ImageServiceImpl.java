package Visionaris.pe.service.Impl;

import Visionaris.pe.entity.Image;
import Visionaris.pe.repository.ImageRepository;
import Visionaris.pe.service.CloudinaryService;
import Visionaris.pe.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImpl  implements ImageService {


    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(CloudinaryService cloudinaryService, ImageRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imageRepository = imageRepository;
    }


    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        // Subir la imagen a Cloudinary
        Map uploadResult = cloudinaryService.upload(file);

        // Obtener la URL y el ID de la imagen
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");

        // Crear la entidad de imagen y guardarla en la base de datos
        Image image = new Image(file.getOriginalFilename(), imageUrl, imageId);
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Image image) throws IOException {
        // Eliminar la imagen de Cloudinary usando el ID
        cloudinaryService.delete(image.getImageId());

        // Eliminar la imagen de la base de datos
        imageRepository.deleteById(image.getId());
    }
}
