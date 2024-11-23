package edu.icet.clothify_web_backend.repository;

import edu.icet.clothify_web_backend.entity.OrderEntity;
import edu.icet.clothify_web_backend.model.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {
    @Query("SELECT o FROM OrderEntity o WHERE o.cusId = :cusId")
    List<OrderEntity> findOrdersByCustomerId(@Param("cusId") Integer cusId);

    @Query("SELECT o FROM OrderEntity o WHERE o.id = :id")
    List<OrderEntity> findOrderByOrderId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderEntity o WHERE o.id = :id")
    int deleteOrderById(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE OrderEntity o SET o.status = :status WHERE o.id = :id")
    int updateOrderStatus(@Param("id") Integer id, @Param("status") String status);

    @Query(value = """
            SELECT
              SUM(CASE WHEN status = 'Processing' THEN 1 ELSE 0 END) AS processing,
              SUM(CASE WHEN status = 'Delivering' THEN 1 ELSE 0 END) AS delivering,
              SUM(CASE WHEN status = 'Delivered' THEN 1 ELSE 0 END) AS delivered,
              SUM(CASE WHEN status = 'Rejected' THEN 1 ELSE 0 END) AS reject
            FROM orders;
            """, nativeQuery = true)
    List<Object[]> getOrderCounts();


    @Query(value = """
            SELECT 
                u.id AS userId, 
                u.name AS userName, 
                u.email AS userEmail, 
                SUM(od.qty) AS totalQuantity 
            FROM users u 
            JOIN orders o ON u.id = o.cus_id 
            JOIN order_details od ON o.id = od.order_id 
            GROUP BY u.id, u.name, u.email 
            ORDER BY totalQuantity DESC
            """,
            nativeQuery = true)
    List<UserDto> getCustomerOrderSummary();

    @Query(value = "SELECT SUM(od.qty * od.price) AS totalPrice FROM order_details od;", nativeQuery = true)
    Double getTotalPrice();
}
