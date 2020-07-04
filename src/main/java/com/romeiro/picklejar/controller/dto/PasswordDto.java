package com.romeiro.picklejar.controller.dto;

import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.Status;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordDto {

    private Integer id;
    private String name;
    private String username;
    private String link;
    private Boolean favorite;
    private String image;
    private Status status;
    private boolean active;
    private LocalDateTime lastAccess;
    private LocalDateTime lastUpdate;
    private LocalDateTime createdAt;

    public PasswordDto(Password password) {
        this.id = password.getId();
        this.name = password.getName();
        this.username = password.getUsername();
        this.link = password.getLink();
        this.favorite = password.getFavorite();
        this.image = password.getImage();
        this.status = password.getStatus();
        this.active = password.isActive();
        this.lastAccess = password.getLastAccess();
        this.lastUpdate = password.getLastUpdate();
        this.createdAt = password.getCreatedAt();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getLink() {
        return link;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public String getImage() {
        return image;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Page<PasswordDto> convert(Page<Password> passwords) {
        return passwords.map(PasswordDto::new);
    }
}
