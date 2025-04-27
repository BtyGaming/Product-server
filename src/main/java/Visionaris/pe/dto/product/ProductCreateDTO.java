package Visionaris.pe.dto.product;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {


    private String nameProduct;
    private String description;
    private Double price;
    private Integer units;
    private Boolean isActive;
    private Long categoryId;


}
