package edu.icet.clothify_web_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String name;

    @Column(unique = true, nullable = false)
    private  String email;
    private  String password;
    private  String role;
}
