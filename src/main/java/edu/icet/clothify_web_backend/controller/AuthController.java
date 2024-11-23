package edu.icet.clothify_web_backend.controller;

import edu.icet.clothify_web_backend.model.UpdatePasswordDto;
import edu.icet.clothify_web_backend.model.User;
import edu.icet.clothify_web_backend.service.AuthSevice;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthSevice authSevice;

    @PostMapping("/login")
    public SuccessfulResponsesData loginUser(@RequestBody Map<String, String> payload) {
        return authSevice.login(payload.get("email"), payload.get("password"));
    }

    @GetMapping("/token")
    public boolean validateToken(Map<String, String> payload) {
        return authSevice.validateToken(payload.get("token"), payload.get("email"));
    }

    @PostMapping
    public SuccessfulResponsesMessage registerUser(@RequestBody User user) {
        return authSevice.registerUser(user);
    }


    @PutMapping("/{id}")
    public  boolean updatePasswordById(@PathVariable int id , @RequestBody UpdatePasswordDto updatePasswordDto){
        return authSevice.updatePassword(id,updatePasswordDto);
    }

    @PutMapping("/offline/{id}")
    boolean updateOfflineStatus(@PathVariable int id){
        return authSevice.updateOfflineStatus(id);
    }

    @PutMapping("/account-status/{id}/{status}")
    public boolean updateAccountStatus(
            @PathVariable int id,
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String status) {
        System.out.println(status);
        String jwt = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader;
        return authSevice.updateAccountStatus(id, jwt, status);
    }
}
