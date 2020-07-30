package com.spring.jwt.controller;


import com.spring.jwt.payload.request.LoginRequest;
import com.spring.jwt.payload.request.SignupRequest;
import com.spring.jwt.payload.response.JwtResponse;
import com.spring.jwt.payload.response.MessageResponse;
import com.spring.jwt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value="/versions/1/auth",produces ="application/json")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
@RequestMapping("/versions/1/auth")
public class UserController {

    @NonNull UserService userService;

    @ApiOperation(value = "Authenticate a User",response = JwtResponse.class )
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @ApiOperation(value = "Signup a User",response = MessageResponse.class )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return  userService.createUser(signUpRequest);
    }

}
