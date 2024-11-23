package edu.icet.clothify_web_backend.repository.impl;

import edu.icet.clothify_web_backend.model.ImageDto;
import edu.icet.clothify_web_backend.model.OrderDto;
import edu.icet.clothify_web_backend.model.ProductDto;
import edu.icet.clothify_web_backend.model.SizeDto;
import edu.icet.clothify_web_backend.repository.ProductJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductJdbcRepositoryImpl  implements ProductJdbcRepository {

    private  final JdbcTemplate jdbcTemplate;

    public int updateSizeQty(int qty, int productId, String name) {
        String sql = "UPDATE sizes SET qty = qty + ? WHERE product_id = ? AND name = ?";
        return jdbcTemplate.update(sql, qty, productId, name);
    }

    public List<OrderDto> getOrdersById(int id) {
        String sql = "SELECT * FROM orders WHERE cus_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(OrderDto.class));
    }

    public List<ProductDto> getBestSellerProducts() {
        String sql = """
        SELECT 
            p.*,
            GROUP_CONCAT(DISTINCT i.url) AS images,
            GROUP_CONCAT(DISTINCT CONCAT(s.name, ':', s.qty, ':', s.price)) AS sizes
        FROM products p
        JOIN (
            SELECT product_id
            FROM order_details
            GROUP BY product_id
            ORDER BY COUNT(product_id) DESC
            LIMIT 4
        ) top_sellers ON p.id = top_sellers.product_id
        LEFT JOIN images i ON i.product_id = p.id
        LEFT JOIN sizes s ON s.product_id = p.id
        GROUP BY p.id;
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProductDto product = new ProductDto();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setCategory(rs.getString("category"));
            product.setSubCategory(rs.getString("sub_category"));
            product.setNew(rs.getBoolean("is_new"));
            product.setStatus(rs.getString("status"));

            String images = rs.getString("images");
            if (images != null) {
                List<ImageDto> imageDtos = Arrays.stream(images.split(","))
                        .map(url -> {
                            ImageDto imageDto = new ImageDto();
                            imageDto.setUrl(url);
                            return imageDto;
                        })
                        .collect(Collectors.toList());
                product.setImages(imageDtos);
            }

            String sizes = rs.getString("sizes");
            if (sizes != null) {
                List<SizeDto> sizeDtoList = Arrays.stream(sizes.split(","))
                        .map(res -> {
                            String[] sizeDetails = res.split(":");
                            SizeDto sizeDto = new SizeDto();
                            sizeDto.setName(sizeDetails[0]);
                            sizeDto.setQty(Integer.parseInt(sizeDetails[1]));
                            sizeDto.setPrice(Double.parseDouble(sizeDetails[2]));
                            return sizeDto;
                        })
                        .collect(Collectors.toList());
                product.setSizes(sizeDtoList);
            }
            return product;
        });
    }




}
