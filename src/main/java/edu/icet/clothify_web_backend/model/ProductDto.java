package edu.icet.clothify_web_backend.model;

import edu.icet.clothify_web_backend.entity.ImageEntity;
import edu.icet.clothify_web_backend.entity.SizeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private String name;
    private String description;
    private double price;
    private int qty;
    private List<ImageDto> images;
    private List<SizeDto> sizes;
    private String category;
    private boolean isNew;
    private String status;


}
