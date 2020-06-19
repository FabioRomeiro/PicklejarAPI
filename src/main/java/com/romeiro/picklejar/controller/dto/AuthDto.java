package com.romeiro.picklejar.controller.dto;

public class AuthDto {

    private String token;
    private String tokenType;
    private UserDto user;

    public AuthDto(String token, String type, UserDto user) {
        this.token = token;
        this.tokenType = type;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public UserDto getUser() {
        return user;
    }
}
