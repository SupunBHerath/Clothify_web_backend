package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity,Integer> {
}
