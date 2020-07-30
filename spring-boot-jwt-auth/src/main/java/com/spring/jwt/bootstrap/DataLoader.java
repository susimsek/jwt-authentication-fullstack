package com.spring.jwt.bootstrap;

import com.spring.jwt.model.ERole;
import com.spring.jwt.model.Role;
import com.spring.jwt.model.User;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @NonNull RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();

    }

    private void initRoles(){
        List<Role> roles=roleRepository.findAll();

        if(roles.isEmpty()){
            Role roleUser=new Role(ERole.ROLE_USER);
            Role roleModerator=new Role(ERole.ROLE_MODERATOR);
            Role roleAdmin=new Role(ERole.ROLE_ADMIN);

            roleRepository.save(roleUser);
            roleRepository.save(roleModerator);
            roleRepository.save(roleAdmin);
        }
    }
}
