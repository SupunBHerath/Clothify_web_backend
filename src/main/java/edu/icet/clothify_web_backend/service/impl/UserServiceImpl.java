package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.UserEntity;
import edu.icet.clothify_web_backend.exception.impl.ErrorException;
import edu.icet.clothify_web_backend.model.UserDto;
import edu.icet.clothify_web_backend.model.UserProductQtyDto;
import edu.icet.clothify_web_backend.repository.UserJpaRepository;
import edu.icet.clothify_web_backend.repository.UserRepository;
import edu.icet.clothify_web_backend.service.UserService;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper mapper;
    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;

    public UserDto getUserById(int id) {
        Optional<UserEntity> userById = userRepository.findById(id);
        return mapper.convertValue(userById, UserDto.class);
    }

    public boolean updateUserById(int id, UserDto userDto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ErrorException("User not found"));
        user.setName(userDto.getName());
        user.setBillingAddress(userDto.getBillingAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPostalCode(userDto.getPostalCode());
        user.setCity(userDto.getCity());
        return userRepository.save(user).getId() > 0;
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

    public List<UserProductQtyDto> getUserByProductQty() {
        List<Object[]> result = userJpaRepository.getCustomerOrderSummary();
        List<UserProductQtyDto> userProductQtyDtoList = new ArrayList<>();
        if (result == null || result.isEmpty()) {
            return userProductQtyDtoList;
        }
        for (Object[] row : result) {
            UserProductQtyDto dto = new UserProductQtyDto();
            dto.setId(row[0].toString());
            dto.setEmail(row[2].toString());
            dto.setQty(Long.parseLong(row[3].toString()));
            userProductQtyDtoList.add(dto);
        }
        return userProductQtyDtoList;
    }

}
