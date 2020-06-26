package com.romeiro.picklejar.controller;

import com.romeiro.picklejar.config.security.TokenService;
import com.romeiro.picklejar.controller.dto.LogDto;
import com.romeiro.picklejar.controller.dto.PasswordDto;
import com.romeiro.picklejar.controller.form.LogForm;
import com.romeiro.picklejar.model.Log;
import com.romeiro.picklejar.model.LogType;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.repository.LogRepository;
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
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public List<LogDto> getLogs(
            @RequestParam(required = false) LogType logType,
            @RequestParam(required = false) Integer passwordId,
            @RequestHeader("Authorization") String token) {

        Integer userId = tokenService.getUserId(tokenService.getFormattedToken(token));
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

        return LogDto.convert(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogDto> getLogById(@PathVariable("id") Integer id) {
        Optional<Log> log = logRepository.findById(id);

        if (log.isPresent()) {
            return ResponseEntity.ok(new LogDto(log.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<LogDto> createLog(
            @RequestBody LogForm logForm,
            @RequestHeader("Authorization") String token,
            @RequestHeader("User-Agent") String userAgent,
            UriComponentsBuilder uriBuilder) {

        Integer userId = tokenService.getUserId(tokenService.getFormattedToken(token));
        Log log = logForm.convert(userAgent, userRepository, userId, passwordRepository);

        Password password = log.getPassword();

        if (password != null) {
            password.setLastAccess(log.getCreatedAt());
            passwordRepository.save(password);
        }

        logRepository.save(log);

        URI uri = uriBuilder.path("/logs/{id}").buildAndExpand(log.getId()).toUri();
        return ResponseEntity.created(uri).body(new LogDto(log));
    }
}
