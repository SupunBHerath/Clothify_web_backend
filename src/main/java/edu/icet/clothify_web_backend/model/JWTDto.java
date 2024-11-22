package edu.icet.clothify_web_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTDto {
    private Integer id;
    private String email;
    private String role;
    private  String jwtToken;

}