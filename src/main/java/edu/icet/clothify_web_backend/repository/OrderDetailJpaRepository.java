package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.OrderDetailsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetailsEntity,Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM order_details WHERE order_id = :orderId", nativeQuery = true)
    void deleteByOrderId(@Param("orderId") Integer orderId);

}
