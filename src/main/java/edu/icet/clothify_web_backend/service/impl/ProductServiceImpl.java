package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.ProductEntity;
import edu.icet.clothify_web_backend.model.ProductDto;
import edu.icet.clothify_web_backend.repository.ProductJdbcRepository;
import edu.icet.clothify_web_backend.repository.ProductRepository;
import edu.icet.clothify_web_backend.service.ProdcutService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProdcutService {

    private final ProductRepository productRepository;
    private final ProductJdbcRepository productJdbcRepository;
    private final ObjectMapper mapper;

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        ProductEntity save = productRepository.save(
                mapper.convertValue(productDto, ProductEntity.class));
        return mapper.convertValue(save, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        Iterable<ProductEntity> result = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        result.forEach(data->productDtoList.add(mapper.convertValue(data, ProductDto.class)));
        return productDtoList;
    }

    @Override
    public List<ProductDto> getBestSellerProducts() {
        List<ProductDto> result = productJdbcRepository.getBestSellerProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        result.forEach(data->productDtoList.add(data));
        return productDtoList;
    }
}
