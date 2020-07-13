package com.romeiro.picklejar.service;

import com.romeiro.picklejar.controller.dto.PasswordDto;
import com.romeiro.picklejar.controller.form.PasswordForm;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    public Page<Password> findByUser(Integer userId, String text, Boolean inactiveIncluded, Pageable pageable) {
        Page<Password> passwords = null;

        if (text == null) {
            if (inactiveIncluded)
                passwords = passwordRepository.findByUserIdIncludingInactives(userId, pageable);
            else
                passwords = passwordRepository.findByUserId(userId, pageable);
        }
        else {
            if (inactiveIncluded)
                passwords = passwordRepository.findByAnyTextIncludingInactives(userId, text, pageable);
            else
                passwords = passwordRepository.findByAnyText(userId, text, pageable);
        }

        return passwords;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Password createPassword(PasswordForm passwordForm, Integer userId) {
        Password password = passwordForm.convert(userRepository, userId);
        passwordRepository.save(password);
        return password;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Password findById(Integer id) {
        Optional<Password> password = passwordRepository.findById(id);

        if (password.isEmpty())
            return null;

        return password.get();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Password updatePassword(Integer id, PasswordForm form) {
        Optional<Password> optional = passwordRepository.findById(id);

        if (optional.isPresent()) {
            return form.update(id, passwordRepository);
        }

        return null;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Boolean deletePassword(Integer id) {
        Optional<Password> optionalPassword = passwordRepository.findById(id);

        if (optionalPassword.isPresent()) {
            Password password = optionalPassword.get();
            password.setActive(false);
            passwordRepository.save(password);
            return true;
        }

        return false;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public String getSecretKey(Integer id) {
        Optional<Password> password = passwordRepository.findById(id);

        if (password.isPresent()) {
            return password.get().getPassword();
        }

        return null;
    }
}
