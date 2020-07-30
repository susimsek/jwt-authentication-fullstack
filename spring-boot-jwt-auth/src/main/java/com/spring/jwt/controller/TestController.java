package com.spring.jwt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="/versions/1/test",produces ="application/json")
@RequestMapping("/versions/1/test")
public class TestController {

    @ApiOperation(value = "All User Access")
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @ApiOperation(value = "Role assigned User Access", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @ApiOperation(value = "Moderator Access", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @ApiOperation(value = "Admin Access", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }


}
