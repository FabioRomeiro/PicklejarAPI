package com.romeiro.picklejar.log;

import com.romeiro.picklejar.model.Log;
import com.romeiro.picklejar.model.LogType;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.LogRepository;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;
import com.romeiro.picklejar.utils.PopulateDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
@Rollback
public class LogTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private LogRepository logRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setPasswordRepository(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Test
    public void registerLogTest() {
        PopulateDatabase.createUsers(userRepository);
        PopulateDatabase.createPasswords(passwordRepository, userRepository);
        User arthur = userRepository.findByEmail("arthur_do_oeste02@gmail.com").get();
        Password password = passwordRepository.getOne(1);

        Log log = new Log();
        log.setType(LogType.PASSWORD_COPY);
        log.setPassword(password);
        log.setUser(arthur);
        log.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
        log.setMetadata("{}");

        logRepository.save(log);

        assertNotEquals(log.getId(), null);
    }
}
