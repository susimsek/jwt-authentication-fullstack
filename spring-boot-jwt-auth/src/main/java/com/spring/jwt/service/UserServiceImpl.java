package com.spring.jwt.service;

import com.spring.jwt.exception.EmailAlreadyExistsException;
import com.spring.jwt.exception.RoleNotFoundException;
import com.spring.jwt.exception.UsernameAlreadyExistsException;
import com.spring.jwt.model.ERole;
import com.spring.jwt.model.Role;
import com.spring.jwt.model.User;
import com.spring.jwt.payload.request.LoginRequest;
import com.spring.jwt.payload.request.SignupRequest;
import com.spring.jwt.payload.response.JwtResponse;
import com.spring.jwt.payload.response.MessageResponse;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.security.jwt.JwtUtils;
import com.spring.jwt.security.service.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @NonNull AuthenticationManager authenticationManager;

    @NonNull JwtUtils jwtUtils;

    @NonNull UserRepository userRepository;

    @NonNull RoleRepository roleRepository;

    @NonNull PasswordEncoder passwordEncoder;

    @NonNull ModelMapper modelMapper;


    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public MessageResponse createUser(SignupRequest signUpRequest) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user=modelMapper.map(signUpRequest,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        user.setRoles(addRoleToUser(strRoles));
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");

    }

    private Set<Role> addRoleToUser(Set<String> userRoles) throws RoleNotFoundException {
        Set<Role> roles = new HashSet<>();

        if (userRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException());
            roles.add(userRole);
        }

        else {
            userRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException());
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RoleNotFoundException());
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException());
                        roles.add(userRole);
                }
            });

        }
        return roles;
    }
}
