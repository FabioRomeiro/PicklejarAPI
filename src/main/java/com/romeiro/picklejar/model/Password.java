package com.romeiro.picklejar.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PASSWORD")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PWD_ID")
    private Integer id;

    @Column(name = "PWD_NAME")
    private String name;

    @Column(name = "PWD_USERNAME")
    private String username;

    @Column(name = "PWD_PASSWORD")
    private String password;

    @Column(name = "PWD_LINK")
    private String link;

    @Column(name = "PWD_FAVORITE")
    private Boolean favorite;

    @Column(name = "PWD_IMAGE")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "PWD_STATUS")
    private Status status;

    @Column(name = "PWD_LASTACCESS")
    private LocalDateTime lastAccess;

    @Column(name = "PWD_LASTUPDATE")
    private LocalDateTime lastUpdate;

    @Column(name = "PWD_CREATEDAT")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "USR_ID")
    private User user;

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
}
