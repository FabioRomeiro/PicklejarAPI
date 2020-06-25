package com.romeiro.picklejar.user;

import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void createUsers() {
        User arthur = new User("Arthur Morgan", "arthur_do_oeste02@gmail.com", "lenneeeeeey", "https://i.imgur.com/Buo2Uvp.jpg");
        User naruto = new User("Naruto Uzumaki", "narutocerto@gmail.com", "sakuras2", "https://pm1.narvii.com/6518/9384b928ce4de44ba384f671a9cc4abca689d65c_hq.jpg");
        userRepository.save(arthur);
        userRepository.save(naruto);
    }

    @Test
    public void registerTest() {
        User user = new User();
        user.setName("Edward Elric");
        user.setEmail("edward.bruxao@gmail.com");
        user.setPassword("vomataohomunculus");
        user.setPicture("https://memegenerator.net/img/images/14824175.jpg");
        userRepository.save(user);
        assertNotEquals(user.getId(), null);
    }

    @Test
    public void authenticateTest() {
        createUsers();
        Optional<User> arthur = userRepository.findByEmail("arthur_do_oeste02@gmail.com");
        Optional<User> hanSolo = userRepository.findByEmail("eu_atiro_primeiro@gmail.com");
        assertTrue(arthur.isPresent());
        assertFalse(hanSolo.isPresent());
    }
}
