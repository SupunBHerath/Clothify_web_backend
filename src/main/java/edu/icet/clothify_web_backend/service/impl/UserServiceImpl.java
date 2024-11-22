package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.UserEntity;
import edu.icet.clothify_web_backend.exception.impl.NotFoundException;
import edu.icet.clothify_web_backend.model.UserDto;
import edu.icet.clothify_web_backend.repository.UserRepository;
import edu.icet.clothify_web_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final ObjectMapper mapper;
    private final UserRepository userRepository;

    public UserDto getUserById(int id){
        Optional<UserEntity> userById = userRepository.findById(id);
        return mapper.convertValue(userById, UserDto.class);
    }

    public boolean updateUserById(int id , UserDto userDto){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(userDto.getName());
        user.setBillingAddress(userDto.getBillingAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return userRepository.save(user).getId()>0;
    }
}
