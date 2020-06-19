package com.romeiro.picklejar.controller.dto;

import com.romeiro.picklejar.model.User;

public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String picture;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }
}
