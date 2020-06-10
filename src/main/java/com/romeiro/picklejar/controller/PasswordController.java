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
    public PasswordDto getPasswordById(@PathVariable("id") Integer id) {
        Password password = passwordRepository.getOne(id);
        return new PasswordDto(password);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PasswordDto> updatePassword(@PathVariable("id") Integer id, @RequestBody PasswordForm form) {
        Password password = form.update(id, passwordRepository);
        return ResponseEntity.ok(new PasswordDto(password));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePassword(@PathVariable("id") Integer id) {
        passwordRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
