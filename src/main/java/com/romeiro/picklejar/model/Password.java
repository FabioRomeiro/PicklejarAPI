package com.romeiro.picklejar.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.romeiro.picklejar.controller.View;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PASSWORD")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PWD_ID")
    @JsonView(View.Password.class)
    private Integer id;

    @Column(name = "PWD_NAME")
    @JsonView(View.Password.class)
    private String name;

    @Column(name = "PWD_USERNAME")
    @JsonView(View.Password.class)
    private String username;

    @Column(name = "PWD_PASSWORD")
    @JsonView(View.Password.class)
    private String password;

    @Column(name = "PWD_LINK")
    @JsonView(View.Password.class)
    private String link;

    @Column(name = "PWD_FAVORITE")
    @JsonView(View.Password.class)
    private Boolean favorite;

    @Column(name = "PWD_IMAGE")
    @JsonView(View.Password.class)
    private String image;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Password.class)
    @Column(name = "PWD_STATUS")
    private Status status;

    @Column(name = "PWD_ACTIVE")
    @JsonView(View.Password.class)
    private boolean active = true;

    @Column(name = "PWD_LASTACCESS")
    @JsonView(View.Password.class)
    private LocalDateTime lastAccess = LocalDateTime.now();

    @Column(name = "PWD_LASTUPDATE")
    @JsonView(View.Password.class)
    private LocalDateTime lastUpdate = LocalDateTime.now();

    @Column(name = "PWD_CREATEDAT")
    @JsonView(View.Password.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "USR_ID")
    @JsonView(View.Password.class)
    private User user;

    public Password () { }

    public Password(String name, String username, String password, String link, Boolean favorite, String image, User user) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.link = link;
        this.favorite = favorite;
        this.image = image;
        this.user = user;

        // Calculate strength
        this.status = Status.MEDIUM;
    }

    public Integer getId() {
        return id;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
