package com.romeiro.picklejar.controller;

import com.romeiro.picklejar.config.security.TokenService;
import com.romeiro.picklejar.controller.dto.PasswordDto;
import com.romeiro.picklejar.controller.form.PasswordForm;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;
import com.romeiro.picklejar.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public Page<PasswordDto> getPasswords(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) boolean favorite,
            @RequestParam(required = false, defaultValue = "false") boolean inactiveIncluded,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable,
            @RequestHeader("Authorization") String token) {

        Integer userId = tokenService.getUserId(tokenService.getFormattedToken(token));
        Page<Password> passwords = passwordService.findByUser(userId, text, inactiveIncluded, pageable);
        return PasswordDto.convert(passwords);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PasswordDto> registerPassword(@RequestBody PasswordForm passwordForm, @RequestHeader("Authorization") String token, UriComponentsBuilder uriBuilder) {
        Integer userId = tokenService.getUserId(tokenService.getFormattedToken(token));
        Password password = passwordService.createPassword(passwordForm, userId);
        URI uri = uriBuilder.path("/passwords/{id}").buildAndExpand(password.getId()).toUri();
        return ResponseEntity.created(uri).body(new PasswordDto(password));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordDto> getPasswordById(@PathVariable("id") Integer id) {
        Password password = passwordService.findById(id);

        if (password != null) {
            return ResponseEntity.ok(new PasswordDto(password));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PasswordDto> updatePassword(@PathVariable("id") Integer id, @RequestBody PasswordForm form) {
        Password password = passwordService.updatePassword(id, form);

        if (password != null) {
            return ResponseEntity.ok(new PasswordDto(password));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePassword(@PathVariable("id") Integer id) {
        Boolean deleted = passwordService.deletePassword(id);

        if (deleted) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/secret-key")
    @CacheEvict(value = "password-secret-key", allEntries = true)
    public ResponseEntity<String> getPasswordSecretKey(@PathVariable("id") Integer id) {
        String secretKey = passwordService.getSecretKey(id);

        if (secretKey != null) {
            return ResponseEntity.ok(secretKey);
        }

        return ResponseEntity.notFound().build();
    }
}
