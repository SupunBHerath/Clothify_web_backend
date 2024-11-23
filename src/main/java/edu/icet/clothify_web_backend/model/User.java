package edu.icet.clothify_web_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private  String name;
    private  String email;
    private  String accountStatus;
    private  String  onlineStatus;
    private  String password;
    private  String role;


}
