package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.ProductEntity;
import edu.icet.clothify_web_backend.entity.SizeEntity;
import org.springframework.data.repository.CrudRepository;

public interface SizeRepository extends CrudRepository<SizeEntity,Integer> {
}
