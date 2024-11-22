package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.UserEntity;
import edu.icet.clothify_web_backend.exception.impl.NotFoundException;
import edu.icet.clothify_web_backend.model.JWTDto;
import edu.icet.clothify_web_backend.model.User;
import edu.icet.clothify_web_backend.model.UserDto;
import edu.icet.clothify_web_backend.repository.UserJdbcRepository;
import edu.icet.clothify_web_backend.repository.UserRepository;
import edu.icet.clothify_web_backend.service.AuthSevice;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesMessage;
import edu.icet.clothify_web_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthSeviceImpl implements AuthSevice {

    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;
    private final UserRepository userRepository;
    private final UserJdbcRepository userJdbcRepository;
    private String success = "Success";
    private String failed = "Failed";

    @Override
    public SuccessfulResponsesData login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new NotFoundException("Please provide email.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new NotFoundException("Please provide password.");
        }

        UserEntity userEntity = userJdbcRepository.login(email,password);
        JWTDto jwtDto = mapper.convertValue(userEntity, JWTDto.class);
        String jwtToken = jwtUtil.generateToken(userEntity.getEmail(),jwtDto);
        jwtDto.setJwtToken(jwtToken);

        return SuccessfulResponsesData.builder()
                .status("success")
                .message("Login successful.")
                .data(jwtDto)
                .build();
    }

    @Override
    public SuccessfulResponsesMessage registerUser(User user) {
        if (userJdbcRepository.userExistsByEmail(user.getEmail()))
            return SuccessfulResponsesMessage.builder()
                    .status(failed)
                    .message("Already have an account")
                    .build();

        UserEntity save = userRepository.save(mapper.convertValue(user, UserEntity.class));
        return SuccessfulResponsesMessage.builder()
                .status(success)
                .message(String.format("%s Register Successfuly...", save.getRole().toUpperCase()))
                .build();
    }

    public boolean validateToken(String token, String email){
        return jwtUtil.validateToken(token, email);
    }
    
    public SuccessfulResponsesData getUsers() {
        Iterable<UserEntity> result = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        result.forEach(data -> userDtoList.add(mapper.convertValue(data, UserDto.class)));
        return SuccessfulResponsesData.builder()
                .status("200")
                .message("All Users Data ")
                .data(userDtoList)
                .build();
    }


}
