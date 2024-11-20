package edu.icet.clothify_web_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private String cusAddress;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productSize;

    @Column(nullable = false)
    private String productImg;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int qty;

}
