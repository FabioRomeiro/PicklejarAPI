package com.romeiro.picklejar.controller;

import com.romeiro.picklejar.config.security.TokenFilterAuthentication;
import com.romeiro.picklejar.config.security.TokenService;
import com.romeiro.picklejar.controller.dto.PasswordDto;
import com.romeiro.picklejar.controller.dto.UserDto;
import com.romeiro.picklejar.controller.form.UserForm;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    @Cacheable(value = "user")
    public UserDto getUserInfo(@RequestHeader("Authorization") String token) {
        Integer userId = tokenService.getUserId(tokenService.getFormattedToken(token));
        User user = userRepository.findById(userId).get();

        return new UserDto(user);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<UserDto> createUser(@RequestBody UserForm form, UriComponentsBuilder uriBuilder) {
        User user = form.convert();

        URI uri = uriBuilder.path("/user").build().toUri();
        return ResponseEntity.created(uri).body(new UserDto(user));
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserForm form, @RequestHeader("Authentication") String token) {
        Integer userId = tokenService.getUserId(token);
        User user = form.update(userId, userRepository);

        return ResponseEntity.ok(new UserDto(user));
    }
}
