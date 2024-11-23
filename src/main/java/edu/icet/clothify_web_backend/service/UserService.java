package edu.icet.clothify_web_backend.service;

import edu.icet.clothify_web_backend.model.UserDto;
import edu.icet.clothify_web_backend.model.UserProductQtyDto;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;

import java.util.List;

public interface UserService {

    UserDto getUserById(int id);
    boolean updateUserById(int id , UserDto userDto);
    SuccessfulResponsesData getUsers();
    List<UserProductQtyDto> getUserByProductQty();
}
