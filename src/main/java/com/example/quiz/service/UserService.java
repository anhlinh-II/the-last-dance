package com.example.quiz.service;

import com.example.quiz.base.baseInterface.BaseService;
import com.example.quiz.model.dto.request.UserRequest;
import com.example.quiz.model.dto.response.UserResponse;
import com.example.quiz.model.entity.User;
import com.example.quiz.model.view.UserView;

public interface UserService extends BaseService<User, Long, UserRequest, UserResponse, UserView> {

    // Get User by username/email/phone
    User handleGetUserByUsernameOrEmailOrPhone(String loginInput);

    void updateUserToken(String token, String emailUsernamePhone);

    User getUserByRefreshTokenAndEmailOrUsernameOrPhone(String token, String emailUsernamePhone);

    User getUserByEmail(String email);

    boolean verifyOtp(User user, String otp);
}
