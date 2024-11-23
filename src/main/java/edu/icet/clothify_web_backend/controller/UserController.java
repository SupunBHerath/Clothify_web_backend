package edu.icet.clothify_web_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.model.UserDto;
import edu.icet.clothify_web_backend.model.UserProductQtyDto;
import edu.icet.clothify_web_backend.service.UserService;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public  boolean updateUserById(@PathVariable int id , @RequestBody UserDto userDto){
        return userService.updateUserById(id,userDto);
    }
    @GetMapping
    public SuccessfulResponsesData getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/qty")
    public List<UserProductQtyDto> getUserByProductQty(){
        return userService.getUserByProductQty();
    }
}
