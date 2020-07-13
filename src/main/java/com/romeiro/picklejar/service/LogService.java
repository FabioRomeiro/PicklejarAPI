package com.romeiro.picklejar.service;

import com.romeiro.picklejar.controller.dto.LogDto;
import com.romeiro.picklejar.controller.form.LogForm;
import com.romeiro.picklejar.controller.form.UserForm;
import com.romeiro.picklejar.model.Log;
import com.romeiro.picklejar.model.LogType;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.LogRepository;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.RoleRepository;
import com.romeiro.picklejar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Log> findByUser(Integer userId, LogType logType, Integer passwordId) {
        List<Log> logs = null;

        if (logType != null && passwordId != null) {
            logs = logRepository.findByTypeAndPasswordId(userId, logType, passwordId);
        }
        else if (logType != null) {
            logs = logRepository.findByType(userId, logType);
        }
        else {
            logs = logRepository.findByUserId(userId);
        }

        return logs;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Log findById(Integer id) {
        Optional<Log> log = logRepository.findById(id);

        if (log.isPresent()) {
            return log.get();
        }

        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Log createLog(LogForm logForm, String userAgent, Integer userId) {
        Log log = logForm.convert(userAgent, userRepository, userId, passwordRepository);

        Password password = log.getPassword();

        if (password != null) {
            password.setLastAccess(log.getCreatedAt());
            passwordRepository.save(password);
        }

        logRepository.save(log);

        return log;
    }
}
