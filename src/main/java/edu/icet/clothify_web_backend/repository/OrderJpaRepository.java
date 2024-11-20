package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity,Integer> {
    @Query("SELECT o FROM OrderEntity o WHERE o.cusId = :cusId")
    List<OrderEntity> findOrdersByCustomerId(@Param("cusId") Integer cusId);
}
