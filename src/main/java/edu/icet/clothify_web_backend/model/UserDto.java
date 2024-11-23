package edu.icet.clothify_web_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String productQty;
    private String billingAddress;
    private String phoneNumber;
    private String city;
    private String postalCode;
    private  String accountStatus;
    private  String  onlineStatus;
}
