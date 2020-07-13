package com.romeiro.picklejar.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.romeiro.picklejar.controller.View;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.Password.class, View.User.class, View.Log.class})
    @Column(name = "ROL_ID")
    private Integer id;

    @Column(name = "ROL_NAME")
    @JsonView({View.Password.class, View.User.class, View.Log.class})
    private String name;

    public Integer getId() {
        return id;
    }

    public Role () { }

    public Role (RoleType type) {
        this.name = type.toString();
    }

    public RoleType getName() {
        return RoleType.valueOf(name);
    }

    public void setName(RoleType type) {
        this.name = type.toString();
    }

    @Override
    public String getAuthority() {
        return name.toString();
    }
}
