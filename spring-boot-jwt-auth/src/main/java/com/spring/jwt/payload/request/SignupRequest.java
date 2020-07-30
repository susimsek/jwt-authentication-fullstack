package com.spring.jwt.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {

    @ApiModelProperty(position = 0)
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @ApiModelProperty(position = 1)
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @ApiModelProperty(position = 2)
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @ApiModelProperty(position = 3)
    private Set<String> role;




}