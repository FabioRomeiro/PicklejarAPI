package com.romeiro.picklejar.password;

import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.utils.PopulateDatabase;
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
public class PasswordTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setPasswordRepository(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Test
    public void registerPasswordTest() {
        PopulateDatabase.createUsers(userRepository);
        User narutu = userRepository.findByEmail("narutocerto@gmail.com").get();

        Password password = new Password();
        password.setName("Aldeia da Folha");
        password.setUsername("narodos");
        password.setPassword("dattebayo");
        password.setUser(narutu);

        passwordRepository.save(password);

        assertNotEquals(password.getId(), null);
    }
}
