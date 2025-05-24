package com.retr0lbb.housekeeper.config;

import com.retr0lbb.housekeeper.entitys.RolesModel;
import com.retr0lbb.housekeeper.entitys.UserModel;
import com.retr0lbb.housekeeper.repository.RoleRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        var adminRole = roleRepository.findByName(RolesModel.Values.ADMIN.name());
        var userAdmin = userRepository.findByEmail("admin@gmail.com");
        userAdmin.ifPresentOrElse(
                user -> {System.out.println("Admin ja existe");},
                () -> {
                    var user = new UserModel();
                    user.setUserName("admin");
                    user.setEmail("admin@gmail.com");
                    user.setPassword(bCryptPasswordEncoder.encode("123"));
                    userRepository.save(user);
                }
        );
    }
}
