package com.romeiro.picklejar.controller.dto;

import com.romeiro.picklejar.model.RoleType;
import com.romeiro.picklejar.model.User;

public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String picture;
    private RoleType role;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRoles().get(0).getName();
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

    public RoleType getRole() {
        return role;
    }
}
