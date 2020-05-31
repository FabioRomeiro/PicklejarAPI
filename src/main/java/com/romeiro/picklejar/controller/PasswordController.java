package com.romeiro.picklejar.controller;

import com.romeiro.picklejar.controller.dto.PasswordDto;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.Status;
import com.romeiro.picklejar.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class PasswordController {

    @Autowired
    private PasswordRepository passwordRepository;

    @RequestMapping("/passwords")
    public List<PasswordDto> getPasswords() {
        List<Password> passwords = passwordRepository.findAll();
        return PasswordDto.convert(passwords);
    }
}
