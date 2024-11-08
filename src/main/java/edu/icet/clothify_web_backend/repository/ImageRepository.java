package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.ImageEntity;
import edu.icet.clothify_web_backend.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageEntity,Integer> {
}
