package com.example.quiz.service;

import com.example.quiz.base.baseInterface.BaseService;
import com.example.quiz.model.dto.request.UserRequest;
import com.example.quiz.model.dto.response.UserResponse;
import com.example.quiz.model.entity.User;
import com.example.quiz.model.view.UserView;

public interface UserService extends BaseService<User, Long, UserRequest, UserResponse, UserView> {

    User getUserByEmail(String email);
}
