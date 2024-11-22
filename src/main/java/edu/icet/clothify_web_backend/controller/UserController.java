package edu.icet.clothify_web_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.model.UserDto;
import edu.icet.clothify_web_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private  final ObjectMapper mapper;
    private  final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }
}
