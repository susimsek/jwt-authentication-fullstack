package com.spring.jwt.security;

import com.spring.jwt.security.jwt.AuthEntryPointJwt;
import com.spring.jwt.security.jwt.AuthTokenFilter;
import com.spring.jwt.security.service.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			"/**/documentation/**",
			"/versions/1/auth/**",
			"/versions/1/test/**",
			"/**/v2/api-docs",           // swagger
			"/**/webjars/**",            // swagger-ui webjars
			"/**/swagger-resources/**",  // swagger-ui resources
			"/**/configuration/**",      // swagger configuration
			"/*.html",
			"/favicon.ico",
			"/**/*.html",
			"/**/*.css",
			"/**/*.js"
	};


	@NonNull UserDetailsServiceImpl userDetailsService;

	@NonNull AuthEntryPointJwt unauthorizedHandler;

	@NonNull AuthTokenFilter authTokenFilter;

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		http
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated();

		http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}