package com.romeiro.picklejar.utils;

import com.romeiro.picklejar.model.Password;
import com.romeiro.picklejar.model.User;
import com.romeiro.picklejar.repository.PasswordRepository;
import com.romeiro.picklejar.repository.UserRepository;

public class PopulateDatabase {

    public static void createUsers(UserRepository userRepository) {
        User arthur = new User("Arthur Morgan", "arthur_do_oeste02@gmail.com", "lenneeeeeey", "https://i.imgur.com/Buo2Uvp.jpg");
        User naruto = new User("Naruto Uzumaki", "narutocerto@gmail.com", "sakuras2", "https://pm1.narvii.com/6518/9384b928ce4de44ba384f671a9cc4abca689d65c_hq.jpg");
        userRepository.save(arthur);
        userRepository.save(naruto);
    }

    public static void createPasswords(PasswordRepository passwordRepository, UserRepository userRepository) {
        User user = userRepository.findByEmail("arthur_do_oeste02@gmail.com").get();
        Password saintDenisJournal = new Password("Saint Denis Journal", "Tacitus Kilgore", "thisisthelastjob", "", true, "", user);
        Password rhodesJournal = new Password("Rhodes Journal", "Leviticus Cornwall", "howdypartner", "", false, "", user);
        passwordRepository.save(saintDenisJournal);
        passwordRepository.save(rhodesJournal);
    }
}
