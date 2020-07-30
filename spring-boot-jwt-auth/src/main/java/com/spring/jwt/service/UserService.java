package com.spring.jwt.service;

import com.spring.jwt.exception.EmailAlreadyExistsException;
import com.spring.jwt.exception.UsernameAlreadyExistsException;
import com.spring.jwt.payload.request.LoginRequest;
import com.spring.jwt.payload.request.SignupRequest;
import com.spring.jwt.payload.response.JwtResponse;
import com.spring.jwt.payload.response.MessageResponse;

public interface UserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse createUser(SignupRequest signUpRequest) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;
}
