package edu.icet.clothify_web_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer cusId;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String billingAddress;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private  String paymentMethod;

    @Column(nullable = false)
    private String invoiceNumber;

    @Column(nullable = false, length = 50)
    private String status;

    @OneToMany(targetEntity = OrderDetailsEntity.class, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private List<OrderDetailsEntity> orderDetails;
}
