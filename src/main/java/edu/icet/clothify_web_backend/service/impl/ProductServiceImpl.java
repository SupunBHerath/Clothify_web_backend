package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.ProductEntity;
import edu.icet.clothify_web_backend.model.ProductDto;
import edu.icet.clothify_web_backend.repository.ProductRepository;
import edu.icet.clothify_web_backend.service.ProdcutService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProdcutService {

    private final ProductRepository productRepository;
    private final ObjectMapper mapper;

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        ProductEntity save = productRepository.save(
                mapper.convertValue(productDto, ProductEntity.class));
        return mapper.convertValue(save, ProductDto.class);
    }
}
