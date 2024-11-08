package edu.icet.clothify_web_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.ProductEntity;
import edu.icet.clothify_web_backend.model.ProductDto;
import edu.icet.clothify_web_backend.service.ProdcutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private  final ProdcutService prodcutService;
    private  final ObjectMapper mapper;

    @PostMapping
    public ProductDto addNewProduct(@RequestBody ProductDto productDto){
        System.out.println(productDto);
        System.out.println("/n---------------------");
      return prodcutService.saveProduct(productDto);
    }

}
