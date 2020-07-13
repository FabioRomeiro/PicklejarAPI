package com.romeiro.picklejar.service;

import com.romeiro.picklejar.controller.form.UserForm;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.RoleRepository;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public User findById(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public User createUser(UserForm form) {
        User user = form.convert(roleRepository);
        userRepository.save(user);
        return user;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public User updateUser(Integer userId, UserForm form) {
        return form.update(userId, userRepository);
    }
}
