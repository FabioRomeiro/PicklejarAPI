package com.romeiro.picklejar.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.romeiro.picklejar.controller.View;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOG")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    @JsonView(View.Log.class)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOG_TYPE")
    @JsonView(View.Log.class)
    private LogType type;

    @Column(name = "LOG_USERAGENT")
    @JsonView(View.Log.class)
    private String userAgent;

    @Column(name = "LOG_METADATA")
    @JsonView(View.Log.class)
    private String metadata;

    @Column(name = "LOG_CREATEDAT")
    @JsonView(View.Log.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "USR_ID")
    @JsonView(View.Log.class)
    private User user;

    @ManyToOne
    @JoinColumn(name = "PWD_ID")
    @JsonView(View.Log.class)
    private Password password;

    public Log() { }

    public Log(LogType type, String userAgent, String metadata, User user, Password password) {
        this.type = type;
        this.userAgent = userAgent;
        this.metadata = metadata;
        this.user = user;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
