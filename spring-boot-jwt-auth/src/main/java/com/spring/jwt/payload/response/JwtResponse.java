package com.spring.jwt.payload.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JwtResponse {

	@NonNull
	private String token;

	private String type = "Bearer";

	@NonNull
	private Long id;

	@NonNull
	private String username;

	@NonNull
	private String email;

	@NonNull
	private List<String> roles;
}