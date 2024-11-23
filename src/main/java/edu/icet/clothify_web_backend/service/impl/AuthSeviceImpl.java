package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.UserEntity;
import edu.icet.clothify_web_backend.exception.impl.ErrorException;
import edu.icet.clothify_web_backend.model.JWTDto;
import edu.icet.clothify_web_backend.model.UpdatePasswordDto;
import edu.icet.clothify_web_backend.model.User;
import edu.icet.clothify_web_backend.repository.UserJdbcRepository;
import edu.icet.clothify_web_backend.repository.UserRepository;
import edu.icet.clothify_web_backend.service.AuthSevice;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesMessage;
import edu.icet.clothify_web_backend.util.MD5HashingUtil;
import edu.icet.clothify_web_backend.util.impl.JwtUtil;
import edu.icet.clothify_web_backend.util.impl.MD5HashingUtilImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthSeviceImpl implements AuthSevice {

    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;
    private final UserRepository userRepository;
    private final UserJdbcRepository userJdbcRepository;
    private final MD5HashingUtil md5HashingUtil = new MD5HashingUtilImpl();

    private String success = "Success";
    private String failed = "Failed";

    @Override
    public SuccessfulResponsesData login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new ErrorException("Please provide email.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ErrorException("Please provide password.");
        }

        UserEntity userEntity = userJdbcRepository.login(email);
        String password1 = userEntity.getPassword();
        if (!md5HashingUtil.compareMD5(password, password1)) throw new ErrorException("Password does not match.");
        userEntity.setOnlineStatus("Online");
        userRepository.save(userEntity);
        JWTDto jwtDto = mapper.convertValue(userEntity, JWTDto.class);
        String jwtToken = jwtUtil.generateToken(userEntity.getEmail(), jwtDto);
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
        String password = user.getPassword();
        user.setPassword(md5HashingUtil.hashWithMD5(password));
        user.setAccountStatus("Active");
        user.setOnlineStatus("Offline");
        UserEntity save = userRepository.save(mapper.convertValue(user, UserEntity.class));
        return SuccessfulResponsesMessage.builder()
                .status(success)
                .message(String.format("%s Register Successfuly...", save.getRole().toUpperCase()))
                .build();
    }

    public boolean validateToken(String token, String email) {
        return jwtUtil.validateToken(token, email);
    }



    public boolean updatePassword(int id, UpdatePasswordDto updatePasswordDto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ErrorException("User Not Found"));

        if (md5HashingUtil.compareMD5(updatePasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(md5HashingUtil.hashWithMD5(updatePasswordDto.getPassword()));
            return userRepository.save(user).getId() == id;
        }
        return false;
    }

    public boolean updateOfflineStatus(int id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ErrorException("User Not Found "));
        user.setOnlineStatus("Offline");
        return userRepository.save(user).getId() == id;

    }

    public boolean updateAccountStatus(int id, String jwt, String status) {
        if (!jwtUtil.validateTokenByToken(jwt)) throw  new ErrorException("JWT Token Validate fail..");
        String role = userRepository.findById(jwtUtil.extractUserId(jwt)).get().getRole();
        if(!role.equalsIgnoreCase("admin"))  throw  new ErrorException("Only Admin Can Update Status ");
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ErrorException("User Not Found "));
        user.setAccountStatus(status);
        return  userRepository.save(user).getId() == id ;
    }
}
