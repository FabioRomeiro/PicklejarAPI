package com.romeiro.picklejar.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOG")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOG_TYPE")
    private LogType type;

    @Column(name = "LOG_USERAGENT")
    private String userAgent;

    @Column(name = "LOG_METADATA")
    private String metadata;

    @Column(name = "LOG_CREATEDAT")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "USR_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PWD_ID")
    private Password password;

    public Integer getId() {
        return id;
    }

    public String getUserAgent() {
        return userAgent;
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
}
