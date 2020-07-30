package com.spring.jwt.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

	@ApiModelProperty(position = 0)
	@NotBlank
	private String username;

	@ApiModelProperty(position = 1)
	@NotBlank
	private String password;
}