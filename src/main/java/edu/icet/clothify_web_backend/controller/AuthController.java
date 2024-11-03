package edu.icet.clothify_web_backend.controller;

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

    @GetMapping
    public SuccessfulResponsesData getUsers() {
        return authSevice.getUsers();
    }
}
