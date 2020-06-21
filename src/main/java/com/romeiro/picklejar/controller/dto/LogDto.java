package com.romeiro.picklejar.controller.dto;

import com.romeiro.picklejar.model.Log;
import com.romeiro.picklejar.model.LogType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LogDto {

    private Integer id;
    private LogType type;
    private String userAgent;
    private String metadata;
    private Integer passwordId;
    private LocalDateTime createdAt;

    public LogDto(Log log) {
        this.id = log.getId();
        this.type = log.getType();
        this.userAgent = log.getUserAgent();
        this.metadata = log.getMetadata();
        this.createdAt = log.getCreatedAt();

        if (log.getPassword() != null) {
            this.passwordId = log.getPassword().getId();
        }
    }

    public Integer getId() {
        return id;
    }

    public LogType getType() {
        return type;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getMetadata() {
        return metadata;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getPasswordId() {
        return passwordId;
    }

    public static List<LogDto> convert(List<Log> logs) {
        return logs.stream().map(LogDto::new).collect(Collectors.toList());
    }
}
