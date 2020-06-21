package com.romeiro.picklejar.controller.form;

import com.romeiro.picklejar.model.Log;
import com.romeiro.picklejar.model.LogType;
import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.LogRepository;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

public class LogForm {

    private LogType type;
    private String metadata;
    private Integer passwordId;

    public void setPasswordId(Integer passwordId) {
        this.passwordId = passwordId;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Log convert(
            String userAgent,
            UserRepository userRepository,
            Integer userId,
            PasswordRepository passwordRepository) {

        User user = userRepository.findById(userId).get();

        Password password = null;

        if (this.passwordId != null) {
            Optional<Password> optionalPassword = passwordRepository.findById(this.passwordId);

            if (optionalPassword.isPresent()) {
                password = optionalPassword.get();
            }
        }

        return new Log(this.type, userAgent, this.metadata, user, password);
    }
}
