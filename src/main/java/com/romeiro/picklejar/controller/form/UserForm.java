package com.romeiro.picklejar.controller.form;

import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.Status;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.UserRepository;

import java.time.LocalDateTime;

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

    public User convert() {
        return new User(name, email, password, picture);
    }

    public User update(Integer userId, UserRepository userRepository) {
        User user = userRepository.getOne(userId);

        if (this.name != null && !user.getName().equals(this.name))
            user.setName(this.name);

        if (this.email != null && !user.getEmail().equals(this.email))
            user.setEmail(this.email);

        if (this.password != null && !user.getPassword().equals(this.password))
            user.setPassword(this.password);

        if (this.picture != null && !user.getPicture().equals(this.picture))
            user.setPicture(this.picture);

        return user;
    }
}
