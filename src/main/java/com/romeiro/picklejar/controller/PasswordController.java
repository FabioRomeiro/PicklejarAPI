package com.romeiro.picklejar.controller;

import com.romeiro.picklejar.controller.dto.PasswordDto;
import com.romeiro.picklejar.controller.form.PasswordForm;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<PasswordDto> getPasswords(String text) {
        List<Password> passwords = null;

        if (text == null) {
            passwords = passwordRepository.findByUserId(1);
        }
        else {
            passwords = passwordRepository.findByAnyText(text);
        }

        return PasswordDto.convert(passwords);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PasswordDto> registerPassword(@RequestBody PasswordForm passwordForm, UriComponentsBuilder uriBuilder) {
        Password password = passwordForm.convert(userRepository);
        passwordRepository.save(password);

        URI uri = uriBuilder.path("/passwords/{id}").buildAndExpand(password.getId()).toUri();
        return ResponseEntity.created(uri).body(new PasswordDto(password));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordDto> getPasswordById(@PathVariable("id") Integer id) {
        Optional<Password> password = passwordRepository.findById(id);

        if (password.isPresent()) {
            return ResponseEntity.ok(new PasswordDto(password.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PasswordDto> updatePassword(@PathVariable("id") Integer id, @RequestBody PasswordForm form) {
        Optional<Password> optional = passwordRepository.findById(id);

        if (optional.isPresent()) {
            Password password = form.update(id, passwordRepository);
            return ResponseEntity.ok(new PasswordDto(password));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePassword(@PathVariable("id") Integer id) {
        Optional<Password> password = passwordRepository.findById(id);

        if (password.isPresent()) {
            passwordRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
