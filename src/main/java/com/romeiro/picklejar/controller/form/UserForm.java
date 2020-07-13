package com.romeiro.picklejar.controller.form;

import com.romeiro.picklejar.model.*;
import com.romeiro.picklejar.repository.RoleRepository;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserForm {

    private String name;
    private String email;
    private String password;
    private String picture;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User convert(RoleRepository roleRepository) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleType.ROLE_USER.toString());
        Role role = null;

        if (optionalRole.isEmpty()) {
            role = new Role(RoleType.ROLE_USER);
            roleRepository.save(role);
        }
        else {
            role = optionalRole.get();
        }

        return new User(name, email, new BCryptPasswordEncoder().encode(password), picture, role);
    }

    public User update(Integer userId, UserRepository userRepository) {
        User user = userRepository.getOne(userId);

        if (this.name != null && !user.getName().equals(this.name))
            user.setName(this.name);

        if (this.email != null && !user.getEmail().equals(this.email))
            user.setEmail(this.email);

        if (this.password != null && !user.getPassword().equals(this.password))
            user.setPassword(new BCryptPasswordEncoder().encode(this.password));

        if (this.picture != null && !user.getPicture().equals(this.picture))
            user.setPicture(this.picture);

        return user;
    }
}
