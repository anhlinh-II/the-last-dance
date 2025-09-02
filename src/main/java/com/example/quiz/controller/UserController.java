package com.example.quiz.controller;

import com.example.quiz.base.baseInterface.BaseService;
import com.example.quiz.base.impl.BaseController;
import com.example.quiz.mapper.UserMapper;
import com.example.quiz.model.dto.request.UserRequest;
import com.example.quiz.model.dto.response.UserResponse;
import com.example.quiz.model.entity.User;
import com.example.quiz.model.view.UserView;
import com.example.quiz.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<User, Long, UserRequest, UserResponse, UserView> {


    protected UserController(BaseService<User, Long, UserRequest, UserResponse, UserView> service) {
        super(service);
    }
}
