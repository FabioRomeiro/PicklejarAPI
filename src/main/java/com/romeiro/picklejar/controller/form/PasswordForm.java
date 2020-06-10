package com.romeiro.picklejar.controller.form;

import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.Status;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;

import java.time.LocalDateTime;


public class PasswordForm {

    private String name;
    private String username;
    private String password;
    private String link;
    private Boolean favorite;
    private String image;
    private Integer userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Password convert(UserRepository userRepository) {
        User user = userRepository.findByUserId(userId);
        return new Password(name, username, password, link, favorite, image, user);
    }

    public Password update(Integer id, PasswordRepository passwordRepository) {
        Password password = passwordRepository.getOne(id);

        password.setLastUpdate(LocalDateTime.now());

        if (this.name != null && !password.getName().equals(this.name))
            password.setName(this.name);

        if (this.image != null && !password.getImage().equals(this.image))
            password.setImage(this.image);

        if (this.favorite != null && password.getFavorite() != this.favorite)
            password.setFavorite(this.favorite);

        if (this.link != null && !password.getLink().equals(this.link))
            password.setLink(this.link);

        if (this.username != null && !password.getUsername().equals(this.username))
            password.setUsername(this.username);

        if (this.password != null && !password.getPassword().equals(this.password)) {
            password.setStatus(Status.MEDIUM);
            password.setPassword(this.password);
        }

        return password;
    }
}
