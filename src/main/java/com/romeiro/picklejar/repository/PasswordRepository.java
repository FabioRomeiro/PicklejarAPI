package com.romeiro.picklejar.repository;

import com.romeiro.picklejar.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

}
