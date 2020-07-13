package com.romeiro.picklejar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.romeiro.picklejar.config.security.TokenService;
import com.romeiro.picklejar.controller.dto.AuthDto;
import com.romeiro.picklejar.controller.dto.UserDto;
import com.romeiro.picklejar.controller.form.LoginForm;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<AuthDto> authenticate(@RequestBody LoginForm form) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken loginData = form.convert();

        try {
            Authentication authentication = authManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            User user = userRepository.findById(tokenService.getUserId(token)).get();

            return ResponseEntity.ok(new AuthDto(token, "Bearer", new UserDto(user)));
        }
        catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
