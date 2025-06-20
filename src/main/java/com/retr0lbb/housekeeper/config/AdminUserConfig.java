package com.retr0lbb.housekeeper.config;

import com.retr0lbb.housekeeper.entitys.UserEntity;
import com.retr0lbb.housekeeper.repository.RoleRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Configuration
public class AdminUserConfig implements CommandLineRunner  {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var adminRole = roleRepository.findByDescription("admin");

        if(adminRole.isEmpty()){
            throw new IllegalArgumentException("cant pass this");
        }

        var userAdmin = userRepository.findByEmail("admin@gmail.com");
        userAdmin.ifPresentOrElse(
                user -> {System.out.println("Admin ja existe");},
                () -> {
                    var user = new UserEntity();
                    user.setName("admin");
                    //user.setId(UUID.fromString("85fce15a-9d26-4f96-a78a-a9fab4093a9e"));
                    user.setEmail("admin@gmail.com");
                    user.setPassword(bCryptPasswordEncoder.encode("123"));
                    user.setAccessLevel(adminRole.get());
                    userRepository.save(user);
                }
        );
    }
}
