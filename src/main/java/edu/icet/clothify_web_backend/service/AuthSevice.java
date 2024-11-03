package edu.icet.clothify_web_backend.service;

import edu.icet.clothify_web_backend.model.User;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesData;
import edu.icet.clothify_web_backend.template.SuccessfulResponsesMessage;

public interface AuthSevice {
    SuccessfulResponsesData login(String email , String password);
    SuccessfulResponsesMessage registerUser(User user);
    SuccessfulResponsesData getUsers();
    boolean validateToken(String token, String email);
}
