package com.example.quiz.service.impl;


import com.example.quiz.base.baseInterface.BaseMapstruct;
import com.example.quiz.base.baseInterface.BaseRepository;
import com.example.quiz.base.impl.AdvancedFilterService;
import com.example.quiz.base.impl.BaseServiceImpl;
import com.example.quiz.exception.AppException;
import com.example.quiz.exception.ErrorCode;
import com.example.quiz.mapper.UserMapper;
import com.example.quiz.model.dto.request.UserRequest;
import com.example.quiz.model.dto.response.UserResponse;
import com.example.quiz.model.entity.User;
import com.example.quiz.model.view.UserView;
import com.example.quiz.repository.UserRepository;
import com.example.quiz.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRequest, UserResponse, UserView>
        implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(AdvancedFilterService advancedFilterService, BaseRepository<User, Long> repository, BaseMapstruct<User, UserRequest, UserResponse, UserView> mapper, JpaRepository<UserView, Long> viewRepository, UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(advancedFilterService, repository, mapper, viewRepository);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Class<UserView> getViewClass() {
        return UserView.class;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
    }

    // Get User by username/email/phone
    @Override
    public User handleGetUserByUsernameOrEmailOrPhone(String loginInput) {
        Optional<User> optionalUser = this.userRepository.findByEmail(loginInput);
        log.info("login input: {}", loginInput);
        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByUsername(loginInput);
        }
        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByPhone(loginInput);
        }
        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.ENTITY_NOT_EXISTED);
        }

        return optionalUser.get();
    }

    public User createUser(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())
                || userRepository.existsByEmail(request.getEmail())
                || userRepository.existsByPhone(request.getPhone())) {
            log.info("something wrong");
            throw new AppException(ErrorCode.ENTITY_EXISTED);
        }
        User user = userMapper.requestToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void updateUserToken(String token, String emailUsernamePhone) {
        User currentUser = this.handleGetUserByUsernameOrEmailOrPhone(emailUsernamePhone);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    @Override
    public User getUserByRefreshTokenAndEmailOrUsernameOrPhone(String token, String emailUsernamePhone) {
        return this.userRepository.findByRefreshTokenAndEmailOrUsernameOrPhone(token, emailUsernamePhone)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    @Override
    public boolean verifyOtp(User user, String otp) {
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), Instant.now()).getSeconds() < 60) {
            user.setOtp(otp);
            userRepository.save(user);
            return true;
        } else if (!user.getOtp().equals(otp)) {
            throw new AppException(ErrorCode.INVALID_OTP);
        } else {
            throw new AppException(ErrorCode.EXPIRED_OTP);
        }
    }

    public User findUserProfile(Jwt jwt) {
        String email = jwt.getClaim("email");
        if (email == null) {
            throw new AppException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new AppException(ErrorCode.ENTITY_NOT_EXISTED);
        }

        return optionalUser.get();
    }

    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

}
