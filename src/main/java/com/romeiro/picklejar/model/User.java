package com.romeiro.picklejar.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.romeiro.picklejar.controller.View;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    @JsonView(View.User.class)
    private Integer id;

    @Column(name = "USR_NAME")
    @JsonView(View.User.class)
    private String name;

    @Column(name = "USR_EMAIL")
    @JsonView(View.User.class)
    private String email;

    @Column(name = "USR_PASSWORD")
    @JsonView(View.User.class)
    private String password;

    @Column(name = "USR_PICTURE")
    @JsonView(View.User.class)
    private String picture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
            joinColumns = { @JoinColumn(name = "USER_USR_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLES_ROL_ID") })
    @JsonView(View.User.class)
    private List<Role> roles = new ArrayList<>();

    public User () { }

    public User(String name, String email, String password, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.roles.add(role);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> roles) { this.roles = roles; }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
